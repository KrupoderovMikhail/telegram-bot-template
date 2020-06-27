package com.KrupoderovMikhail.github.telegrambot.dto.videos;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * @author Krupoderov Mikhail
 * @version 1.0
 */
@Data
public class Thumbs {

    @SerializedName("size")
    private String size;

    @SerializedName("width")
    private String width;

    @SerializedName("height")
    private String height;

    @SerializedName("src")
    private String src;
}
