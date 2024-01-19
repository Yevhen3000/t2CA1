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
 * @date    18/01/24
 * @author  Yevhen Kuropiatnyk
 * @email   evgeniy.kuropyatnik@gmail.com
 * @student sba23066
 */

public class mainLogic {
    private String myInFile = "students.txt";           // File name to read data from
    private String myOutFile = "status.txt";            // File name to write data into
    
    // Colors to brighten up the gray routine
    private final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    private final String ANSI_RED_BACKGROUND = "\u001B[41m";
    private final String ANSI_GREEN = "\u001B[32m";
    private final String ANSI_RED = "\u001B[31m";
    private final String ANSI_WHITE = "\u001B[37m";
    private final String ANSI_RESET = "\u001B[0m";
    
    private BufferedWriter outputF;
    private BufferedReader inputF;
    private boolean verbal = true;                      // Mode of verbal ineraction with user: true - show all messages, false - just results
    private int local_count;                            // Counter for keeping student's record position (line)
    
    public boolean canWrite;                            // Flag to indicate if data are valid and can be written to the output file
    public String studentWorkload ="";                  // Holds current student's worckload
    public String studentSecondName ="";                // Holds current student's second name
    public String StudentNumber = "";                   // Holds current student's number
    public int total_count;                             // Holds total lines from the file treated
    public int successRecords = 0;                      // Holds counter for valid recods written to the output file
    
    private String Regex_combination_of_letters_or_numbers = "^[a-zA-Z0-9]+$"; //"^(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9]+$";
    private String Regex_just_letters = "^(?=.*[a-zA-Z])[a-zA-Z]+$"; //"\\w+\\.?"
    private String Regex_just_numbers = "^(?=.*[0-9])[0-9]+$";
    
    public mainLogic(){
        // Init
    }
    public mainLogic(boolean ShowMessages){
       verbal = ShowMessages;
    }        
    
