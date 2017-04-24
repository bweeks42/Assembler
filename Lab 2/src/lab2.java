
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author bjweeks
 */
public class lab2 {
    
    
    public static void main(String []args) {
        
        File instructions = new File("./instruction_set.txt");
        Reader input = new InputStreamReader(System.in);
        Writer output = new OutputStreamWriter(System.out);
        
        AssemblerConsole ac = new AssemblerConsole(input, output, instructions);
    }
} 
