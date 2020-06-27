package com.KrupoderovMikhail.github.telegrambot.command;

import com.KrupoderovMikhail.github.telegrambot.dto.videos.Thumbs;
import com.KrupoderovMikhail.github.telegrambot.dto.videos.VideoDTO;
import com.KrupoderovMikhail.github.telegrambot.dto.videos.Videos;
import com.KrupoderovMikhail.github.telegrambot.services.MessageSender;
import com.KrupoderovMikhail.github.telegrambot.services.VideoService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Krupoderov Mikhail
 * @version 1.0
 */
public class FindCommand {
    private static final String URL_SEARCH_VIDEO = "https://api.redtube.com/?data=redtube.Videos.searchVideos&output=json&tags[]=Teen&thumbsize=medium&search=";
    private static final String URL = "https://api.redtube.com/?data=redtube.Videos.searchVideos&output=json&tags[]=Teen&thumbsize=medium&search=hard";

    private static final String ALL = "https://api.redtube.com/?data=redtube.Videos.searchVideos&output=json&thumbsize=big";
    private List<Videos> videos;
    private ArrayList<Videos> arraysTitle;
    private ArrayList<Videos> arraysEmbededUrl;
    private ArrayList<Videos> arraysThumbs;

    @Autowired
    public MessageSender sender;

    @Autowired
    public VideoService videoService;




    public List<VideoDTO> findCommand(String message, List<Videos> videos) {
        System.out.println("TEST");

        List<VideoDTO> videoDTOS = new ArrayList<>();

        // Метод возвращает массивы данных по нашему запросу (заголовки, ссылки на видео и картинки)
//        setArraysData(message);
//        String searchingMessage = message.replaceAll("/test ", "");

//        String url = URL_SEARCH_VIDEO + searchingMessage;
//        System.out.println(url);

//        List<Videos> videos = videoService.getVideo(ALL).getVideos();
        for (int i = 0; i < 5; i++) {

            String title = videos.get(i).getVideo().getTitle();
//            String embeded_url = videos.get(i).getVideo().getEmbed_url();
            String url = videos.get(i).getVideo().getUrl();
            String thumbs = videos.get(i).getVideo().getThumb();
            String duration = videos.get(i).getVideo().getDuration();
            String img = videos.get(i).getVideo().getThumbs().get(0).getSrc();
//            List<Thumbs> images = videos.get(i).getVideo().getThumbs();

            VideoDTO video = new VideoDTO();
            video.setTitle(title);
            video.setUrl(url);
            video.setThumbs(img);
            video.setDuration(duration);
//            video.setThumbsList(images);

            videoDTOS.add(video);
//            SendPhoto sendPhoto = new SendingPhoto().sendPhoto(message, caption, href, urlImg); // Отправляем фото с подписью и ссылкой
//            arrayListSendPhoto.add(sendPhoto);
            System.out.println(title + "\n" + url + "\n" + thumbs + "\n");
        }

        return videoDTOS;
    }

//     messageSender(chatId, videos.get(random).getVideo().getTitle() + " \n " +
//            videos.get(random).getVideo().getEmbed_url() + " \n ");
//    sendImage(chatId, videos.get(random).getVideo().getThumbs().get(0).getSrc());

//    List<Videos> videos = videoService.getVideo(ALL_VIDEO).getVideos();
//    public void setArraysData(String message) {
//
////        // Очищаем массивы с заголовками, фото и ссылками перед новым поисковым запросом
////        if (!arraysTitle.isEmpty()) {
////            arraysTitle.clear();
////        }
////        if (!arraysEmbededUrl.isEmpty()) {
////            arraysEmbededUrl.clear();
////        }
////        if (!arraysThumbs.isEmpty()) {
////            arraysThumbs.clear();
////        }
//
//        // Поисковый запрос без /http. По этим поисковым словам будем выдергивать видео из поиска сайта
////        String messageText = message;
//
//        System.out.println(searchingMessage);
//    }

}
