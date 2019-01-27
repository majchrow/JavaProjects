package com.condition.air;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;


/**
 * Loader Class which curl the website.
 *
 * @author Dawid Majchrowski
 */
public class Loader {
    public final static String giosStation = "http://api.gios.gov.pl/pjp-api/rest/station/findAll";
    public final static String giosSensor = " http://api.gios.gov.pl/pjp-api/rest/station/sensors/";
    public final static String giosData = "http://api.gios.gov.pl/pjp-api/rest/data/getData/";
    public final static String giosIndex = "http://api.gios.gov.pl/pjp-api/rest/aqindex/getIndex/";
    public final static String methodGet = "GET";

    /**
     * CURL the website and write to file response
     *
     * @param urlToRead url to be scrapped
     * @param path path to the file to get data
     * @param name name of the file to get data
     * @param requestMethod requested method f.e "GET", "POST"
     */
    public static void Update(String urlToRead, String path, String name, String requestMethod){
        StringBuilder result = new StringBuilder();
        URL url = null;
        try {
            url = new URL(urlToRead);
        }catch (MalformedURLException e){
            System.err.println("Failed to read url " + urlToRead);
            throw new IllegalArgumentException();
        }
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(requestMethod);
        }catch (Exception e){
            System.err.println("Failed to get Http connection" + urlToRead + "with "+methodGet +" method");
            throw new IllegalArgumentException();
        }
        try (BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
        }catch (IOException e){
            System.err.println("Failed to read Input from Http connection from " + urlToRead);
            throw new IllegalArgumentException();
        }
        try(BufferedWriter writer = Files.newBufferedWriter(Paths.get(path+name),StandardCharsets.UTF_8)){
            writer.write(result.toString());
        }catch (IOException e){
            System.err.println("Failed to write to Http request " + urlToRead + " to file" + path+name);
            throw new IllegalArgumentException();
        }
    }
}


