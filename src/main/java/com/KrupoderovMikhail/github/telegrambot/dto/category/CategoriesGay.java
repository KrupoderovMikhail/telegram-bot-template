package com.KrupoderovMikhail.github.telegrambot.dto.category;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * @author Krupoderov Mikhail
 */
@Data
public class CategoriesGay {

    @SerializedName("id")
    private String id;

    @SerializedName("category")
    private String category;
}
