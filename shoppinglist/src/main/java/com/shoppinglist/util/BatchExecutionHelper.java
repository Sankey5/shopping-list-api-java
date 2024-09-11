package com.shoppinglist.util;

import java.sql.Statement;

public class BatchExecutionHelper {

    public static boolean failedBatchExecution(int[] retVals) {

        for(int retVal : retVals){
            if (retVal == Statement.EXECUTE_FAILED || retVal == Statement.SUCCESS_NO_INFO)
                return true;
        }

        return false;
    }

    public static boolean successfulBatchExecution(int[] retVals) {

        for(int retVal : retVals){
            if (retVal == Statement.EXECUTE_FAILED || retVal == Statement.SUCCESS_NO_INFO)
                return false;
        }

        return true;
    }
}
