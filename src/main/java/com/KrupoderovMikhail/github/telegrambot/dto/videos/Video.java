package com.KrupoderovMikhail.github.telegrambot.dto.videos;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

/**
 * @author Krupoderov Mikhail
 * @version 1.0
 */
@Data
public class Video {

    @SerializedName("duration")
    private String duration;

    @SerializedName("views")
    private String views;

    @SerializedName("video_id")
    private String video_id;

    @SerializedName("rating")
    private String rating;

    @SerializedName("ratings")
    private String ratings;

    @SerializedName("title")
    private String title;

    @SerializedName("url")
    private String url;

    @SerializedName("embed_url")
    private String embed_url;

    @SerializedName("default_thumb")
    private String default_thumb;

    @SerializedName("thumb")
    private String thumb;

    @SerializedName("publish_date")
    private String publish_date;

    @SerializedName("thumbs")
    private List<Thumbs> thumbs;

    @SerializedName("tags")
    private List<Tags> tags;
}
