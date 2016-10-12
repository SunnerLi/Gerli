package edu.sunner.parserdevelop.parser;

/**
 * The parser that would parse the string to the record object
 *
 * There're four types of string originally:
 *  1. <spend/deposit> (name_of_item) $(value)
 *  2. <花費/存入> (項目名稱) $(價值)
 *  The parentheses is needless which is shown as the explanation symbol.
 *  The selection inside the <> parentheses cannot be change.
 *  On the other hands, the string inside the () parentheses can be change.
 *  Notice: the space between the parentheses cannot be skip just like the above format
 *
 *  The following shows the correct string:
 *  1. spend drink $100
 *  2. deposit salary $1000
 *  3. 花費 午餐 $100
 *  4. 存入 中獎發票 $2000
 *
 * @author sunner
 * @since 12/10/2016
 */
public class Parser {
}
