import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JFrame;
import javax.swing.UIManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.io.FileWriter;

import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;

public class CipherToolProgram{
    public static void main(String[] args){
        CipherTool cipherTool = new CipherTool();

        JFrame frame = new JFrame("Cipher Tool Program");
        Object[] options1 = {"Encrypt", "Decrypt"};

        JPanel panel = new JPanel();

        while(true){
            panel.add(new JLabel("Encrypt or decrypt?"));

            int result = JOptionPane.showOptionDialog(frame, panel, "Enter a Number", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options1, null);
            if (result == JOptionPane.YES_OPTION){ // ENCRYPT
                Object[] options2 = {"File", "Input"};
                panel.removeAll();
                panel.add(new JLabel("File or text input?"));
                result = JOptionPane.showOptionDialog(frame, panel, "Input Type", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options2, null);
    
                if(result == JOptionPane.YES_OPTION){ // FILE
    
                    String inputFileName = JOptionPane.showInputDialog("Name of text file to encrypt (add .txt)");
                    String keyFileName = JOptionPane.showInputDialog("Name of key file to use\nLeave blank to generate a new key");
    
                    try{
                        File inputFile = new File("../" + inputFileName);
                        Scanner inputFileReader = new Scanner(inputFile);
                        while(inputFileReader.hasNextLine()) {
                            // READING FILE CONTENTS
                            String inputFileData = inputFileReader.nextLine();
                            
                            // ENCRYPTING FILE CONTENTS
                            try{
                                FileWriter fileWriter = new FileWriter("../(e) " + inputFileName);
                                if(keyFileName.equals("")){ // if key input is blank
                                    fileWriter.write(cipherTool.encrypt(inputFileData, keyFileName));
                                    fileWriter.flush();
                                    fileWriter.close();

                                    FileWriter keyWriter = new FileWriter("../key.txt");
                                    System.out.println("The key to write is: " + cipherTool.getKey());
                                    keyWriter.write(cipherTool.getKey());
                                    keyWriter.flush();
                                    keyWriter.close();

                                    JOptionPane.showMessageDialog(frame, "Both encrypted text file and key file successfully written.");
                                }
                                else{
                                    File keyFile = new File("../" + keyFileName);
                                    Scanner keyFileReader = new Scanner(keyFile);
                                    while(keyFileReader.hasNextLine()){
                                        String keyFileData = keyFileReader.nextLine();
                                        fileWriter.write(cipherTool.encrypt(inputFileData, keyFileData));
                                        fileWriter.flush();
                                        fileWriter.close();
                                    }
                                    keyFileReader.close();

                                    JOptionPane.showMessageDialog(frame, "Encrypted text file successfully written.");
                                }

                                JOptionPane.showMessageDialog(frame, "Both encrypted text file and key file successfully written.");
                            }catch(IOException e) {
                                JOptionPane.showMessageDialog(frame, "Error");
                                e.printStackTrace();
                            }
                        }
                        inputFileReader.close();
                    }catch(FileNotFoundException e) {
                        System.out.println("Error");
                        e.printStackTrace();
                    }
                }
                else if(result == JOptionPane.NO_OPTION){ // Encrypt from input
                    String inputToEncrypt = JOptionPane.showInputDialog("Text to encrypt");
                    String keyFileName = JOptionPane.showInputDialog("Name of key file to use\nLeave blank to generate a new key");
                    JTextArea ta = new JTextArea(10, 40);
                    ta.setWrapStyleWord(true);
                    ta.setLineWrap(true);
                    ta.setCaretPosition(0);
                    ta.setEditable(false);

                    try{
                        if(keyFileName.equals("")){ // if key input is blank
                            String encryptedMessage = cipherTool.encrypt(inputToEncrypt, keyFileName); // Get the encrypted message
                            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(encryptedMessage),null); // Copy encrypted message to clipboard
                            JOptionPane.showMessageDialog(frame, "Message copied to clipboard. Paste it where you need to now.\nClick the button in the next window to copy the key to clipboard.");
                            ta.setText("Encrypted Message:\n" + encryptedMessage + "\n\nThe key is: " + cipherTool.getKey()); // Setting text area content to the message and the key.

                            Object[] options3 = {"Copy key"}; // Setting the buttons that the window would use.
                            result = JOptionPane.showOptionDialog(frame, new JScrollPane(ta), "Result", JOptionPane.YES_OPTION, JOptionPane.PLAIN_MESSAGE, null, options3, null);

                            if(result == JOptionPane.YES_OPTION){ // COPY KEY
                                Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(cipherTool.getKey()),null); // Copy key to the clipboard

                            }
                        }
                        else{ // if key file is provided
                            File keyFile = new File("../" + keyFileName);
                            Scanner keyFileReader = new Scanner(keyFile);
                            while(keyFileReader.hasNextLine()){
                                String keyFileData = keyFileReader.nextLine();
                                String encryptedMessage = cipherTool.encrypt(inputToEncrypt, keyFileData);
                                ta.setText("Encrypted Message:\n" + encryptedMessage); // Setting text area content to the message.

                                Object[] options3 = {"Copy message"}; // Setting the buttons that the window would use.
                                result = JOptionPane.showOptionDialog(frame, new JScrollPane(ta), "Result", JOptionPane.YES_OPTION, JOptionPane.PLAIN_MESSAGE, null, options3, null);

                                if(result == JOptionPane.YES_NO_OPTION){
                                    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(encryptedMessage),null); // Copy message to the clipboard
                                }
                            }
                            keyFileReader.close();
                        }
                    }catch(FileNotFoundException e){
                        JOptionPane.showMessageDialog(frame, "Error");
                    }
                    
                }
                else{ // If X button is clicked
                    System.exit(0);
                }
                panel.removeAll();
            }
            else if(result == JOptionPane.NO_OPTION){ // DECRYPT
                Object[] options2 = {"File", "Input"};
                panel.removeAll();
                panel.add(new JLabel("File or text input?"));
                result = JOptionPane.showOptionDialog(frame, panel, "Input Type", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options2, null);
                
                if(result == JOptionPane.YES_OPTION){ // FILE
                    String inputFileName = JOptionPane.showInputDialog("Name of text file to decrypt (add .txt)");
                    String keyFileName = JOptionPane.showInputDialog("Name of key file to use");

                    try{
                        File inputFile = new File("../" + inputFileName);
                        Scanner inputFileReader = new Scanner(inputFile);

                        while(inputFileReader.hasNextLine()) {
                            // READING FILE CONTENTS
                            String inputFileData = inputFileReader.nextLine();
                            
                            // DECRYPTING FILE CONTENTS
                            try{
                                // Checking if inputFileName contains "(e)". If so, remove it.
                                if(inputFileName.contains("(e)")){
                                    // Removing "(e)" assuming it's in the beginning of the file name.
                                    inputFileName = inputFileName.substring(3);
                                }
                                FileWriter fileWriter = new FileWriter("../ " + inputFileName);

                                File keyFile = new File("../" + keyFileName);
                                Scanner keyFileReader = new Scanner(keyFile);
                                while(keyFileReader.hasNextLine()){
                                    String keyFileData = keyFileReader.nextLine();
                                    fileWriter.write(cipherTool.decrypt(inputFileData, keyFileData));
                                    fileWriter.flush();
                                    fileWriter.close();
                                }
                                keyFileReader.close();
                                JOptionPane.showMessageDialog(frame, "Decrypted text file successfully written.");
                            }catch(IOException e) {
                                JOptionPane.showMessageDialog(frame, "Error");
                                e.printStackTrace();
                            }
                        }
                    }catch(FileNotFoundException e) {
                        System.out.println("Error");
                        e.printStackTrace();
                    }
                }
                else if(result == JOptionPane.NO_OPTION){ // INPUT

                }
                else{ // If X button is clicked
                    System.exit(0);
                }
            }
            else{ // X button is clicked
                System.exit(0);
            }

            panel.removeAll();
        }
    }
}



/*
    TO DO:

        - Clean up unnecessary code -
        - Comment code -

        Program encryption - From input (DONE)
        Program decryption - From input (NOT DONE)
        Program decryption - From file (NOT DONE)

    

    Issues:

        Clicking cancel on file input dialog doesn't close (quit) the instance.

*/