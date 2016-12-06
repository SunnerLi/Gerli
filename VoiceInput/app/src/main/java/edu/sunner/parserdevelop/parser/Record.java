package edu.sunner.parserdevelop.parser;

import android.accounts.Account;
import android.util.Log;

import com.gerli.handsomeboy.gerliUnit.AccountType;

/**
 * Created by sunner on 10/28/16.
 */
public class Record {
    private static int id = -1;
    private static String name = null;
    private static int money = -1;
    private static AccountType myType;
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
        __setType(AccountType.getString(type));
    }

    public void __setType(String type){
        switch (type){
            case "breakfast":
                myType = AccountType.BREAKFAST;
                break;
            case "lunch":
                myType = AccountType.LUNCH;
                break;
            case "dinner":
                myType = AccountType.DINNER;
                break;
            case "supper":
                myType = AccountType.SUPPER;
                break;
            case "drink":
                myType = AccountType.DRINK;
                break;
            case "snack":
                myType = AccountType.SNACK;
                break;
            case "clothes":
                myType = AccountType.CLOTHES;
                break;
            case "accessory":
                myType = AccountType.ACCESSORY;
                break;
            case "shoes":
                myType = AccountType.SHOES;
                break;
            case "rent":
                myType = AccountType.RENT_FOR_HOUSE;
                break;
            case "supplies":
                myType = AccountType.DAILY_SUPPLIES;
                break;
            case "payment":
                myType = AccountType.PAYMENT;
                break;
            case "transport":
                myType = AccountType.TRANSPORT;
                break;
            case "fuel":
                myType = AccountType.FUEL;
                break;
            case "automobile":
                myType = AccountType.AUTOMOBILE;
                break;
            case "book":
                myType = AccountType.BOOK;
                break;
            case "stationery":
                myType = AccountType.STATIONERY;
                break;
            case "art":
                myType = AccountType.ART;
                break;
            case "entertainment":
                myType = AccountType.ENTERTAINMENT;
                break;
            case "shopping":
                myType = AccountType.SHOPPING;
                break;
            case "invest":
                myType = AccountType.INVEST;
                break;
            case "gift":
                myType = AccountType.GIFTS;
                break;
            case "others":
            case "income":
                myType = AccountType.OTHERS;
                break;
        }
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

    public AccountType __getType(){
        return this.myType;
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
