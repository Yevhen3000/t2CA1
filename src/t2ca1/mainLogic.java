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
    private BufferedWriter outputF;
    private BufferedReader inputF;
    private int count = 0;
    private boolean verbal = true;

    public mainLogic(){
        // Init
    }    
    
    public boolean treatFile(){
        // ==== Read current trade rates ===
        try {
            inputF = new BufferedReader(new FileReader(myInFile));
        } catch (Exception e) {
            System.out.println("Open file error: " + e );
            return false;
        }

        String line;
        try {
            line = inputF.readLine();
            while (line != null) {
                
                count++;
                if (verbal) System.out.print(count + ") " + line + " ");
                System.out.println("" );
                
                line = inputF.readLine();
                
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
