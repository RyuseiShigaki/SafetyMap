package com.shigaki.sano.safetymap;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;

public class JavaNetHttpClient {
    //public static void main(String[] args) {
     //   executeGet();
     //   executePost();
    //}

    String executeGet() {
        String text="";
        System.out.println("===== HTTP GET Start =====");
        try {
            URL url = new URL("http://edu3.te.kumamoto-nct.ac.jp:8088/~te14shigaki/testdata.txt");

            HttpURLConnection connection = null;

            try {
                connection = (HttpURLConnection) url.openConnection();

                connection.setRequestMethod("GET");

                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    System.out.println("===== HTTP GET Start =====");
                    try (InputStreamReader isr = new InputStreamReader(connection.getInputStream(),
                            StandardCharsets.UTF_8);
                         BufferedReader reader = new BufferedReader(isr)) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            System.out.println(line);
                            text=line;
                        }
                    }
                }
            } finally {
                if (connection != null) {

                    connection.disconnect();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("===== HTTP GET End =====");
        return text;
    }

    static void executePost() {
        System.out.println("===== HTTP POST Start =====");
        try {
            URL url = new URL("http://localhost:8080/post");

            HttpURLConnection connection = null;

            try {
                connection = (HttpURLConnection) url.openConnection();
                connection.setDoOutput(true);
                connection.setRequestMethod("POST");

                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(),
                        StandardCharsets.UTF_8));
                writer.write("POST Body");
                writer.write("\r\n");
                writer.write("Hello Http Server!!");
                writer.write("\r\n");
                writer.flush();

                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    try (InputStreamReader isr = new InputStreamReader(connection.getInputStream(),
                            StandardCharsets.UTF_8);
                         BufferedReader reader = new BufferedReader(isr)) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            System.out.println(line);

                        }
                    }
                }
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("===== HTTP POST End =====");
    }
}