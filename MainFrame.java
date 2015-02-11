import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

/**
 * The GUI component to the Converter application
 * Controls, buttons, and functions of the application
 * are created and set up here.
 *
 * @author Siddarth Senthilkumar
 * @version 1.0
 */
public class MainFrame extends JFrame {

    private Converter converter;
    private GridBagLayout gridBagLayout;

    private JPanel mainPanel;
    private JPanel scaleAndButtonPanel;

    private JScrollPane indianNotationScrollPane;
    private JScrollPane standardNotationScrollPane;
    private JScrollPane scaleScrollPane;

    private JTextArea indianNotationTextArea;
    private JTextArea standardNotationTextArea;
    private JTextArea scaleTextArea;

    private JLabel indianNotationLabel;
    private JLabel standardNotationLabel;
    private JLabel scaleLabel;

    private JButton convertButton;
    private JButton saveButton;

    /**
     * Initializes components of the GUI,
     * creates buttons for the GUI,
     * then places everything onto this frame.
     */
    public MainFrame() {
        super("Music Note Converter");
        converter = new Converter();
        initialize();
        setUpConvertButton();
        setUpSaveButton();
        addComponentsToFrame();
    }

    /**
     * Creates the underlying panels and layout of the GUI.
     * Adds textareas for user input and corresponding labels.
     * Sets useful properties of the textareas.
     * Creates Button objects to be placed onto JFrame.
     */
    private void initialize() {
        gridBagLayout = new GridBagLayout();
        mainPanel = new JPanel(new GridBagLayout());
        scaleAndButtonPanel = new JPanel();

        indianNotationTextArea = new JTextArea();
        standardNotationTextArea = new JTextArea();
        scaleTextArea = new JTextArea();

        indianNotationScrollPane = new JScrollPane(indianNotationTextArea);
        standardNotationScrollPane = new JScrollPane(standardNotationTextArea);
        scaleScrollPane = new JScrollPane(scaleTextArea);

        indianNotationLabel = new JLabel("Indian Notation",
                SwingConstants.CENTER);
        standardNotationLabel = new JLabel("Standard Notation",
                SwingConstants.CENTER);
        scaleLabel = new JLabel("Scale:");

        convertButton = new JButton("Convert");
        saveButton = new JButton("Save");

        indianNotationTextArea.setLineWrap(true);
        indianNotationTextArea.setWrapStyleWord(true);

        standardNotationTextArea.setLineWrap(true);
        standardNotationTextArea.setWrapStyleWord(true);

        scaleTextArea.setLineWrap(true);
        scaleTextArea.setWrapStyleWord(true);
    }

    /**
     * Places the notation labels onto the main panel
     * with desirable spacings.
     * @param c The GridBagConstraints object specifying visual properties
     */
    private void addIndianStandardLabels(GridBagConstraints c) {

        c.ipadx = 400;
        c.ipady = 20;
        mainPanel.add(indianNotationLabel, c);

        c.gridx = 1;
        mainPanel.add(standardNotationLabel, c);
    }

    /**
     * Places text areas (on scrollPanes) onto the main panel
     * with desirable spacings.
     * @param c The GridBagConstraints object specifying visual properties
     */
    private void addTextAreas(GridBagConstraints c) {

        c.gridx = 0;
        c.gridy = 1;
        c.ipady = 400;
        mainPanel.add(indianNotationScrollPane, c);

        c.gridx = 1;
        mainPanel.add(standardNotationScrollPane, c);
    }

    /**
     * Places row of components with scale label and Convert button
     * onto the main panel with the correct spacings.
     * @param c The GridBagConstraints object specifying visual properties
     */
    private void addScaleAndButtonRow(GridBagConstraints c) {
        scaleAndButtonPanel.setLayout(gridBagLayout);
        c.ipady = 0;
        c.ipadx = 0;
        c.gridy = 0;
        c.gridx = 0;
        c.anchor = GridBagConstraints.WEST;
        c.insets = new Insets(0, 0, 0, 15);
        scaleAndButtonPanel.add(scaleLabel, c);
        c.ipadx = 385;
        c.gridx = 1;
        c.insets = new Insets(0, 0, 0, 65);
        scaleAndButtonPanel.add(scaleScrollPane, c);
        c.ipadx = 100;
        c.gridx = 2;
        c.insets = new Insets(0, 0, 0, 85);
        scaleAndButtonPanel.add(convertButton, c);
        c.gridx = 3;
        c.insets = new Insets(0, 0, 0, 0);
        scaleAndButtonPanel.add(saveButton, c);
        c.ipadx = 0;
        c.insets = new Insets(20, 0, 0, 0);
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 2;
        mainPanel.add(scaleAndButtonPanel, c);
    }

