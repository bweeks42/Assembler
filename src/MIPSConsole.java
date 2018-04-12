import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.Writer;
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
public class MIPSConsole
{

    private File assembly;
    private Scanner commands;
    private Emulator emulator;
    
    public MIPSConsole(File assembly, Scanner commands) throws FileNotFoundException
    {
        this.assembly = assembly;
        this.commands = commands;
        this.emulator = new Emulator(assembly);
        
    }
    
    public void run() {
        
        String command = "";
        System.out.print("mips> ");
        
        while (!"q".equals(command = commands.nextLine())) {
            processCommand(command);
            System.out.print("mips> ");
        }
    }
    
    private void processCommand(String command) {
        switch(command) {
            case "h":
                showHelp();
                break;
            case "d":
                emulator.dump();
                break;
            case "r":
                emulator.runToEnd();
                break;
            case "c":
                emulator.clear();
                break;
            default:
                complexCommand(command);
        }
    }
    
    private void complexCommand(String command) {
        String []splitCommand = command.split(" ");
        if (splitCommand[0].equals("s")) {
            int step = 0;
            if (splitCommand.length > 1) {
                step = Integer.parseInt(splitCommand[1]) - 1;
            }
            emulator.step(step);
        }
        else if (splitCommand[0].equals("m")) {
            emulator.memDisplay(Integer.parseInt(splitCommand[1]), Integer.parseInt(splitCommand[2]));
        }
        System.out.println("pc\tif/id\tid/exe\texe/mem\tmem/wb");
        emulator.pipeDisplay();
    }

    private void showHelp()
    {
        System.out.println(
        "\nh = show help\n" +
        "d = dump register state\n" +
        "s = single step through the program (i.e. execute 1 instruction and stop)\n" +
        "s num = step through num instructions of the program\n" +
        "r = run until the program ends\n" +
        "m num1 num2 = display data memory from location num1 to num2\n" +
        "c = clear all registers, memory, and the program counter to 0\n" +
        "q = exit the program\n"
        );
    }
   
}
