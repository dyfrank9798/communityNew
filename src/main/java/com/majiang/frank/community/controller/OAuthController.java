package com.majiang.frank.community.controller;

import com.majiang.frank.community.dto.AccessTokenDto;
import com.majiang.frank.community.dto.GithubUerDto;
import com.majiang.frank.community.util.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class OAuthController {
    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.redirect.uri}")
    private String redirectUri;

    @Value("${github.client.secret}")
    private String clientSecret;


    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state
    ) {
        AccessTokenDto accessTokenDto = new AccessTokenDto();
        accessTokenDto.setCode(code);
        accessTokenDto.setState(state);
        accessTokenDto.setClient_id(clientId);
        accessTokenDto.setRedirect_uri(redirectUri);
        accessTokenDto.setClient_secret(clientSecret);

        String access_token = githubProvider.getAccessToken(accessTokenDto);
        GithubUerDto githubUerDto = githubProvider.getUser(access_token);
        System.out.println(githubUerDto.getName());



        return "index";
    }

}
