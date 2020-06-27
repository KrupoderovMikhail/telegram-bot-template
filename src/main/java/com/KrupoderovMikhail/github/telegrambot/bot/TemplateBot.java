package com.KrupoderovMikhail.github.telegrambot.bot;

import com.KrupoderovMikhail.github.telegrambot.command.FindCommand;
import com.KrupoderovMikhail.github.telegrambot.dto.videos.Videos;
import com.KrupoderovMikhail.github.telegrambot.dto.category.WrapperCategory;
import com.KrupoderovMikhail.github.telegrambot.logger.Logging;
import com.KrupoderovMikhail.github.telegrambot.services.MessageSender;
import com.KrupoderovMikhail.github.telegrambot.services.VideoService;
import com.KrupoderovMikhail.github.telegrambot.services.keyboard.InlineKeyboard;
import com.KrupoderovMikhail.github.telegrambot.services.keyboard.MoreKeyboard;
import com.KrupoderovMikhail.github.telegrambot.utils.GifSequenceWriter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.SneakyThrows;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.methods.send.SendVideo;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Krupoderov Mikhail
 * @version 1.0
 */
@Component
public class TemplateBot extends TelegramLongPollingBot {

    private static final String URL_SEARCH_VIDEO = "https://api.redtube.com/?data=redtube.Videos.searchVideos&output=json&page=1&thumbsize=big&search=";
    private static final String ALL_VIDEO = "https://api.redtube.com/?data=redtube.Videos.searchVideos&output=json&thumbsize=big";
    private static final String URL_CATEGORY = " https://api.redtube.com/?data=redtube.Categories.getCategoriesList&output=json";
    private static final int DISCARD_MESSAGE_DURATION = 5; // seconds
    private static final String START = "/start";
    private static final String HELLO = "/hello";
    private static final String CATEGORY = "/category";
    private static final String VIDEO_SEARCH = "/video_search";

    private static final String RANDOM_VIDEO = "/random";
    private static final String TEST = "/test";
    private static final String[] START_ARR = {
            MessageConstants.START_1,
            MessageConstants.START_2,
            MessageConstants.START_3
    };

    FindCommand find = new FindCommand();

    @Value("${telegram.app.name}")
    private String name;

    @Value("${telegram.app.token}")
    private String token;

    @Logging
    private Logger logger;

    @Autowired
    public MessageSender messageSender;

    @Autowired
    public VideoService videoService;


    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    //    private SearchService searchService;
    InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();

    public TemplateBot(DefaultBotOptions options) {
        super(options);
    }

    public TemplateBot() {
    }

    @SneakyThrows
    public void onUpdateReceived(Update update) {
        logger.info("Incoming message from user [id:" + update.getMessage().getFrom().getId() +
                ", name:" + update.getMessage().getFrom().getFirstName() + "]: " + update.getMessage().getText());
        if (update.hasMessage() && update.getMessage().getText() != null) {
            registerUser(update.getMessage().getChatId(), update.getMessage().getFrom().getId());
            action(update);
        }
    }

//    }

    @Override
    public String getBotUsername() {
        return name;
    }

    @Override
    public String getBotToken() {
        return token;
    }

//    private void sendMessage(Long chatId, String message) {
//        try {
//            execute(new SendMessage(chatId, message));
//            logger.info("Sent message to chat [id:" + chatId + "]: " + message.replaceAll("\n", ""));
//        } catch (TelegramApiException e) {
//            e.printStackTrace();
//        }
//    }

