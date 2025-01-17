package Group22.API;

import Group22.Errorhandling.ConnectionFailedException;
import Group22.Errorhandling.Logging;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URISyntaxException;

public class DroneAPI {
    private static final String TOKEN = "Token 140c969fed7748aafd5f1ac7dc1ed246aab72acd";
    private static final String USER_AGENT = "Celebi EX";
    private final String url;

    public DroneAPI(String url) {
        this.url = url;
    }

    public JSONObject fetchJSON() {
        JSONObject returnJson = null;
        try {
            URI uri = new URI(url);
            HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();
            setRequestProperties(connection);
            String data = fetchData(connection);
            returnJson = new JSONObject(data);
            connection.disconnect();
        } catch(Exception e) {
            Logging.error("Connection failed. Make sure you are connected to the internet.");
        }
        return returnJson;
    }
    private String fetchData(HttpURLConnection connection) throws Exception  {
        int responseCode = connection.getResponseCode();
        if(responseCode != 200){
            throw new ConnectionFailedException("Connection failed. Response code: " + responseCode);
        }
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }

    private void setRequestProperties(HttpURLConnection connection) throws ProtocolException {
        connection.setRequestProperty("Authorization", TOKEN);
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", USER_AGENT);
    }
}
