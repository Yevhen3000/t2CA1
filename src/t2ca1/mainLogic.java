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
    
    private final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    private final String ANSI_RED_BACKGROUND = "\u001B[41m";
    private final String ANSI_GREEN = "\u001B[32m";
    private final String ANSI_RED = "\u001B[31m";
    private final String ANSI_WHITE = "\u001B[37m";
    private final String ANSI_RESET = "\u001B[0m";
    
    private BufferedWriter outputF;
    private BufferedReader inputF;
    private boolean verbal = true;
    private int local_count;
    
    public boolean canWrite;
    public String studentWorkload ="";
    public String studentSecondName ="";
    public String StudentNumber = ""; 
    public int total_count;
    public int successRecords = 0;
    
    private String Regex_combination_of_letters_or_numbers = "^[a-zA-Z0-9]+$"; //"^(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9]+$";
    private String Regex_just_letters = "^(?=.*[a-zA-Z])[a-zA-Z]+$"; //"\\w+\\.?"
    private String Regex_just_numbers = "^(?=.*[0-9])[0-9]+$";
    
    public mainLogic(){
        // Init
    }
    public mainLogic(boolean ShowMessages){
       verbal = ShowMessages;
    }        
    
    public boolean treatFile(){
        total_count = 0;
        local_count = 1;
        String line;
        
        try {
            inputF = new BufferedReader(new FileReader(myInFile)); // Try to open the raw students' data file
        } catch (Exception e) {
            System.out.println("Open file error: " + e );
            return false;
        }        

        write2File("",false);       // Reset file content
        canWrite = true;                            // A flag to determine that student's record is valid and can be written to the file
        
        try {
            line = inputF.readLine();               // Read a line from the file
            while (line != null) {                  // End of file ?
                
                if (!line.trim().isEmpty()) {       //Check for empty line and skip it
                    
                    if (verbal) System.out.print(local_count + ") " + line + " "); // Show line for user and apopriate error if there is
                    
                    switch (local_count) {
                        case 1: // Validate the first line of a student's data record
                            validateL1(line);
                            break;
                            
                        case 2: // Validate the second line of a student's data record
                            validateL2(line);
                            break;
                        
                        case 3: // Validate the third line of a student's data record
                            validateL3(line);
                            break;                            
                    }
                    
                    if (verbal) { // Just for pritty output
                        if (!canWrite) {
                            System.out.println("");
                        } else {
                            System.out.println(" " + ANSI_GREEN + "[OK]" + ANSI_RESET);
                            //System.out.println(" " + ANSI_WHITE + ANSI_GREEN_BACKGROUND + "[OK]" + ANSI_RESET);
                            
                        }
                    }
                }
                
                local_count++;              // Increase file line counter
                if(local_count>3) {         // If we've read 3 lines (student's record) go on to next one
                    local_count = 1;
                    if (verbal) System.out.println("");
                    if(canWrite) {
                        write2File(StudentNumber + " - " + studentSecondName + "\n" +  studentWorkload, true );
                        successRecords++;
                    }
                    canWrite = true;         // Reset flag
                }                
                
                line = inputF.readLine();   // Read next line from the file
                total_count++;              // Total lines treated \ read
            }
            
        } catch (Exception e) {
            System.out.println("Reading error: " + e );
        }
                
        return true;
    }

    
    public void treatConsole(){
        //111
    }
    
    public void validateL1(String line){
        int index_firstName  = 0;
        int index_secondName  = 1;
        
        String[] studentCredians = line.trim().split(" ");
        studentSecondName = studentCredians[index_secondName];
        
        if (studentCredians.length<=1) { // condition 1b check
            logShowErr("[Error] the second name must be separated from the first name by a single space");
        } else {
            
            if (!(studentCredians[index_firstName].matches(Regex_just_letters))) { // condition 1a check  
                logShowErr("[Error] the first name must be letters only");
            } else {
                
                if (!(studentSecondName.matches(Regex_combination_of_letters_or_numbers))) { // condition 1b check 
                    logShowErr("[Error] the second name can be letters and/or numbers");
                }          
            }
        }
    }
    
    public boolean isNumeric(String str) { 
        try {  
          Double.parseDouble(str);  
          return true;
        } catch(Exception e){  
          return false;  
        }  
      }
    
    public void validateL2(String line){
        int studentClass;
        if (isNumeric(line)) {
            studentClass = Integer.parseInt(line);
            if (studentClass<1 || studentClass>8) { // condition 1c check 
                logShowErr("[Error] the number of classes must be an integer value between 1 and 8 (inclusive)");
            } else {
                studentWorkload = getStudentWorkload(studentClass);
                if (verbal) System.out.print(" [" + studentWorkload + "] " );
            }
        } else {
            logShowErr("[Error] student's class is not a number");
        }
    }
    
    public void validateL3(String line){

        if (line.trim().length()<6) { // 1d check
            logShowErr("[Error] the student “number” must be a minimum of 6 characters ");
        } else {
            String first2characters_numbers = line.substring(0, 2);
            int studentYear = Integer.parseInt(first2characters_numbers); //with the first 2 characters being numbers, 
            if (studentYear<20) { // DW1 check: Ensure that the student number year is at least 2020 (i.e. that the number starts with 20 or higher)
                logShowErr("[Error] the year is not valid in students's number (Must be 20 or higher)");
            } else {
                char L1 = line.charAt(2);
                char L2 = line.charAt(3);
                char L3 = line.charAt(4);

                if (Character.isLetter(L1) && Character.isLetter(L2)){ //the 3rd  and 4th characters (and possibly 5th ) being a letter

                    int pos = 4;                                            // if there are 2 letters
                    if (Character.isLetter(L3)) pos = 5;                 // correction if there are 3 letters
                    String reasonableNumber = line.substring(pos); // get number after letters
                    
                    if (isNumeric(reasonableNumber)) {                  // Check is this number for sure

                        int intReasonableNumber = Integer.parseInt(reasonableNumber);
                        if (intReasonableNumber<1 || intReasonableNumber>200) {
                            logShowErr("[Error] students's number " + intReasonableNumber + " is not reasonable (must be between 1 and 200)");
                        } else {
                            if (verbal) System.out.print(first2characters_numbers + " " + reasonableNumber);
                            StudentNumber = line;
                        }
                    } else {
                        logShowErr("[Error] there must be not more than 3 letters");
                    }

                } else {
                    logShowErr("[Error] the 3rd  and 4th characters (and possibly 5th ) in student's number must be a letter");
                }

            }
        }
    }
    
    public void write2File(String studentData, boolean append ){
        try {
            outputF  = new BufferedWriter(new FileWriter(myOutFile, append ));
            outputF.write(studentData);
            if (append) outputF.newLine();
            outputF.close();
        } catch (Exception e) {
            if (verbal) System.out.println("ERROR [write2File]: " + e);
        }            
    }    
    
    public void logShowErr(String errMessage){
        if (verbal) System.out.print(ANSI_RED + errMessage + ANSI_RESET);
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
