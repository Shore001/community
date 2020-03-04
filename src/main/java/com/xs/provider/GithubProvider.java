package com.xs.provider;

import com.alibaba.fastjson.JSON;
import com.xs.dto.AccessTokenDTO;
import com.xs.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;


import java.io.IOException;

/*spring组件注解*/
@Component
public class GithubProvider {

    /**
     * 获取access_token
     * @param accessTokenDTO
     * @return
     */
    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            //解析返回数据得到token值
            String[] split = string.split("&");
            response.close();
            return split[0].split("=")[1];
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *获取github用户信息
     */

    public GithubUser getUser(String accessToken) {
        //构建客户端对象*/
        OkHttpClient client = new OkHttpClient();
        //构建请求对象*/
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            //执行请求，得到返回结果
            String string = response.body().string();
            //json转换
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
            response.close();
            return githubUser;
        } catch (IOException e) {
            e.printStackTrace();

            return null;
        }
    }
}
