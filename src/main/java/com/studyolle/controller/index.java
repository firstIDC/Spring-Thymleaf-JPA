package com.studyolle.controller;

import com.studyolle.account.SignUpForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class index {

    @GetMapping("/testView")
    public String testVIew(Model model) {


        model.addAttribute("signUpForm", new SignUpForm());
        return "testView";
    }

}
