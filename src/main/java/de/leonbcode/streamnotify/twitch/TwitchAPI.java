package de.leonbcode.streamnotify.twitch;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import de.leonbcode.streamnotify.Config;
import de.leonbcode.streamnotify.Manager;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class TwitchAPI {

    private final String statusUrl;
    private final String tokenUrl;
    private final Map<String, String> reqHeaders;

    public TwitchAPI() {
        Config config = Manager.getInstance().getConfig();

        tokenUrl = "https://id.twitch.tv/oauth2/token?client_id=" + config.getClientID() + "&client_secret=" + config.getClientSecret() + "&grant_type=client_credentials";
        statusUrl = "https://api.twitch.tv/helix/streams?user_login=" + String.join("&user_login=", config.getStreamers());

        String accessToken = requestAccessToken();

        reqHeaders = new HashMap<>();
        reqHeaders.put("Client-ID", config.getClientID());
        reqHeaders.put("Authorization", "Bearer " + accessToken);
    }

    public void update() {
        Optional<List<String>> onlineStreams = getOnlineStreamers();

        if (onlineStreams.isEmpty()) return;
        for (TwitchStream stream : Manager.getInstance().getStreams()) {
            if (onlineStreams.get().contains(stream.getName()) && !stream.isLive()) {
                Manager.getInstance().getBot().sendNotification(stream.getName());
                stream.setLive(true);
            } else if (!onlineStreams.get().contains(stream.getName()) && stream.isLive()) {
                stream.setLive(false);
            }
        }
    }

    private String requestAccessToken() {
        Optional<String> data = sendHttpRequest(tokenUrl, "POST", null);
        if (data.isEmpty()) {
            System.out.println("No valid accesstoken!");
            System.exit(0);
        }
        return data.get().substring(17, 47);
    }

    private Optional<String> sendHttpRequest(String urlString, String requestMethod, Map<String, String> headers) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(requestMethod);

            if (headers != null) {
                for (String key : headers.keySet()) {
                    connection.addRequestProperty(key, headers.get(key));
                }
            }
            if (connection.getResponseCode() != 200) return Optional.empty();

            InputStream response = connection.getInputStream();
            try (Scanner scanner = new Scanner(response)) {
                return Optional.of(scanner.useDelimiter("\\A").next());
            }
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }

        return Optional.empty();
    }

    private Optional<List<String>> getOnlineStreamers() {
        Optional<String> data = sendHttpRequest(statusUrl, "GET", reqHeaders);
        if (data.isEmpty()) return Optional.empty();
        JsonArray JsonData = JsonParser.parseString(data.get()).getAsJsonObject().getAsJsonArray("data");

        List<String> onlineStreams = new LinkedList<>();
        for (JsonElement element : JsonData) {
            onlineStreams.add(element.getAsJsonObject().get("user_login").getAsString());
        }
        return Optional.of(onlineStreams);
    }
}
