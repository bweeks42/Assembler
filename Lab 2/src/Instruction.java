/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author bjweeks
 */
public class Instruction {
    
   
    public enum Type {R, I, J};
    
    private String name;
    private Type type;
    private int opcode;
    private int shamt;
    private int funct;
    
    
    public Instruction(String name, Type type, int opcode, int shamt, int funct) {
        this.name = name;
        this.type = type;
        this.opcode = opcode;
        this.shamt = shamt;
        this.funct = funct;
    }
    
    public String parseLine(String line) {
        return line;
    }
}
