package com.KrupoderovMikhail.github.telegrambot.bot;

/**
 * @author Krupoderov Mikhail
 * @version 1.0
 */
public class MessageConstants {

    private static final String COMMANDS =
            "/start - список команд\n" +
                    "<Список остальных команд>";
    static final String START_1 =
            "Привет, %s! Я - бот, <Описание бота>. " +
                    "Список доступных команд для вас: \n" + COMMANDS;
    static final String START_2 =
            "Привет, %s. <Еще одно описание бота>. " +
                    "Как со мной взаимодействовать: \n" + COMMANDS;
    static final String START_3 =
            "<Описание бота>" + COMMANDS;
}
