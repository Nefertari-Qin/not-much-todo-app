package network;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;

// Code & Idea reference:
// I create the class solely for this particular deliverable,
// so I used the easiest approach without change the provided URL

public class MessagePrinter implements Observer {
    private BufferedReader br = null;
    private String theURL;

    public MessagePrinter() {
        theURL = "https://www.students.cs.ubc.ca/~cs-210/2018w1/welcomemsg.html";
    }

    // MODIFIES: this
    // EFFECTS: print out course welcome message
    public void printWebInfo() {
        try {
            URL url = new URL(theURL);
            br = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
            }
            System.out.println(sb);
            if (br != null) {
                br.close();
            }
        } catch (Exception e) {
            System.out.println("Message Printer broke down!");
        }
    }

    /**
     * This method is called whenever the observed object is changed. An
     * application calls an <tt>Observable</tt> object's
     * <code>notifyObservers</code> method to have all the object's
     * observers notified of the change.
     *
     * @param o   the observable object.
     * @param arg an argument passed to the <code>notifyObservers</code>
     */
    @Override
    public void update(Observable o, Object arg) {
        System.out.println(arg + " is added to App.");
        // TODO: rather than print to console, I will try something else
        //  Current Idea is a pie chart, depending on whether or not my ability
        //   can catch my tastes and also whether or not I have time .
    }
}
