package com.KrupoderovMikhail.github.telegrambot.services;

import com.KrupoderovMikhail.github.telegrambot.dto.videos.WrapperVideos;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Krupoderov Mikhail
 * @version 1.0
 */
@Service
public class VideoService {

    public WrapperVideos getVideo(String url) {
        Gson gson = new Gson();
        Type type = new TypeToken<WrapperVideos>() {
        }.getType();
        WrapperVideos wrapperVideos = gson.fromJson(getJsonFromUrl(url), type);
        return wrapperVideos;
    }

    /* Достает json по url */
    public InputStreamReader getJsonFromUrl(String site_url) {
        URL url = null;
        try {
            url = new URL(site_url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        InputStreamReader reader = null;
        try {
            reader = new InputStreamReader(url.openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return reader;
    }

//    public void sendVideos(Long chatId, int number, String url) {
//        for (int i = 0; i < number; i++) {
//            sendMessage(chatId, (videoService.getVideo(url).getVideos().get(i).getVideo().getTitle() + " \n " +
//                    videoService.getVideo(url).getVideos().get(i).getVideo().getDuration() + " \n " +
//                    videoService.getVideo(url).getVideos().get(i).getVideo().getEmbed_url() + " \n" + " \n "));
//        }
//    }
}
