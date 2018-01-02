package com.Crawler.test;


import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.logging.Logger;


public class cgv extends HttpServlet {
    Logger LOG = Logger.getLogger(cgv.class.getSimpleName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        long a = System.currentTimeMillis();
        try {
            sendPostBody();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        long b = System.currentTimeMillis();
        resp.getWriter().println("ok - " + (b - a) + "ms");
    }

    public static void main(String[] args) {
        try {
            new cgv().sendPostBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startNormal() {
        Logger LOG = Logger.getLogger(cgv.class.getSimpleName());
        try {
            long a = System.currentTimeMillis();
            LOG.info("start get bufferredaer");
            URL url = new URL("https://www.cgv.vn/default/cinox/site/");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");


            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                LOG.info("ok");
                BufferedReader bf = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line = "";
                while ((line = bf.readLine()) != null) {
                    System.out.println(line);
                }
            } else {
                LOG.severe("false");
            }

            long b = System.currentTimeMillis();
            LOG.info("geted bufferredaer - " + (b - a) + "ms");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendPostParams() throws Exception {
        String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.12; rv:59.0) Gecko/20100101 Firefox/59.0";
        String target = "https://www.cgv.vn/default/cinox/site";

        StringBuilder tokenUri = new StringBuilder();
        tokenUri.append("TS015ef8cd_76=");
        tokenUri.append(URLEncoder.encode("0", "UTF-8"));
        tokenUri.append("&TS015ef8cd_cr=");
        tokenUri.append(URLEncoder.encode("c63983790dee442c7af576d651ed42cd:nkll:LlAjA4qd:795523845", "UTF-8"));
        tokenUri.append("&TS015ef8cd_id=");
        tokenUri.append(URLEncoder.encode("3", "UTF-8"));
        tokenUri.append("&TS015ef8cd_md=");
        tokenUri.append(URLEncoder.encode("1", "UTF-8"));
        tokenUri.append("&TS015ef8cd_pd=");
        tokenUri.append(URLEncoder.encode("0", "UTF-8"));
        tokenUri.append("&TS015ef8cd_rf=");
        tokenUri.append(URLEncoder.encode("0", "UTF-8"));


        URL url = new URL(target);

        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "UTF-8");

        con.setDoOutput(true);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(con.getOutputStream());
        outputStreamWriter.write(tokenUri.toString());
        outputStreamWriter.flush();

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + target);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer r = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            r.append(inputLine);
        }
        in.close();

        System.out.println(r.toString());

    }


    public void sendPostBody() throws IOException {
        String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.12; rv:59.0) Gecko/20100101 Firefox/59.0";

        String postBody = "TS015ef8cd_id=3&TS015ef8cd_cr=783cd6cbd7faa22bcbf2f90aedd6fd99%3Axzxz%3A85CbHbVV%3A168162141&TS015ef8cd_76=0&TS015ef8cd_md=1&TS015ef8cd_rf=0&TS015ef8cd_ct=0&TS015ef8cd_pd=0";

        String url = "https://www.cgv.vn/default/cinox/site/cgv-aeon-binh-tan";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Host", "www.cgv.vn");
        con.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
       // con.setRequestProperty("Accept-Encoding", "gzip, deflate, br");
//        con.setRequestProperty("Cookie", "TS015ef8cd=018ea3cdda4cc2e1df3a2d3fc281e63af4751896e9eb5c8607eefd385f6ea3cb89e9ac2ded; TS01faf9b1=018ea3cdda9a6001df9fcf60d4fde1f16c43cf8b198c7127dbb5d079333386bbd8769e6379bf0c1263b467f9c0d9c77cc105ad0b11f0fceda77d79a9ac16a9f7e4c3266ed6; TS015ef8cd_77=2086_40ce275283edaf45_rsb_0_rs_https%3A%2F%2Fwww.cgv.vn%2Fdefault%2Fcinox%2Fsite%2F_rs_0_rs_0; frontend=c6h0q83hh2mvoif8b54e98ujq1; frontend_cid=iXdUAXJzhUxhxWw0");
        con.setRequestProperty("Cookie", "frontend=oin90olqife61rf3a842ibnhv6; TS015ef8cd=018ea3cddaa1b048986fe7019c9603d824b649a8fcf1723b259e477bb15b8e1e4145885d41; TS01faf9b1=018ea3cddaf8b120bfb4c52a90109e7a43a4abc5fe8c7127dbb5d079333386bbd8769e6379b955d5b16406d96a0110d66dda2152a715a6af4df963426def4c9e2eba716442; TS015ef8cd_77=2584_f5bc216fa6944abf_rsb_0_rs_https%3A%2F%2Fwww.cgv.vn%2Fdefault%2Fcinox%2Fsite%2F_rs_1_rs_0; frontend_cid=kbEuPDWOGTlJvrJe");
        con.setRequestProperty("DNT", "1");
        con.setRequestProperty("Connection", "keep-alive");
        con.setRequestProperty("Upgrade-Insecure-Requests", "1");
        con.setRequestProperty("Cache-Control", "max-age=0");
        con.setRequestProperty("Referer", "https://www.cgv.vn/default/cinox/site/");
        con.setRequestProperty("Content-Type", "UTF-8");
        con.setRequestProperty("Content-Length", "172");

        con.setDoOutput(true);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(con.getOutputStream());
        outputStreamWriter.write(postBody);
        outputStreamWriter.flush();

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        System.out.println(response.toString());
    }



}
