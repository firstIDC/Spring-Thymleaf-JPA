package com.studyolle.setting;

import com.studyolle.WithAccount;
import com.studyolle.account.AccountRepository;
import com.studyolle.domain.Account;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class SettingControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    AccountRepository accountRepository;

    //각 테스트들이 끝난 후에 실행됨.
    @AfterEach
    void afterEach() {
        accountRepository.deleteAll();
    }


    // keesun이라는 유저를 만드는 커스텀어노테이션 이게 없으면,
    // 스프링세큐리티가 인증되지 않은 유저이기때문에, 로그인 페이지로 보내버림
    @WithAccount("keesun")
    @DisplayName("프로필 수정 폼")
    @Test
    void updateProfileForm() throws Exception {
        mockMvc.perform(get(SettingController.URL))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("account"))
                .andExpect(model().attributeExists("profile"));
    }

    // keesun이라는 유저를 만드는 커스텀어노테이션 이게 없으면,
    // 스프링세큐리티가 인증되지 않은 유저이기때문에, 로그인 페이지로 보내버림
    @WithAccount("keesun")
    @DisplayName("프로필 수정하기 -입력값 정상")
    @Test
    void updateProfile() throws Exception {
        String bio = "짧은 소개를 수정하는 경우.";

        mockMvc.perform(post(SettingController.URL)
                        .param("bio", bio)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(SettingController.URL))
                //flash().attributeExists란 attribute안에 키가 존재하는지 확인함.
                .andExpect(flash().attributeExists("message"))
                //키와 값을 같이 하고 확인하고 싶으면 아래와 같이 하면 됨
                .andExpect(flash().attribute("message", "프로필을 수정하였습니다."));

        Account keesun = accountRepository.findByNickName("keesun");
        assertEquals(bio, keesun.getBio());
    }

    // keesun이라는 유저를 만드는 커스텀어노테이션 이게 없으면,
    // 스프링세큐리티가 인증되지 않은 유저이기때문에, 로그인 페이지로 보내버림
    @WithAccount("keesun")
    @DisplayName("프로필 수정하기 - 입력값 에러")
    @Test
    void updateProfile_error() throws Exception {
        String bio = "길게 소개를 수정하는 경우. 길게 소개를 수정하는 경우. 길게 소개를 수정하는 경우. 너무나도 길게 소개를 수정하는 경우. ";
        mockMvc.perform(post(SettingController.URL)
                        .param("bio", bio)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name(SettingController.VIEW_NAME))
                .andExpect(model().attributeExists("account"))
                .andExpect(model().attributeExists("profile"))
                .andExpect(model().hasErrors());

        Account keesun = accountRepository.findByNickName("keesun");
        assertNull(keesun.getBio());
    }


}