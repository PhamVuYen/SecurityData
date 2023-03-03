

import javax.swing.*;

public class viewForm extends JFrame{
   // private static final long serialVersionUID = 1L;

    private JLabel labelKey;
    private JTextField textFieldKey;
    private JLabel labelPort;
    private JTextField textFieldPort;
    private JButton btnBrowse;
    private JTextField textFieldFilePath;
    private JButton btnSendFile;
    private JTextArea textAreaResult;
    private JButton btnExit;

    public viewForm() {
        setTitle("Truyền file mã hóa DES");
        labelKey = new JLabel("Key:");
        textFieldKey = new JTextField();
       // labelPort = new JLabel("DES");
        //textFieldPort = new JTextField();
        labelKey.setBounds(20, 20, 50, 25);
        textFieldKey.setBounds(55, 20, 120, 25);
        //labelPort.setBounds(190, 20, 50, 25);
        //textFieldPort.setBounds(220, 20, 50, 25);
        textAreaResult = new JTextArea();
        textAreaResult.setBounds(20, 50, 500, 150);
        btnExit = new JButton("Exit");
        btnExit.setBounds(240, 245, 80, 25);

        textFieldFilePath = new JTextField();
        textFieldFilePath.setBounds(20, 210, 420, 25);
        btnBrowse = new JButton("Browse");
        btnBrowse.setBounds(440, 210, 80, 25);
        btnSendFile = new JButton("Send File");
        btnSendFile.setBounds(20, 245, 100, 25);

        add(labelKey);
        add(textFieldKey);
        //add(labelPort);
       // add(textFieldPort);
        add(textAreaResult);
        add(textFieldFilePath);
        add(btnBrowse);
        add(btnSendFile);
        add(btnExit);

        setLayout(null);
        setSize(600, 350);
        setVisible(true);
        // thoát chương trình khi tắt window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void chooseFile() {
        final JFileChooser fc = new JFileChooser();
        fc.showOpenDialog(this);
        try {
            if (fc.getSelectedFile() != null) {
                textFieldFilePath.setText(fc.getSelectedFile().getPath());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public JLabel getLabelKey() {
        return labelKey;
    }

    public void setLabelKey(JLabel labelKey) {
        this.labelKey = labelKey;
    }

    public JTextField getTextFieldKey() {
        return textFieldKey;
    }

    public void setTextFieldKey(JTextField textFieldKey) {
        this.textFieldKey = textFieldKey;
    }

    public JLabel getLabelPort() {
        return labelPort;
    }

    public void setLabelPort(JLabel labelPort) {
        this.labelPort = labelPort;
    }

    public JTextField getTextFieldPort() {
        return textFieldPort;
    }

    public void setTextFieldPort(JTextField textFieldPort) {
        this.textFieldPort = textFieldPort;
    }

    public JButton getBtnBrowse() {
        return btnBrowse;
    }

    public void setBtnBrowse(JButton btnBrowse) {
        this.btnBrowse = btnBrowse;
    }

    public JTextField getTextFieldFilePath() {
        return textFieldFilePath;
    }

    public void setTextFieldFilePath(JTextField textFieldFilePath) {
        this.textFieldFilePath = textFieldFilePath;
    }

    public JButton getBtnSendFile() {
        return btnSendFile;
    }

    public void setBtnSendFile(JButton btnSendFile) {
        this.btnSendFile = btnSendFile;
    }

    public JTextArea getTextAreaResult() {
        return textAreaResult;
    }

    public JButton getBtnExit() {
        return btnExit;
    }

    public void setBtnExit(JButton btnExit) {
        this.btnExit = btnExit;
    }

    public void setTextAreaResult(JTextArea textAreaResult) {
        this.textAreaResult = textAreaResult;
    }
}
