package com.example.sibhali.facedet;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by mithileshhinge on 14/06/17.
 */
public class AudioClient extends Thread {

    private static int handshakePort = 6661, audioUdpPort = 6666;
    private byte[] buffer;
    private DatagramSocket socket;
    AudioRecord recorder;
    private int sampleRate = 44100;
    private int channelConfig = AudioFormat.CHANNEL_IN_MONO;
    private int audioFormat = AudioFormat.ENCODING_PCM_16BIT;
    private int minBufSize;
    private String servername;

    public boolean status = true;

    public AudioClient(){
        servername = MainActivity.jIP.getText().toString();
    }

    @Override
    public void run() {
        try {
            Socket handshakeSocket = new Socket(servername, handshakePort);
            OutputStream out = handshakeSocket.getOutputStream();
            out.write(1);
            out.flush();
            System.out.println("P=1 PATHAVLA");
            InputStream in = handshakeSocket.getInputStream();
            in.read();
            System.out.println("Tyani p pathavla");
            handshakeSocket.close();
            System.out.println(".........HANDSHAKE SOCKET BANDA.....");

            System.out.println("......STREAMING START JHALI.....");

            socket = new DatagramSocket();
            System.out.println("DataGramSocket BANAVLA!!!!!");

            minBufSize = 4096;
            buffer = new byte[minBufSize];

            DatagramPacket packet;

            final InetAddress destination = InetAddress.getByName(servername);

            recorder = new AudioRecord(MediaRecorder.AudioSource.MIC, sampleRate, channelConfig, audioFormat, minBufSize);
            recorder.startRecording();
            System.out.println("######RECORDING START JHALI");

            while (status){
                minBufSize = recorder.read(buffer, 0, buffer.length);

                packet = new DatagramPacket(buffer, buffer.length, destination, audioUdpPort);

                socket.send(packet);
                System.out.println("SENDING DATA");
            }

            recorder.release();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