    public boolean treatFile(boolean clearOutputFile){
        total_count = 0;    // Init counters
        local_count = 1;
        String line;
        
        try {
            inputF = new BufferedReader(new FileReader(myInFile)); // Try to open the raw students' data file
        } catch (Exception e) {
            System.out.println("Open file error: " + e );
            return false;
        }        

        if(clearOutputFile) write2File("",false);       // Reset file content
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

    
    public void treatConsole(){ // Validate users's console input
        
        String userInpurString;
        boolean canGoOn = false;

        while(!canGoOn) {
            do  {
                userInpurString = getUserInput("\nPlease, enter student first and second names separated by one space [CANCEL to return to the main menu]:");
            } while (userInpurString.isEmpty());
            if(userInpurString.equalsIgnoreCase("cancel")) return;
            if (validateL1(userInpurString)) canGoOn = true;
        }
        
        canGoOn = false;
        while(!canGoOn) {
            do  {
                userInpurString = getUserInput("\nPlease, enter student's classes count, integer more than one [CANCEL to return to the main menu]:");
            } while (userInpurString.isEmpty());
            if(userInpurString.equalsIgnoreCase("cancel")) return;
            if (validateL2(userInpurString)) canGoOn = true;
        }

        canGoOn = false;
        while(!canGoOn) {
            do  {
                userInpurString = getUserInput("\nPlease, enter student's number, [CANCEL to return to the main menu].Format YYLLLDDD, where YY - the last two digits of the year, LLL - letters 2 or 3, DDD - number within 1-200:");
            } while (userInpurString.isEmpty());
            if(userInpurString.equalsIgnoreCase("cancel")) return;
            if (validateL3(userInpurString)) canGoOn = true;
        }
                
        write2File(StudentNumber + " - " + studentSecondName + "\n" +  studentWorkload, true );
        successRecords++;
        System.out.println("\nDone" );
        
    }
    
    public boolean validateL1(String line){ // Validate student's first and second names
        int index_firstName  = 0;
        int index_secondName  = 1;
        boolean ret = true;
        
        String[] studentCredians = line.trim().split(" ");
        studentSecondName = studentCredians[index_secondName];
        
        if (studentCredians.length<=1) { // condition 1b check
            logShowErr("[Error] the second name must be separated from the first name by a single space");
            ret = false;
        } else {
            
            if (!(studentCredians[index_firstName].matches(Regex_just_letters))) { // condition 1a check  
                logShowErr("[Error] the first name must be letters only");
                ret = false;
            } else {
                
                if (!(studentSecondName.matches(Regex_combination_of_letters_or_numbers))) { // condition 1b check 
                    logShowErr("[Error] the second name can be letters and/or numbers");
                    ret = false;
                }          
            }
        }
        return ret;
    }
    
    public boolean isNumeric(String str) { // Check if given String is a number to catch an error
        try {  
          Double.parseDouble(str);  
          return true;
        } catch(Exception e){  
          return false;  
        }  
      }
    
    public boolean validateL2(String line){ // Validate student's classes
        int studentClass;
        boolean ret = true;
        if (isNumeric(line)) {
            studentClass = Integer.parseInt(line);
            if (studentClass<1 || studentClass>8) { // condition 1c check 
                logShowErr("[Error] the number of classes must be an integer value between 1 and 8 (inclusive)");
                ret = false;
            } else {
                studentWorkload = getStudentWorkload(studentClass);
                if (verbal) System.out.print(" [" + studentWorkload + "] " );
            }
        } else {
            logShowErr("[Error] student's class is not a number");
            ret = false;
        }
        return ret;
    }
    
    public boolean validateL3(String line){ // Validate student's number
        boolean ret = true;
        if (line.trim().length()<6) { // 1d check
            logShowErr("[Error] the student “number” must be a minimum of 6 characters ");
            ret = false;
        } else {
            String first2characters_numbers = line.substring(0, 2);
            int studentYear = Integer.parseInt(first2characters_numbers); //with the first 2 characters being numbers, 
            if (studentYear<20) { // DW1 check: Ensure that the student number year is at least 2020 (i.e. that the number starts with 20 or higher)
                logShowErr("[Error] the year is not valid in students's number (Must be 20 or higher)");
                ret = false;
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
                            ret = false;
                        } else {
                            if (verbal) System.out.print(first2characters_numbers + " " + reasonableNumber);
                            StudentNumber = line;
                        }
                    } else {
                        logShowErr("[Error] there must be not more than 3 letters");
                        ret = false;
                    }

                } else {
                    logShowErr("[Error] the 3rd  and 4th characters (and possibly 5th ) in student's number must be a letter");
                    ret = false;
                }
            }
        }
        return ret;
    }
    
    public void write2File(String studentData, boolean append ){ // Write formatted and validated data to the file
        try {
            outputF  = new BufferedWriter(new FileWriter(myOutFile, append ));
            outputF.write(studentData);
            if (append) outputF.newLine();
            outputF.close();
        } catch (Exception e) {
            if (verbal) System.out.println("ERROR [write2File]: " + e);
        }            
    }    
    
    public void logShowErr(String errMessage){ // Just to shorten and centrilize error output and turn the error flag on
        if (verbal) System.out.print(ANSI_RED + errMessage + ANSI_RESET);
        canWrite = false;
    }
    
    public String getStudentWorkload(int studentClass){ // Get a string with workload description by class number 
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
    
    public String getUserInput(String message){ // Get a string from user
        String useInput = "";
        Scanner sc  = new Scanner(System.in);
        boolean bInputOk = false;
        while (!bInputOk) {
            if (!message.equalsIgnoreCase("")) System.out.println(message); // Show a promt to user (optional)
            try {
                useInput = sc.nextLine();
                bInputOk = true;
            } catch (Exception e){
                System.out.println("Ops, something went wrong!");
            }
        }
        return useInput;
    }
    
    public void makeTestFile(){ // Just to avoid a manual work
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
