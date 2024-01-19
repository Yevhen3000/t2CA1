/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package t2ca1;

import java.util.Scanner;

/**
 * @date    18/01/24
 * @author  Yevhen Kuropiatnyk
 * @email   evgeniy.kuropyatnik@gmail.com
 * @student sba23066
 */

public class mainMenu {
    // Colors to brighten up the gray routine
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
        mLogic = new mainLogic(true );
    }
    
    public void ShowPromt() { // Show main menu to user and promt
        System.out.println(ANSI_PURPLE + "################# MAIN MENU ################" + ANSI_RESET);
        System.out.println(ANSI_PURPLE +"#                                        " + ANSI_PURPLE +"  #");
        System.out.println(ANSI_PURPLE +"#  " + ANSI_BLUE + "[1] Treat students' data from the file" + ANSI_PURPLE +"  #");
        System.out.println(ANSI_PURPLE +"#  " + ANSI_GREEN+ "[2] Add students' data via the console"  + ANSI_PURPLE +"  #");
        System.out.println(ANSI_PURPLE +"#  " + ANSI_RED + "[3] Exit"  + ANSI_PURPLE +"                                #");
        System.out.println(ANSI_PURPLE +"#                                        " + ANSI_PURPLE +"  #");
        System.out.println(ANSI_PURPLE +"############################################" + ANSI_RESET);
        System.out.println("Please, enter menu command number:");        
    }    

    public void Show() {                // Main menu cycle, hit 3 to exit the programm
        String choice;                  // Holds user's input string
        
        while (true) {                  // General menu cycle
            ShowPromt();                // Show main menu
            try {
                choice = sc.nextLine(); //choice = (char) System.in.read();
                switch (choice) {
                    case "1":
                        mLogic.treatFile(true); // Process data from the file
                        System.out.println("Done. Treated lines:" + mLogic.total_count +", success records:" +mLogic.successRecords );
                        break;

                    case "2":
                        mLogic.treatConsole(); // Process data from user's input (console)
                        break;

                    case "3":
                        menuShutDown();        // Exit and go home
                        break;

                    default:                   // User has entered string different from 1,2,3
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
        sc.close();             // Release console resource
        System.exit(0);
    }
    
    public void clearScreen() {  //Simulation of CLS
        for (int i = 0; i < 50; ++i) System.out.println("");
    }
    
}

