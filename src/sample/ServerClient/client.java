package sample.ServerClient;

import java.io.*;
import java.net.Socket;

public class client {
    private Socket socket=null;
    private ObjectOutputStream ObjectOutput= null;
    private ObjectInputStream ObjectInput= null;
    private DataOutputStream dataOutput= null;
    private DataInputStream dataInput=null;
    public client(String ip, int port){
        try{
            socket = new Socket(ip, port);
            System.out.println("Connected");

            ObjectOutput= new ObjectOutputStream(socket.getOutputStream());
            ObjectInput= new ObjectInputStream(socket.getInputStream());
            dataInput=new DataInputStream(socket.getInputStream());
            dataOutput= new DataOutputStream(socket.getOutputStream());
        }
        catch (IOException d){

            System.out.println(d);
        }
    }

    public void sendString(String toSend){
        try{
            dataOutput.writeUTF(toSend);
        }
        catch(IOException e){
            System.out.println(e);
        }
    }

    public String receiveString(){
        try{
            return dataInput.readUTF();
        }
        catch(IOException e){
            System.out.println(e);
        }
        return null;
    }

    public Boolean recieveBoolean(){
        try{
            return dataInput.readBoolean();
        }
        catch(IOException e){
            System.out.println(e);
        }
        return false;
    }

    public void sendInt(int a){
        try{
            dataOutput.writeInt(a);
        }
        catch(IOException e){
            System.out.println(e);
        }
    }
    public int receiveInt(){
        try{
            return dataInput.readInt();
        }
        catch(IOException e){
            System.out.println(e);
        }
        return 0;
    }

    public Object recieveObject(){
        try{
            return ObjectInput.readObject();
        }
        catch(IOException |ClassNotFoundException e){
            System.out.println(e);
            return null;
        }
    }
    public void sendObject(Object ob){
        try{
            ObjectOutput.writeObject(ob);
        }
        catch(IOException e){
            System.out.println(e);
        }
    }

}

