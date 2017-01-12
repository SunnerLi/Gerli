package com.gerli.gerli.parser;


import android.util.Log;

import com.gerli.handsomeboy.gerliUnit.CalendarManager;
import com.gerli.handsomeboy.gerlisqlitedemo.GerliDatabaseManager;

import java.util.Calendar;

/**
 * Created by sunner on 10/28/16.
 */
public class MoneyHandler {
    Parser parser = null;
    GerliDatabaseManager manager;

    public MoneyHandler(GerliDatabaseManager mana) {
        parser = new Parser();
        manager = mana;
    }

    /**
     * The main function of money handler
     * Notice, the parser would do the parsing work asynchronously.
     * So if you want to read the parsing result,
     * you should call the get function
     *
     * @param string  the string want to parse
     * @param __year  the value of year
     * @param __month the value of month
     * @param __day   the value of day
     * @return if the work do well
     */
    public boolean work(String string, int __year, int __month, int __day) {
        parser.parse(string, Parser.sentence);
        Log.d("--> MoneyHandler", "" + __year + __month + __day);
        Record record = parser.get();
        return work(record, __year, __month, __day);
    }

    /**
     * The wrapper work function
     *
     * @param string the string want to parse
     * @return if the work do well
     */
    public boolean work(String string) {
        Calendar calendar = Calendar.getInstance();
        return work(string,
            calendar.get(Calendar.YEAR),
            Math.max(1, calendar.get(Calendar.MONTH)),
            calendar.get(Calendar.DATE));
    }

    /**
     * 做parsing完後的剩餘工作
     * 這個部份裡面的函式需要大家幫忙完成
     *
     * @param record  the result object after parsing
     * @param __year  the value of year
     * @param __month the value of month
     * @param __day   the value of day
     * @return if the work do well
     */
    public boolean work(Record record, int __year, int __month, int __day) {
        // Check if parse fail
        if (record == null) {
            Log.d("--> MoneyHandler", "2");
            return false;
        }
        record.dump();

        // Save the result
        save2SQL(record, __year, __month, __day);

        // Save into cloud service
        save2Cloud();

        return true;
    }

    /**
     * Save the record object into SQLite
     *
     * @param record  The record object you want to store
     * @param __year  the value of year
     * @param __month the value of month
     * @param __day   the value of day
     */
    public void save2SQL(Record record, int __year, int __month, int __day) {
        record.dump();

        // Store the information if the record object is just a default instance
        if (record.getName() != null && record.getMoney() != -1) {
            manager.insertAccount(record.getName(),
                record.getMoney(),
                record.getType(),
                CalendarManager.getDay(__year, __month, __day), null);
        }
    }

    public void save2Cloud() {
        // Haven't implement
    }

    public String getSentence() {
        return parser.getSentence();
    }
}
