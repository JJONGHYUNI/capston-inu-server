package capston.server.config;

import capston.server.exception.CustomAccessDeniedHandler;
import capston.server.exception.CustomAuthenticationEntryPoint;
import capston.server.oauth2.jwt.filter.JwtAuthenticationFilter;
import capston.server.oauth2.jwt.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final JwtService jwtService;


    //필터를 거치지 않을 url 설정 (토큰 없이 볼 수 있는 url)
    @Override
    public void configure(WebSecurity web) throws Exception {
        String[] staticResources = {
                "/assets/**",
                "/css/**",
                "/ico/**",
                "/images/**",
                "/js/**",
                "/plugins/**",
                "/favicon.ico",
                "/member/existsnickname",
                "/api/v3/**",
                "/swagger-ui.html",
                "/swagger/**",
                "/swagger-resources/**",
                "/webjar/**",
                "/v3/api-docs",
                "/swagger-ui/**",
                "/trip/**"
        };
        web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                .mvcMatchers("/","login/**")
                .mvcMatchers(staticResources);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .formLogin().disable() // FormLogin 사용 X
                .httpBasic().disable() // httpBasic 사용 X
                .csrf().disable() // csrf 보안 사용 X
                .cors()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .anyRequest().authenticated() // 위의 경로 이외에는 모두 인증된 사용자만 접근 가능
                .and()
                .exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler())
                .and()
                .exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint());

        http.addFilterBefore(new JwtAuthenticationFilter(jwtService), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }


}
