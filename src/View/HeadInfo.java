package View;

import javax.swing.*;
import java.awt.*;

import static java.awt.Component.LEFT_ALIGNMENT;

public class HeadInfo extends JPanel {
    private JLabel title;
    private JLabel value;

    /**
     * set up the header container
     * @param exp
     */
    public HeadInfo(String exp)
    {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JLabel expJ = new JLabel(exp);

        this.setBackground(new Color(40,40,40));
        this.setPreferredSize(new Dimension(1000, 70));
        this.setMaximumSize(new Dimension(1000, 70));
        expJ.setForeground(new Color(140,140,140));

        expJ.setLocation(35,15);

        this.add(expJ);
    }

    /**
     * set up the box in the header
     * @param title
     * @param value
     */
    public HeadInfo(String title, String value)
    {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.title = new JLabel(title);
        this.value = new JLabel(value);

        this.setBackground(new Color(40,40,40));
        this.setPreferredSize(new Dimension(100, 70));
        this.setMaximumSize(new Dimension(100, 70));
        this.title.setForeground(new Color(140,140,140));
        this.title.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.value.setForeground(Color.white);
        this.value.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.add(this.title);
        this.add(Box.createVerticalGlue());
        this.add(this.value);
        this.add(Box.createVerticalGlue());
    }

}
