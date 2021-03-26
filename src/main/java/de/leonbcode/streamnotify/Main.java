package de.leonbcode.streamnotify;

import java.util.Timer;
import java.util.TimerTask;

public class Main {

    public static void main(String[] args) {
        Manager manager = new Manager();

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                manager.getTwitchAPI().update();
            }
        }, 0, 10000);
    }
}