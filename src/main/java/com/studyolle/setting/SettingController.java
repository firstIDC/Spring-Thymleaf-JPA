package com.studyolle.setting;

import com.studyolle.account.AccountService;
import com.studyolle.account.CurrentUser;
import com.studyolle.domain.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class SettingController {

    public static final String VIEW_NAME = "settings/profile";
    public static final String URL = "/settings/profile";
    public final AccountService accountService;

    @GetMapping(URL)
    public String profileUpdateForm(@CurrentUser Account account, Model model) {

        model.addAttribute("account", account);
        model.addAttribute("profile", new ProfileForm(account));

        return VIEW_NAME;
    }

    @PostMapping(URL)
    public String updateProfile(@CurrentUser Account account, Model model
    , @Valid @ModelAttribute ProfileForm profileForm, Errors errors
    , RedirectAttributes redirectAttributes) {
        if (errors.hasErrors()) {
            model.addAttribute("account", account);
            return VIEW_NAME;
        }

        accountService.updateProfile(account, profileForm);
        redirectAttributes.addFlashAttribute("message", "프로필을 수정하였습니다.");
        return "redirect:"+ URL;
    }
}
