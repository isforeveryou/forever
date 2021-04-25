package com.forever.test.jsoup;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

/**
 * @author WJX
 * @date 2020/7/31 9:23
 */
public class UrlUtils {

    public static void main(String[] args) {
        getCookies();
    }

    private static Map<String, String> cookies;


    public static Map<String, String> getCookies() {
        if (cookies == null) {
            login("sf_jiangsw", "Ab25115582#");
        }
        return cookies;
    }


    static URL getLoginSuccessUrl(String username, String password) {
        return login(username, password);
    }


    private static URL login(String username, String password) {

        String loginUrl = "https://asc.dev.cloud.scrcu.com/login?_input_charset=utf-8";

        Connection connection = Jsoup.connect(loginUrl);


        connection.header("X-Requested-With", "XMLHttpRequest");
        connection.header("Origin", "https://asc.dev.cloud.scrcu.com");
        connection.header("Accept", "application/json, text/javascript");
        connection.header("Content-Type", "application/x-www-form-urlencoded");
        connection.header("Referer", "https://asc.dev.cloud.scrcu.com/login?oauth_callback=https%3A%2F%2Fasc.dev.cloud.scrcu.com%2Fmodule%2Fworkbench%23%2F");
        connection.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.75 Safari/537.36");

        connection.data("username", username);
        connection.data("password", password);

        SslUtils.ignoreSsl();

        try {
            Connection.Response response = connection.ignoreContentType(true).method(Connection.Method.POST).execute();

            cookies = response.cookies();
            return response.url();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }

    }


}
