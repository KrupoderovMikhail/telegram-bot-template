package com.KrupoderovMikhail.github.telegrambot.dto.category;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * @author Krupoderov Mikhail
 * @version 1.0
 */
@Data
public class Categories {

    @SerializedName("id")
    private String id;

    @SerializedName("category")
    private String category;
}
