package com.example.projectcn.model;

import com.google.gson.annotations.SerializedName;

public class Category {
    @SerializedName("id")
    private Long id;

    @SerializedName("nameCategory")
    private String nameCategory;

    @SerializedName("reading")
    private boolean Isreading;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameCategory() {
        return nameCategory;
    }

    public void setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
    }
}
