package com.majiang.frank.community.controller;

import com.majiang.frank.community.dto.AccessTokenDto;
import com.majiang.frank.community.dto.GithubUerDto;
import com.majiang.frank.community.util.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class OAuthController {
    @Autowired
    private GithubProvider githubProvider;
    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state
    ) {
        AccessTokenDto accessTokenDto = new AccessTokenDto();
        accessTokenDto.setCode(code);
        accessTokenDto.setState(state);
        accessTokenDto.setClient_id("Iv1.63cf4b5a97d23b04");
        accessTokenDto.setRedirect_uri("http://localhost:8887/callback");
        accessTokenDto.setClient_secret("531dfbac55b125c7adb8a8a8ed92b79bc301d1f6");

        String access_token = githubProvider.getAccessToken(accessTokenDto);
        GithubUerDto githubUerDto = githubProvider.getUser(access_token);
        System.out.println(githubUerDto.getName());



        return "index";
    }

}
