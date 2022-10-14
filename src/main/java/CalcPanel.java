import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class CalcPanel extends JPanel {
    private JPanel panel;
    private boolean start = true;//если правда, значит итерация находится в самом начале, т.е. еще не начали считать.
    private JTextPane display = new JTextPane();
    private JTextArea display2 = new JTextArea();


    private String preDisplay;//здесь считается индивидуально каждая итерация.
    private double result;//результат вычислений
    private String input;//сюда записывается нажатия клавиш
    private String command;//сюда записывается команда "="
    private String memory = "";//записывается то, что на экране во время удаления или добавления.
    private String memoryForPreDisplay = "";//отдельно считается каждая итерация и сюда записывается при удалении (<<) или
    //берётся при возврате(>>)
    private boolean iteration = false;//были итерация или нет(какое-либо вычисление,то есть нажималось ли "=")
    private String buffer = "";//сюда копируется всё содержимое display в конце итерации
    private Style style1 = null;
    private Style style3 = null;
    String str = "";//в команде gram в конце вставляется руб./кг

    SimpleAttributeSet attributeSetForHistory = new SimpleAttributeSet();
    SimpleAttributeSet attributeSetFor_str = new SimpleAttributeSet();
    SimpleAttributeSet attributeSetForResult = new SimpleAttributeSet();

    public CalcPanel() {
        createDisplay();
        createPanelButtons();
    }

    private void createDisplay() {
        display.setText(buffer + "\n" + "0");
        display.setFont(new Font("Arial", Font.TRUETYPE_FONT, 50));
        setLayout(null);
        display.setEditable(false);

        JScrollPane js_display2 = new JScrollPane(display,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        js_display2.setBounds(20, 0, 350, 150);
        add(js_display2);
    }

//    private void createCurrencyButtons(){
//        MainButton dollar = new MainButton(Text.get("ADD"), Style.ICON_OK, new AddEditDialogHandler(frame,this), HandlerCode.ADD);
//    }

    private void createPanelButtons() {
        panel = new JPanel();
        panel.setBounds(20, 170, 350, 280);
        GridLayout gridBut = new GridLayout(6, 4);
        panel.setLayout(gridBut);

        InsertAction insertAction = new InsertAction();
        CommandAction commandAction = new CommandAction();

        addButton("AC", commandAction);
        addButton("gram", insertAction);
        addButton("<<", commandAction);
        addButton(">>", commandAction);

        addButton("SCR", commandAction);
        addButton("%", insertAction);
        addButton("root", insertAction);
        addButton("^", insertAction);

        addButton("7", insertAction);
        addButton("8", insertAction);
        addButton("9", insertAction);
        addButton("/", insertAction);

        addButton("4", insertAction);
        addButton("5", insertAction);
        addButton("6", insertAction);
        addButton("*", insertAction);

        addButton("1", insertAction);
        addButton("2", insertAction);
        addButton("3", insertAction);
        addButton("-", insertAction);

        addButton("0", insertAction);
        addButton(".", insertAction);
        addButton("=", commandAction);
        addButton("+", insertAction);
        add(panel);
    }
private class MyButton extends JButton implements MouseListener{

MyButton(String title){
    super(title);
}
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        ((MyButton)e.getSource()).setBackground(Color.YELLOW);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        ((MyButton)e.getSource()).setBackground(Color.GRAY);
    }
}
    private void addButton(String label, ActionListener listener) {
//        JButton button = new JButton(label);
        MyButton button = new MyButton(label);
        button.setFont(new Font("Arial", Font.BOLD, 20));
        button.addActionListener(listener);
        panel.add(button);
    }

    private class InsertAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            StyleConstants.setFontSize(attributeSetForHistory, 20);
//            StyleConstants.setItalic(attributeSetForHistory, true);

            StyleConstants.setFontSize(attributeSetFor_str, 30);
            StyleConstants.setBold(attributeSetForResult, true);

            if (e.getActionCommand() == "root") input = "r";
            else if (e.getActionCommand() == "gram") input = "g";
            else if (e.getActionCommand() == "цена") input = "ц";
            else input = e.getActionCommand();

            if (start & !iteration) {//------------------------------------Начало
                if (input == ".") {
                    preDisplay = "0.";
                    start = false;
                } else if (input == "0") {
                    preDisplay = "0";
                } else {
                    preDisplay = input;
                    if (input != "+") start = false;
                }
                System.out.println("начало!------------------------------------------------------");
            } else if (start & iteration) {//------------------------------Начало и была итерация
                if (input == ".") {
                    preDisplay = "0.";
                    start = false;
                } else if (input == "0") {
                    preDisplay = "0";
                } else {
                    preDisplay = preDisplay + input;
                    start = false;
                    System.out.println("итерация! Начало----------------------------------------");
                    System.out.println(" preDisplay: " + preDisplay);
                    System.out.println(" display : " + display.getText());
                    System.out.println(" buffer  : " + buffer);
                    System.out.println("----------------------------------------");
                }
            } else {//----------Не в начале, любая итерация
                if ((input != "." || !(preDisplay.lastIndexOf(".") == preDisplay.length() - 1))//запрещает ставить подряд точки
                        && (input != "^" || !preDisplay.contains("^"))
                        && (input != "r" || !preDisplay.contains("r"))
                        && (input != "+" || !preDisplay.contains("+"))
                        && (input != "*" || !preDisplay.contains("*"))
                        && (input != "/" || !preDisplay.contains("/"))
                        && (input != "%" || !preDisplay.contains("%"))
                        && (input != "gram" || !preDisplay.contains("g"))) {
                    preDisplay = preDisplay + input;
                    memoryForPreDisplay = "";//очищаем хранилище для смещения(<<   >>)
                    System.out.println("не в начале и любая итерация--------------------------------------------------------");
                    System.out.println("preDisplay.lastIndexOf(\".\") " + preDisplay.lastIndexOf("."));
                    System.out.println("preDisplay.length() " + preDisplay.length());
                }
            }
            display.setText(buffer + "\n" + preDisplay);
            display.getStyledDocument().setCharacterAttributes(0, buffer.length(), attributeSetForHistory, true);
            System.out.println("preDisplay: " + preDisplay);
            System.out.println("display:" + display.getText());
            System.out.println("buffer:" + buffer);
        }
    }

    void setTextFit(JLabel label, String text) {
        Font originalFont = (Font) label.getClientProperty("originalfont"); // Get the original Font from client properties
        if (originalFont == null) { // First time we call it: add it
            originalFont = label.getFont();
            label.putClientProperty("originalfont", originalFont);
        }

        int stringWidth = label.getFontMetrics(originalFont).stringWidth(text);
        int componentWidth = label.getWidth();

        if (stringWidth > componentWidth) { // Resize only if needed
            // Find out how much the font can shrink in width.
            double widthRatio = (double) componentWidth / (double) stringWidth;

            int newFontSize = (int) Math.floor(originalFont.getSize() * widthRatio); // Keep the minimum size

            // Set the label's font size to the newly determined size.
            label.setFont(new Font(originalFont.getName(), originalFont.getStyle(), newFontSize));
        } else
            label.setFont(originalFont); // Text fits, do not change font size

        label.setText(text);
    }//Уменьшает шрифт в размер окна

    private class CommandAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            command = e.getActionCommand();
            str = "";
            calc(preDisplay);

            if (!(CalcPanel.this.command == "<<")) {
                if (!(CalcPanel.this.command == "AC")) {
                    if (!(CalcPanel.this.command == ">>")) {
                        start = true;
                        iteration = true;
                        memoryForPreDisplay = "";
                    }
                }
            }

        }

        private double plus(String x) {
            System.out.println(start);
            System.out.println("зашли в метод сложение");
            double x2;
            try {
                String x1 = preDisplay.substring(0, preDisplay.lastIndexOf("+"));
                x2 = Double.parseDouble(x1);
            } catch (NumberFormatException nfe) {
                x2 = result;
            }//чтобы можно было прибавлять сразу после =
            String y1;
            if (x.contains("%")) {
                y1 = preDisplay.substring(preDisplay.lastIndexOf("+") + 1, preDisplay.lastIndexOf("%"));
                double y2 = Double.parseDouble(y1);
                result = Math.round((x2 + (x2 / 100 * y2)) * Math.pow(10, 6)) / Math.pow(10, 6);
            } else {
                y1 = preDisplay.substring(preDisplay.lastIndexOf("+") + 1);
                double y2 = Double.parseDouble(y1);
                result = Math.round((x2 + y2) * Math.pow(10, 6)) / Math.pow(10, 6);
            }

            return result;
        }//для кнопки "+"

        private double minus(String x) {
            System.out.println("зашли в метод минус");
            String x1 = preDisplay.substring(0, preDisplay.lastIndexOf("-"));
            double x2 = Double.parseDouble(x1);
            String y1;
            if (x.contains("%")) {
                y1 = preDisplay.substring(preDisplay.lastIndexOf("-") + 1, preDisplay.lastIndexOf("%"));
                double y2 = Double.parseDouble(y1);
                result = Math.round((x2 - (x2 / 100 * y2)) * Math.pow(10, 6)) / Math.pow(10, 6);
            } else {
                y1 = preDisplay.substring(preDisplay.lastIndexOf("-") + 1);
                double y2 = Double.parseDouble(y1);
                result = Math.round((x2 - y2) * Math.pow(10, 6)) / Math.pow(10, 6);
            }
//            System.out.println("x2:" + x2 + "; y2:" + y2 + "; =" + result);
            return result;
        }//для кнопки "-"

        private double multiplication(String x) {
            System.out.println("зашли в метод умножение");
            String x1 = preDisplay.substring(0, preDisplay.lastIndexOf("*"));
            double x2;
            try {
                x2 = Double.parseDouble(x1);
            } catch (NumberFormatException nfe) {
                //чтобы можно было умножать сразу после кнопки =
                x2 = result;
            }
            String y1;
            if (x.contains("%")) {
                y1 = preDisplay.substring(preDisplay.lastIndexOf("*") + 1, preDisplay.lastIndexOf("%"));
                double y2 = Double.parseDouble(y1);
                result = Math.round((x2 * (x2 / 100 * y2)) * Math.pow(10, 6)) / Math.pow(10, 6);
            } else {
                y1 = preDisplay.substring(preDisplay.lastIndexOf("*") + 1);
                double y2 = Double.parseDouble(y1);
                result = Math.round((x2 * y2) * Math.pow(10, 6)) / Math.pow(10, 6);
            }
            return result;
        }//для кнопки "*"

        private double divide(String x) {
            System.out.println("зашли в метод деление");
            String x1 = preDisplay.substring(0, preDisplay.lastIndexOf("/"));
            double x2;
            try {
                x2 = Double.parseDouble(x1);
            } catch (NumberFormatException nfe) {
                //чтобы можно было делить сразу после кнопки =
                x2 = result;
            }
            String y1;
            if (x.contains("%")) {
                y1 = preDisplay.substring(preDisplay.lastIndexOf("/") + 1, preDisplay.lastIndexOf("%"));
                double y2 = Double.parseDouble(y1);
                result = Math.round((x2 / (x2 / 100 * y2)) * Math.pow(10, 6)) / Math.pow(10, 6);
            } else {
                y1 = preDisplay.substring(preDisplay.lastIndexOf("/") + 1);
                double y2 = Double.parseDouble(y1);
                result = Math.round((x2 / y2) * Math.pow(10, 6)) / Math.pow(10, 6);
                System.out.println("x2:" + x2 + "; y2:" + y2 + "; =" + result);
            }
            return result;
        }//для кнопки "/"

        private double power(String x) {
            String x1 = preDisplay.substring(0, preDisplay.lastIndexOf("^"));
            double x2;
            try {
                x2 = Double.parseDouble(x1);
            } catch (NumberFormatException nfe) {
                //чтобы можно было возводить в степень сразу после кнопки =
                x2 = result;
            }
            String y1 = preDisplay.substring(preDisplay.lastIndexOf("^") + 1);
            double y2 = Double.parseDouble(y1);
            result = Math.pow(x2, y2);
            return result;
        }//для кнопки "^"

        private double root(String x) {
            String x1 = preDisplay.substring(0, preDisplay.lastIndexOf("r"));
            double x2;
            try {
                x2 = Double.parseDouble(x1);
            } catch (NumberFormatException nfe) {
                //чтобы можно было брать корень сразу после кнопки =
                x2 = result;
            }
            String y1 = preDisplay.substring(preDisplay.lastIndexOf("r") + 1);
            double y2 = Double.parseDouble(y1);
            result = Math.round((Math.pow(x2, (1.0 / y2))) * Math.pow(10, 6)) / Math.pow(10, 6);
            return result;
        }//для кнопки "root"

        private double percent(String x) {
            String x1 = preDisplay.substring(0, preDisplay.lastIndexOf("%"));
            double x2;
            try {
                x2 = Double.parseDouble(x1);
            } catch (NumberFormatException nfe) {
                x2 = result;
            }//чтобы можно было брать процент сразу после кнопки =
            String y1 = preDisplay.substring(preDisplay.lastIndexOf("%") + 1);
            double y2 = Double.parseDouble(y1);
            result = (Math.round(x2 / 100 * y2 * 10000)) / 10000;
            return result;
        }//для кнопки "%"

        private double pricePerKilogram(String x) {
            String x1 = preDisplay.substring(0, preDisplay.lastIndexOf("g"));
            double x2 = Double.parseDouble(x1);
            String y1 = preDisplay.substring(preDisplay.lastIndexOf("g") + 1);
            double y2 = Double.parseDouble(y1);
            result = Math.round(y2 / (x2 / 1000));
            str = "руб./кг";
            return result;
        }//для кнопки "gram"

        private void AllClear() {
            if (!preDisplay.isEmpty()) {
                preDisplay = "";
                start = true;
                if (buffer.isBlank()) display.setText("0");
                else {
                    display.setText(buffer + "\n0");
                    display.getStyledDocument().setCharacterAttributes(0, buffer.length() + 1, attributeSetForHistory, true);
                }
            }
            display.setText(buffer + "\n0");
            display.getStyledDocument().setCharacterAttributes(0, buffer.length() + 1, attributeSetForHistory, true);
            start = true;
        }//для кнопки "AC"

        private void moveLeft() {
            if (!display.getText().equals(buffer + "\n0")) {
                System.out.println("------------------------------------------------");
                System.out.println("Мы зашли в moveLeft");
                memoryForPreDisplay += preDisplay.substring(preDisplay.length() - 1);
                preDisplay = preDisplay.substring(0, preDisplay.length() - 1);
                display.setText(buffer + "\n" + preDisplay);
                if (preDisplay.isBlank()) {
                    display.setText(buffer + "\n0");
                }
                display.getStyledDocument().setCharacterAttributes(0, buffer.length(), attributeSetForHistory, true);
                System.out.println("memoryForPreDisplay: " + memoryForPreDisplay);
                System.out.println("preDisplay: " + preDisplay);
                System.out.println("display: " + display.getText());
                System.out.println("buffer: " + buffer);
                System.out.println("------------------------------------------------");
            }
        }

        private void moveRight() {
            if (!memoryForPreDisplay.isBlank()) {
                System.out.println("------------------------------------------------");
                System.out.println("Мы зашли в  moveRight");
                preDisplay += memoryForPreDisplay.substring(memoryForPreDisplay.length() - 1);
                memoryForPreDisplay = memoryForPreDisplay.substring(0, memoryForPreDisplay.length() - 1);
                display.setText(buffer + "\n" + preDisplay);
                display.getStyledDocument().setCharacterAttributes(0, buffer.length(), attributeSetForHistory, true);
                System.out.println("memoryForPreDisplay: " + memoryForPreDisplay);
                System.out.println("preDisplay: " + preDisplay);
                System.out.println("display: " + display.getText());
                System.out.println("buffer: " + buffer);
                System.out.println("------------------------------------------------");
            }
        }

        //считаем кол-во "-" в preDisplay
        private static long count(String x, char c) {
            return x.chars().filter(k -> k == c).count();
        }//считаем кол-во символов, когда понадобится.

        public void calc(String x) {
            System.out.println("зашли в метод Calc-----------------------------------");
            if (command.equals("<<") && !start) moveLeft();
            if (command.equals(">>") && !start) moveRight();
            if (command.equals("AC")) AllClear();
            if (command.equals("=")) {
                System.out.println("count: " + count(x, '-'));
                if (x.contains("+")) result = plus(x);
                /*Когда производим какие-либо действия с отрицательными числами (напр. -5*6)
                нужно чтобы минус не отрабатывал в начале, поэтому проверка(минус стоит не в начале и в кол-ве не 1 шт.) */
                else if (x.contains("-") && !(x.startsWith("-") && count(x, '-') == 1)) result = minus(x);
                else if (x.contains("*")) result = multiplication(x);
                else if (x.contains("/")) result = divide(x);
                else if (x.contains("^")) result = power(x);
                else if (x.contains("r")) result = root(x);
                else if (x.contains("%")) result = percent(x);
                else if (x.contains("g")) result = pricePerKilogram(x);

                //если result число целое убираем точку
                if (result % 1 == 0) preDisplay = x + " =" + (int) result + str + ";";
                else preDisplay = x + " =" + result + str + ";";

                // вывод результата на экран
                display.setText(buffer + "\n" + preDisplay);

                System.out.println("result: " + result);
                System.out.println("(\"\"+result).length()   " + (("" + result).length()));

//                //str сделать результат жирным шрифтом
//                display.getStyledDocument().setCharacterAttributes
//                        (display.getText().substring(0, display.getText().lastIndexOf("=")).length(), ("" + result).length(), attributeSetForResult, true);

//                str сделать шрифтом поменьше, чем основной текст
                display.getStyledDocument().setCharacterAttributes
                        (display.getText().length() - str.length() - 1, str.length() + 1, attributeSetFor_str, true);

                //предыдущие итерации (историю) сделать меньшим шрифтом.
                display.getStyledDocument().setCharacterAttributes
                        (0, buffer.length(), attributeSetForHistory, true);

                //сохраняем в буфер то, что было в этой итерации и удаляем внутри все пробелы и ставим 3 пробела в конце
                buffer = buffer + preDisplay.replaceAll("\\s", "") + "   ";

                preDisplay = "";//обнуляем значение данной итерации
                System.out.println("preDisplay очищен" + preDisplay + "\n ////////////////////////КОНЕЦ//////////////////////////////////");
            }
            if (command.equals("SCR")) {
                System.out.println(Calculator.frame.getSize());
            }
        }
    }
}
