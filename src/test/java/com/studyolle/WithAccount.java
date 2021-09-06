package com.studyolle;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
// SpringSecurity에서 지원하는, 테스트 어노테이션
@WithSecurityContext(factory = WithAccountSecurityContextFactory.class)
public @interface WithAccount {

    //WithAccountSecurityContextFactory에서 사용가능함.
    String value();
}
