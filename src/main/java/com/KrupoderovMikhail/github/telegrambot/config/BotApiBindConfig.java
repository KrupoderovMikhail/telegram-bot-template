package com.KrupoderovMikhail.github.telegrambot.config;

import com.KrupoderovMikhail.github.telegrambot.bot.Bot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.TelegramBotsApi;

/**
 * @author Krupoderov Mikhail
 * @version 1.0
 */
@Configuration
public class BotApiBindConfig implements CommandLineRunner {

    private TelegramBotsApi telegramBotsApi = new TelegramBotsApi();

    @Autowired
    private Bot bot;

    @Override
    public void run(String... args) throws Exception {
        telegramBotsApi.registerBot(bot);
    }
}
