package com.example.eautosalon.data;

import java.io.Serializable;

public class ModelVM implements Serializable {

    private String Name;
    private MakeVM Make;

    public  ModelVM(String name, MakeVM make){
        this.Name = name;
        this.Make = make;
    }

    public String getName(){ return this.Name; }
    public MakeVM getMake(){ return this.Make; }

}
