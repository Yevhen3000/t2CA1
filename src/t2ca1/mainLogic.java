package t2ca1;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    private boolean canGoOn;
    private boolean canWrite;
    
    private String Regex_combination_of_letters_or_numbers = "^[a-zA-Z0-9]+$"; //"^(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9]+$";
    private String Regex_just_letters = "^(?=.*[a-zA-Z])[a-zA-Z]+$"; //"\\w+\\.?"
    private String Regex_just_numbers = "^(?=.*[0-9])[0-9]+$";
    
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
        
        int studentClass;
        String studentWorkload ="";
        String studentSecondName ="";
        String StudentNumber = "";
                
        try {
            line = inputF.readLine();
            while (line != null) {
                
                if (!line.trim().isEmpty()) { //Check for empty line and skip it
                    canWrite = true; 
                    if (verbal) System.out.print(local_count + ") " + line + " "); // Show line for user and apopriate error if there is
                    
                    switch (local_count) {
                        case 1:
                            String[] studentCredians = line.trim().split(" ");
                            studentSecondName = studentCredians[index_secondName];
                            // condition 1b check
                            if (studentCredians.length<=1) {
                                logShowErr("[Error] the second name must be separated from the first name by a single space");
                            } else {
                                // condition 1a check 
                                if (!(studentCredians[index_firstName].matches(Regex_just_letters))) { 
                                    logShowErr("[Error] the first name must be letters only");
                                } else {
                                    // condition 1b check 
                                    if (!(studentSecondName.matches(Regex_combination_of_letters_or_numbers))) {
                                        logShowErr("[Error] the second name can be letters and/or numbers");
                                    }          
                                }
                            }
                            break;
                            
                        case 2: // condition 1c check 
                            studentClass = Integer.parseInt(line);
                            if (studentClass<1 || studentClass>8) {
                                logShowErr("[Error] the number of classes must be an integer value between 1 and 8 (inclusive)");
                            } else {
                                studentWorkload = getStudentWorkload(studentClass);
                                if (verbal) System.out.print(" [" + studentWorkload + "] " );
                            }
                            break;
                        
                        case 3: // 1d)	the student “number” must be a minimum of 6 characters with the first 2 characters being numbers, 
                                // the 3rd  and 4th characters (and possibly 5th ) being a letter, and everything after the last letter character being a number. 
                            StudentNumber = line;
                            
                            break;                            
                    }
                    
                    if (canWrite) {
                        if (verbal) System.out.println(" [OK]");
                        if(local_count>2) write2File(StudentNumber + " - " + studentSecondName + "\n" +  studentWorkload );
                    } 

                }
                
                local_count++;
                if(local_count>3) {
                    local_count = 1;
                    System.out.println("");
                    canGoOn = true;
                }                
                
                line = inputF.readLine();   // Read next line from the file
                total_count++;              // Total lines treated \ read
            }
            
        } catch (Exception e) {
            System.out.println("Reading error: " + e );
        }
                
        return true;
    }

    public void write2File(String studentData ){
        try {
            outputF  = new BufferedWriter(new FileWriter(myOutFile, true ));
            outputF.write(studentData);
            outputF.newLine();
            outputF.close();
        } catch (Exception e) {
            if (verbal) System.out.println("ERROR [write2File]: " + e);
        }            
    }    
    
    public void logShowErr(String errMessage){
         System.out.println(errMessage);
         canWrite = false;
    }
    
    public String getStudentWorkload(int studentClass){
        String Workload = "";
        if (studentClass==1) {
            Workload = "Very Light";
        } else if (studentClass==2){
            Workload = "Light";
        } else if (studentClass>=3 && studentClass<=5){
            Workload = "Part Time";
        } else if (studentClass>5){
            Workload = "Full Time";
        }
        return Workload;
    }
    
    public String getUserInput(String message){
        String useInput = "";
        Scanner sc  = new Scanner(System.in);
        boolean bInputOk = false;
        while (!bInputOk) {
            if (!message.equalsIgnoreCase("")) System.out.println(message);
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
        }
    }
    
    
}
