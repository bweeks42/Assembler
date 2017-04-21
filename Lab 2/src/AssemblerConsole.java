
import java.io.BufferedWriter;
import java.io.File;
import java.io.Reader;
import java.io.Writer;
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
public class AssemblerConsole {
    
    Scanner input;
    BufferedWriter output;
    Parser parser;
    
    public AssemblerConsole(Reader input, Writer output, File instructionSet) {
        this.input = new Scanner(input);
        this.output = new BufferedWriter(output);
        this.parser = new Parser(instructionSet);
    }
}
