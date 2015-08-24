/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lidar_data_tr;

import java.io.*;
import java.net.*;

/**
 *
 * @author Carlos Javier
 */

class Client {

    public static void main(String args[]) {
        Boolean print_msgs = false; // Print packets received and sent
        try {
            int port = 2370; //2369 or 2370
            int out_port = 2368;

            // Create a socket to listen on the port.
            DatagramSocket dsocket = new DatagramSocket(port);
            DatagramSocket out_dsocket = new DatagramSocket();
            //InetAddress rIPAddress = InetAddress.getByName("192.168.1.202"); // receiving from 
            InetAddress IPAddress = InetAddress.getByName("255.255.255.255"); // sending to

      // Create a buffer to read datagrams into. If a
            // packet is larger than this buffer, the
            // excess will simply be discarded!
            byte[] buffer = new byte[1206]; // receiver 
            byte[] Sbuffer = buffer; // 

      // Create a packet to receive data into the buffer
            //DatagramPacket packet = new DatagramPacket(buffer, buffer.length, rIPAddress, port);
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
      // Create a packet to send data into the buffer
            //DatagramPacket sendPacket = new DatagramPacket(buffer, buffer.length, IPAddress, out_port);
            DatagramPacket sendPacket = new DatagramPacket(Sbuffer, Sbuffer.length, IPAddress, out_port);

            // Now loop forever, waiting to receive packets and printing them.
            while (true) {
                // Wait to receive a datagram
                dsocket.receive(packet);
                out_dsocket.send(sendPacket); //

                // Convert the contents to a string, and display them
                if (print_msgs) {
                    String msg = new String(buffer, 0, packet.getLength());
                    String send_msg = new String(Sbuffer, 0, sendPacket.getLength()); //
                    System.out.println("Receiving from: " + packet.getAddress().getHostName() + " Buffer length "
                            + buffer.length + " Port " + packet.getPort() + ": "
                            + msg);
                    System.out.println("Sending to: " + sendPacket.getAddress().getHostName() + " Buffer length "
                            + Sbuffer.length + " Port " + sendPacket.getPort() + ": "
                            + send_msg); //
                }

                // Reset the length of the packet before reusing it.
                packet.setLength(buffer.length);
                sendPacket.setLength(Sbuffer.length); //
            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}



