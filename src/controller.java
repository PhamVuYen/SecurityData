

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class controller implements ActionListener {

    private viewForm view;
    public controller(viewForm view){
        this.view = view;
        view.getBtnBrowse().addActionListener(this);
        view.getBtnSendFile().addActionListener(this);

    }

    protected static byte[] readByteFile(File file){
        byte data[] = null;
        try{
            FileInputStream fis = new FileInputStream(file);

            int c,i=0;
            data = new byte[(int)file.length()];
            while ((c = fis.read()) != -1)
                data[i++] = (byte)c;
            fis.close();
        }
        catch(IOException e){
            JOptionPane.showMessageDialog(null,
                    file.getName() + " not found or cannot be read.",
                    "Error",JOptionPane.ERROR_MESSAGE);
            return null;
        }
        return data;
    }

    protected boolean writeByteFile(String fileName, byte[] data){
        File file = new File(fileName);
        if (!file.canWrite()){
            try {file.createNewFile();}
            catch(IOException e){
                JOptionPane.showMessageDialog(null,
                        "Unable to create file " + file.getName() + " for writing.",
                        "Error",JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }

        try{
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(data);
            fos.close();
            return true;
        }
        catch(IOException e){
            JOptionPane.showMessageDialog(null,
                    "Unable to write to file " + file.getName(),
                    "Error",JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton btn = (JButton)e.getSource();
        if (e.getActionCommand().equals(view.getBtnBrowse().getText())) {
            view.chooseFile();
        }

        if(e.getActionCommand().equals(view.getBtnSendFile().getText())){
            String keyIn = view.getTextFieldKey().getText().trim();
            String sourceFilePath = view.getTextFieldFilePath().getText();
            try {
                DES des  = new DES();
                ServerSocket server;
                server = new ServerSocket(8080);
                System.out.print("Dang cho ket noi");
                Socket socket=server.accept();
                DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                Scanner scanner = new Scanner(System.in);

                while(true){
                    String key = DES.utfToBin(keyIn);
                    //Read
                    String fileName = dataInputStream.readUTF();
                    String str = dataInputStream.readUTF();
                    String fileNameDe = des.decrypt(key, DES.hexToBin(fileName));
                    String fileNameDeAf = DES.binToUTF(fileNameDe);
                    String dataDe = des.decrypt(key, DES.hexToBin(str));
                    String dataDeAf = DES.binToUTF(dataDe);
                    byte[] data = dataDeAf.getBytes();
                    if(writeByteFile("D:\\Server/" + fileNameDeAf, data)) {
                        System.out.println("Client sent success");
                    }
                    view.getTextAreaResult().setText("Client sent file: " + fileNameDeAf);
                    // Sending data
                    File file = new File(sourceFilePath);
                    String fileNameSen = file.getName();
                    view.getTextAreaResult().setText("Server sent success: " + fileNameSen);
                    byte[] dataSen = readByteFile(file);
                    String dataString = new String(dataSen);

                    //Encrypt file name, data in file
                    String fileNameEn = des.encrypt(key, DES.utfToBin(fileNameSen));
                    String dataEn = des.encrypt(key, DES.utfToBin(dataString));

                    dataOutputStream.writeUTF(DES.binToHex(fileNameEn));
                    dataOutputStream.writeUTF(DES.binToHex(dataEn));
                    dataOutputStream.flush();

                    String end = scanner.nextLine();
                    if(end.equals("stop")){
                        break;
                    }
                }
                dataOutputStream.close();
                dataInputStream.close();
                socket.close();


            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        viewForm viewForm = new viewForm();
    controller controller = new controller(viewForm);

    }
}
