import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class FileTransferServer {

    protected byte[] readByteFile(File file){
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

    /**
     *	Writes byte contents to a file
     *	@param file	The file object to write to
     *	@param data	The data (array of bytes) to write
     *	@return Whether the process is successful
     */
    protected static boolean writeByteFile(String fileName, byte[] data){
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

    public static void main(String[] args) throws IOException {

        DES des = new DES();
        ServerSocket server;

        server = new ServerSocket(8080);
        System.out.print("Dang cho ket noi");
        Socket socket=server.accept();

        DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        Scanner scanner = new Scanner(System.in);
        while (true){
            //Read
            String fileName = dataInputStream.readUTF();
            String str = dataInputStream.readUTF();
            String fileNameDe = des.decrypt("1234567891234567", DES.hexToBin(fileName));
            String fileNameDeAf = DES.binToUTF(fileNameDe);
            String dataDe = des.decrypt("1234567891234567", DES.hexToBin(str));
            String dataDeAf = DES.binToUTF(dataDe);
            byte[] data = dataDeAf.getBytes();

            if(writeByteFile("D:\\Server/" + fileNameDeAf, data)){
                System.out.println("Done");
            }
            if(str.equals("stop")){
                break;
            }
            System.out.println(fileName);
            System.out.println("Number 1 send file: " +str);

            // data translate
            String str2 = scanner.nextLine();
            dataOutputStream.writeUTF(str2);
            dataOutputStream.flush();
        }
        dataInputStream.close();
        dataOutputStream.close();
        socket.close();
    }
}
