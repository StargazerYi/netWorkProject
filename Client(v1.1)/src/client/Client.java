import java.io.*;
import java.net.*;
import java.util.Map;
import java.util.HashMap;
import net.sf.json.*;

import jsonAnalyzer.JsonAnalyzer;
import ui.*;
import data.Data;

public class Client {

    public static Socket clientSocket;
    public static DatagramSocket udpSocket;

    private static byte[] sendData = new byte[1024];

    public static void main(String[] args) throws InterruptedException {
        try {
            clientSocket = new Socket(Data.serverIP, 5555);
            udpSocket = new DatagramSocket();

            new Thread(new TCPReceiveThread(clientSocket)).start();
            new Thread(new UDPReceiveThread(udpSocket)).start();

            BufferedWriter outToServer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream(), "UTF-8"));
            BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

            MainWindow mainWindow = new MainWindow();
            Data.pointer = mainWindow;

            while (true) {
                if (!Data.tcpSend.empty()) {
                    outToServer.write(Data.tcpSend.pop().toString()+"\n");
                    outToServer.flush();
                }
                
                if (!Data.udpSend.empty()) {
                    JSONObject json = Data.udpSend.pop();
                    String destination = Data.udpGuide.pop();
                    Map<String, String> map = JsonAnalyzer.parse(json.toString());

                    if (destination.equals("Server")) {
                        UDPsend(json.toString(), Data.serverIP, 5556);
                    } else {
                        String id = Data.onlineName.get(destination);
                        String ip = Data.onlineIP.get(id);
                        String port = Data.onlinePort.get(ip);

                        UDPsend(json.toString(), ip, Integer.parseInt(port));
                    }
                }
            }
        } catch(IOException e) {
            System.out.println("Connection closed");
        } finally {
            try {
                clientSocket.close();
            } catch(IOException e) {
                System.out.println("Connection closed");
            }
        }
    }

    public static void UDPsend(String data, String ip, int port) throws IOException {
        sendData = data.getBytes();
        InetAddress address = InetAddress.getByName(ip);
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, address, port);
        udpSocket.send(sendPacket);
    }
}