package com.majiang.frank.community.util;


import com.alibaba.fastjson.JSON;
import com.majiang.frank.community.dto.AccessTokenDto;
import com.majiang.frank.community.dto.GithubUerDto;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GithubProvider {
    public String getAccessToken(AccessTokenDto accessTokenDto) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();
        String githubPostUrl = "https://github.com/login/oauth/access_token";
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDto));
        Request request = new Request.Builder()
                .url(githubPostUrl)
                .post(body)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String result = response.body().string();
            String[] items = result.split("&");
            String access_token = null;
            for(int i =0;i<items.length;i++){
                if("access_token".equals(items[i].split("=")[0])){
                    access_token =items[i].split("=")[1];
                }
            }
            return access_token;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }


    public GithubUerDto getUser(String accessToken) {
        OkHttpClient client = new OkHttpClient();
        String url = "https://api.github.com/user?access_token=" + accessToken;
        Request request = new Request.Builder()
                .url(url)
                .build();

        try {
            Response response = client.newCall(request).execute();
            GithubUerDto githubUerDto = JSON.parseObject(response.body().string(), GithubUerDto.class);
            return githubUerDto;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return  null;
    }
}
