package com.example.cnpm.Data;

public class DataClass {
    private String dataTitle;
    private String key;
    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public String getDataTitle() {
        return dataTitle;
    }
    public DataClass(String dataTitle) {
        this.dataTitle = dataTitle;
    }
    public DataClass(){
    }
}

