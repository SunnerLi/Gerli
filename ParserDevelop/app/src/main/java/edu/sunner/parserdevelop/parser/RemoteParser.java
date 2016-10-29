package edu.sunner.parserdevelop.parser;

import android.os.Handler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

/**
 * Created by sunner on 10/28/16.
 */
public class RemoteParser {
    // Connection information
    String SERVER_IP = "192.168.0.100";
    int SERVER_PORT = 2000;

    // Connection Variable
    InetSocketAddress serverAddress;
    DatagramSocket datagramSocket;
    DatagramPacket datagramPacket;

    // String type constants
    public static final String sentence = "sentence";
    public static final String control = "control";
    static String stringType = null;

    JSONObject json;
    byte[] buf = new byte[65536];
    String parseString;

    Runnable sendRunnable = new Runnable() {
        @Override
        public void run() {
            // Set sentence into json format
            try {
                json = new JSONObject();
                json.put("sentence", parseString);
                json.put("type", stringType);
                buf = json.toString().getBytes("utf-8");

                serverAddress = new InetSocketAddress(SERVER_IP, SERVER_PORT);
                datagramPacket = new DatagramPacket(buf, buf.length, serverAddress);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (SocketException e) {
                e.printStackTrace();
            }

            // Send to server
            try {

                datagramSocket = new DatagramSocket();
                datagramSocket.send(datagramPacket);
            } catch (SocketException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    };

    public boolean work(String string, String type) {
        parseString = string;
        stringType = type;
        new Thread(sendRunnable).run();
        return true;
    }
}
