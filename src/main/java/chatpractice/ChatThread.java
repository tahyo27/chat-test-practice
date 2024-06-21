package chatpractice;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class ChatThread extends Thread{

    private Socket socket;
    List<DataOutputStream> outputList;
    DataOutputStream dataOutputStream;
    DataInputStream dataInputStream;

    public ChatThread(Socket socket, List<DataOutputStream> outputList) {
        this.socket = socket;
        this.outputList = outputList;
        //1. 객체 얻기
        //2. scoket에 쓰기 위한 객체 얻는다 (현재 클라이언트에게 쓰기 위한 객체)
        try {
            dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            outputList.add(dataOutputStream);
        } catch (IOException e) {
            System.out.println("ChatThread Exception" + e.getMessage());
        }
    }

    public void run() {
        System.out.println("ChatThread run");
        try {
            int data;
            byte[] bytes = new byte[512];
            while ((data = dataInputStream.read(bytes)) != -1) {
                // 데이터 처리 로직
                String msg = new String(bytes, 0, data);
                System.out.println("msg : " + msg + " socket" + socket.getInetAddress());
                for (DataOutputStream out : outputList) { // 접속한 사용자들에게 받은메세지 보냄
                    out.write(bytes, 0, data);
                    out.flush();
                }
            }
        } catch (IOException e) {
            System.out.println("IOE exception " + e.getMessage());
        }  finally {
            try {
                outputList.remove(dataOutputStream);
            } catch (Exception e) {
                System.out.println("Exception" + e.getMessage());
            }
        }
        /*
        while(true) {
            try {
                String msg = dataInputStream.readUTF();
                System.out.println();
                for (DataOutputStream out : outputList) {
                    out.writeUTF(msg);
                    out.flush();
                }
            } catch (IOException e) {
                System.out.println("IOE exception " + e.getMessage());
                break;
            } finally {
                try {
                    outputList.remove(dataOutputStream);
                } catch (Exception e) {
                    System.out.println("Exception" + e.getMessage());
                }
            }
        }

         */

        //3. 클라이언트에 보낸 메시지 읽는다.
        //4. 접속된 모든 클라이언트에게 메세지 보냄 (클라이언트 모두에게 쓸 수 있는 객체 필요)
    }
}
