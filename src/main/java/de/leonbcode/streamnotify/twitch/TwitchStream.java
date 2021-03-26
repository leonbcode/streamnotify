package de.leonbcode.streamnotify.twitch;

import lombok.Data;

@Data
public class TwitchStream {

    private final String name;
    private boolean live = false;

    public TwitchStream(String name) {
        this.name = name;
    }

}
