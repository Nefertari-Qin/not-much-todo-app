package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

// Code & Idea reference:
// The Construction of ActionHistory relied on very careful examination of following source:
// ---> C04-AlarmSystem              Lecture Lab
// ---> Singleton Design Pattern     https://sourcemaking.com/design_patterns/

public class ActionHistory implements Iterable<Action> {
    // Singleton Design Pattern: the only ActionHistory in the system
    private static ActionHistory theActionHistory;
    private Collection<Action> actions;

    // Singleton Design Pattern: Prevent external construction.
    private ActionHistory() {
        actions = new ArrayList<>();
    }

    // Creates a instance of ActionHistory if it doesn't already exist.
    public static ActionHistory getInstance() {
        if (theActionHistory == null) {
            theActionHistory = new ActionHistory();
        }
        return theActionHistory;
    }


    public void recordAction(Action a) {
        actions.add(a);
    }

    public void clear() {
        actions.clear();
        recordAction(new Action("All Action History cleared."));
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<Action> iterator() {
        return actions.iterator();
    }
}
