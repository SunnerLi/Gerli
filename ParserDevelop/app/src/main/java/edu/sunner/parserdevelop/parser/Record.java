package edu.sunner.parserdevelop.parser;

import android.util.Log;

import java.net.PortUnreachableException;
import java.util.Date;

/**
 * Created by sunner on 10/12/16.
 */
public class Record {
    /*
        The language related
     */
    // The default constant
    public static final int UND = -1;

    // The static constant of chinese name
    public static final int CHINESE = 2;

    // The static constant of english name
    public static final int ENGLISH = 3;

    // The language variable
    private int _language = UND;

    // The value of the record
    private String _value;

    // The name of the record
    private String _name;

    // The class of the record
    private String _class;

    // The time object of the record
    private Date _date;

    // Constructor
    public Record() {

    }

    /**
     * Fundamental method
     */
    public void set_language(int _lang) {
        this._language = _lang;
    }

    public void set_value(String _value) {
        this._value = _value;
    }

    public void set_name(String name) {
        this._name = name;
    }

    public void set_class(String _class) {
        this._class = _class;
    }

    public void set_date(Date _date) {
        this._date = _date;
    }

    public int get_language() {
        return this._language;
    }

    public String get_value() {
        return this._value;
    }

    public String get_name() {
        return this._name;
    }

    public String get_class() {
        return this._class;
    }

    public Date get_date() {
        return this._date;
    }

    // Show the record value
    public void dump() {
        Log.v("--> Record Log", "Language: " + languageCodeToString(this._language)
            + "\nValue: " + this._value
            + "\nName:  " + this._name
            + "\nClass: " + this._class);
    }

    // Return language Code express
    private String languageCodeToString(int code){
        switch (code){
            case CHINESE:
                return "Chinese";
            case ENGLISH:
                return "English";
            default:
                return "None";
        }
    }
}
