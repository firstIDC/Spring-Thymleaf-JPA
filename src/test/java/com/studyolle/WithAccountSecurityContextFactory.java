package com.studyolle;

import com.studyolle.account.AccountService;
import com.studyolle.account.SignUpForm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

@RequiredArgsConstructor
public class WithAccountSecurityContextFactory implements WithSecurityContextFactory<WithAccount> {

    private final AccountService accountService;

    @Override
    public SecurityContext createSecurityContext(WithAccount withAccount) {
        String nickName = withAccount.value();

        //유저를 생성중 (스프링 세큐리티 userDetails에서 사용하기 위하여)
        SignUpForm signUpForm = new SignUpForm();
        signUpForm.setNickname(nickName);
        signUpForm.setEmail(nickName+ "@email.com");
        signUpForm.setPassword("12345678");
        accountService.processNewAccount(signUpForm);

        //DB에서 유저를 취득함.
        UserDetails principal = accountService.loadUserByUsername(nickName);

        //취득한 유저를 스프링세큐리티 인증을 한 후, 세큐리티Context에 담아 돌려줌.
        Authentication authentication = new UsernamePasswordAuthenticationToken(principal, principal.getPassword(), principal.getAuthorities());
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);

        return context;
    }
}