    /**
     * Adds the various components to the frame
     * using the GridBagConstraints to specify positioning
     * and spacing on panels.
     */
    private void addComponentsToFrame() {
        GridBagConstraints constraints = new GridBagConstraints();
        addIndianStandardLabels(constraints);
        addTextAreas(constraints);
        addScaleAndButtonRow(constraints);
        getContentPane().add(mainPanel);
    }

    /**
     * Uses converter object to set up functionality of
     * Convert button.
     */
    private void setUpConvertButton() {
        convertButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                String western = converter.toWestern(
                        indianNotationTextArea.getText(),
                        scaleTextArea.getText());
                standardNotationTextArea.setText(western);
            }
        });
    }

    /**
     * Adds an action listener to the save button,
     * and calls another method to implement its functionality
     */
    private void setUpSaveButton() {
        saveButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                saveActions();
            }
        });
    }

    /**
     * Actions to be taken whenever the user asks to save
     * Saves a copy of converted and original notes to a .txt file.
     * User can specify where to save the file to.
     */
    private void saveActions() {
        final JFileChooser fc = new JFileChooser();
        int returnVal = fc.showSaveDialog(mainPanel); // parent component to
                                                        // JFileChooser
        if (returnVal == JFileChooser.APPROVE_OPTION) { // OK button pressed by
                                                        // user
            File file = fc.getSelectedFile(); // get File selected by user
            PrintWriter out;
            try {
                out = new PrintWriter(file + ".txt");
                out.println("------------------------------------------------");
                out.println("Scale: " + scaleTextArea.getText());
                out.println("------------------------------------------------");
                out.println();
                out.println("------------------------------------------------");
                out.println("Indian Notation");
                out.println("------------------------------------------------");
                out.println();
                out.println(indianNotationTextArea.getText());
                out.println();
                out.println();
                out.println("------------------------------------------------");
                out.println("Standard Notation");
                out.println("------------------------------------------------");
                out.println();
                out.println(standardNotationTextArea.getText());
                out.close();
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * Changes the default behavior of closing the frame's window.
     * Useful to ask "are you sure you want to exit?" dialogues.
     */
    private void askOnExit() {
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                exit();
            }
        });
    }

    /**
     * Asks the user if they are sure they want to quit.
     */
    private void exit() {
        int result = JOptionPane.showConfirmDialog(null,
                "Exit the application?");
        if (result == JOptionPane.OK_OPTION) {
            System.exit(0);
        }
    }

    /**
     * Places logos and menu and sets useful JFrame properties.
     * Makes the program visible and operable.
     */
    public void run() {
        ImageIcon icon = new ImageIcon("Resources/trebleIcon.jpg");
        setIconImage(icon.getImage());
        setJMenuBar(createMenuBar());
        askOnExit();
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    /**
     * Creates a menu bar at the top of the application.
     * Contains save functionality and "About" option.
     * @return The JMenuBar with the desired functions
     */
    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu file = new JMenu("File");
        JMenu about = new JMenu("About");

        menuBar.add(file);
        menuBar.add(about);

        JMenuItem save = new JMenuItem("Save");
        JMenuItem exit = new JMenuItem("Exit");
        JMenuItem contact = new JMenuItem("Contact Me");

        save.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                saveActions();
            }
        });

        exit.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                exit();
            }
        });

        contact.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                showAboutDialog();
            }
        });

        file.add(save);
        file.add(exit);
        about.add(contact);

        return menuBar;
    }

    /**
     * Message dialogue to be shown when
     * user asks to see "about me"
     */
    private void showAboutDialog() {
        JOptionPane
                .showMessageDialog(
                        this,
                        "This program created by Sid S."
                         + "\nSend questions or comments to pikahero2@gmail.com"
                         + "\nThank you for using this program!",
                        "Contact Me", JOptionPane.INFORMATION_MESSAGE);
    }
}
