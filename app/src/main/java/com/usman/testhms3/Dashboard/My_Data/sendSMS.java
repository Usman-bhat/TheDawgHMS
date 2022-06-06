package com.usman.testhms3.Dashboard.My_Data;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class sendSMS {
    String phno;

    public sendSMS(String phno) {
        this.phno = phno;
    }

    public String sendSms() {
        Log.e("me","inSMS Class "+this.phno);
        try {
            // Construct data
            String msgtext = "Thanks For Regestering To KGP Hostel. We Will Get to you Soon ";
            String apiKey = "apikey=" + "NzgzNDU0NjU0ZTU4NDEzOTY4Nzk1ODQ1NzEzMjU3NGQ=";
            String message = "&message=" + msgtext;
            String sender = "&sender=" + "TheDawg";
            String numbers = "&numbers=91" + phno;

            // Send data
            HttpURLConnection conn = (HttpURLConnection) new URL("https://api.textlocal.in/send/?").openConnection();
            String data = apiKey + numbers + message + sender;
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
            conn.getOutputStream().write(data.getBytes("UTF-8"));
            final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            final StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line = rd.readLine()) != null) {
                stringBuffer.append(line);
            }
            rd.close();

            return stringBuffer.toString();
        } catch (Exception e) {
            System.out.println("Error SMS "+e);
            return "Error "+e;
        }
    }
}
