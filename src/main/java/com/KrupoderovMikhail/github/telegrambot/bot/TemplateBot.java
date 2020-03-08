package com.KrupoderovMikhail.github.telegrambot.bot;

import com.KrupoderovMikhail.github.telegrambot.logger.Logging;
import com.KrupoderovMikhail.github.telegrambot.services.MessageSender;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Krupoderov Mikhail
 * @version 1.0
 */
@Component
public class TemplateBot extends TelegramLongPollingBot {
    private static final int DISCARD_MESSAGE_DURATION = 5; // seconds
    private static final String START = "/start";
    private static final String HELLO = "/hello";
    private static final String[] START_ARR = {
            MessageConstants.START_1,
            MessageConstants.START_2,
            MessageConstants.START_3
    };

    @Value("${telegram.app.name}")
    private String name;

    @Value("${telegram.app.token}")
    private String token;

    @Logging
    private Logger logger;

    private MessageSender messageSender;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Autowired
    public void setMessageSender(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    public TemplateBot(DefaultBotOptions options) {
        super(options);
    }

    public TemplateBot() {
    }

    @Override
    public void onUpdateReceived(Update update) {
        logger.info("Incoming message from user [id:" + update.getMessage().getFrom().getId() +
                ", name:" + update.getMessage().getFrom().getFirstName() + "]: " + update.getMessage().getText());
        if (update.hasMessage() && update.getMessage().getText() != null) {
            int msgDate = update.getMessage().getDate();
            int currentDate = (int) (DateTime.now().getMillis() / 1000);
            boolean isDiscard = (currentDate - msgDate) > DISCARD_MESSAGE_DURATION * 60;

            if (!isDiscard) {
                registerUser(update.getMessage().getChatId(), update.getMessage().getFrom().getId());
                action(update);
            }
        }
    }

    @Override
    public String getBotUsername() {
        return name;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    private void sendMessage(Long chatId, String message) {
        try {
            execute(new SendMessage(chatId, message));
            logger.info("Sent message to chat [id:" + chatId + "]: " + message.replaceAll("\n", ""));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void action(@NotNull final Update update) {
        Long userId = Long.valueOf(update.getMessage().getFrom().getId());
        Long chatId = update.getMessage().getChatId();
        String command = update.getMessage().getText().trim();

        switch (command) {
            case START:
                sendMessage(chatId, String.format(
                        START_ARR[new Random().nextInt(START_ARR.length - 1)],
                        update.getMessage().getFrom().getFirstName()));
                break;
            case HELLO:
                sendMessage(chatId, "Hello World!!!");
                break;
        }
    }

    private void registerUser(long chatId, long userId) {
        messageSender.register(userId, chatId);
    }
}