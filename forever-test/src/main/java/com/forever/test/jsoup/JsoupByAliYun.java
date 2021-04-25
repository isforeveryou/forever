package com.forever.test.jsoup;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import com.gargoylesoftware.htmlunit.util.Cookie;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Set;

/**
 * @author WJX
 * @date 2020/7/31 9:31
 */
public class JsoupByAliYun {

    public static void main(String[] args) throws IOException {
        test();
    }



    @SuppressWarnings("unchecked")
    private static void test() throws IOException {

        WebClient webClient = new WebClient(BrowserVersion.CHROME);

        webClient.getOptions().setUseInsecureSSL(true);

        // 不启用CSS
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setActiveXNative(false);
        // 启用JS
        webClient.getOptions().setJavaScriptEnabled(false);
        // JS执行出错不抛异常
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        // httpCode！=200不抛异常
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        // 支持Ajax
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());

//        webClient.setJavaScriptTimeout(3000);


        Set<Cookie> cookies = webClient.getCookies(UrlUtils.getLoginSuccessUrl("sf_jiangsw", "Ab25115582#"));

        for (Cookie cookie : cookies) {
            webClient.getCookieManager().addCookie(cookie);
        }


        HtmlPage page = webClient.getPage("http://sofastack.ant.dev.cloud.scrcu.com/index#/portal/overview?projectName=scrcu&workspaceGroupName=sit2core");

        Document document = Jsoup.parse(page.asXml());

    }


}
