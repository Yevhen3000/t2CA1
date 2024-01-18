package t2ca1;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author User
 */

public class mainLogic {
    private String myInFile = "students.txt";
    private String myOutFile = "status.txt";
    
    private BufferedWriter outputF;
    private BufferedReader inputF;
    private int total_count = 0;
    private boolean verbal = true;
    private int local_count = 1;

    public mainLogic(){
        // Init
    }    
    
    public boolean treatFile(){
       
        total_count = 0;
        
        try {
            inputF = new BufferedReader(new FileReader(myInFile)); // Try open the file
        } catch (Exception e) {
            System.out.println("Open file error: " + e );
            return false;
        }

//        Sam Weiss
//        5
//        22DIP1123
//        Steve Rodgers
//        7
//        20MSC1914
        
        String line;
        
        int index_firstName  = 0;
        int index_secondName  = 1;
        boolean canGoOn = true;
        try {
            line = inputF.readLine();
            while (line != null) {
                
                if (!line.trim().isEmpty()) { //Check for empty line and skip it

                    if (verbal) System.out.print(local_count + ") " + line + " "); // Show line for user and apopriate error if there is
                    
                    switch (local_count) {
                        case 1:
                            String[] studentCredians = line.split(" ");
                            
                            if (verbal) System.out.print("Line 1");
                            
                            // condition 1b check
                            if (studentCredians.length<=1) {
                                 System.out.println("[Error] the second name must be separated from the first name by a single space");
                                 canGoOn = false;
                                 //continue;
                            }

                            // condition 1a check 
                            if (!(studentCredians[index_firstName].matches("\\w+\\.?"))) {
                                System.out.println("[Error] the first name must be letters only");
                                canGoOn = false;
                                //continue;
                            }

                            // condition 1b check 
                            if (!(studentCredians[index_secondName].matches("[A-Z0-9]+"))) {
                                System.out.println("[Error] the second name can be letters and/or numbers");
                                canGoOn = false;
                                //continue;
                            }                            
                            break;
                            
                        case 2: // condition 1c: the number of classes must be an integer value between 1 and 8 (inclusive)
                            if (verbal) System.out.println("Line 2");
                            
                            break;
                        
                        case 3: // 1d)	the student “number” must be a minimum of 6 characters with the first 2 characters being numbers, 
                                // the 3rd  and 4th characters (and possibly 5th ) being a letter, and everything after the last letter character being a number. 
                            if (verbal) System.out.println("Line 3");
                            
                            break;                            
                    }
                    

                    local_count++;
                    if(local_count>3) {
                        local_count = 1;
                        System.out.println("");
                    }

                }
                
                if (canGoOn) System.out.println(" [OK]"); 
                line = inputF.readLine(); // Read next line from the file
                total_count++;
            }
            
        } catch (Exception e) {
            System.out.println("Reading error: " + e );
        }
        //  outputF  = new BufferedWriter(new FileWriter(myOutFile, false));
        
        return true;
    }
    
    public String getUserInput(String message){
        String useInput = "";
        Scanner sc  = new Scanner(System.in);
        boolean bInputOk = false;
        while (!bInputOk) {
            if (!message.equalsIgnoreCase(""))System.out.println(message);
            try {
                useInput = sc.nextLine();
                bInputOk = true;
            } catch (Exception e){
                System.out.println("Ops, something went wrong!");
            }
        }
        return useInput;
    }
    
    public void makeTestFile(){
        try {
            outputF  = new BufferedWriter(new FileWriter(myInFile, false));
            outputF.write("Sam Weiss\n" +
                        "5\n" +
                        "22DIP1123\n" +
                        "Steve Rodgers\n" +
                        "7\n" +
                        "20MSC1914\n" +
                        "");
            outputF.close();
        } catch (Exception e){
            System.out.println("File write error!");
            System.exit(-1);
        }
    }
    
    
}
