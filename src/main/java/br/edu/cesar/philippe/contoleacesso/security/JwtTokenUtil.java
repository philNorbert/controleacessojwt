package br.edu.cesar.philippe.contoleacesso.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Clock;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

@Component
public class JwtTokenUtil {

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private Long expiration;
    private Clock dataAtual;

    public JwtTokenUtil() {
        dataAtual = () -> Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC));
    }

    public String getLoginFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getCriacaoFromToken(String token) {
        return getClaimFromToken(token, Claims::getIssuedAt);
    }

    public Date getExpiracaoFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getTodosClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    public Boolean tokenPodeSerRefreshed(String token, Date ultimoResetSenha) {
        final Date created = getCriacaoFromToken(token);
        return !isCreatedAntesDoResetSenha(created, ultimoResetSenha) && !isTokenExpirado(token);
    }

    public String refreshToken(String token) {
        final Date issuedAt = dataAtual.now();
        final Date dataExpiracao = calcularDataExpiracao(issuedAt);

        final Claims claims = getTodosClaimsFromToken(token);
        claims.setIssuedAt(issuedAt);
        claims.setExpiration(dataExpiracao);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(issuedAt)
                .setExpiration(dataExpiracao)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        JwtUser usuario = (JwtUser) userDetails;
        final String login = getLoginFromToken(token);
        final Date created = getCriacaoFromToken(token);
        return login.equals(usuario.getUsername())
                && !isTokenExpirado(token)
                && !isCreatedAntesDoResetSenha(created, usuario.getLastPasswordResetDate());
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {
        final Date createdDate = dataAtual.now();
        final Date expirationDate = calcularDataExpiracao(createdDate);

        return Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(createdDate)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    private Claims getTodosClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpirado(String token) {
        final Date expiration = getExpiracaoFromToken(token);
        return expiration.before(dataAtual.now());
    }

    private Boolean isCreatedAntesDoResetSenha(Date created, Date ultimoResetSenha) {
        return ultimoResetSenha != null && created.before(ultimoResetSenha);
    }

    private Date calcularDataExpiracao(Date createdDate) {
        return new Date(createdDate.getTime() + expiration * 1000);
    }

}
