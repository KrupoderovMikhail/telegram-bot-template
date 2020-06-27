package com.KrupoderovMikhail.github.telegrambot.dto.videos;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

/**
 * @author Krupoderov Mikhail
 * @version 1.0
 */
@Data
public class WrapperVideos {

    @SerializedName("videos")
    private List<Videos> videos;

    @SerializedName("count")
    private String count;
}
