package kurs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * Created by tori on 027 27.12.16.
 */
public class GUI extends JFrame {
    private JButton button = new JButton("Press");
    private JTextField input = new JTextField("", 5);
    private JLabel label = new JLabel("Input:");
    private JRadioButton radio1 = new JRadioButton("Select this");
    private JRadioButton radio2 = new JRadioButton("Select that");
    private JCheckBox check = new JCheckBox("Check", false);
    private JTextField x11 = new JTextField("6000", 5);
    private JTextField x12 = new JTextField("8000", 5);
    private JTextField c12 = new JTextField("40", 5);
    private JTextField x13 = new JTextField("5000", 5);
    private JTextField c13 = new JTextField("20", 5);
    private JTextField x21 = new JTextField("9000", 5);
    private JTextField x22 = new JTextField("6000", 5);
    private JTextField c22 = new JTextField("30", 5);
    private JTextField x23 = new JTextField("4000", 5);
    private JTextField c23 = new JTextField("25", 5);
    private JTextField x31 = new JTextField("8000", 5);
    private JTextField c31 = new JTextField("30", 5);
    private JTextField x32 = new JTextField("7000", 5);
    private JTextField c32 = new JTextField("50", 5);
    private JTextField x33 = new JTextField("5000", 5);
    private JTextField c33 = new JTextField("30", 5);
    private JTextField c21 = new JTextField("20", 5);
    private JTextField c11 = new JTextField("50", 5);
    private JTextField alfa = new JTextField("", 5);
    private JTextField T1 = new JTextField("15000", 5);
    private JTextField T2 = new JTextField("20000", 5);
    private JTextField B1T1 = new JTextField("", 2);
    private JTextField B1T2 = new JTextField("", 2);
    private JTextField B1T3 = new JTextField("", 2);
    private JTextField B2T1 = new JTextField("", 2);
    private JTextField B2T2 = new JTextField("", 2);
    private JTextField B2T3 = new JTextField("", 2);
    private JTextField B3T1 = new JTextField("", 2);
    private JTextField B3T2 = new JTextField("", 2);
    private JTextField B3T3 = new JTextField("", 2);

    public GUI() {
        super("Simple Example");
        this.setBounds(100, 100, 900, 250);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        Container container = this.getContentPane();
        container.setLayout(new GridLayout(0,12));

        container.add(new JLabel("Country"));
        container.add(new JLabel("T1"));
        container.add(new JLabel("C1"));
        container.add(new JLabel("T2"));
        container.add(new JLabel("C2"));
        container.add(new JLabel("T3"));
        container.add(new JLabel("C3"));
        container.add(new JLabel());
        container.add(new JLabel());
        container.add(new JLabel("T1"));
        container.add(new JLabel("T2"));
        container.add(new JLabel("T3"));


        container.add(new JLabel("B1"));
        container.add(x11);
        container.add(c11);
        container.add(x12);
        container.add(c12);
        container.add(x13);
        container.add(c13);
        container.add(new JLabel());
        container.add(new JLabel("B1"));
        container.add(B1T1);
        container.add(B1T2);
        container.add(B1T3);


        container.add(new JLabel("B2"));
        container.add(x21);
        container.add(c21);
        container.add(x22);
        container.add(c22);
        container.add(x23);
        container.add(c23);
        container.add(new JLabel());
        container.add(new JLabel("B2"));
        container.add(B2T1);
        container.add(B2T2);
        container.add(B2T3);

        container.add(new JLabel("B3"));
        container.add(x31);
        container.add(c31);
        container.add(x32);
        container.add(c32);
        container.add(x33);
        container.add(c33);
        container.add(new JLabel());
        container.add(new JLabel("B3"));
        container.add(B3T1);
        container.add(B3T2);
        container.add(B3T3);

        container.add(new JLabel("T1"));
        container.add(T1);
        container.add(new JLabel("T2"));
        container.add(T2);
        container.add(new Label());
        container.add(new Label("Alfa"));
        container.add(alfa);
        container.add(button);
        button.addActionListener(new ButtonEventListener());


    }

    class ButtonEventListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String targetFunction = "0x1+0x2+0x3+0x4+0x5+0x6+1x7+1x8+1x9 -> max";
            String StargetFunction = "-x1-x2-x3-3x4-3X5-3x6+0x7+0x8+0x9 -> max";

            java.util.List<String> restrictions = new ArrayList<String>(Arrays.asList(
                    new String( c11.getText() + "x1 + " + c21.getText() + "x2 + " + c31.getText() +"x3 + " + c12.getText()
                            + "x4 + " + c22.getText() + "x5 + " + c32.getText() +"x6 - " + c13.getText() + "x7 - "
                            + c23.getText() + "x8 - " + c33.getText() +"x9 = 0" ),
                    new String("x1 + 0x2 + 0x3 + 0x4 + 0x5 + 0x6 + 0x7 + 0x8 + 0x9 <= " + x11.getText()),
                    new String("0x1 + x2 + 0x3 + 0x4 + 0x5 + 0x6 + 0x7 + 0x8 + 0x9 <= " + x21.getText()),
                    new String("0x1 + 0x2 + x3 + 0x4 + 0x5 + 0x6 + 0x7 + 0x8 + 0x9 <= " + x31.getText()),
                    new String("0x1 + 0x2 + 0x3 + x4 + 0x5 + 0x6 + 0x7 + 0x8 + 0x9 <= " + x12.getText()),
                    new String("0x1 + 0x2 + 0x3 + 0x4 + x5 + 0x6 + 0x7 + 0x8 + 0x9 <= " + x22.getText()),
                    new String("0x1 + 0x2 + 0x3 + 0x4 + 0x5 + x6 + 0x7 + 0x8 + 0x9 <= " + x32.getText()),
                    new String("0x1 + 0x2 + 0x3 + 0x4 + 0x5 + 0x6 + x7 + 0x8 + 0x9 <= " + x13.getText()),
                    new String("0x1 + 0x2 + 0x3 + 0x4 + 0x5 + 0x6 + 0x7 + x8 + 0x9 <= " + x23.getText()),
                    new String("0x1 + 0x2 + 0x3 + 0x4 + 0x5 + 0x6 + 0x7 + 0x8 + x9 <= " + x33.getText()),
                    new String("x1 + x2 + x3 + 0x4 + 0x5 + 0x6 + 0x7 + 0x8 + 0x9 <= " + T1.getText()),
                    new String("0x1 + 0x2 + 0x3 + x4 + x5 + x6 + 0x7 + 0x8 + 0x9 <= " + T2.getText())
            ));
            (new MultiTask()).multiTaskResult(targetFunction,StargetFunction,restrictions);
            //new LamdaTask(coefficients, restrictions);
            System.out.println(restrictions);

            String message = "";
            message += "Button was pressed\n";
            message += "Text is " + input.getText() + "\n";
            message += (radio1.isSelected() ? "Radio #1" : "Radio #2")
                    + " is selected\n";
            message += "CheckBox is " + ((check.isSelected())
                    ? "checked" : "unchecked");
            JOptionPane.showMessageDialog(null,
                    message,
                    "Output",
                    JOptionPane.PLAIN_MESSAGE);
        }
    }
}
