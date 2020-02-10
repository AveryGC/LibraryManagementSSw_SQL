package com.SS.training.Input;

public class InputValidation {
    public static boolean checkInput(int min, int max, String input){
        try {
            int parsed = Integer.parseInt(input);
            if(parsed <= max && parsed >= min)
                return true;
            else{
                System.out.println("!!!!Please enter proper input option!!!!");
                return false;
            }
        }
        catch (Exception e){
            System.out.println("!!!Improper input format!!!");
            return false;
        }
    }
}
