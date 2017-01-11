package com.gerli.gerli.parser;

import android.os.Handler;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.concurrent.Semaphore;


/**
 * The implementation of remote parser
 * It would transmit the sentence to the server
 * After parsing algorithm, the result would be sent back to the phone
 *
 * @author SunnerLi
 * @revise 10/28/16.
 */
public class RemoteParser {
    // Connection information
    String SERVER_IP = "192.168.43.90";
    int SERVER_PORT = 2000;

    // Connection Variable
    InetSocketAddress serverAddress;
    DatagramSocket datagramSocket;
    DatagramPacket datagramPacket;

    // String type constants
    public static final String sentence = "sentence";
    public static final String control = "control";
    static String stringType = null;

    public JSONObject json;
    byte[] buf = new byte[65536];
    String parseString;

    // Parsing result
    public Record recordResult = null;
    public int sentimentResult = -1;

    public RemoteParser(){
        // SERVER_IP = MainActivity.addr.getText().toString();
        // SERVER_IP = "192.168.0.102";
    }

    // Flag to control if precessing in the remote side
    Semaphore semaphore = new Semaphore(1);

    // The runnable that deal with the sending process
    Runnable sendRunnable = new Runnable() {
        @Override
        public void run() {
            // Set as begin state
            try {
                semaphore.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Set sentence into json format
            try {
                json = new JSONObject();
                json.put("sentence", parseString);
                json.put("type", stringType);
                json.put("address", getLocalIpAddress());
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

    // The runnable that deal with the receiving process
    Runnable recvRunnable = new Runnable() {
        @Override
        public void run() {
            DatagramSocket ds = null;
            try {
                // Receive the parse result
                ds = new DatagramSocket(SERVER_PORT + 1);
                byte[] _buf = new byte[65536];
                DatagramPacket datagramPacket = new DatagramPacket(_buf, _buf.length);
                ds.receive(datagramPacket);
                String string = new String(_buf);

                // Reformat the result to the record object
                json = new JSONObject(string);
                sentimentResult = json.getInt("sentiment");
                Log.v("--> Parser Log", json.get("sentence").toString());
                recordResult = new Record(new JSONObject(json.get("record").toString()));
                sentimentResult = json.getInt("sentiment");

                recordResult.dump();


            } catch (SocketException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("--> RemoteParser", "1");
            } finally {
                if (ds != null)
                    ds.close();

                // Set as end state
                Log.d("--> RemoteParser", "2");
                semaphore.release();
            }
            Log.d("--> RemoteParser", "3");
        }
    };

    /**
     * The function wrapper of remote parser
     *
     * @param string the string want to parse
     * @param type the type of the string (command or sentence)
     * @return if parse success, always true default
     */
    public boolean work(String string, String type) {
        parseString = string;
        stringType = type;
        new Thread(sendRunnable).run();
        new Thread(recvRunnable).run();
        return true;
    }

    /**
     * Get the wlan IP address
     *
     * @return the IP address string
     */
    public String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface
                .getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf
                    .getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    System.out.println("ip1--:" + inetAddress);
                    System.out.println("ip2--:" + inetAddress.getHostAddress());

                    // for getting IPV4 format
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        String ip = inetAddress.getHostAddress().toString();
                        System.out.println("ip---::" + ip);
                        return ip;
                    }
                }
            }
        } catch (Exception ex) {
            Log.e("IP Address", ex.toString());
        }
        return null;
    }

    /**
     * Return the value of semaphore
     * You shouldn't use this function directly
     *
     * @return the value of the semaphore
     */
    public int getSemaphore(){
        return semaphore.availablePermits();
    }
}
