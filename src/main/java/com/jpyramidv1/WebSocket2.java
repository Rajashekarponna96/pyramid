package com.jpyramidv1;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class WebSocket2 {
  public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
    ServerSocket server = null;
    try {
      server = new ServerSocket(8080);
    } catch (IOException e) {
      e.printStackTrace();
    }
    try {
      System.out.println("Server has started on 127.0.0.1:8080.\r\nWaiting for a connection...");
      Socket client = server.accept();
      System.out.println("A client connected.");

      InputStream in = client.getInputStream();
      OutputStream out = client.getOutputStream();
      Scanner s = new Scanner(in, "UTF-8");

    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }
}
