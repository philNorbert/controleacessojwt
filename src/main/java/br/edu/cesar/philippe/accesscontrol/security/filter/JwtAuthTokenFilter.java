package br.edu.cesar.philippe.accesscontrol.security.filter;

import br.edu.cesar.philippe.accesscontrol.security.JwtTokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@EqualsAndHashCode(callSuper = true)
@RequiredArgsConstructor
@Slf4j
public class JwtAuthTokenFilter extends OncePerRequestFilter {
    
    @Value("${jwt.header}")
    private String tokenHeader;
    private final UserDetailsService userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws
            ServletException, IOException {
        final String requestHeader = request.getHeader(this.tokenHeader);

        String login = null;
        String authToken = null;
        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
            authToken = requestHeader.substring(7);
            try {
                login = jwtTokenUtil.getUsernameFromToken(authToken);
            } catch (IllegalArgumentException e) {
                if (log.isErrorEnabled()) {
                    log.error("Ocorreu um erro durante a conversao de name do usuario do token", e);
                }
            } catch (ExpiredJwtException e) {
                if (log.isWarnEnabled()) {
                    log.warn("O token expirou", e);
                }
            }
        } else {
            if (log.isWarnEnabled()) {
                log.warn("Não foi encontrado o prefixo Bearer na string do header");
            }
        }
    
        if (log.isInfoEnabled()) {
            log.info(String.format("Fazendo a checagem do usuario: %s", login));
        }

        if (login != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = this.userDetailsService.loadUserByUsername(login);

            if (jwtTokenUtil.validateToken(authToken, userDetails)) {
                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(userDetails.getUsername(),
                                userDetails.getPassword(),
                                userDetails.getAuthorities());

                if (requestHeader.startsWith("Bearer ")) {
                    response.setHeader(this.tokenHeader, requestHeader.substring(7));
                }

                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(auth);

                if (log.isInfoEnabled()) {
                    log.info(String.format("Usuário %s autenticado. Setando SecurityContext", login));
                }
            }

        }

        chain.doFilter(request, response);
    }
}