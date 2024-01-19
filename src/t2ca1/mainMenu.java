/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package t2ca1;

import java.util.Scanner;

/**
 *
 * @author User
 */

public class mainMenu {

    public final String ANSI_RESET = "\u001B[0m";
    public final String ANSI_BLACK = "\u001B[30m";
    public final String ANSI_RED = "\u001B[31m";
    public final String ANSI_GREEN = "\u001B[32m";
    public final String ANSI_YELLOW = "\u001B[33m";
    public final String ANSI_BLUE = "\u001B[34m";
    public final String ANSI_PURPLE = "\u001B[35m";
    public final String ANSI_CYAN = "\u001B[36m";
    public final String ANSI_WHITE = "\u001B[37m";
    
    public mainLogic mLogic;
    
    private Scanner sc  = new Scanner(System.in);
    
    public mainMenu() {
       // Init
        mLogic = new mainLogic(false);
    }
    
    public void ShowPromt() {
        System.out.println(ANSI_PURPLE + "...-----====== MAIN MENU ======-----..." + ANSI_RESET);
        System.out.println(ANSI_BLUE + "[1] Treat students' data from the file");
        System.out.println(ANSI_GREEN+ "[2] Add students' data via the console");
        System.out.println(ANSI_CYAN+ "[3] Exit");
        System.out.println(ANSI_PURPLE +"--------------------------------------" + ANSI_RESET);
        System.out.println("Please, enter menu command number:");        
    }    

    public void Show() {
        String choice;

        ShowPromt();
        while (true) {
            
            try {
                choice = sc.nextLine(); //choice = (char) System.in.read();

                    switch (choice) {
                        case "1":
                            //clearScreen();
                            mLogic.treatFile();
                            System.out.println("Done. \nTreated lines:" + mLogic.total_count +"\nSuccess records:" +mLogic.successRecords );
                            ShowPromt();
                            break;

                        case "2":
                            //clearScreen();
                            mLogic.treatConsole();
                            ShowPromt();
                            break;
                        
                        case "3":
                            clearScreen();
                            menuShutDown();
                            break;

                        default:
                            System.out.println("Please, enter correct menu number:");
                            break;                            
                    }
                

            } catch (Exception e){
                System.out.println("Ops, enter a number, please!");
            }
        }        
  
    }
    
    public void menuShutDown() {
        System.out.println("Exiting...");
        System.exit(0);
    }
    
    public void clearScreen() {  
        for (int i = 0; i < 50; ++i) System.out.println("");
    }
    
}

