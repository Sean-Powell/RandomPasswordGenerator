import javax.swing.*;
import java.awt.*;

public class Run {
    private static boolean numbersBool = false;
    private static boolean uppercaseBool = false;
    private static boolean symbolsBool = false;

    private static String currentPassword = "";
    private static JFrame frame = new JFrame();
    private static JPanel oldPanel = null;

    private static int oldLengthSelected = 0;
    public static void main(String[] args) {
        frame.setSize(850, 200);
        frame.setTitle("Random Password Generator");
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        addPanel(createPanel());
        frame.setVisible(true);
    }

    private static JPanel createPanel() {
        JPanel panel = new JPanel();
        //creates the main container
        Container container = new Container();
        container.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        //creates the 3 radio buttons for selecting what you want in the password
        JRadioButton numbers = new JRadioButton("Numbers", numbersBool);
        numbers.addItemListener(e -> numbersBool = !numbersBool);
        JRadioButton uppercase = new JRadioButton("Uppercase", uppercaseBool);
        uppercase.addItemListener(e -> uppercaseBool = !uppercaseBool);
        JRadioButton symbols = new JRadioButton("Symbols", symbolsBool);
        symbols.addItemListener(e -> symbolsBool = !symbolsBool);
        //creates the JComboBox for how long you want the password to be
        Label label = new Label("Select Length:");
        Integer[] passwordLengths = {8, 12, 16, 32, 64, 128};
        JComboBox<Integer> comboBox = new JComboBox<>(passwordLengths);
        comboBox.setSelectedIndex(oldLengthSelected);
        //creates the button for generating a new password
        JButton GeneratePassword = new JButton("Create Password");
        GeneratePassword.addActionListener(e -> createPassword(passwordLengths[(oldLengthSelected = comboBox.getSelectedIndex())]));
        //adds the above objects to the container
        container.add(label);
        container.add(comboBox);
        container.add(numbers);
        container.add(uppercase);
        container.add(symbols);
        container.add(GeneratePassword);
        //separate container for displaying the password in
        Container passwordContainer = new Container();
        passwordContainer.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JTextField textField = new JTextField();
        textField.setText(currentPassword);
        textField.setColumns(74);
        textField.selectAll();
        passwordContainer.add(textField);
        //adds the containers to the panel
        panel.add(container);
        panel.add(passwordContainer);

        return panel;
    }

    private static void createPassword(int length) {
        StringBuilder password = new StringBuilder();
        //defines ascii limits of the symbols, letters and numbers
        final int symbolsMin = 33;
        final int symbolsMax = 47;
        final int symbols2Min = 58;
        final int symbols2Max = 63;

        final int numbersMin = 48;
        final int numbersMax = 57;

        final int uppercaseMin = 65;
        final int uppercaseMax = 90;

        final int lowercaseMin = 97;
        final int lowercaseMax = 122;
        int actualLength = 0;
        while(actualLength < length){
            int current = (int) Math.floor(Math.random() * (122 - 33) + 33); //creates a random number between the two limits
            if (symbolsBool && current > symbolsMin && current <= symbolsMax ||
                    symbolsBool && current >= symbols2Min && current <= symbols2Max ||
                    numbersBool && current >= numbersMin && current <= numbersMax ||
                    uppercaseBool && current >= uppercaseMin && current <= uppercaseMax ||
                    current >= lowercaseMin && current <= lowercaseMax) {
                char x = (char) current;
                password.append(x);
                actualLength++;
            }
        }

        currentPassword = password.toString();
        addPanel(createPanel());
    }

    private static void addPanel(JPanel panel){
        if(oldPanel != null){
            frame.remove(oldPanel); //keeps track of the old panel to remove it on updating the frame
        }
        oldPanel = panel;
        frame.add(panel);
        frame.repaint();
        frame.setVisible(true);
    }
}