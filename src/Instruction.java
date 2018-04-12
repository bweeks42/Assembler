/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author blain
 */
public class Instruction
{
    public String rs;
    public String rt;
    public String rd;
    public String name;
    public String imm;
    
    public Instruction() {
        rs = null;
        rt = null;
        rd = null;
        name = null;
        imm = null;
    }
    
    public Instruction(String name) {
        this.name = name;
        rs = null;
        rt = null;
        rd = null;
        imm = null;
    }
}
