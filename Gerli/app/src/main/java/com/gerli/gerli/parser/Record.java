package com.gerli.gerli.parser;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * The flow object that contain the transaction information
 *
 * @author SunnerLi
 * @revise 10/29/2016.
 */
public class Record {
    // The id that would used in SQLite
    private static int __id = -1;

    // The name of the transaction
    private static String __name = null;

    // The value of the spending
    private static int __money = -1;

    // The type of the transection
    private static int __type = -1;

    // The time when the deal happen
    private static String __time = null;

    // The other description
    private static String __description = null;

    /**
     * Constructor
     */
    public Record() {
    }

    /**
     * Constructor with parameters
     * @param name the name which you want to assign
     * @param money the value which you want to assign
     */
    public Record(String name, int money) {
        setName(name);
        setMoney(money);
    }

    /**
     * Constructor with JSON format
     * @param json the json string description
     */
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

    public void setType(int type) {
        this.__type = type;
    }

    public void setType(String type) {
        this.__type = Integer.valueOf(type);
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

    public int getType() {
        return this.__type;
    }

    public String getTime() {
        return this.__time;
    }

    public String getDescription() {
        return this.__description;
    }

    /**
     * Show the record value
     */
    public void dump() {
        Log.v("--> Record Log", "\nMoney: " + this.getMoney()
            + "\nName:  " + this.getName()
            + "\nType: " + this.getType());
    }
}
