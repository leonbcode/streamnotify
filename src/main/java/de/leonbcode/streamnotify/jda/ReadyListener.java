package de.leonbcode.streamnotify.jda;

import de.leonbcode.streamnotify.Config;
import de.leonbcode.streamnotify.Manager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class ReadyListener extends ListenerAdapter {

    private final Config config;

    public ReadyListener() {
        config = Manager.getInstance().getConfig();
    }

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        JDA jda = Manager.getInstance().getBot().getJda();
        Manager.getInstance().getBot().setTextChannel(jda.getTextChannelById(config.getChannelID()));
    }
}
