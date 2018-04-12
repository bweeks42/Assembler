
import java.util.LinkedList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author bweeks
 */
public class Pipeline extends LinkedList<Instruction>
{
    public int pc;
    public int cycle;

    public enum Outcome
    {

        Taken, NotTaken
    };

    LinkedList<Outcome> outcomeQ;

    Instruction if_id;
    Instruction id_exe;
    Instruction exe_mem;
    Instruction mem_wb;

    private void write_back()
    {
        memory();
    }

    private void memory()
    {
        mem_wb = exe_mem;
        boolean squash = false;
        if (mem_wb.name.equals("bne") || mem_wb.name.equals("beq")) {
            squash = (outcomeQ.pop() == Outcome.Taken);
        }
        execute(squash);
    }

    private void execute(boolean squash)
    {
        exe_mem = id_exe;
        boolean delay = false;
        if (squash) {
            exe_mem = new Instruction("squash");
            decode(squash);
        }
        else {
            if (exe_mem.name.equals("lw")) {
                delay = exe_mem.rt.equals(if_id.rt) || exe_mem.rt.equals(if_id.rs);
            }
            if (delay) {
                id_exe = new Instruction("stall");
            }
            else {
                decode(squash);
            }
        }
    }

    private void decode(boolean squash)
    {
        id_exe = if_id;
        if (squash) {
            id_exe = new Instruction("squash");
        }
        squash = id_exe.name.equals("j") ||
                id_exe.name.equals("jal") ||
                id_exe.name.equals("jr") || squash;
        
        fetch(squash);
    }

    private void fetch(boolean squash)
    {
        if_id = this.pop();
        if(squash) {
            if_id = new Instruction("squash");
        }
    }

    public Pipeline()
    {
        pc = 0;
        cycle = 0;
        if_id = new Instruction("empty");
        id_exe = new Instruction("empty");
        exe_mem = new Instruction("empty");
        mem_wb = new Instruction("empty");
    }

    public void enqueue(Instruction inst)
    {
        this.addLast(inst);
    }

    public void taken(Outcome outcome)
    {
        outcomeQ.addLast(outcome);
    }

    public void tick()
    {
        if(write_back()) {
            pc++;
        };
        cycle++;
        
    }

    @Override
    public String toString()
    {
        String toReturn = "";

        for (int i = 0; i < 4; i++)
        {
            if (this.size() > i)
            {
                toReturn += this.get(i).name;
            } else
            {
                toReturn += "empty";
            }
            if (i < 3)
            {
                toReturn += "\t";
            }
        }
        return toReturn;
    }

    private void addSecond(Instruction inst)
    {
        this.add(1, inst);
    }
}
