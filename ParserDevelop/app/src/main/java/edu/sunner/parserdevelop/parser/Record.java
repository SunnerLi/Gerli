package edu.sunner.parserdevelop.parser;

import android.util.Log;

/**
 * Created by sunner on 10/28/16.
 */
public class Record {
    private static int id = -1;
    private static String name = null;
    private static int money = -1;
    private static int type = -1;
    private static String time = null;
    private static String description = null;

    public Record(){

    }

    public Record(String name, int money){
        __setName(name);
        __setMoney(money);
    }

    public void __setId(int id){
        this.id = id;
    }

    public void __setName(String name){
        this.name = name;
    }

    public void __setMoney(int money){
        this.money = money;
    }

    public void __setMoney(String money){
        this.money = Integer.valueOf(money);
    }

    public void __setType(int type){
        this.type = type;
    }

    public void __setType(String type){
        this.type = Integer.valueOf(type);
    }

    public void __setTime(String time){
        this.time = time;
    }

    public void __setDescription(String description){
        this.description = description;
    }

    public int __getId(){
        return this.id;
    }

    public String __getName(){
        return this.name;
    }

    public int __getMoney(){
        return this.money;
    }

    public int __getType(){
        return this.type;
    }

    public String __getTime(){
        return this.time;
    }

    public String __getDescription(){
        return this.description;
    }

    // Show the record value
    public void dump() {
        Log.v("--> Record_prev Log", "\nMoney: " + this.__getMoney()
            + "\nName:  " + this.__getName()
            + "\nType: " + this.__getType());
    }
}
