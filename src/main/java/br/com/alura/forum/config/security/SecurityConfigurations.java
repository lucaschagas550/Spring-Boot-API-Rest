package br.com.alura.forum.config.security;

import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

import java.sql.SQLOutput;

@EnableWebSecurity
@Configuration
public class SecurityConfigurations extends WebSecurityConfigurerAdapter {

    @Autowired
    private AutenticacaoService autenticacaoService;

    //Configuracoes de autenticacao
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(autenticacaoService).passwordEncoder(new Pbkdf2PasswordEncoder());
    }

    //Configuração de autorização
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET,"/topicos").permitAll()
                .antMatchers(HttpMethod.GET,"/topicos/*").permitAll()
                .anyRequest().authenticated()  // qualquer outro request precisa esta autenticado
                .and().formLogin();

    }

    //Configuração de recursos estaticos - js, css, imagens, etc..
    @Override
    public void configure(WebSecurity web) throws Exception {

    }

  /*  public static void main(String[] args){
        System.out.println(new Pbkdf2PasswordEncoder().encode("123"));
    }*/


}
