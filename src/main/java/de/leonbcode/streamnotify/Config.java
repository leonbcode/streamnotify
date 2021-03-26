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
}
