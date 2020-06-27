package com.KrupoderovMikhail.github.telegrambot.dto;

import com.KrupoderovMikhail.github.telegrambot.dto.videos.Videos;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

/**
 * @author Krupoderov Mikhail
 * @version 1.0
 */
@Data
public class Test {

    @SerializedName("videos")
    private List<Videos> videos;

    @SerializedName("count")
    private String count;
}
