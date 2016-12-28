package com.gerli.handsomeboy.gerliUnit;

import java.util.ArrayList;

/**
 * Created by HandsomeBoy on 2016/12/1.
 */

public class UnitPackage {
    public class GerliPackage{

    }

    public class TotalPackage extends GerliPackage {
        public TotalPackage(int expense,int income){
            Expense = expense;
            Income = income;
        }
        public int Expense;
        public int Income;
    }

    public class BarChartPackage extends GerliPackage {
        public BarChartPackage(ArrayList<String> weekList, float[] expenseArr){
            this.dateList = (ArrayList<String>) weekList.clone();
            this.expenseArr = expenseArr.clone();
        }
        public ArrayList<String> dateList;
        public float[] expenseArr;
    }

    public class PieChartPackage extends GerliPackage{
        public PieChartPackage(ArrayList<String> weekList, float[] expenseArr){
            this.typeList = (ArrayList<String>) weekList.clone();
            this.expenseArr = expenseArr.clone();
        }
        public ArrayList<String> typeList;
        public float[] expenseArr;
    }

    public class PlanPackage extends GerliPackage{
        public PlanPackage(int[] id,String[] description){
            this.id = id;
            this.description = description;
        }

        public int[] id;
        public String[] description;
    }
}


