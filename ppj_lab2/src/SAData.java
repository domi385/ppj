import analizator.Action;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.Set;

/**
 * Created by Dominik on 9.11.2016..
 */
public class SAData implements Serializable {
    private static final long serialVersionUID = -6296692901086583557L;

    public Grammar grammar;
    public Map<Symbol, Set<Symbol>> first;
    public Map<Pair, Action> actions;
    public Map<Pair, Integer> goTos;

    public SAData(
            Grammar grammar, Map<Symbol, Set<Symbol>> first, Map<Pair, Action> actions, Map<Pair, Integer> goTos) {
        this.grammar = grammar;
        this.first = first;
        this.actions = actions;
        this.goTos = goTos;
    }

    public SAData(String path) throws IOException, ClassNotFoundException {
        readData(path);
    }

    public void writeData(String path) throws IOException {
        FileOutputStream fos = new FileOutputStream(path);
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        writeObject(oos);
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.writeObject(grammar);
        out.writeObject(first);
        out.writeObject(actions);
        out.writeObject(goTos);

        out.close();
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        grammar = (Grammar) in.readObject();
        first = (Map<Symbol, Set<Symbol>>) in.readObject();
        actions = (Map<Pair, Action>) in.readObject();
        goTos = (Map<Pair, Integer>) in.readObject();

        in.close();
    }

    public void readData(String path) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(path);
        ObjectInputStream ois = new ObjectInputStream(fis);

        readObject(ois);
    }
}
