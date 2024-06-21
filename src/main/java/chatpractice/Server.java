package chatpractice;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Server {
    public static void main(String[] args) throws IOException {
        //클라이언트에서 입력 받으면 접속한 모든 호스트에 내용 보내야함 쓰레드 따로

        ServerSocket serverSocket = new ServerSocket(10000);
        List<DataOutputStream> outputList = Collections.synchronizedList(new ArrayList<>());

        while(true) {
            Socket socket = serverSocket.accept();
            System.out.println("접속 했습니다." + socket);

            ChatThread chatThread = new ChatThread(socket, outputList);
            chatThread.start();
        }



    }
}
