package uaic.info.csft.translationservice.utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtilities {


    public String parseTranslationJSON(String translationJSON) {
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

    public List<String> parseAutoCorrectJSON(String autoCorrectJSON) {
        JSONObject objJSON;
        List<String> replacements = new ArrayList<>();
        try {
            objJSON = new JSONObject(autoCorrectJSON);
            JSONArray replacementsJSON = objJSON.getJSONArray("matches").getJSONObject(0).getJSONArray("replacements");
            for(int index = 0; index < replacementsJSON.length(); index++) {
                replacements.add(replacementsJSON.getJSONObject(index).getString("value"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            return replacements;
        }
    }
}
