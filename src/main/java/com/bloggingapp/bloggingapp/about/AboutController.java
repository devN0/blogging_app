package com.bloggingapp.bloggingapp.about;

import com.bloggingapp.bloggingapp.users.dtos.UserResponseDto;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/about")
public class AboutController {
    @GetMapping("")
    public String about() {
        return "About page";
    }
    @GetMapping("/private")
    public String privateAbout(@AuthenticationPrincipal UserResponseDto userResponseDto) {
        return "Private about page, only accessible for logged in users. You're viewing it as " + userResponseDto.getUsername();
    }
}
