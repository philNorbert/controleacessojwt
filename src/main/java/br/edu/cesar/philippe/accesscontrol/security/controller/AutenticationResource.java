package br.edu.cesar.philippe.accesscontrol.security.controller;

import br.edu.cesar.philippe.accesscontrol.ApiVersion;
import br.edu.cesar.philippe.accesscontrol.security.JwtAuthRequest;
import br.edu.cesar.philippe.accesscontrol.security.JwtAuthResponse;
import br.edu.cesar.philippe.accesscontrol.security.JwtTokenUtil;
import br.edu.cesar.philippe.accesscontrol.security.JwtUser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping(value = ApiVersion.V1, produces = MediaType.APPLICATION_JSON_VALUE)
public class AutenticationResource {

    @Value("${jwt.header}")
    private String tokenHeader;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;

    @PostMapping("/auth")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<JwtAuthResponse> createAuthenticationToken(@RequestBody final JwtAuthRequest authRequest)
            throws AuthenticationException {

        //  Startar a segurança (Autenticacao)
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getUsername(),
                        authRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Capturamos os detalhes do usuario do Security e montamos o token
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);

        // Caso as informações forem corretas, retornamos o token montado para o usuário.
        return ResponseEntity.ok(new JwtAuthResponse(token));
    }

    @GetMapping("/auth/refresh")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<JwtAuthResponse> refreshAndGetAuthenticationToken(final HttpServletRequest request) {
        String token = request.getHeader(tokenHeader).substring(7);
        String login = jwtTokenUtil.getUsernameFromToken(token);
        JwtUser usuario = (JwtUser) userDetailsService.loadUserByUsername(login);

        if (jwtTokenUtil.tokenPodeSerRefreshed(token, usuario.getLastPasswordResetDate())) {
            String refreshedToken = jwtTokenUtil.refreshToken(token);
            return ResponseEntity.ok(new JwtAuthResponse(refreshedToken));
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/auth")
    @ResponseStatus(code = HttpStatus.OK)
    public JwtUser buscarToken(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader).substring(7);
        String login = jwtTokenUtil.getUsernameFromToken(token);
        return (JwtUser) userDetailsService.loadUserByUsername(login);
    }

    @RequestMapping(value = "/**", method = RequestMethod.OPTIONS)
    public ResponseEntity handle() {
        return new ResponseEntity(HttpStatus.OK);
    }

}