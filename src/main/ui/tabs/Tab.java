package ui.tabs;

import model.App;
import ui.AppMainGui;

import javax.swing.*;
import java.awt.*;

public abstract class Tab extends JPanel {

    private AppMainGui appController;

    //REQUIRES: AppMainGui controller that holds this tab
    public Tab(AppMainGui appController) {
        this.appController = appController;
    }

    //EFFECTS: creates and returns row with button included
    public JPanel formatButtonRow(JButton b) {
        JPanel p = new JPanel();
        p.setLayout(new FlowLayout());
        p.add(b);

        return p;
    }

    //EFFECTS: returns the AppMainGui controller for this tab
    public AppMainGui getAppController() {
        return appController;
    }

}


