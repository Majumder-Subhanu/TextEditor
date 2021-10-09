//import all the required java packages

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//A new class that has the functionalities of JFrame and implements ActionListeners by default, letting us use them anonymously
public class Main extends JFrame implements ActionListener {

    private final JTextArea editor; // Declare text area
    private final JFrame frame; // Declare new frame
    private int c = 0; // Declare integer variable and set it to 0
    private Font defaultFont;
    private int defaultFontSize;
    private String font;

    // Declaring a constructor to initialize all the data members
    public Main() {

        // Initialize different gui data members
        frame = new JFrame("Text Editor");
        editor = new JTextArea();
        editor.setFont(new Font("Consolas", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(editor);
        JMenuBar menuBar = new JMenuBar();
        JMenu menuItem = new JMenu("File");
        JMenu editFile = new JMenu("Edit");
        JMenu mode = new JMenu("Preferences");

        Image icon = Toolkit.getDefaultToolkit().getImage("notepad.ico");
        frame.setIconImage(icon);

        // Declare new sub menu item
        JMenuItem newFile = new JMenuItem("New");
        JMenuItem openFile = new JMenuItem("Open");
        JMenuItem saveFile = new JMenuItem("Save");
        JMenuItem printFile = new JMenuItem("Print");
        JMenuItem cutText = new JMenuItem("cut");
        JMenuItem copyText = new JMenuItem("copy");
        JMenuItem pasteText = new JMenuItem("paste");
        JMenuItem replaceText = new JMenuItem("replace");
        JMenuItem wrapText = new JMenuItem("wrap text");
        //JMenuItem closeFile = new JMenuItem("close");
        JMenuItem darkMode = new JMenuItem("Dark theme");
        JMenuItem lightMode = new JMenuItem("Light theme");
        JMenuItem fontSize = new JMenuItem("Font size");
        JMenuItem font = new JMenuItem("Font");
        JMenuItem bold = new JMenuItem("Bold");
        JMenuItem italic = new JMenuItem("Italic");
        JMenuItem normal = new JMenuItem("Normal");

        editor.setMargin(new Insets(10, 10, 10, 10)); // Set margin for text area
        // Add anonymous action listeners
        newFile.addActionListener(this);
        openFile.addActionListener(this);
        saveFile.addActionListener(this);
        printFile.addActionListener(this);
        cutText.addActionListener(this);
        copyText.addActionListener(this);
        pasteText.addActionListener(this);
        replaceText.addActionListener(this);
        wrapText.addActionListener(this);
        //closeFile.addActionListener(this);
        darkMode.addActionListener(this);
        lightMode.addActionListener(this);
        fontSize.addActionListener(this);
        font.addActionListener(this);
        bold.addActionListener(this);
        italic.addActionListener(this);
        normal.addActionListener(this);

        // Add the sub menu items to the parent menu item
        menuItem.add(newFile);
        menuItem.add(openFile);
        menuItem.add(saveFile);
        menuItem.add(printFile);
        editFile.add(cutText);
        editFile.add(copyText);
        editFile.add(pasteText);
        editFile.add(bold);
        editFile.add(italic);
        editFile.add(normal);
        //JMenuItem replaceText = new JMenuItem("replace");
        editFile.add(wrapText);
        editFile.add(replaceText);
        mode.add(darkMode);
        mode.add(lightMode);
        mode.add(font);
        mode.add(fontSize);

        // Add menu item to the menu bar
        // File.add(replaceText);
        menuBar.add(menuItem);
        menuBar.add(editFile);
        menuBar.add(mode);
        //menuBar.add(closeFile);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);// Set the default close operation
        frame.setSize(900, 700); // Set the initial size of the window

        // Add all the gui items to the frame
        frame.setJMenuBar(menuBar);
        frame.add(scrollPane);

        frame.setVisible(true); // Make the frame visible
    }

    @Override
    // overriding a class function to assign tasks to the menu items via action
    // listeners
    public void actionPerformed(ActionEvent e) {

        String s = e.getActionCommand(); // Gets the value of the menu bar items through anonymous click listener

        // Switch case statement to assign separate tasks to the different menubar items
        switch (s) {

                // Cut action
            case "cut":
                editor.cut();
                break;

                // Copy action
            case "copy":
                editor.copy();
                break;

                // Paste option
            case "paste":
                editor.paste();
                break;

                // Wrap text functionality
            case "wrap text":
                c++;
                if (c % 2 != 0) {
                    editor.setLineWrap(true);
                    editor.setWrapStyleWord(true);
                } else {
                    editor.setLineWrap(false);
                    editor.setWrapStyleWord(false);
                }
                break;

            case "replace":
                JTextField old_word = new JTextField();
                JTextField new_word = new JTextField();
                Object[] message = {
                        "old word:", old_word,
                        "new word:", new_word
                    };

                int option = JOptionPane.showConfirmDialog(null, message, "Login", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) 
                    editor.setText(editor.getText().replace(old_word.getText(), new_word.getText()));
                else{
                    if (!editor.getText().contains(old_word.getText()))
                        JOptionPane.showMessageDialog(frame, "No word present");
                    else
                        JOptionPane.showMessageDialog(frame, "the user cancelled the operation");
                }

                break;
                // Save option
            case "Save": 
                // Declare a new chooser, to choose the location of saving the file
                JFileChooser j = new JFileChooser("");
                int r = j.showSaveDialog(null);

                // Check weather the user has selected a location to save the file
                if (r == JFileChooser.APPROVE_OPTION) {
                    // Extract the location set by the user from the chooser object
                    File file = new File(j.getSelectedFile().getAbsolutePath());
                    try {

                        // Writing all the content to the assigned file
                        FileWriter fr = new FileWriter(file, false);
                        BufferedWriter br = new BufferedWriter(fr);
                        // Extract the text from the text area
                        br.write(editor.getText());
                        br.flush();
                        br.close();
                    } catch (Exception evt) {
                        // Generate a new default window to show an error message
                        JOptionPane.showMessageDialog(frame, evt.getMessage());
                    }
                } else
                // Generate a new default window to show a cancellation message
                    JOptionPane.showMessageDialog(frame, "the user cancelled the operation");
                break;

                // Print option
            case "Print":
                try {
                    // Try to print the file using the inbuilt method print()
                    editor.print();
                } catch (Exception evt) {
                    // Generate a new default window to show an error message
                    JOptionPane.showMessageDialog(frame, evt.getMessage());
                }

                break;

            case "Bold":
                Font boldFont =new Font(editor.getFont().getName(), Font.BOLD, editor.getFont().getSize());
                editor.setFont(boldFont);
                break;

            case "Italic":
                boldFont =new Font(editor.getFont().getName(), Font.ITALIC, editor.getFont().getSize());
                editor.setFont(boldFont);
                break;

            case "Normal":
                boldFont =new Font(editor.getFont().getName(), Font.PLAIN, editor.getFont().getSize());
                editor.setFont(boldFont);
                break;
                // Open file option
            case "Open": 
                // Declare a new chooser, to choose the location of saving the file
                j = new JFileChooser("");
                r = j.showOpenDialog(null);
                // Check weather the user has selected a valid file
                if (r == JFileChooser.APPROVE_OPTION) {
                    // Extract the location used by the user from the chooser object
                    File fi = new File(j.getSelectedFile().getAbsolutePath());
                    try {

                        // Read from file and set the text of the text area to be the contents of the
                        // file
                        String s1, sl;
                        FileReader fr = new FileReader(fi);
                        BufferedReader br = new BufferedReader(fr);
                        sl = br.readLine();
                        while ((s1 = br.readLine()) != null) {
                            sl = sl.concat("\n").concat(s1);
                        }
                        editor.setText(sl);
                        br.close();
                    } catch (Exception evt) {
                        // Generate a new default window to show an error message
                        JOptionPane.showMessageDialog(frame, evt.getMessage());
                    }
                } else
                // Generate a new default window to show a cancellation message
                    JOptionPane.showMessageDialog(frame, "the user cancelled the operation");
                break;

                // Create new file option
            case "New":
                // Make a new empty file
                editor.setText("");
                break;

            case "Dark theme":
                editor.setCaretColor(Color.LIGHT_GRAY);
                editor.setForeground(Color.WHITE);
                editor.setBackground(Color.BLACK);
                break;

            case "Light theme":
                editor.setCaretColor(Color.BLACK);
                editor.setForeground(Color.BLACK);
                editor.setBackground(Color.WHITE);
                break;

            case "Font size":
                String [] fontValues = {"10", "12", "14", "16", "18", "20"};
                String fontSize = (String) JOptionPane.showInputDialog(null, "Select Font Size", "Font Size Selector", JOptionPane.QUESTION_MESSAGE, null, fontValues, fontValues[0]);
                if (fontSize != null) {
                    defaultFontSize = Integer.parseInt(fontSize);
                    defaultFont = new Font(font, Font.PLAIN, defaultFontSize );
                    editor.setFont(defaultFont);
                }
                break;
            case "Font":
                String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
                font = (String) JOptionPane.showInputDialog(null, "Select Font Size", "Font Size Selector", JOptionPane.QUESTION_MESSAGE, null, fonts, fonts[0]);
                if (font != null) {
                    defaultFont = new Font(font, Font.PLAIN,  defaultFontSize);
                    editor.setFont(defaultFont);
                }
        }
    }

    // Main method
    public static void main(String[] args) {
        // New instance of the class Main
        new Main();
    }
}