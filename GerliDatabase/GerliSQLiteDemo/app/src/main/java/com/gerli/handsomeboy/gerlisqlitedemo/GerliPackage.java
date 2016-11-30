package com.gerli.handsomeboy.gerlisqlitedemo;

import java.util.ArrayList;

/**
 * Created by HandsomeBoy on 2016/11/21.
 */

class GerliPackage{

}

class TotalPackage extends GerliPackage{
    public TotalPackage(int expense,int income){
        Expense = expense;
        Income = income;
    }
    public int Expense;
    public int Income;
}

class BarChartPackage extends GerliPackage{
    public BarChartPackage(ArrayList<String> weekList,float[] expenseArr){
        this.dateList = (ArrayList<String>) weekList.clone();
        this.expenseArr = expenseArr.clone();
    }
    public ArrayList<String> dateList;
    public float[] expenseArr;
}

class yearPlanPackage{

}

class monthPlanPackage{

}