import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.lang.Math.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JOptionPane;

public class MainWindow extends JFrame {
    // Поля для ввода и отображения результата, а также для радио-кнопок и чекбокса
    private JTextField xEdit; // Поле для ввода значения X
    private JLabel resultLabel; // Метка для отображения результата вычислений
    private JRadioButton formula1Radio, formula2Radio, formula3Radio; // Радио-кнопки для выбора формулы
    private JCheckBox doubleResultCheck; // Чекбокс для выбора удвоения результата
    private JButton calculateButton; // Кнопка для запуска вычислений

    public MainWindow() {
        // Инициализируем компоненты и устанавливаем поведение при закрытии окна
        initComponents();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        // Устанавливаем формулу 1 как выбранную по умолчанию
        formula1Radio.setSelected(true);
    }

    private void initComponents() {
        // Создаем поля и кнопки
        xEdit = new JTextField(5); // Поле для ввода значения X
        resultLabel = new JLabel(); // Метка для отображения результата вычислений
        formula1Radio = new JRadioButton("y =[cos(x)]^2, x<=0"); // Радио-кнопка для выбора формулы 1
        formula2Radio = new JRadioButton("y= 2x^3-2, 0<x<1"); // Радио-кнопка для выбора формулы 2
        formula3Radio = new JRadioButton("y =(x+1)*(sqrt(x)), x>=1"); // Радио-кнопка для выбора формулы 3
        doubleResultCheck = new JCheckBox("Удвоить результат"); // Чекбокс для выбора удвоения результата
        calculateButton = new JButton("Рассчитать"); // Кнопка для запуска вычислений

        // Группируем радио-кнопки, чтобы можно было выбрать только одну из них
        ButtonGroup formulaGroup = new ButtonGroup();
        formulaGroup.add(formula1Radio);
        formulaGroup.add(formula2Radio);
        formulaGroup.add(formula3Radio);

        // Создаем панель для размещения компонентов
        JPanel panel = new JPanel(new GridLayout(5, 2));
        panel.add(new JLabel("Введите X:")); // Метка для поля ввода значения X
        panel.add(xEdit);
        panel.add(formula1Radio);
        panel.add(formula2Radio);
        panel.add(formula3Radio);
        panel.add(doubleResultCheck);
        panel.add(new JLabel()); // Пустое поле для выравнивания
        panel.add(calculateButton);
        panel.add(new JLabel("Результат y:")); // Метка для отображения результата вычислений
        panel.add(resultLabel);

        // Добавляем панель в основное окно
        add(panel);
        pack(); // Автоматическое изменение размера окна под содержимое

        // Обработчик нажатия кнопки "Рассчитать"
        calculateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Получаем значение X из поля ввода
                String xInput = xEdit.getText().trim();
                if (xInput.isEmpty()) {
                    // Если поле ввода пустое, показываем сообщение об ошибке
                    JOptionPane.showMessageDialog(null, "Пожалуйста, введите значение для X.", "Ошибка", JOptionPane.ERROR_MESSAGE);
                    xEdit.requestFocus();
                    return;
                }

                try {
                    double x = Double.parseDouble(xInput);

                    if (Double.isNaN(x)) {
                        // Если введенное значение не является числом, показываем сообщение об ошибке
                        JOptionPane.showMessageDialog(null, "Пожалуйста, введите допустимое значение для X.", "Ошибка", JOptionPane.ERROR_MESSAGE);
                        xEdit.requestFocus();
                        xEdit.selectAll();
                        return;
                    }

                    double result = 0.0;

                    // Выбираем формулу в зависимости от значения X
                    if (x <= 0) {
                        formula1Radio.setSelected(true);
                        result = pow(cos(x), 2);
                    } else if (x < 1) {
                        formula2Radio.setSelected(true);
                        result = 2 * pow(x, 3) - 2;
                    } else {
                        formula3Radio.setSelected(true);
                        result = (x + 1) * sqrt(x);
                    }

                    // Удваиваем результат, если чекбокс "Удвоить результат" выбран
                    if (doubleResultCheck.isSelected()) {
                        result *= 2;
                    }

                    // Отображаем результат в поле "Результат"
                    resultLabel.setText(Double.toString(result));
                } catch (NumberFormatException ex) {
                    // Если введенное значение не является числом, показываем сообщение об ошибке
                    JOptionPane.showMessageDialog(null, "Пожалуйста, введите допустимое значение для X.", "Ошибка", JOptionPane.ERROR_MESSAGE);
                    xEdit.requestFocus();
                    xEdit.selectAll();
                }
            }
        });

        // Обработчики нажатия радио-кнопок
        formula1Radio.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Получаем значение X из поля ввода
                double x = Double.parseDouble(xEdit.getText());
                // Вычисляем результат по формуле 1
                double result = pow(cos(x), 2);
                // Удваиваем результат, если чекбокс "Удвоить результат" выбран
                if (doubleResultCheck.isSelected()) {
                    result *= 2;
                }
                // Отображаем результат в поле "Результат"
                resultLabel.setText(Double.toString(result));
            }
        });

        formula2Radio.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Получаем значение X из поля ввода
                double x = Double.parseDouble(xEdit.getText());
                // Вычисляем результат по формуле 2
                double result = 2 * pow(x, 3) - 2;
                // Удваиваем результат, если чекбокс "Удвоить результат" выбран
                if (doubleResultCheck.isSelected()) {
                    result *= 2;
                }
                // Отображаем результат в поле "Результат"
                resultLabel.setText(Double.toString(result));
            }
        });

        formula3Radio.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Получаем значение X из поля ввода
                double x = Double.parseDouble(xEdit.getText());
                // Вычисляем результат по формуле 3
                double result = (x + 1) * sqrt(x);
                // Удваиваем результат, если чекбокс "Удвоить результат" выбран
                if (doubleResultCheck.isSelected()) {
                    result *= 2;
                }
                // Отображаем результат в поле "Результат"
                resultLabel.setText(Double.toString(result));
            }
        });

        // Обработчик изменения состояния чекбокса "Удвоить результат"
        doubleResultCheck.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Получаем текущий результат вычислений
                double result = Double.parseDouble(resultLabel.getText());
                // Если чекбокс "Удвоить результат" выбран, удваиваем результат, иначе делим его на 2
                if (doubleResultCheck.isSelected()) {
                    result *= 2;
                } else {
                    result /= 2;
                }
                // Отображаем новый результат в поле "Результат"
                resultLabel.setText(Double.toString(result));
            }
        });
    }

    public static void main(String[] args) {
        // Создаем и показываем окно при запуске приложения
        SwingUtilities.invokeLater(() -> new MainWindow());
    }
}