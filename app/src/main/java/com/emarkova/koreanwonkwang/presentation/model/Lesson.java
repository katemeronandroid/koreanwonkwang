package com.emarkova.koreanwonkwang.presentation.model;

/**
 * Presentation layer model
 */
public class Lesson {
    private int number;
    private int open;
    private double per;
    private String desc;

    public Lesson(){}

    public Lesson(String number, String open, String per, String desc){
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

    public boolean isOpen() {
        if (open == 1)
            return true;
        else
            return false;
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

    public int getOpen() {
        return open;
    }

    public void setOpen(int open) {
        this.open = open;
    }
}
