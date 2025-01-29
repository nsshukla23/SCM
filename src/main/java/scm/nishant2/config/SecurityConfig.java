package scm.nishant2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import scm.nishant2.services.SecurityCoustomUserDetailService;

@Configuration
public class SecurityConfig {

  @Autowired
  private SecurityCoustomUserDetailService userDetailService;

  @Autowired
  private OAuthAuthenicationSuccessHandler handler;

  // @Bean
  // public UserDetailsService userDetailsService() {

  // UserDetails user1 = User.withDefaultPasswordEncoder().
  // username("admin123")
  // .password("admin123")
  // .roles("ADMIN" ,"USER")
  // .build();

  // UserDetails user2 = User.withUsername("user").password("user").build();
  // var inMemoryUserDetailsManager = new InMemoryUserDetailsManager(user1 );
  // return inMemoryUserDetailsManager;
  // }

  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
    // user detail service ka object:
    daoAuthenticationProvider.setUserDetailsService(userDetailService);
    // password encoder ka object
    daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
    return daoAuthenticationProvider;
  }

  // securityfilterChain use hota h ki kis page ko configer kerna h ki nhi
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
        .authorizeHttpRequests(authorize -> {
          authorize.requestMatchers("/user/**").authenticated()
              .anyRequest().permitAll();
        });

    httpSecurity.formLogin(form -> {
      form.loginPage("/login");

      // iska matlb h ki login page k baad form jo submit hoga wo is url per hoga
      form.loginProcessingUrl("/authenticate");

      // login hone k baad page is per redirect ho jayega
      form.successForwardUrl("/user/profile");
      // form.failureForwardUrl("/login?error=true");

      // ye username k palceholder per Email likh dega
      form.usernameParameter("email");

      // ye password k palceholder m password likh dega

      form.passwordParameter("password");

      // form.failureHandler(new AuthenticationFailureHandler() {

      // @Override
      // public void onAuthenticationFailure(HttpServletRequest request,
      // HttpServletResponse response,
      // AuthenticationException exception) throws IOException, ServletException {
      // // TODO Auto-generated method stub
      // throw new UnsupportedOperationException("Unimplemented method");
      // }

      // });

      // form.successHandler(new AuthenticationSuccessHandler() {

      // @Override
      // public void onAuthenticationSuccess(HttpServletRequest request,
      // HttpServletResponse response,
      // Authentication authentication) throws IOException, ServletException {
      // // TODO Auto-generated method stub
      // throw new UnsupportedOperationException("Unimplemented method");
      // });

    });

    // LOGOUT KERNE K LIYE YE USE HOGA
    httpSecurity.csrf(AbstractHttpConfigurer::disable);
    httpSecurity.logout(form -> {

      form.logoutUrl("/logout");
    });

    // oauth Configuration
    httpSecurity.oauth2Login(oauth -> {
      oauth.loginPage("/login");
      oauth.successHandler(handler);
    });

    return httpSecurity.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {

    return new BCryptPasswordEncoder();

  }

}