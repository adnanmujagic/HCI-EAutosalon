package com.example.eautosalon.data;

import java.io.Serializable;
import java.util.List;

public class MakeVM implements Serializable {

    private int MakeId;
    private String Name;

    public  MakeVM(int makeId, String name){
        this.MakeId = MakeId;
        this.Name = name;
    }

    public int getId(){ return this.MakeId; }
    public String getName(){ return this.Name; }

}
