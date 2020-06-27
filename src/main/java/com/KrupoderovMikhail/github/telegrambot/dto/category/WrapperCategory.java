package com.KrupoderovMikhail.github.telegrambot.dto.category;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

/**
 * @author Krupoderov Mikhail
 * @version 1.0
 */
@Data
public class WrapperCategory {

    @SerializedName("categories")
    private List<Categories> categories;

    @SerializedName("categories_gay")
    private List<CategoriesGay> categoriesGay;
}
