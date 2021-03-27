package de.leonbcode.streamnotify;

import lombok.Data;

import java.util.List;

@Data
public class Config {

    private String botToken;
    private String channelID;

    private String clientID;
    private String clientSecret;
    private List<String> streamers;

    public boolean validate() {
        return botToken != null && channelID != null && clientID != null && clientSecret != null && streamers != null
                && !botToken.isEmpty() && !channelID.isEmpty() && !clientID.isEmpty() && !clientSecret.isEmpty() && !streamers.isEmpty();
    }
}