    private void action(@NotNull final Update update) throws InterruptedException, TelegramApiException, IOException {
        Long userId = Long.valueOf(update.getMessage().getFrom().getId());
        Long chatId = update.getMessage().getChatId();
        String command = update.getMessage().getText().trim();
        Message message = update.getMessage();

        switch (command) {
            case START:
                messageSender.send(chatId, String.format(
                        START_ARR[new Random().nextInt(START_ARR.length - 1)],
                        update.getMessage().getFrom().getFirstName()));
                break;
            case CATEGORY:
                execute(new InlineKeyboard().send(chatId));
            case RANDOM_VIDEO:
                List<Videos> videos = videoService.getVideo(ALL_VIDEO).getVideos();
                int random = getRandomNumber();
                String getUrlWithTitle = "[" + videos.get(random).getVideo().getTitle() + "]" + "(" + videos.get(random).getVideo().getEmbed_url() + ")";
                String getThumbs = videos.get(random).getVideo().getThumb();
                String duration = "⏳ *" + videos.get(random).getVideo().getDuration() + "*";
                sendImage(chatId, getUrlWithTitle, getThumbs, duration);
                break;
        }

        if (command.contains("/find")) {
            String searchingMessage = command.replaceAll("/find ", "");

            if (!searchingMessage.contains("/find")) {
                String url = URL_SEARCH_VIDEO + searchingMessage;

                String url1 = url.replaceAll(" ", "%20");
                List<Videos> videos = videoService.getVideo(url1).getVideos();
                for (int i = 0; i < 5; i++) {
                    String getTitle = find.findCommand(command, videos).get(i).getTitle().toUpperCase();
                    String getUrl = find.findCommand(command, videos).get(i).getUrl();

                    String getUrlWithTitle = "[" + getTitle + "]" + "(" + getUrl + ")";
                    String getThumbs = find.findCommand(command, videos).get(i).getThumbs();
                    String duration = "⏳ *" + find.findCommand(command, videos).get(i).getDuration() + "*";

                    sendImage(chatId, getUrlWithTitle, getThumbs, duration);

                    messageSender.send(chatId, "▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁");
                    TimeUnit.SECONDS.sleep(1);
                }

                execute(new MoreKeyboard().install(message, "\"В меню доступна навигация по разделам видео. \\nТам же есть список основных команд по управлению ботом.\""));
                System.out.println(find.findCommand(command, videos).get(0).getTitle());
            } else {
                messageSender.send(chatId, "Что вы хотите найти?");
        }
        }

        if (command.contains("/help")) {
            messageSender.send(chatId, "Команда   |   Описание\n" +
                    "/start          |   Стартовая информация\n" +
                    "/find           |   Поиск ссылок на видео. Напр, /find мамка админа\n" +
                    "/more           |   Показать еще 5 видео, работает после команды /find или выбора категории\n");
            messageSender.send(chatId, "<a href=\"google.com\">123</a>");
        }

        /* Формирует gif файл из картинок.
        * Необходимо добавить загрузку картинок из апи и конвертацию их в одну гиф
        * Метод перенести в сервис и использовать при поиске
        *
        * Доработать получение файлов по ссылке*/
        if(command.contains("/test")) {
            BufferedImage first = ImageIO.read(new URL("https://ci.rdtcdn.com/m=eWgr9f/media/videos/201203/30/167220/original/1.jpg"));
            ImageOutputStream output = new FileImageOutputStream(new File("src/main/resources/exampl1e.gif"));

            GifSequenceWriter writer = new GifSequenceWriter(output, first.getType(), 400, true);
            writer.writeToSequence(first);

            URL url1 = new URL("https://ci.rdtcdn.com/m=eWgr9f/media/videos/201203/30/167220/original/1.jpg");
            URL url2 = new URL("https://ci.rdtcdn.com/m=eWgr9f/media/videos/201203/30/167220/original/2.jpg");
            URL url3 = new URL("https://ci.rdtcdn.com/m=eWgr9f/media/videos/201203/30/167220/original/3.jpg");
            Image image1 = ImageIO.read(url1);
            Image image2 = ImageIO.read(url2);
            Image image3 = ImageIO.read(url3);

//            File[] images = new File[]{
//                    new File("src/main/resources/1.jpg"),
//                    new File("src/main/resources/2.jpg"),
//                    new File("src/main/resources/3.jpg"),
//            };
            List<Image> images = new ArrayList<>();
            images.add(image1);
            images.add(image2);
            images.add(image3);

            for (Image image : images) {
                BufferedImage next = (BufferedImage) image;
                writer.writeToSequence(next);
            }

            writer.close();
            output.close();
        }

        if (command.contains("/testvideo")) {
            List<Videos> videos = videoService.getVideo(URL_SEARCH_VIDEO).getVideos();
            String getUrl = find.findCommand(command, videos).get(0).getUrl();
            sendVideo(chatId, getUrl);
        }
    }

    public void sendImage(Long chatId, String url) {
        SendPhoto sendPhotoRequest = new SendPhoto();
        sendPhotoRequest.setChatId(chatId);
        sendPhotoRequest.setPhoto(url);
        try {
            sendPhoto(sendPhotoRequest);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendImage(Long chatId, String title, String img, String duration) {
        SendPhoto sendPhotoRequest = new SendPhoto();
        sendPhotoRequest.setChatId(chatId);

        sendPhotoRequest.setPhoto(img);

        try {
            messageSender.send(chatId, title);
            sendPhoto(sendPhotoRequest);
            messageSender.send(chatId, duration);

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    //
    public void sendVideo(Long chatId, String url) {
        SendVideo sendVideoRequest = new SendVideo();
        sendVideoRequest.setChatId(chatId);
        sendVideoRequest.setVideo(url);
        try {
            sendVideo(sendVideoRequest);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public WrapperCategory getCategory() {
        Gson gson = new Gson();
        Type type = new TypeToken<WrapperCategory>() {
        }.getType();
        WrapperCategory wrapperCategory = gson.fromJson(videoService.getJsonFromUrl(URL_CATEGORY), type);
        return wrapperCategory;
    }


    private String urlGenerate(int number) {
        String s = "https://api.redtube.com/?data=redtube.Videos.searchVideos&output=json&thumbsize=big";
        s = s.concat("&tags[]=" + getCategory().getCategories().get(number).getCategory());
        String s2 = s.replaceAll(" ", "");

        return s2;
    }

    public int getRandomNumber() {
        int a = 0; // Начальное значение диапазона - "от"
        int b = 20; // Конечное значение диапазона - "до"

        int randomNumber = a + (int) (Math.random() * b); // Генерация 1-го числа
        return randomNumber;
    }

    /* Регистрация пользователя */
    private void registerUser(long chatId, long userId) {
        messageSender.register(userId, chatId);
    }
}