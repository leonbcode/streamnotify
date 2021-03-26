package de.leonbcode.streamnotify.jda;

import de.leonbcode.streamnotify.Config;
import de.leonbcode.streamnotify.Manager;
import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.TextChannel;

import javax.security.auth.login.LoginException;

public class Bot {

    @Getter
    private JDA jda;
    @Setter
    private TextChannel textChannel;

    private final String[] notification = {"Hey @everyone , ", ", is now live on ( https://www.twitch.tv/", " )!\nGo check it out!"};

    public Bot() {
        Config config = Manager.getInstance().getConfig();
        try {
            jda = JDABuilder.createDefault(config.getBotToken()).addEventListeners(new ReadyListener()).build();
        } catch (LoginException ex) {
            ex.printStackTrace();
        }
    }

    public void sendNotification(String name) {
        if (textChannel != null) {
            String msg = String.join(name, notification);
            textChannel.sendMessage(msg).queue();
        } else {
            System.out.println("No Text-Channel available");
        }
    }
}
