package com.emarkova.koreanwonkwang.data.api;

import com.google.gson.annotations.SerializedName;

public class TraslateResponse {

    @SerializedName("text")
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
