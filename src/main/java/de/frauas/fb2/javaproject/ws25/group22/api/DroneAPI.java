package de.frauas.fb2.javaproject.ws25.group22.api;

import de.frauas.fb2.javaproject.ws25.group22.errorhandling.ConnectionFailedException;
import de.frauas.fb2.javaproject.ws25.group22.errorhandling.IllegalJSONFormatException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URI;

/**
 * Provides methods to fetch JSON data from a given URL using HTTP GET requests.
 * Handles the connection.
 *
 * @author Tobias Ilcken, Younes Wimmer
 */
public class DroneAPI {
    private static final String TOKEN = "Token 140c969fed7748aafd5f1ac7dc1ed246aab72acd";
    private static final String USER_AGENT = "Group22";
    private final String url;

    /**
     * Constructs a DroneAPI instance with the specified URL.
     *
     * @param url the URL to connect to for fetching drone data.
     */
    public DroneAPI(String url) {
        this.url = url;
    }

    /**
     * Fetches JSON data from the configured URL.
     *
     * @return the fetched data as a JSONObject, or null if fetching fails.
     * @throws ConnectionFailedException if the connection to the server fails.
     * @throws IllegalJSONFormatException if the received JSON is not valid.
     */
    public JSONObject fetchJSON() throws ConnectionFailedException, IllegalJSONFormatException {
        JSONObject returnJson = null;
        try {
            URI uri = new URI(url);
            HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();
            setRequestProperties(connection);
            String data = fetchData(connection);
            returnJson = new JSONObject(data);
            connection.disconnect();
        } catch(Exception e) {
            throw new ConnectionFailedException();
        }
        if(!validateJSON(returnJson)){
            throw new IllegalJSONFormatException();
        }
        return returnJson;
    }

    private String fetchData(HttpURLConnection connection) throws Exception {
        int responseCode = connection.getResponseCode();
        if(responseCode != 200){
            throw new Exception();
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
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

    private boolean validateJSON(JSONObject o) {
        if(!o.has("count") || !o.has("results")){
            return false;
        }
        return true;
    }
}
