package com.Crawler.test;

import com.Crawler.entity.City;
import com.googlecode.objectify.ObjectifyService;
import com.untility.RestfulHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;

import static com.googlecode.objectify.ObjectifyService.ofy;


public class test extends HttpServlet {
    static {
        ObjectifyService.register(City.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String postBody = "TS015ef8cd_id=3&TS015ef8cd_cr=783cd6cbd7faa22bcbf2f90aedd6fd99%3Axzxz%3A85CbHbVV%3A168162141&TS015ef8cd_76=0&TS015ef8cd_md=1&TS015ef8cd_rf=0&TS015ef8cd_ct=0&TS015ef8cd_pd=0";
        URL obj = new URL("https://www.cgv.vn/default/cinox/site/");
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setInstanceFollowRedirects(true);
        con.setConnectTimeout(60000);
        con.setReadTimeout(60000);

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
        resp.getWriter().println(response.toString());

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      String content = RestfulHelper.parseStringInputStream(req.getInputStream());
      City c = RestfulHelper.gson.fromJson(content, City.class);

      ofy().save().entity(c).now();

    }

    public static void main(String[] args) {
        String req ="[B@2d554825";
       String s1 = "Hành động, tâm lý";
       String reqCategory = "tâm lý";


        byte[] bytes = new byte[0]; // Charset to encode into
        try {
            String t = URLDecoder.decode(s1,"UTF-8");
            bytes = s1.getBytes("UTF-8");
//            System.out.println(bytes.toString());
            String s2 = new String(req.getBytes(), "UTF-8"); // Charset with which bytes were encoded
            System.out.println(t);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }
}
