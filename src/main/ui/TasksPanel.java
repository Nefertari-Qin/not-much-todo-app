package ui;

import javax.swing.*;
import java.awt.*;

public class TasksPanel extends JPanel {
    public static final int TP_WIDTH = (AppGui.WIDTH / 4) * 3;
    public static final int TP_HEIGHT = AppGui.HEIGHT;

    public TasksPanel() {
        setPreferredSize(new Dimension(TP_WIDTH, TP_HEIGHT));
        setBackground(Color.WHITE);
    }
}
