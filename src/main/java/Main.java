import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws Exception {
        BooleanSearchEngine engine = new BooleanSearchEngine(new File("pdfs"));
        System.out.println(engine.search("бизнес"));
        final int PORT = 8989;
        try (ServerSocket serverSocket = new ServerSocket(PORT);) {
            System.out.println("Запуск сервера");
            BooleanSearchEngine searchEngine = new BooleanSearchEngine(new File("pdfs"));
            Gson gson = new GsonBuilder().create();

            while (true) {
                try (
                        Socket socket = serverSocket.accept();
                        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        PrintWriter out = new PrintWriter(socket.getOutputStream());
                ) {
                    String word = in.readLine();

                    var json = gson.toJson(searchEngine.search(word));
                    out.println(json);
                }
            }
        } catch (IOException e) {
            System.out.println("Не могу стартовать сервер");
            e.printStackTrace();

        }
    }
}