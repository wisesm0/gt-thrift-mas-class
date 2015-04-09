package com.example.cxz.gtthrift;


import java.io.Serializable;

public class APIResponder implements Serializable{
    private int eCode = -1;
    private String eMessage;
    APIResponder(){}
    APIResponder(int eCode, String eMessage){
        this.eMessage=eMessage;
        this.eCode = eCode;
    }
   public String getErrorMessage(){
        return this.eMessage;
    }
   public int getECode() {return eCode;}

}
