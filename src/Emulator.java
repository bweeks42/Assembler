
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
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
public class Emulator
{
    int internalPc;
    int[] data;
    HashMap<String, Register> registers = new HashMap<String, Register>();
    LinkedList<Instruction> instructions = new LinkedList<Instruction>();
    Pipeline pipeline = new Pipeline();

    public Emulator(File assembly) throws FileNotFoundException
    {
        internalPc = 0;
        data = new int[8192];
        Arrays.fill(data, 0);
        initRegisters();
        parseAssembly(assembly);
    }

    private void parseAssembly(File assembly) throws FileNotFoundException
    {
        Parser parser = new Parser();
        Scanner sc = new Scanner(assembly);

        parser.firstPass(sc);

        sc = new Scanner(assembly);

        Instruction inst;
        int lineNumber = 1;
        while (sc.hasNextLine())
        {
            inst = parser.parseLine(sc.nextLine(), lineNumber);
            if (inst != null)
            {
                lineNumber++;
                instructions.addLast(inst);
            }
        }
    }

    private boolean processInstruction()
    {
        
        Instruction inst;
        Register rd, rs, rt;
        int imm;
        Pipeline.Outcome outcome = Pipeline.Outcome.NotTaken;
        boolean validInst = false;
        if (internalPc < instructions.size()) {
            validInst = true;
            inst = instructions.get(internalPc);
            pipeline.enqueue(inst);
            switch (inst.name)
            {
                case "and":
                    rd = registers.get(inst.rd);
                    rs = registers.get(inst.rs);
                    rt = registers.get(inst.rt);
                    rd.value = rs.value & rt.value;
                    break;
                case "or":
                    rd = registers.get(inst.rd);
                    rs = registers.get(inst.rs);
                    rt = registers.get(inst.rt);
                    rd.value = rs.value | rt.value;
                    break;
                case "add":
                    rd = registers.get(inst.rd);
                    rs = registers.get(inst.rs);
                    rt = registers.get(inst.rt);
                    rd.value = rs.value + rt.value;
                    break;
                case "addi":
                    rd = registers.get(inst.rd);
                    rs = registers.get(inst.rs);
                    imm = Integer.parseInt(inst.imm);
                    rd.value = rs.value + imm;
                    break;
                case "sll":
                    rt = registers.get(inst.rt);
                    rs = registers.get(inst.rs);
                    imm = Integer.parseInt(inst.imm);
                    rt.value = rs.value << imm;
                    break;
                case "sub":
                    rd = registers.get(inst.rd);
                    rs = registers.get(inst.rs);
                    rt = registers.get(inst.rt);
                    rd.value = rs.value - rt.value;
                    break;
                case "slt":
                    rd = registers.get(inst.rd);
                    rs = registers.get(inst.rs);
                    rt = registers.get(inst.rt);
                    rd.value = rs.value < rt.value ? 1 : 0;
                    break;
                case "beq":
                    rt = registers.get(inst.rt);
                    rs = registers.get(inst.rs);
                    imm = Integer.parseInt(inst.imm);
                    if (rt.value == rs.value) {
                        outcome = Pipeline.Outcome.Taken;
                        internalPc = imm - 1;
                    }
                    pipeline.taken(outcome);
                    break;
                case "bne":
                    rt = registers.get(inst.rt);
                    rs = registers.get(inst.rs);
                    imm = Integer.parseInt(inst.imm);
                    if (rt.value != rs.value) {
                        outcome = Pipeline.Outcome.Taken;
                        internalPc = imm - 1;
                    }
                    pipeline.taken(outcome);
                    break;
                case "lw":
                    rt = registers.get(inst.rt);
                    rs = registers.get(inst.rs);
                    imm = Integer.parseInt(inst.imm);
                    rt.value = data[rs.value + imm];
                    break;
                case "sw":
                    rt = registers.get(inst.rt);
                    rs = registers.get(inst.rs);
                    imm = Integer.parseInt(inst.imm);
                    data[rs.value + imm] = rt.value;
                    break;
                case "j":
                    imm = Integer.parseInt(inst.imm);
                    internalPc = imm;
                    break;
                case "jr":
                    rs = registers.get(inst.rs);
                    internalPc = rs.value - 1;
                    break;
                case "jal":
                    rd = registers.get("ra");
                    rd.value = internalPc + 1;
                    imm = Integer.parseInt(inst.imm);
                    internalPc = imm - 1;
                    break;
            }
            internalPc++;
        }
        return validInst;
    }

