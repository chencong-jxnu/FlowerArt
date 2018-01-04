package com.cc.flowerart.config;

/**
 * Created by Administrator on 2017/8/26.
 */

public class HtmlConfig {
    private String contentUrl;
    private String html;

    public HtmlConfig(String contentUrl) {
        this.contentUrl = contentUrl;
    }

    public String getHtml() {
        html  = "<html>"
                + "<body>"
                + "<img width=100% src= '" + contentUrl + "' />"
                + "</body>" + "</html>";
        return html;
    }
}
