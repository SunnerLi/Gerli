package edu.sunner.parserdevelop.parser;

import android.util.Log;
import android.widget.Toast;

import com.example.huyuxuan.voiceinput.MainActivity;

/**
 * The parser that would parse the string to the record object
 * <p/>
 * There're four types of string originally:
 * 1. (name_of_item) (name_of_class) $(value)
 * 2. (項目名稱) (類別名稱) <+/-></>(價值)
 * The parentheses is needless which is shown as the explanation symbol.
 * The selection inside the <> parentheses cannot be change.
 * On the other hands, the string inside the () parentheses can be change.
 * Notice: the space between the parentheses cannot be skip just like the above format
 * <p/>
 * The following shows the correct string:
 * 1. drink drink +100
 * 2. salary others -1000
 * 3. rice dinner +100ㄌ
 * 4. 中獎發票 others -2000
 *
 * @author sunner
 * @since 12/10/2016
 */
public class Parser {
    // Log tag
    public static final String TAG = "--> Parsing Fail";
    // The status constant to tell if successfully progress
    // Yes
    public static final int SUCCESS = 1;

    // No
    public static final int FAIL = 0;

    // Remote parser
    static RemoteParser remoteParser = new RemoteParser();

    // Result Record object
    Record parseResult = new Record();

    // Sentemce type
    public static final String sentence = remoteParser.sentence;
    public static final String control = remoteParser.control;

    // Constructor
    public Parser() {
    }

    // Work function
    public Record parse(String _string,String stringType) throws NullPointerException{
        // Store the each conponent of the string
        String[] list = _string.split(" ");
        Log.d("Parse ", "string:"+_string);
        // Judge if the string is invalid
        if (list.length != 3) {
            Log.d(TAG, "Reason: the number of element < 3");
            remoteParser.work(_string, stringType);
        }
        if (list[2].charAt(0) != '+' && list[2].charAt(0) != '-') {
            Log.d(TAG, "Reason: lose the +- symbol");
            remoteParser.work(_string, stringType);
        } else {
            String _num = list[2].substring(1, list[2].length());
            if (!isNumeric((_num))) {
                Log.d(TAG, "Reason: the value is invalid");
                remoteParser.work(_string, stringType);
            }else{
                Log.d(TAG, "Start local parsing");
                parseResult = _parse(list);
                parseResult.dump();
            }
        }

        return _parse(list);
    }

    // Parsing
    private Record _parse(String[] list) {
        Record record = new Record();

        // Store the item
        record.setName(list[0]);
        record.setType(list[1]);
        record.setMoney(list[2]);
        return record;
    }

    // Judge if the string is alpha
    public boolean isStringAlpha(String aString) {
        int charCount = 0;
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        if (aString.length() == 0) return false;//zero length string ain't alpha
        for (int i = 0; i < aString.length(); i++) {
            for (int j = 0; j < alphabet.length(); j++) {
                if (aString.substring(i, i + 1).equals(alphabet.substring(j, j + 1))
                    || aString.substring(i, i + 1).equals(alphabet.substring(j, j + 1).toLowerCase()))
                    charCount++;
            }
            if (charCount != (i + 1)) {
                System.out.println("\n**Invalid input! Enter alpha values**\n");
                return false;
            }
        }
        return true;
    }

    // Judge if the string is numberic
    public static boolean isNumeric(String str) {
        try {
            double d = Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    /**
     * Return the record object after the parsing process
     *
     * @return the record object
     */
    public Record get() {
        while (remoteParser.getSemaphore() == 0) ;
        return parseResult;
    }

}
