import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

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
    
    String []validInstructions;
    Map<String, Integer> lookUpTable;
    Map<String, Register> mappedRegisters;
    
    Parser() {
        this.validInstructions = new String[]{"and", "or", "add", "addi", "sll",
            "sub", "slt", "beq", "bne", "lw", "sw", "j", "jr", "jal"};
        this.mappedRegisters = new HashMap<>();
        this.lookUpTable = new HashMap<>();
    }
    
    
    public void firstPass(Scanner sc) throws FileNotFoundException {
        String line;
        String []tokens;
        String []first;
        String token;
        int lineNumber = 1;
        
        while(sc.hasNext()) {
            line = sc.nextLine();
            line = line.trim();
            if (line.length() > 0) {
                tokens = line.split(" ");
                if (tokens.length >= 1) {
                    if (tokens[0].indexOf("#") > -1) {
                        continue;
                    }
                    if (tokens[0].indexOf(':') > -1) {
                        first = tokens[0].split(":");
                        
                        this.lookUpTable.put(first[0], lineNumber);
                    }
                }
                if (tokens.length > 1) {
                    lineNumber++;
                }
            }    
        }
    }
    
    
    public Instruction parseLine(String line, int lineNumber) {
        line = line.trim();
        String instructionString = "";
        Instruction inst = null;
        
        String []tokens;
        int commentStart;
        
        tokens = line.split(":");
        if (tokens.length > 0) {
            if (this.lookUpTable.containsKey(tokens[0])) {
                tokens = Arrays.copyOfRange(tokens, 1, tokens.length);
            }
            
            
            if (tokens.length > 0) {
                tokens[0] = tokens[0].trim();
                tokens[0] = tokens[0].replace("\t", "");
                tokens = tokens[0].split("[,$ ()\t]+");
                
                
                for (int i = 0; i < tokens.length; i++) {
                    if ((commentStart = tokens[i].indexOf("#")) > -1) {
                        tokens[i] = tokens[i].substring(0, commentStart);
                    }
                }
                if (tokens[0].equals("#")) {
                    return null;
                }
                
                if (tokens.length <= 1) {
                    return null;
                }
                if (Arrays.asList(validInstructions).contains(tokens[0])) {
                    inst = new Instruction();
                    inst.name = tokens[0];
                    switch(tokens[0]) {
                        case "and":
                            inst.rd = tokens[1];
                            inst.rs = tokens[2];
                            inst.rt = tokens[3];
                            break;
                        case "or":
                            inst.rd = tokens[1];
                            inst.rs = tokens[2];
                            inst.rt = tokens[3];
                            break;
                        case "add":
                            inst.rd = tokens[1];
                            inst.rs = tokens[2];
                            inst.rt = tokens[3];
                            break;
                        case "addi":
                            inst.rd = tokens[1];
                            inst.rs = tokens[2];
                            inst.imm = tokens[3];
                            break;
                        case "sll":
                            inst.rt = tokens[1];
                            inst.rs = tokens[2];
                            inst.imm = tokens[3];
                            break;
                        case "sub":
                            inst.rd = tokens[1];
                            inst.rs = tokens[2];
                            inst.rt = tokens[3];
                            break;
                        case "slt":
                            inst.rd = tokens[1];
                            inst.rs = tokens[2];
                            inst.rt = tokens[3];
                            break;
                        case "beq":
                            inst.rs = tokens[1];
                            inst.rt = tokens[2];
                            inst.imm = Integer.toString(lookUpTable.get(tokens[3]) - 1);
                            break;
                        case "bne":
                            inst.rs = tokens[1];
                            inst.rt = tokens[2];
                            inst.imm = Integer.toString(lookUpTable.get(tokens[3]) - 1);
                            break;
                        case "lw":
                            inst.rt = tokens[1];
                            inst.rs = tokens[3];
                            inst.imm = tokens[2];
                            break;
                        case "sw":
                            inst.rt = tokens[1];
                            inst.rs = tokens[3];
                            inst.imm = tokens[2];
                            break;
                        case "j":
                            inst.imm = Integer.toString(lookUpTable.get(tokens[1]) - 1);
                            break;
                        case "jr":
                            inst.rs = tokens[1];
                            break;
                        case "jal":
                            inst.imm = Integer.toString(lookUpTable.get(tokens[1]) - 1);
                            break;
                    }
                    
                }      
                else if (!tokens[0].equals("")){
                    System.out.println("invalid instruction: " + tokens[0]);
                    return null;
                }
            }
        }
        return inst;
    }
}