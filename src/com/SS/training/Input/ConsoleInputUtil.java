package com.SS.training.Input;

import java.util.Scanner;

public class ConsoleInputUtil {
    private static ConsoleInputUtil instance = null;
    private Scanner scanner;

    private ConsoleInputUtil() {
        scanner = new Scanner(System.in);
    }

    public static ConsoleInputUtil getInstance(){
        if (instance==null)
            instance= new ConsoleInputUtil();
        return  instance;
    }

    public Scanner getScanner() {
        return scanner;
    }
}
