package com.mentorship.user_service.middlewares;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import com.mentorship.user_service.services.JwtService;
import jakarta.servlet.ServletException;
import lombok.NonNull;
import org.springframework.security.core.userdetails.UserDetailsService;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter{
    private final HandlerExceptionResolver handlerExceptionResolver;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    
    //Bu classta her request geldiginde autharization header'ini kontrol edip, eger token varsa onu validate edip, kullaniciyi authenticate edecegiz.
    //bu class springin ilk calisan kismidir. dolayisiyla handlerexceitonresolver burada kullanilir. eger header info bilgileri bossa veya valide degilse 500 kodlu hatayi firlatir.
    public JWTAuthenticationFilter(HandlerExceptionResolver handlerExceptionResolver, JwtService jwtService, UserDetailsService userDetailsService) {
        this.handlerExceptionResolver = handlerExceptionResolver;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {

                final String authString = request.getHeader("Authorization");
                if (authString == null || !authString.startsWith("Bearer ")) {
                    filterChain.doFilter(request, response);
                    handlerExceptionResolver.resolveException(request, response, null,
                            new RuntimeException("Authorization header is missing or invalid"));
                    return;
                }
                try {
                    String jwt = authString.substring(7);
                    String userEmail = jwtService.extractUsername(jwt);
                    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                    
                    if(userEmail != null && authentication == null) {
                        UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
                        if(jwtService.isTokenValid(jwt, userDetails)) {
                            //kullaniciyi authenticate et
                            //burada authentication objesi olusturup securitycontext'e set etmemiz gerekiyor.
                            //bunu yaparken userin rollerini de set etmemiz gerekiyor.
                            //bunu yaparken userdetailsservice kullanabiliriz.
                            //ama simdilik bu kisimda sadece tokenin valid oldugunu kontrol edecegiz.
                            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                            );
                            authToken.setDetails(new org.springframework.security.web.authentication.WebAuthenticationDetailsSource().buildDetails(request));
                            SecurityContextHolder.getContext().setAuthentication(authToken);
                        };
                        filterChain.doFilter(request, response);
                        

                    }
                } catch (Exception e) {
                    handlerExceptionResolver.resolveException(request, response, null, e);
                }
    }

}
