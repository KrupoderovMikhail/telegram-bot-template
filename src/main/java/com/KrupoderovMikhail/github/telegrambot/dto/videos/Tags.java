package com.KrupoderovMikhail.github.telegrambot.dto.videos;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * @author Krupoderov Mikhail
 * @version 1.0
 */
@Data
public class Tags {

    @SerializedName("tag_name")
    private String tag_name;
}
