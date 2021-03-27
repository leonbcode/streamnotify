package de.leonbcode.streamnotify;

import de.leonbcode.streamnotify.jda.Bot;
import de.leonbcode.streamnotify.twitch.TwitchAPI;
import de.leonbcode.streamnotify.twitch.TwitchStream;
import lombok.Getter;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class Manager {

    @Getter
    public static Manager instance;

    @Getter
    private final Config config;
    @Getter
    private final Bot bot;
    @Getter
    private final TwitchAPI twitchAPI;
    @Getter
    private final List<TwitchStream> streams = new ArrayList<>();

    public Manager() {
        instance = this;

        config = loadConfig("./config.yml");
        if (!config.validate()) {
            System.out.println("Invalid config!");
            System.exit(1);
        }

        bot = new Bot();
        twitchAPI = new TwitchAPI();

        init();
    }

    private void init() {
        for (String name : config.getStreamers()) {
            streams.add(new TwitchStream(name));
        }
    }

    private <T> T loadConfig(String path) {
        String[] configTemplate = {"!!de.leonbcode.streamnotify.Config", "#Discord", "botToken: ", "channelID: ", "\n", "#Twitch", "clientID: ", "clientSecret: ", "streamers:", "  - "};
        T config;
        InputStream inputStream;
        Yaml yaml = new Yaml();
        File file = new File(path);
        try {
            if (file.createNewFile()) {
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write(String.join("\n", configTemplate));
                fileWriter.flush();
                fileWriter.close();
                System.out.println("Created config file.");
                System.out.println("Enter the credentials in the config file!");
                System.exit(1);
            } else {
                inputStream = new FileInputStream("config.yml");
                config = yaml.load(inputStream);
                return config;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
