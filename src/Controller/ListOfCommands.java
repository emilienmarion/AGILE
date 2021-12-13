package Controller;

import java.util.LinkedList;

public class ListOfCommands {
    private final LinkedList<Command> l;
    private int i;

    /**
     * constructor of the Class ListOfCommands
     */
    public ListOfCommands(){
        i = -1;
        l = new LinkedList<Command>();
    }

    /**
     * method which manage add point
     * @param c
     */
    public void add(Command c){
        i++;
        l.add(i, c);
        c.doCommand();
    }

    /**
     * method which manage undo
     */
    public void undo(){
        if(i>=0){
            l.get(i).undoCommand();
            i--;
        }
    }

    /**
     * method which manage redo
     */
    public void redo(){
        i++;
        l.get(i).doCommand();
    }
}
