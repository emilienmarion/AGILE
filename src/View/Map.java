package View;

import javax.swing.*;
import java.awt.*;

public class Map extends JPanel {
    public Map(){
        super();
        this.setBounds(50,50,100,100);
        this.setBackground(Color.white);
        this.setLayout(new GridBagLayout());
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawRect(50,50,50,50);
    }
}
