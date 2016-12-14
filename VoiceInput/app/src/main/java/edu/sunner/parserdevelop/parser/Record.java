package edu.sunner.parserdevelop.parser;

import android.accounts.Account;
import android.util.Log;

import com.gerli.handsomeboy.gerliUnit.AccountType;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by sunner on 10/28/16.
 */
public class Record {
    // The id that would used in SQLite
    private static int __id = -1;

    // The name of the transaction
    private static String __name = null;

    // The value of the spending
    private static int __money = -1;
    private static AccountType __type;
    // The time when the deal happen
    private static String __time = null;

    // The other description
    private static String __description = null;


    public Record(){

    }

    public Record(String name, int money) {
        setName(name);
        setMoney(money);
    }

    public Record(JSONObject json) {
        try {
            setId(json.getInt("id"));
            setMoney(json.getInt("money"));
            setTime(json.getString("time"));
            setType(json.getString("type"));
            setDescription(json.getString("description"));
            setName(json.getString("name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setId(int id) {
        this.__id = id;
    }

    public void setName(String name) {
        this.__name = name;
    }

    public void setMoney(int money) {
        this.__money = money;
    }

    public void setMoney(String money) {
        this.__money = Integer.valueOf(money);
    }


    public void setType(int type){
        setType(AccountType.getString(type));
    }

    public void setType(String type){
        switch (type){
            case "breakfast":
                __type = AccountType.BREAKFAST;
                break;
            case "lunch":
                __type = AccountType.LUNCH;
                break;
            case "dinner":
                __type = AccountType.DINNER;
                break;
            case "supper":
                __type = AccountType.SUPPER;
                break;
            case "drink":
                __type = AccountType.DRINK;
                break;
            case "snack":
                __type = AccountType.SNACK;
                break;
            case "clothes":
                __type = AccountType.CLOTHES;
                break;
            case "accessory":
                __type = AccountType.ACCESSORY;
                break;
            case "shoes":
                __type = AccountType.SHOES;
                break;
            case "rent":
                __type = AccountType.RENT_FOR_HOUSE;
                break;
            case "supplies":
                __type = AccountType.DAILY_SUPPLIES;
                break;
            case "payment":
                __type = AccountType.PAYMENT;
                break;
            case "transport":
                __type = AccountType.TRANSPORT;
                break;
            case "fuel":
                __type = AccountType.FUEL;
                break;
            case "automobile":
                __type = AccountType.AUTOMOBILE;
                break;
            case "book":
                __type = AccountType.BOOK;
                break;
            case "stationery":
                __type = AccountType.STATIONERY;
                break;
            case "art":
                __type = AccountType.ART;
                break;
            case "entertainment":
                __type = AccountType.ENTERTAINMENT;
                break;
            case "shopping":
                __type = AccountType.SHOPPING;
                break;
            case "invest":
                __type = AccountType.INVEST;
                break;
            case "gift":
                __type = AccountType.GIFTS;
                break;
            case "others":
            case "income":
                __type = AccountType.OTHERS;
                break;
            default:
                __type = AccountType.OTHERS;
                break;
        }
    }

    public void setTime(String time) {
        this.__time = time;
    }

    public void setDescription(String description) {
        this.__description = description;
    }

    public int getId() {
        return this.__id;
    }

    public String getName() {
        return this.__name;
    }

    public int getMoney() {
        return this.__money;
    }


    public AccountType getType(){
        return this.__type;
    }

    public String getTime() {
        return this.__time;
    }

    public String getDescription() {
        return this.__description;
    }

    // Show the record value
    public void dump() {
        Log.v("--> Record Log", "\nMoney: " + this.getMoney()
                + "\nName:  " + this.getName()
                + "\nType: " + AccountType.getString(this.getType()));
    }
}
