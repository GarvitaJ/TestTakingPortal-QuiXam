package sample.Student;

import sample.Misc.ClassInfoStudent;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import sample.Main;
import sample.SceneChange;
import sample.ServerClient.Session_Id;

import java.util.ArrayList;

public class StudentDashboard
{
    @FXML
    private JFXListView<?> classList;

    @FXML
    private JFXButton back;


    public void initialize(){
        Main.user.sendString("GetClassInfo");
        Main.user.sendString(Session_Id.getUsername());
//        Session_Id.setSec(Main.user.receiveString());
//        Session_Id.setSem(Main.user.receiveString());
       ArrayList<ClassInfoStudent> classes=(ArrayList<ClassInfoStudent>) Main.user.recieveObject();
        ArrayList a=new ArrayList();
        if(classes.size()!=0)
        {
            int i=1;
            for (ClassInfoStudent test : classes) {
                VBox vbox = new VBox();
                vbox.setPadding(new Insets(10, 10, 10, 10));
                if(i%2==0) vbox.setBackground(new Background(new BackgroundFill(Color.MISTYROSE, CornerRadii.EMPTY,Insets.EMPTY)));
                else vbox.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY,CornerRadii.EMPTY,Insets.EMPTY)));
                i++;
                Label lbl1 = new Label("Subject: "+test.getSub());
                lbl1.setFont(Font.font("Baskerville Old Face", FontWeight.BOLD,20));
                Label lbl2 = new Label("Faculty:"+test.getFname());
                lbl2.setFont(Font.font("Baskerville Old Face", FontWeight.BOLD,15));
                vbox.getChildren().addAll(lbl1,lbl2);
                a.add(vbox);
                vbox.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                            Main.user.sendString("getClassidForStudent");
                            Main.user.sendString(test.getFname());
                            Main.user.sendString(test.getSub());
                            Main.user.sendString(Session_Id.getUsername());
                            Session_Id.setClassid(Main.user.receiveInt());
                            //System.out.println(Session_Id.getClassid());
                            change.changeScene("Student/SInsideClass.fxml", event, "Teacher");
                    }
                });
            }
        }
        classList.setItems(FXCollections.observableArrayList(a));
    }

    SceneChange change =new SceneChange();
    public void back(ActionEvent click) {
        change.changeScene("LoginSignup/Login.fxml", click, "Login");
    }

    public void Logout(ActionEvent actionEvent) {
        change.changeScene("LoginSignup/Login.fxml", actionEvent, "Login");
    }
}
