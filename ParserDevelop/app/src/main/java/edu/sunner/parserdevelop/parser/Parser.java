package edu.sunner.parserdevelop.parser;

import android.util.Log;

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
 * 1. drink 食 ㄦ-100
 * 2. salary 無 +1000
 * 3. 午餐 食 -100
 * 4. 中獎發票 無 +2000
 *
 * @author sunner
 * @since 12/10/2016
 */
public class Parser {
    // The status constant to tell if successfully progress
    // Yes
    public static final int SUCCESS = 1;

    // No
    public static final int FAIL = 0;

    // Constructor
    public Parser() {
    }

    // Work function
    public int parse(String _string) {
        // Store the each conponent of the string
        String[] list = _string.split(" ");

        // Judge if the string is invalid
        if (list.length != 3 ) {
            return FAIL;
        }
        if (list[2].charAt(1) != '+' && list[2].charAt(1) != '-') {
            return FAIL;
        } else {
            String _num = list[2].substring(1, list[2].length());
            if (!_num.matches("-?\\d+(\\.\\d+)?")) {
                return FAIL;
            }
        }


        return SUCCESS;
    }
}
