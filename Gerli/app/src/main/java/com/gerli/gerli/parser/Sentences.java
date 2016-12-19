package com.gerli.gerli.parser;

/**
 * Created by sunner on 12/15/16.
 */
public class Sentences {
    public String encourage[] = {
        "You did the good job !!", "Nice habit !!"
    };
    public String critical[] = {
        "You should improve by yourself !!", "Why did you do this !?"
    };

    /**
     * The exception we define to prevent the length of the both side are not equal
     */
    public class LengthNoEqualException extends Exception {
        public LengthNoEqualException(String message){
            super(message);
        }
    }

    /**
     * Constructor that would check if there is exception raising
     */
    public Sentences() {
        if (encourage.length != critical.length) {
            try {
                throw new LengthNoEqualException("Length isn't equal");
            } catch (LengthNoEqualException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Get the size of the sentences
     *
     * @return The value of the size
     */
    public int size(){
        return encourage.length;
    }

    /**
     * The wrapper function to get the random encourage string
     * The index is determined randomly
     *
     * @return The encourage string
     */
    public String getEncourage() {
        return getEncourage((int) Math.floor(Math.random()*size()));
    }

    /**
     * Get the encourage string for the specific index
     *
     * @param index The index of the string you want to get
     * @return The target string
     */
    public String getEncourage(int index) {
        return encourage[index];
    }

    /**
     * The wrapper function to get the random encourage string
     * The index is determined randomly
     *
     * @return The critical string
     */
    public String getCritical() {
        return getCritical((int) Math.floor(Math.random()*size()));
    }

    /**
     * Get the critical string for the specific index
     *
     * @param index The index of the string you want to get
     * @return The target string
     */
    public String getCritical(int index) {
        return critical[index];
    }
}
