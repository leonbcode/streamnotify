package de.leonbcode.streamnotify;

import de.leonbcode.streamnotify.jda.Bot;
import de.leonbcode.streamnotify.twitch.TwitchAPI;
import de.leonbcode.streamnotify.twitch.TwitchStream;
import lombok.Data;
import lombok.Getter;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
public class Manager {

    @Getter
    public static Manager instance;

    private final Config config;
    private final Bot bot;
    private final TwitchAPI twitchAPI;
    private final List<TwitchStream> streams = new ArrayList<>();

    public Manager() {
        instance = this;

        Optional<Config> cfg = loadConfig();
        if (cfg.isPresent()) {
            config = cfg.get();
        } else {
            System.out.println("No config available.");
            config = null;
            System.exit(0);
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

    private Optional<Config> loadConfig() {
        String[] configTemplate = {"!!de.leonbcode.streamnotify.Config", "#Discord", "botToken: ", "channelID: ", "\n", "#Twitch", "clientID: ", "clientSecret: ", "streamers:", "  - "};
        Config config;
        InputStream inputStream;
        Yaml yaml = new Yaml();
        File file = new File("config.yml");
        try {
            if (file.createNewFile()) {
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write(String.join("\n", configTemplate));
                fileWriter.flush();
                fileWriter.close();
                System.out.println("Created config file.");
                System.out.println("Enter the credentials in the config file!");
                System.exit(0);
            } else {
                inputStream = new FileInputStream("config.yml");
                config = yaml.load(inputStream);
                return Optional.of(config);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
