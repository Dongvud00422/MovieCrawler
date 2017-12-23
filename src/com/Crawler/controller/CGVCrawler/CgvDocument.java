package com.Crawler.controller.CGVCrawler;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class CgvDocument {

    public String getDocument(String target) throws Exception {

        String postBody = "TS015ef8cd_id=3&TS015ef8cd_cr=783cd6cbd7faa22bcbf2f90aedd6fd99%3Axzxz%3A85CbHbVV%3A168162141&TS015ef8cd_76=0&TS015ef8cd_md=1&TS015ef8cd_rf=0&TS015ef8cd_ct=0&TS015ef8cd_pd=0";
        URL obj = new URL(target);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.12; rv:59.0) Gecko/20100101 Firefox/59.0");
        con.setRequestProperty("Accept-Language", "UTF-8");
        con.setRequestProperty("Host","www.cgv.vn");
        con.setRequestProperty("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        con.setRequestProperty("Referer","https://www.cgv.vn/default/cinox/site/");
        con.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
        con.setRequestProperty("Content-Length","173");
        con.setRequestProperty("Cookie","TS015ef8cd=018ea3cddaf62f90113681d4467ef5559ff36a5ffb857a3d0b73f6e25c6eb428cd8e6c887e; TS01faf9b1=018ea3cddabc87693c4d076756ac3d4d5b1a3f0d3731befb541bb919914178758ebf61f4d04d3ae42c85a1aa36359db7174798b8184ef62454c10621a86bacfba9ba7ff0b2; TS015ef8cd_77=9304_c38604ae62a67e90_rsb_0_rs_https%3A%2F%2Fwww.cgv.vn%2Fdefault%2Fcinox%2Fsite%2F_rs_1_rs_0; frontend=el5bbnba1a9bpal0gbo8r461k7; frontend_cid=g0gWs0wW8IuPqDdr");
        con.setRequestProperty("DNT","1");
        con.setRequestProperty("Connection","keep-alive");
        con.setRequestProperty("Upgrade-Insecure-Requests","1");
        con.setRequestProperty("Cache-Control","max-age=0, no-cache");
        con.setRequestProperty("Pragma","no-cache");

        con.setDoOutput(true);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(con.getOutputStream());
        outputStreamWriter.write(postBody);
        outputStreamWriter.flush();

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }



}
