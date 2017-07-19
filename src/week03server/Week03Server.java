/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package week03server;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import model.Student;

/**
 *
 * @author kj
 */
public class Week03Server {

    /**
     * @param args the command line arguments
     */
    private int port = 2000;
    private Student s = null;
    private static List<Student> data = new ArrayList<Student>();
    public static void main(String[] args) {
        readData();
        new Week03Server().go();
    }    
    public static void readData(){
        Student s;
        try{
            ObjectInputStream objectInputFile = new ObjectInputStream(new FileInputStream("Student.dat"));
            data = (List)(objectInputFile.readObject());
            Iterator student = data.iterator();
            while(student.hasNext()){
                s = (Student)student.next();
                System.out.println(s.getName()+"\t"+s.getPin()+"\t"+"\t"+s.getBalance());
            }
            objectInputFile.close();
        } catch(Exception e){
            System.out.println("Input error!");
        }        
    }    
    public void go() {
        ServerSocket serverSocket = null;
        Socket communicationSocket;    
        try {
            System.out.println("Attempting to start server...");
            serverSocket = new ServerSocket(port);
        } catch (IOException e) { 
            System.out.println("Error starting server: Could not open port "+port);
            e.printStackTrace();
            System.exit(-1);
        }
        System.out.println ("Started server on port "+port);
        while (true) {
            try {
                communicationSocket = serverSocket.accept();
                Connection con = new Connection(communicationSocket);
                con.start();
            } catch (Exception e) { 
                System.out.println("Unable to spawn child socket.");
                e.printStackTrace();
            } 
        }
    }
    class Connection {
        private Socket communicationSocket = null;
        private OutputStreamWriter out = null;
        private BufferedReader in = null;
        public Connection(Socket s) {
            communicationSocket = s;
        }        
        public void start() {
            OutputStream socketOutput = null;
            InputStream socketInput = null;
            try {
                socketOutput = communicationSocket.getOutputStream();
                out = new OutputStreamWriter(socketOutput);
                socketInput = communicationSocket.getInputStream();
                in = new BufferedReader(new InputStreamReader(socketInput));
                String input = null;
                while((input = in.readLine()) != null) {
                    System.out.println(input);
                    sendMessage("Received: "+input+"!\n");
                    if(input.equals("S")){
                        String username = in.readLine();
                        System.out.println(username);
                        sendMessage("Received: "+username+"!\n");
                        String password = in.readLine();
                        System.out.println(password);
                        sendMessage("Received: "+password+"!\n");
                        for(int i=0; i<data.size(); i++){
                            if(username.equals(((Student)(data.get(i))).getName())){
                                s = (Student)(data.get(i));
                                break;
                            }
                        }
                        if(s!=null){
                            if(s.Login(Integer.parseInt(password))){
                                sendMessage("Welcome!\n");
                            } else{
                                sendMessage("Login Failed!\n");
                            }
                        } else{
                            sendMessage("Login Failed!\n");
                        }
                    } else if(input.equals("C")){
                        String a = in.readLine();
                        System.out.println(a);
                        sendMessage("Received: "+a+"!\n");
                        char b = in.readLine().charAt(0);
                        System.out.println(b);
                        sendMessage("Received: "+b+"!\n");
                        int c = Integer.parseInt(in.readLine());
                        System.out.println(c);
                        sendMessage("Received: "+c+"!\n");
                        double d = Double.parseDouble(in.readLine());
                        System.out.println(d);
                        sendMessage("Received: "+d+"!\n");
                        Student newStudent = new Student(a,b,c,d);
                        data.add(newStudent);
                    } else if(input.equals("D")){
                        double d = Double.parseDouble(in.readLine());
                        System.out.println(d);
                        sendMessage("Received: "+d+"!\n");
                        s.deposit(d);
                        sendMessage("Transaction Successful!\n");
                        sendMessage("New balance is RM"+s.getBalance()+"!\n");
                    } else if(input.equals("W")){
                        double d = Double.parseDouble(in.readLine());
                        System.out.println(d);
                        sendMessage("Received: "+d+"!\n");
                        s.withdraw(d);
                        sendMessage("Transaction Successful!\n");
                        sendMessage("New balance is RM"+s.getBalance()+"!\n");
                    }
                }
            } catch(Exception e) { 
            } finally {
                try {
                    if(in != null) in.close();
                    if(out != null) out.close();
                    communicationSocket.close();
                    writeData();
                    System.out.println("DONE!");            
                } catch(Exception e) { 
                    e.printStackTrace(); 
                }
            }
        }
        public void sendMessage(String message) {
            try {
                out.write(message);
                out.flush();
            } catch(Exception e) { 
                e.printStackTrace(); 
            }
        }
    } 
    public static void writeData(){
        try{
            ObjectOutputStream objectOutputFile = new ObjectOutputStream(new FileOutputStream("Student.dat"));
            objectOutputFile.writeObject(data);
            objectOutputFile.close();
        } catch(Exception e){
            System.out.println("Output error!");
        }        
    }
    
}
