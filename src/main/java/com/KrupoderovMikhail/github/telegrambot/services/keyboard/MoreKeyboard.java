package com.KrupoderovMikhail.github.telegrambot.services.keyboard;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;

/**
 * @author Krupoderov Mikhail
 * @version 1.0
 */
public class MoreKeyboard {

    ReplyKeyboardMarkup replyKeyboardMarkup;
    ArrayList<KeyboardRow> keyboard;

    public MoreKeyboard () {

        this.replyKeyboardMarkup = new ReplyKeyboardMarkup();
        this.keyboard = new ArrayList<KeyboardRow>();
    }

    public SendMessage install(Message message, String s) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setReplyMarkup(replyKeyboardMarkup); // устанавливаем клаву для пользователя

        // Создаем строки
        KeyboardRow keyboardFirstRow = new KeyboardRow();

        // Основные установки для клавиатуры
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        keyboard.clear();
        keyboardFirstRow.clear();

        keyboardFirstRow.add("/more");
        keyboard.add(keyboardFirstRow);

        replyKeyboardMarkup.setKeyboard(keyboard); // обновляем клаву

        sendMessage.enableMarkdown(false); // при true вылетают исключения
        sendMessage.disableWebPagePreview();
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setParseMode("HTML");
        sendMessage.setText(s);

        return sendMessage;

    }
}