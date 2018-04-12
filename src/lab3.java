import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author blain
 */
public class lab3
{

    public static void main(String[] args) throws FileNotFoundException
    {
        String assemblyFilename = args[0];
        File assemblyFile;
        String scriptFilename = null;
        File scriptFile;
        Scanner commandScanner;
        
        assemblyFile = new File(assemblyFilename);
        
        
        if (args.length > 1) {
            scriptFilename = args[1];
        }
        if (scriptFilename != null) {
            scriptFile = new File(scriptFilename);
            commandScanner = new Scanner(scriptFile);
        }
        else {
            commandScanner = new Scanner(System.in);
        }
        
        MIPSConsole console = new MIPSConsole(assemblyFile, commandScanner);
        Pipeline pipe = new Pipeline();
        
        
        
        console.run();
        
    }
}
