package com.KrupoderovMikhail.github.telegrambot.services;

import com.KrupoderovMikhail.github.telegrambot.bot.TemplateBot;
import com.KrupoderovMikhail.github.telegrambot.logger.Logging;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.methods.send.SendVideo;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Krupoderov Mikhail
 * @version 1.0
 */
@Service
public class MessageSenderImpl implements MessageSender {

    private static final Map<Long, Long> usersChats = new ConcurrentHashMap<>();
    private TemplateBot templateBot;

    @Logging
    private Logger logger;

    @Autowired
    public void setTemplateBot(TemplateBot templateBot) {
        this.templateBot = templateBot;
    }

    @Override
    public void send(long userId, @NotNull String message) {
        Long chatId = usersChats.get(userId);
        try {
            templateBot.execute(new SendMessage(chatId, message).disableWebPagePreview().setParseMode("Markdown"));
            logger.info("Sent message to chat [id:" + chatId + "]: " + message.replaceAll("\n", ""));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void register(long userId, long chatId) {
        if (!usersChats.containsKey(userId)) {
            usersChats.put(userId, chatId);
            logger.info("New user [id:" + userId + "] has been registered.");
        }
    }
}
