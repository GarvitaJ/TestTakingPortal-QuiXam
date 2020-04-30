package sample.Student;

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
import sample.Main;
import sample.SceneChange;
import sample.ServerClient.Session_Id;

import java.util.ArrayList;

public class SInsideClass {

    @FXML
    private JFXListView<?> quizList;

    @FXML
    private JFXListView<?> quizList2;

    @FXML
    private JFXButton back;

    SceneChange change=new SceneChange();
    public void initialize(){
        Main.user.sendString("GetQuizzes");
        //System.out.println(Session_Id.getClassid());
        Main.user.sendInt(Session_Id.getClassid());
        ArrayList<String> quizzes=(ArrayList<String>)Main.user.recieveObject();
        ArrayList a=new ArrayList();
        ArrayList b=new ArrayList();
        if(quizzes.size()!=0){
            int i=1;
            for(String test:quizzes){
                VBox vbox = new VBox();
                vbox.setPadding(new Insets(10, 10, 10, 10));
                if(i%2==0) vbox.setBackground(new Background(new BackgroundFill(Color.MISTYROSE, CornerRadii.EMPTY,Insets.EMPTY)));
                else vbox.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY,CornerRadii.EMPTY,Insets.EMPTY)));
                i++;
                Label lbl=new Label(test);
                lbl.setFont(new Font(20));
                vbox.getChildren().addAll(lbl);
                Main.user.sendString("QuizTable");
                Main.user.sendString(Session_Id.getUsername());
                Main.user.sendString(test);
                Boolean table=Main.user.recieveBoolean();
//                System.out.println(test+":"+table);
                if(table)  a.add(vbox);
                else  b.add(vbox);
                vbox.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        Session_Id.setQuizname(test);
                       if(table) change.changeScene("Leaderboard.fxml",event,"Questions");
                       else change.changeScene("Student/TakeQuiz.fxml",event,"Questions");
                    }
                });

            }
        }
        quizList.setItems(FXCollections.observableArrayList(a));
        quizList2.setItems(FXCollections.observableArrayList(b));

    }

    public void back(ActionEvent click) {
        change.changeScene("Student/StudentDashboard.fxml", click, "Student");
    }

    public void Logout(ActionEvent actionEvent) {
        change.changeScene("LoginSignup/Login.fxml", actionEvent, "Login");
    }

}
