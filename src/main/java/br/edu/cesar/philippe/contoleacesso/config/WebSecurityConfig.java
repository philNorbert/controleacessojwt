package br.edu.cesar.philippe.contoleacesso.config;

import br.edu.cesar.philippe.contoleacesso.security.JwtAuthEntryPoint;
import br.edu.cesar.philippe.contoleacesso.security.JwtTokenUtil;
import br.edu.cesar.philippe.contoleacesso.security.filter.JwtAuthTokenFilter;
import br.org.cesar.vetanalysis.api.v1.resource.ApiVersion;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private JwtAuthEntryPoint authEntryPoint;
    private UserDetailsService userDetailsService;
    private JwtTokenUtil jwtTokenUtil;
    
    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(this.userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthTokenFilter authTokenFilterBean() throws Exception {
        return new JwtAuthTokenFilter(userDetailsService, jwtTokenUtil);
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        final String apiPath = ApiVersion.V1 + "%s";
        
        httpSecurity
                .cors().and()
                // Não precisamos de tratamento para CSRF porque o token é invulnerável
                .csrf().disable()

                .exceptionHandling().authenticationEntryPoint(authEntryPoint).and()

                // API's REST por definição são STATLESS
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

                .authorizeRequests()

                // Permitir requests para resources sem estar logado
                .antMatchers(String.format(apiPath,"/auth")).permitAll()
                .antMatchers(String.format(apiPath,"/signup")).permitAll()

                .anyRequest().authenticated();

        // JWT Filter implementado
        httpSecurity
                .addFilterBefore(authTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);

        // Desabilitando o cache de página
        httpSecurity.headers().cacheControl();
    }

}
