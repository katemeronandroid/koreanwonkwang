package com.emarkova.koreanwonkwang.data.model;

public class DataLesson {
    private int number;
    private int open;
    private double per;
    private String desc;

    public DataLesson(){}

    public DataLesson(String number, String open, String per, String desc){
        this.number = Integer.parseInt(number);
        this.open = Integer.parseInt(open);
        this.per = Double.parseDouble(per);
        this.desc = desc;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getOpen() {
        return open;
    }

    public void setOpen(int open) {
        this.open = open;
    }

    public double getPer() {
        return per;
    }

    public void setPer(double per) {
        this.per = per;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
