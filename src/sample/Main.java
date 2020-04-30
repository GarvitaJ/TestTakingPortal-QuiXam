package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.ServerClient.client;

public class Main extends Application {

    public static client user= new client("127.0.0.1", 9009);

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("LoginSignup/Login.fxml"));
        primaryStage.setTitle("TTP");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
    public void stop() throws Exception{
        Main.user.sendString("Exit");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
