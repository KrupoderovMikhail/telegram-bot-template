package com.KrupoderovMikhail.github.telegrambot.dto.videos;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * @author Krupoderov Mikhail
 * @version 1.0
 */
@Data
public class Videos {

    @SerializedName("video")
    private Video video;
}
