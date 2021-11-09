package uaic.info.csft.translationservice.services;

import lombok.AllArgsConstructor;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
@AllArgsConstructor
public class TranslationService {


    public String test() {
        return "test";
    }

    public static String callAPI(String targetURL, String urlParameters) {
        HttpURLConnection connection = null;

        try {
            //Create connection
            URL url = new URL(targetURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");

            connection.setRequestProperty("Content-Length",
                    Integer.toString(urlParameters.getBytes().length));
            connection.setRequestProperty("Content-Language", "en-US");

            connection.setUseCaches(false);
            connection.setDoOutput(true);

            //Send request
            DataOutputStream wr = new DataOutputStream (
                    connection.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.close();

            //Get Response
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private String parseJSON(String translationJSON) {
        JSONObject objJSON;
        String translation = "";
        try {
            objJSON = new JSONObject(translationJSON);
            JSONArray translationsJSON = objJSON.getJSONArray("translations");
            JSONObject firstTranslationJSON = translationsJSON.getJSONObject(0);
            translation = firstTranslationJSON.getString("text");
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            return translation;
        }
    }

    public String translate(String sourceLanguage, String targetLanguage, String phrase) {
        String targetURL = "https://api-free.deepl.com/v2/translate";
        String urlParameters = "auth_key=6d831dca-c882-4f8a-d68e-77e51dc8751e:fx" + "&text=" + phrase + "&source_lang=" + sourceLanguage + "&target_lang=" + targetLanguage;
        String translationJSON = callAPI(targetURL, urlParameters);
        String translation = parseJSON(translationJSON);
        return translation;
    }

    public String autoCorrect(String language, String word) {
        if(language.equals("ROMANIAN")) {
            return "cuvant";
        } else if(language.equals("ENGLISH")) {
            return "word";
        } else {
            return "Couldn't autocorrect";
        }
    }

}
