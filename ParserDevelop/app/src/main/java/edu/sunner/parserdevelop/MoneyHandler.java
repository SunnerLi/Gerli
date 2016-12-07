package edu.sunner.parserdevelop;

import edu.sunner.parserdevelop.parser.Parser;
import edu.sunner.parserdevelop.parser.Record;

/**
 * Created by sunner on 10/28/16.
 */
public class MoneyHandler {
    Parser parser = null;

    public MoneyHandler(){
        parser = new Parser();
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

        // 兔曹 (Haven't implement)
        voiceRemind();

        // Save into cloud service
        save2Cloud();

        // Post the article
        postArticle();

        return true;
    }

    public void save2SQL(Record record){
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
