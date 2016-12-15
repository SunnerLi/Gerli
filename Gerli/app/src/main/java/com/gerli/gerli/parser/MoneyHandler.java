package com.gerli.gerli.parser;


import com.gerli.handsomeboy.gerliUnit.CalendarManager;
import com.gerli.handsomeboy.gerlisqlitedemo.GerliDatabaseManager;

/**
 * Created by sunner on 10/28/16.
 */
public class MoneyHandler {
    Parser parser = null;
    GerliDatabaseManager manager;

    public MoneyHandler(GerliDatabaseManager mana){
        parser = new Parser();
        manager = mana;
    }

    /**
     * The main function of money handler
     * Notice, the parser would do the parsing work asynchronousㄗㄠly.
     * So if you want to read the parsing result,
     * you should call the get function
     *
     * @param string the string want to parse
     * @return if the work do well
     */
    public boolean work(String string){
        parser.parse(string, Parser.sentence);
        Record record = parser.get();
        return work(record);
    }

    /**
     * 做parsing完後的剩餘工作
     * 這個部份裡面的函式需要大家幫忙完成
     *
     * @param record the result object after parsing
     * @return if the work do well
     */
    public boolean work(Record record){
        // Check if parse fail
        if (record == null){
            return false;
        }
        record.dump();

        // Save the result
        save2SQL(record);

        // Save into cloud service
        save2Cloud();

        return true;
    }

    /**
     * Save the record object into SQLite
     *
     * @param record The record object you want to store
     */
    public void save2SQL(Record record){
        manager.insertAccount(record.getName(),
            record.getMoney(),
            record.getType(),
            CalendarManager.getTime(),null);
    }

    public void save2Cloud(){
        // Haven't implement
    }
}
