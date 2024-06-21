package chatpractice2;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class TextClient {

    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 9999);
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
             Scanner scanner = new Scanner(System.in)) {

            while (true) {
                System.out.print("Enter message: ");
                String message = scanner.nextLine();
                writer.write(message);
                writer.newLine();
                writer.flush(); // 데이터를 즉시 전송
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
