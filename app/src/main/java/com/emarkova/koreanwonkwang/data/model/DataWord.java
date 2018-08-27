package com.emarkova.koreanwonkwang.data.model;

/**
 * Data layer model
 */
public class DataWord {
    private String id;
    private String koWord;
    private String ruWord;

    public String getRuWord() {
        return ruWord;
    }

    public void setRuWord(String ruWord) {
        this.ruWord = ruWord;
    }

    public String getKoWord() {
        return koWord;
    }

    public void setKoWord(String koWord) {
        this.koWord = koWord;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
