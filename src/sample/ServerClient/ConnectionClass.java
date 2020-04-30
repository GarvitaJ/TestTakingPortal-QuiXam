package sample.ServerClient;

import java.sql.*;

public class ConnectionClass {
    //Connecting to SQL database
    public Connection connection;
    public Connection getconnection(){
        String dbName= "TTP"; //Database Name
        String user= "root"; //Username of Database
        String password="garvita";//Password
        //Tries connecting to database if does not work gives a error
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+dbName+"?useSSL=false",user,password);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return connection;
    }
}