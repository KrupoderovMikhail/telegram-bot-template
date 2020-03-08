package com.KrupoderovMikhail.github.telegrambot.services;

import org.jetbrains.annotations.NotNull;

public interface MessageSender {
    /**
     * @param userId  user ID
     * @param message sent message
     */
    void send(final long userId, @NotNull final String message);

    /**
     * @param userId user ID
     * @param chatId chat id associated with userId
     */
    void register(final long userId, final long chatId);
}
