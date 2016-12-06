package edu.sunner.parserdevelop;

import com.gerli.handsomeboy.gerliUnit.CalendarManager;
import com.gerli.handsomeboy.gerlisqlitedemo.GerliDatabaseManager;

import edu.sunner.parserdevelop.parser.Parser;
import edu.sunner.parserdevelop.parser.Record;

/**
 * Created by sunner on 10/28/16.
 */
public class MoneyHandler {
    Parser parser = null;
    GerliDatabaseManager manager;

    public MoneyHandler(GerliDatabaseManager mana){
        parser = new Parser();
        manager = mana ;
    }

    public boolean work(String string){
        return work(parser.parse(string));
    }

    public boolean work(Record record){
        // Check if parse fail
        if (record == null){
            return false;
        }
        record.dump();

        // Save the result
        save2SQL(record);

        // 兔曹 (Haven't implement)
        voiceRemind();

        // Save into cloud service
        save2Cloud();

        // Post the article
        postArticle();

        return true;
    }

    public void save2SQL(Record record){
        manager.insertAccount(record.__getName(),record.__getMoney(),record.__getType(), CalendarManager.getTime(),null);
        // Haven't implement
    }

    public void voiceRemind(){
        // Haven't implement
    }

    public void save2Cloud(){
        // Haven't implement
    }

    public void postArticle(){
        // Haven't implement
    }
}
