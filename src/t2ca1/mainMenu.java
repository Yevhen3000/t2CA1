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
        System.out.println(ANSI_PURPLE +"#  " + ANSI_CYAN + "[4] Help"  + ANSI_PURPLE +"                                #");
        System.out.println(ANSI_PURPLE +"#                                        " + ANSI_PURPLE +"  #");
        System.out.println(ANSI_PURPLE +"############################################" + ANSI_RESET);
        System.out.println("Please, enter the menu command number:");        
    }    

    public void Show() {                // Main menu cycle, hit 3 to exit the programm
        String choice;                  // Holds user's input string
        
        while (true) {                  // General menu cycle
            ShowPromt();                // Show main menu
            try {
                choice = sc.nextLine(); //choice = (char) System.in.read(); as variant of implementation
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
                        
                    case "4":
                        menuShowHelp();        // Show the rules
                        break;                        

                    default:                   // User has entered string different from 1,2,3
                        System.out.println("Please, enter the correct menu number:");
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
    
    public void menuShowHelp(){
        System.out.println("Welcome to students' data converter, ver. "+mLogic.version+"!");
        System.out.println("=================================================");
        System.out.println("The format of the input file, every student record must consist of three lines:\n");
        System.out.println(ANSI_BLUE + "Line 1 – <First Name> <Second Name>\n" + ANSI_RESET +
                "a) the first name must be letters only; \n" +
                "b) The second name can be letters and/or numbers and must be separated from the first name by a single space; \n");
        System.out.println(ANSI_BLUE + "Line 2 – <Number of classes>\n" + ANSI_RESET +
                "c) the number of classes must be an integer value between 1 and 8 (inclusive) \n" );
        System.out.println(ANSI_BLUE + "Line 3 – <Student number>\n" + ANSI_RESET +
                "d) the student “number” must be a minimum of 6 characters with the first 2 characters being numbers, the 3rd  and 4th characters (and possibly 5th ) \nbeing a letter, and everything after the last letter character being a number.\n" +
                " - the student number year is at least 2020 (i.e. that the number starts with 20 or higher) \n" +
                " - the number after the letter(s) is reasonable – i.e. that it is between 1 and 200\n");
        System.out.println("=================================================");
        System.out.println("Output format is:");
        System.out.println("Line 1: <Student number> - <Second Name>");
        System.out.println("Line 2: <Workload>\n\n");
    }
    
}

