package com.jpyramidv1;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.RemoteEndpoint.Basic;
import javax.websocket.server.ServerEndpoint;

import org.json.JSONObject;

@ServerEndpoint("/websocket")
public class WebSocket {

	private static Set<Session> peers = Collections.synchronizedSet(new HashSet<Session>());
	public static Basic remote;

	@OnMessage
	public void onMessage(String message, Session session) throws IOException, InterruptedException {

		// Print the client message for testing purposes
		System.out.println("Received: " + message);

		Basic remote = session.getBasicRemote();

		// Pipe to the client
		remote.sendText("{\"name\": \"scanner1\", \"data\": \"00000000000000123\"}");

		// Send 3 messages to the client every 5 seconds
		int sentMessages = 0;
		while (sentMessages < 3) {
			Thread.sleep(5000);
			remote.sendText("This is an intermediate server message. Count: " + sentMessages);
			sentMessages++;
		}

		// Send a final message to the client
		remote.sendText("This is the last but one server message");

		HashMap<String, String> map1 = new HashMap<String, String>();
		map1.put("name", "scanner1");
		map1.put("data", "00000000000000123");
		String msg = new JSONObject(map1).toString();
		System.out.println(msg);
		remote.sendText(msg);
	}

	@OnOpen
	public void onOpen(Session session) throws Exception {
		// Add session to the connected sessions set
		peers.add(session);
		System.out.println("Open");
		this.remote = session.getBasicRemote();
		// new GUI();
	}

	@OnClose
	public void onClose(Session session) {
		// Remove session from the connected sessions set
		peers.remove(session);
		System.out.println("close");
	}
}
