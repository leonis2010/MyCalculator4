import javax.swing.*;

public class Calculator {
    static JFrame frame;
    public static void main(String[] args) {
        frame = new JFrame();
        frame.setTitle("�����������");
        frame.setBounds(800, 200, 405, 525);
        CalcPanel panel = new CalcPanel();
        System.out.println("�� ���?");
        frame.add(panel);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setResizable(false);

    }
}