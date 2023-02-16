package com.epam.testing.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Properties;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.net.ssl.HttpsURLConnection;

/**
 * Verify reCAPTCHA util
 *
 * @author rom4ik
 */
public class VerifyRecaptcha {
    private static final Logger LOGGER = LogManager.getLogger(VerifyRecaptcha.class);
    private static final String RECAPTCHA_PROPERTIES_PATH="recaptcha.properties";
    private static final Properties properties;

    /**
     * Don't let anyone instantiate this class.
     */
    private VerifyRecaptcha() {}

    static {
        properties = readProperties();
    }

    /**
     * Sends request to google verify service
     *
     * @param gRecaptchaResponse response from html page
     * @return verification success
     */
    public static boolean verify(String gRecaptchaResponse) {
        if (gRecaptchaResponse == null || "".equals(gRecaptchaResponse)) {
            return false;
        }
        Properties properties = readProperties();
        try {
            URL obj = new URL(properties.getProperty("verify.url"));
            HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

            addRequestHeader(con);
            sendPostRequest(con, gRecaptchaResponse);

            int responseCode = con.getResponseCode();
            LOGGER.info("Sending 'POST' request to URL : {}", properties.getProperty("verify.url"));
            LOGGER.info("Response Code : {}", responseCode);

            String response = getResponse(con);
            boolean success = parseResponseAndGetSuccessValue(response);
            LOGGER.info("Verify: {}", success);
            return success;
        }catch(Exception e){
            e.printStackTrace();
            LOGGER.warn(e.getMessage());
            return false;
        }
    }

    private static void addRequestHeader(HttpsURLConnection connection) throws ProtocolException {
        connection.setRequestMethod("POST");
        connection.setRequestProperty("User-Agent", properties.getProperty("user.agent"));
        connection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
    }

    private static void sendPostRequest(HttpsURLConnection connection, String gRecaptchaResponse) throws IOException {
        String postParams = "secret=" + properties.getProperty("secrete.key") + "&response="
                + gRecaptchaResponse;

        connection.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
        wr.writeBytes(postParams);
        wr.flush();
        wr.close();
    }

    private static String getResponse(HttpsURLConnection connection) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(
                connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }

    private static boolean parseResponseAndGetSuccessValue(String response) {
        JsonObject jsonObject;
        try(JsonReader jsonReader = Json.createReader(new StringReader(response.toString()))) {
            jsonObject = jsonReader.readObject();
        }
        return jsonObject.getBoolean("success");
    }

    private static Properties readProperties() {
        Properties properties = new Properties();
        try {
            properties.load(EmailSenderUtil.class.getClassLoader().getResourceAsStream(RECAPTCHA_PROPERTIES_PATH));
        } catch (IOException e) {
            LOGGER.warn(e.getMessage());
            e.printStackTrace();
            return null;
        }
        return properties;
    }
}
