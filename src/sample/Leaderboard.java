package sample;


import sample.Misc.Score;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import sample.ServerClient.Session_Id;

import java.util.ArrayList;

public class Leaderboard {

    @FXML
    private JFXListView<?> scorelist;
    @FXML
    private JFXButton back;

    public void initialize(){


        Main.user.sendString("GetScores");
        Main.user.sendString(Session_Id.getQuizname());
        ArrayList<Score> s=(ArrayList<Score>)Main.user.recieveObject();
        ArrayList al=new ArrayList();
        if(s.size()!=0)
        {   int i=1;
            for(Score test:s){
                Label l1=new Label(test.getUSN());
                l1.setStyle("-fx-text-fill: #913131;");
                l1.setFont(Font.font("Baskerville Old Face", FontWeight.BOLD,20));
                Label l2=new Label(""+test.getScore());
                l2.setStyle("-fx-text-fill: #913131;");
                l2.setFont(Font.font("Baskerville Old Face", FontWeight.BOLD,20));
                HBox hb=new HBox(100);
                hb.setPadding(new Insets(10,20,10,20));
                if(i%2==0) hb.setBackground(new Background(new BackgroundFill(Color.MISTYROSE, CornerRadii.EMPTY,Insets.EMPTY)));
                else hb.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY,CornerRadii.EMPTY,Insets.EMPTY)));
                i++;
                hb.getChildren().addAll(l1,l2);
                al.add(hb);
            }
        }
        scorelist.setItems(FXCollections.observableArrayList(al));
    }
    SceneChange change=new SceneChange();
    public void back(ActionEvent click)
    {
        String type=Session_Id.getUsertype();
        if(type.equals("Student")) {
            change.changeScene("Student/SInsideClass.fxml",click,"Quiz");
        }
        else {
            change.changeScene("Teacher/TInsideClass.fxml",click,"Quiz");
        }
    }

    public void Logout(ActionEvent actionEvent) {
        change.changeScene("LoginSignup/Login.fxml", actionEvent, "Login");

    }
}