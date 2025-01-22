package Group22.API;

import Group22.Errorhandling.ConnectionFailedException;
import Group22.Errorhandling.IllegalJSONFormatException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URI;


/**
 * Provides methods to fetch JSON data from a given URL using HTTP GET requests.
 * Handles connection properties, data fetching, and error logging.
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

    /**
     * Retrieves data from the HTTP connection.
     *
     * @param connection the HttpURLConnection to read data from.
     * @return the response data as a String.
     * @throws Exception if the connection fails or the response code is not 200.
     */
    private String fetchData(HttpURLConnection connection) throws Exception {
        int responseCode = connection.getResponseCode();
        if(responseCode != 200){
            throw new Exception();
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

    /**
     * Sets necessary request properties for the HTTP connection including headers and request method.
     *
     * @param connection the HttpURLConnection to configure.
     * @throws ProtocolException if setting the request method fails.
     */
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
