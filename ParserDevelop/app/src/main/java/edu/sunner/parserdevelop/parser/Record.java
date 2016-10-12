package edu.sunner.parserdevelop.parser;

import java.net.PortUnreachableException;

/**
 * Created by sunner on 10/12/16.
 */
public class Record {
    /*
        The status related
     */
    // The static constants (save money)
    public static final int IN = 0;

    // The static constants (spend money)
    public static final int OUT = 1;

    // The default status constant
    public static final int UND = -1;

    // The type of the record
    public int status = UND;

    /*
        The language related
     */
    // The static constant of chinese name
    public static final int CHINESE = 2;

    // The static constant of english name
    public static final int ENGLISH = 3;

    // The language variable
    public int language = UND;

    // The name of the record
    public String name;

    // The value of the record
    public int value;

    // Constructor
    public Record(int _status, String _name, int _value){
        if (_status == IN || _status == OUT)
            setStatus(_status);
        setName(_name);
        setValue(_value);
    }

    /**
     * Fundamental method
     */
    public void setStatus(int statusNumber){
        this.status = statusNumber;
    }

    public void setLanguage(int _lang){
        this.language = _lang;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setValue(int _value){
        this.value = _value;
    }

    public int getStatus(){
        return this.status;
    }

    public int getLanguage(){
        return this.language;
    }

    public String getName(){
        return this.name;
    }

    public int getValue(){
        return this.value;
    }
}