    private void initRegisters()
    {
        Register zero = new Register();
        Register v0 = new Register();
        Register v1 = new Register();
        Register a0 = new Register();
        Register a1 = new Register();
        Register a2 = new Register();
        Register a3 = new Register();
        Register t0 = new Register();
        Register t1 = new Register();
        Register t2 = new Register();
        Register t3 = new Register();
        Register t4 = new Register();
        Register t5 = new Register();
        Register t6 = new Register();
        Register t7 = new Register();
        Register s0 = new Register();
        Register s1 = new Register();
        Register s2 = new Register();
        Register s3 = new Register();
        Register s4 = new Register();
        Register s5 = new Register();
        Register s6 = new Register();
        Register s7 = new Register();
        Register t8 = new Register();
        Register t9 = new Register();
        Register sp = new Register();
        Register ra = new Register();

        registers.put("0", zero);
        registers.put("zero", zero);
        registers.put("v0", v0);
        registers.put("v1", v1);
        registers.put("a0", a0);
        registers.put("a1", a1);
        registers.put("a2", a2);
        registers.put("a3", a3);
        registers.put("t0", t0);
        registers.put("t1", t1);
        registers.put("t2", t2);
        registers.put("t3", t3);
        registers.put("t4", t4);
        registers.put("t5", t5);
        registers.put("t6", t6);
        registers.put("t7", t7);
        registers.put("s0", s0);
        registers.put("s1", s1);
        registers.put("s2", s2);
        registers.put("s3", s3);
        registers.put("s4", s4);
        registers.put("s5", s5);
        registers.put("s6", s6);
        registers.put("s7", s7);
        registers.put("t8", t8);
        registers.put("t9", t9);
        registers.put("sp", sp);
        registers.put("ra", ra);
    }

    void dump()
    {
        
        System.out.println("\npc = " + internalPc);
        String space = "         ";
        System.out.print("$0 = " + registers.get("0").value + space + " ");
        System.out.print("$v0 = " + registers.get("v0").value + space);
        System.out.print("$v1 = " + registers.get("v1").value + space);
        System.out.print("$a0 = " + registers.get("a0").value + "\n");

        System.out.print("$a1 = " + registers.get("a1").value + space);
        System.out.print("$a2 = " + registers.get("s2").value + space);
        System.out.print("$a3 = " + registers.get("a3").value + space);
        System.out.print("$t0 = " + registers.get("t0").value + "\n");

        System.out.print("$t1 = " + registers.get("t1").value + space);
        System.out.print("$t2 = " + registers.get("t2").value + space);
        System.out.print("$t3 = " + registers.get("t3").value + space);
        System.out.print("$t4 = " + registers.get("t4").value + "\n");

        System.out.print("$t5 = " + registers.get("t5").value + space);
        System.out.print("$t6 = " + registers.get("t6").value + space);
        System.out.print("$t7 = " + registers.get("t7").value + space);
        System.out.print("$s0 = " + registers.get("s0").value + "\n");

        System.out.print("$s1 = " + registers.get("s1").value + space);
        System.out.print("$s2 = " + registers.get("s2").value + space);
        System.out.print("$s3 = " + registers.get("s3").value + space);
        System.out.print("$s4 = " + registers.get("s4").value + "\n");

        System.out.print("$s5 = " + registers.get("s5").value + space);
        System.out.print("$s6 = " + registers.get("s6").value + space);
        System.out.print("$s7 = " + registers.get("s7").value + space);
        System.out.print("$t8 = " + registers.get("t8").value + "\n");

        System.out.print("$t9 = " + registers.get("t9").value + space);
        System.out.print("$sp = " + registers.get("sp").value + space);
        System.out.print("$ra = " + registers.get("ra").value + "\n\n");
    }

    void runToEnd()
    {
        while(processInstruction());
    }

    void clear()
    {
        System.out.println("\tSimulator reset\n");
        Arrays.fill(data, 0);
        for (Map.Entry<String, Register> reg : registers.entrySet())
        {
            reg.getValue().value = 0;
        }
        internalPc = 0;
    }

    void memDisplay(int start, int stop)
    {
        System.out.println();
        for (int i = start; i <= stop; i++)
        {
            System.out.println("[" + i + "]" + " = " + data[i]);
        }
        System.out.println();
    }

    void step(int step)
    {
        for (int i = 0; i <= step; i++)
        {
            processInstruction();
            pipeline.tick();
        }
    }
    
    public void pipeDisplay() {
        System.out.println(pipeline.pc + "\t" + 
                pipeline.if_id.name + "\t" + 
                pipeline.id_exe.name + "\t" + 
                pipeline.exe_mem.name + "\t" +
                pipeline.mem_wb.name);
    }
}
