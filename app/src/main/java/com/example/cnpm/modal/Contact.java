package com.example.cnpm.modal;

import java.io.Serializable;

public class Contact implements Serializable {
    String NameDM;
    Integer id=0;


    public Contact() {

    }

    public Contact(String nameDM, Integer id) {
        NameDM = nameDM;
        this.id = id;
    }

    public String getNameDM() {
        return NameDM;
    }

    public void setNameDM(String nameDM) {
        NameDM = nameDM;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id =id;
    }

    @Override
    public String toString() {
        return id++ +"\t\t" +NameDM;
    }
}
