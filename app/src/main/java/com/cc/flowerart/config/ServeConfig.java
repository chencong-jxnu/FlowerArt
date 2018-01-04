package com.cc.flowerart.config;

/**
 * Created by Administrator on 2017/8/5.
 */

public class ServeConfig {
//    private String adress = "118.89.141.172";
    private String adress = "10.100.17.255";//localhost
    //    private String adress = "localhost";
    private String servlet;
    private String method;

    public ServeConfig(String servlet, String method) {
        this.servlet = servlet;
        this.method = method;
    }

    public String getMethod() {
        return method;
    }

    public String getServlet() {
        return servlet;
    }

    public void setServlet(String servlet) {
        this.servlet = servlet;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getURL() {
        return "http://" + adress + ":8080/FlowerArtServe/servlet/" + servlet + "?" + "method=" + method + "&message=";
    }
}