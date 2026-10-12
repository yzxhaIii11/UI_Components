package com.example.ui_components;

/**
 * 动物数据模型：名称、英文名、图片资源。
 * 实验3-5 RecyclerView 的列表项数据。
 */
public class Animal {

    private final String name;        // 中文名
    private final String englishName; // 英文名
    private final int imageRes;       // 图片资源 id

    public Animal(String name, String englishName, int imageRes) {
        this.name = name;
        this.englishName = englishName;
        this.imageRes = imageRes;
    }

    public String getName() {
        return name;
    }

    public String getEnglishName() {
        return englishName;
    }

    public int getImageRes() {
        return imageRes;
    }
}
