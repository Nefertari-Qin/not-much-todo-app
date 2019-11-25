package ui.tabs;

import ui.AppGui;

import javax.swing.*;
import java.awt.*;

public abstract class Tab {

    private AppGui appController;

    public JPanel getTab() {
        return tab;
    }

    protected JPanel tab;

    //REQUIRES: AppMainGui controller that holds this tab
    public Tab(AppGui appController) {
        this.appController = appController;
        tab = new JPanel();
    }

    //EFFECTS: creates and returns row with button included
    public JPanel formatButtonRow(JButton b) {
        JPanel p = new JPanel();
        p.setLayout(new FlowLayout());
        p.add(b);

        return p;
    }

    //EFFECTS: returns the AppMainGui controller for this tab
    public AppGui getAppController() {
        return appController;
    }

}


