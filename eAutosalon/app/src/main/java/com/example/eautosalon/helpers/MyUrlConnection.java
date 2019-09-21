package com.example.eautosalon.helpers;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class MyUrlConnection {
    private static final String TAG = "MyUrlConnection";

    public enum HttpMethod
    {
        GET, POST, HEAD, OPTIONS, PUT, DELETE, TRACE
    }

    public static MyApiResult request(String urlString, HttpMethod httpMethod, String postData, String contentType) {

        HttpURLConnection urlConnection = null;
        String charset = "UTF-8";
        String result = null;
        try {
            URL url = new URL(urlString);

            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setDoInput(true);

            urlConnection.setRequestProperty("Content-Type", contentType);
            urlConnection.setRequestProperty("Accept", contentType);
            urlConnection.setRequestProperty("Accept-Charset", charset);
            urlConnection.setRequestMethod(httpMethod.toString());
            urlConnection.setUseCaches(false);
            urlConnection.setAllowUserInteraction(false);

            if (postData != null) {
                urlConnection.setDoOutput(true);
                byte[] outputBytes = postData.getBytes(charset);
                OutputStream os = urlConnection.getOutputStream();
                os.write(outputBytes);
                os.flush();
                os.close();
            }

            int statusCode = urlConnection.getResponseCode();

            if (statusCode ==  200 || statusCode == 204) {

                InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());

                String response = convertToString(inputStream);

                return  MyApiResult.OK(response);
            } else {
                // Status code is not 200
                // Do something to handle the error
                InputStream inputStream = new BufferedInputStream(urlConnection.getErrorStream());
                String response = convertToString(inputStream);
                if (response.length()==0)
                    response = urlConnection.getResponseMessage();

                return MyApiResult.Error(statusCode, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return MyApiResult.Error(0, e.getMessage());
        }
        finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }

    public static String Post(String urlString, String postData) {
        HttpURLConnection urlConnection=null;

        try
        {
            // This is getting the url from the string we passed in
            URL url = new URL(urlString);

            // Create the urlConnection
            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);

            urlConnection.setRequestProperty("Content-Type", "application/json");

            urlConnection.setRequestMethod("POST");


            // OPTIONAL - Sets an authorization header
            //  urlConnection.setRequestProperty("Authorization", "someAuthString");

            // Send the post body
            if (postData != null) {
                OutputStreamWriter writer = new OutputStreamWriter(urlConnection.getOutputStream());
                writer.write(postData.toString());
                writer.flush();
            }

            int statusCode = urlConnection.getResponseCode();

            if (statusCode ==  200) {

                InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());

                String response = convertToString(inputStream);

                return  response;
            } else {
                // Status code is not 200
                // Do something to handle the error
            }

        } catch (Exception e) {
            Log.d(TAG, e.getLocalizedMessage());
        }
        finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return null;
    }

    private static String convertToString(InputStream in) {
        BufferedReader reader = null;
        StringBuilder response = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return response.toString();
    }
}
