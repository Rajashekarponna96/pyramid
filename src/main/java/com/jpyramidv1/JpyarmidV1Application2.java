//package com.jpyramidv1;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.OutputStream;
//import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
//import java.net.ProtocolException;
//import java.net.URL;
//import java.util.HashMap;
//import java.util.Map;
//
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//
//import com.pyramidacceptors.ptalk.api.PyramidAcceptor;
//import com.pyramidacceptors.ptalk.api.PyramidDeviceException;
//import com.pyramidacceptors.ptalk.api.PyramidPort;
//import com.pyramidacceptors.ptalk.api.RS232Configuration;
//import com.pyramidacceptors.ptalk.api.event.CreditEvent;
//import com.pyramidacceptors.ptalk.api.event.Events;
//import com.pyramidacceptors.ptalk.api.event.PTalkEvent;
//import com.pyramidacceptors.ptalk.api.event.PTalkEventListener;
//import com.pyramidacceptors.ptalk.api.event.SerialDataEvent;
//
//@SpringBootApplication
//public class JpyarmidV1Application2 implements PTalkEventListener {
//
//	  public static PyramidAcceptor mAcceptor;
//	  Map<String,Integer> billDenominationMap;
//
//	  static int total = 0;
//	public static void main(String[] args) {
//		SpringApplication.run(JpyarmidV1Application2.class, args);
//		   System.out.println("Hello World123!");
//		   JpyarmidV1Application2 main = new JpyarmidV1Application2();
//		      for(int i=0;i<PyramidPort.getPortList().length;i++){
//		       System.out.println(PyramidPort.getPortList()[i].toString());
//		        main.connect(PyramidPort.getPortList()[i].toString());
//		      }
//		     
//		      System.out.println("xxx");
//		    }
//
//		  public void connect(String portName) {
//		    // Populate available OS serial ports
//		    String[] portList = PyramidPort.getPortList();
//		    System.out.println("Hello Wo123!");
//
//		    System.out.println(portList.length);
//
//		    billDenominationMap=new HashMap<String,Integer>();
//		    billDenominationMap.put("Bill1", 1);
//		    billDenominationMap.put("Bill2", 2);
//		    billDenominationMap.put("Bill3", 5);
//		    billDenominationMap.put("Bill4", 10);
//		    billDenominationMap.put("Bill5", 20);
//		    billDenominationMap.put("Bill6", 50);
//		    billDenominationMap.put("Bill7", 100);
//
//		    // Init
//		    try {
//		      //System.out.println("Before Test");
//		      //PyramidAcceptor.asTest();
//		      //System.out.println("After Test");
//		      //PyramidAcceptor.valueOfRS232();
//		      System.out.println("After valueOfRS232");
//		      // COM-6
//		      mAcceptor = PyramidAcceptor.valueOfRS232(portName);
//		    } catch (PyramidDeviceException e) {
//		      System.out.println("Failed to connect to acceptor");
//		      e.printStackTrace();
//		    }
//
//
//		    //System.out.println(mAcceptor.getAcceptorModel().toString());
//		    //System.out.println(mAcceptor.getFirmwareRevision());
//		    //mAcceptor.requestSerialNumber();
//		    //System.out.println(mAcceptor.getSerialNumber());
//
//		    mAcceptor.connect();
//		    if (!mAcceptor.isConnected()) {
//		      System.out.println("Unable to connect to device");
//		    }
//		    else {
//		      mAcceptor.addChangeListener(this);
//		    }
//		  }
//
//		  public void disconnect() {
//		    mAcceptor.removeChangeListener(this);
//		    mAcceptor.disconnect();
//		    mAcceptor = null;
//		  }
//
//		  public void configuration() {
//		    RS232Configuration.INSTANCE.setEventMask(RS232Configuration.DEFAULT_EVENT_MASK | Events.SerialData.getIntId());
//
//		    RS232Configuration.INSTANCE.setEventMask(RS232Configuration.DEFAULT_EVENT_MASK & ~(Events.SerialData.getIntId()));
//		  }
//
//		  public void others() {
//		    mAcceptor.pause();
//		    mAcceptor.unpause();
//
//		    mAcceptor.requestReset();
//		  }
//
//		  @Override
//		  public void changeEventReceived(PTalkEvent evt) {
//		    //System.out.println("evt.getId(): " + evt.getId());
//
//		    if (evt.getId() == Events.SerialData) {
//		      System.out.println(evt.getPacketString());
//		      SerialDataEvent e = (SerialDataEvent) evt;
//		      System.out.println(e.message);
//		      System.out.println(e.messageType);
//		    } else if (evt.getId() == Events.CommunicationFailure) {
//		     // ConnectionFailureEvent c = (ConnectionFailureEvent) evt;
//
//		     // System.out.println(String.format("Acceptor is no longer responding! Count: %d", c.getFailureCount()));
//		    } else if (evt.getId() == Events.Credit) {
//		      String bill = ((CreditEvent) evt).getBillName().name();
//		      System.out.println( "Credit received! " + bill);
//		      int value = billDenominationMap.get(bill);
//
//		      try {
//		        doPOST(value);
//		      } catch (IOException e) {
//		        e.printStackTrace();
//		      }
//
//		      total += value;
//		      System.out.println("Total received: $" + total);
//		    } else {
//		      //
//		    }
//		  }
//
//		    // POST
//		    private static void doPOST(int bill) throws MalformedURLException, IOException, ProtocolException {
//
//		    //  URL url = new URL("http://localhost:9002/api/v1/insertbills/insert");
//		      URL url = new URL("http://localhost:9002/insertbill/");
//		      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//		      conn.setRequestMethod("POST");
//		      conn.setDoOutput(true);
//		      conn.setRequestProperty("Content-Type", "application/json");
//		  
//		      String input = "{\"amount\":\"$" + bill + "\"}";
//		      OutputStream os = conn.getOutputStream();
//		      os.write(input.getBytes());
//		      os.flush();
//		  
//		      BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
//		  
//		      String output;
//		      System.out.println("Output from Server .... \n");
//		      while ((output = br.readLine()) != null) {
//		        System.out.println(output);
//		      }
//		  
//		      conn.disconnect();
//		    }
//}