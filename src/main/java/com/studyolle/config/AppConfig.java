package com.studyolle.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AppConfig {
    
    //스프링세큐리티 비밀번호 인코딩 설정.
    //PasswordEncoder를 bean으로 한개만 등록 했을때는 스프링세큐리티가 빈에서 자동으로 찾아서, 인코딩을 함.
    //두개 이상일때는, 설정이 필요함
    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
