
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author bjweeks
 */
public class Parser {
    
    File instructionSet;
    Map<String, String[]> mappedInstructions;
    Map<String, Integer> lookUpTable;
    
    Parser(File instructionSet) {
        this.instructionSet = instructionSet;
        this.mappedInstructions = new HashMap<>();
        try {
            this.readAndMapInstructions();
        } catch (FileNotFoundException ex) {
            System.out.println(ex.toString());
        }
    }
    
    private void readAndMapInstructions() throws FileNotFoundException {
        Scanner sc = new Scanner(instructionSet);
        String line;
        String []tokens;
        
        while(sc.hasNext()) {
            line = sc.nextLine();
            tokens = line.split(" +");
            
            mappedInstructions.put(tokens[0], Arrays.copyOfRange(tokens, 1, tokens.length));
        }
        sc.close();
    }
    
    public void firstPass(File assembly) throws FileNotFoundException {
        Scanner sc = new Scanner(assembly);
        String line;
        String []tokens;
        String token;
        int lineNumber = 1;
        
        while(sc.hasNext()) {
            line = sc.nextLine();
            line = line.trim();
            tokens = line.split(" ");
            
            if (tokens.length > 0) {
                token = tokens[0];
                if (token.contains(":")) {
                    lookUpTable.put(token.substring(0, token.length() - 1), lineNumber);
                    if (tokens.length > 1) {
                        token = tokens[1];
                    }
                }
                if (mappedInstructions.containsKey(token)) {
                    ++lineNumber;
                }
            }
        }
    }
    
    
    public int parseLine(String line) {
        line = line.trim();
        String []tokens;
        String delims = "[ :,]";
        int instruction = 0; 
        tokens = line.split(delims);
        
        for (String token : tokens) {
            if (mappedInstructions.containsKey(token)) {
                
            }
        }
        
        
        return instruction;
    }
}
