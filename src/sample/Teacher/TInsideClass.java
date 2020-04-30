package sample.Teacher;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Main;
import sample.SceneChange;
import sample.ServerClient.Session_Id;

import java.util.ArrayList;

public class TInsideClass {

    @FXML
    private JFXListView<?> QuizList;

    @FXML
    private JFXTextField Quizname;

    @FXML
    private JFXTextField Totalqns;

    @FXML
    private JFXTextField QuizQns;

    @FXML
    private JFXTextField time;

    @FXML
    private JFXButton ok;

    private SceneChange change = new SceneChange();

    public void createquiz(ActionEvent click){
        String quizname=Quizname.getText();
        String totalqn=Totalqns.getText();
        int totalqns=Integer.parseInt(totalqn);
        String QuizQn=QuizQns.getText();
        int QuizQns=Integer.parseInt(QuizQn);

       if(totalqns<QuizQns)
       {
           Stage popup=new Stage();
           popup.initModality(Modality.APPLICATION_MODAL);
           VBox v=new VBox();
           v.setSpacing(12);
           v.setStyle("-fx-background-color: #fceae0;");
           Label l=new Label("Qns to display > Total Questions" );
           l.setStyle("-fx-text-fill: #913131;");
           l.setFont(Font.font("Baskerville Old Face", FontWeight.BOLD,27));
           JFXButton b1=new JFXButton("OK");
           b1.setButtonType(JFXButton.ButtonType.RAISED);
           b1.setFont(Font.font("Bookman Old Style", FontWeight.BOLD,14));
//        b1.setBackground(new Background(new BackgroundFill(Color.A)));
           b1.setStyle("-fx-background-color: #dfa490; -fx-text-fill: #801e1e;");
           b1.setOnMouseClicked(event -> popup.close());
           v.getChildren().addAll(l,b1);
           v.setAlignment(Pos.CENTER);
           Scene scene=new Scene(v,400,150);
           popup.setScene(scene);
           popup.showAndWait();
           change.changeScene("Teacher/TInsideClass.fxml",click,"Quiz");
           return;
       }


        Main.user.sendString("CreateQuiz");
        Main.user.sendString(quizname);
        Main.user.sendInt(totalqns);
        Main.user.sendInt(QuizQns);
        Main.user.sendInt(Session_Id.getClassid());
        Boolean create=Main.user.recieveBoolean();
        if(create)
        {
            System.out.println("New quiz created");
            Session_Id.setQuizname(quizname);
            change.changeScene("Teacher/CreateQuiz.fxml", click, "Questions");
            Session_Id.setQuizname(quizname);
        }
        else
        {
            Stage popup=new Stage();
            popup.initModality(Modality.APPLICATION_MODAL);
            VBox v=new VBox();
            v.setSpacing(12);
            v.setStyle("-fx-background-color: #fceae0;");
            Label l=new Label("This quiz already exists");
            l.setStyle("-fx-text-fill: #913131;");
            l.setFont(Font.font("Baskerville Old Face", FontWeight.BOLD,27));
            JFXButton b1=new JFXButton("OK");
            b1.setButtonType(JFXButton.ButtonType.RAISED);
            b1.setFont(Font.font("Bookman Old Style", FontWeight.BOLD,14));
            b1.setStyle("-fx-background-color: #dfa490; -fx-text-fill: #801e1e;");
            b1.setOnMouseClicked(event -> popup.close());
            v.getChildren().addAll(l,b1);
            v.setAlignment(Pos.CENTER);
            Scene scene=new Scene(v,300,150);
            popup.setScene(scene);
            popup.setTitle("Failed");
            popup.showAndWait();
            System.out.println("Quiz couldnt be created");
        }
    }
    public void initialize(){
        Main.user.sendString("GetQuizzes");
        //System.out.println(Session_Id.getClassid());
        Main.user.sendInt(Session_Id.getClassid());
        ArrayList<String> quizzes=(ArrayList<String>)Main.user.recieveObject();
        ArrayList a=new ArrayList();
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
                a.add(vbox);
                vbox.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        Session_Id.setQuizname(test);
                        change.changeScene("Leaderboard.fxml",event,"Leaderboard");
                    }
                });
            }
        }
        QuizList.setItems(FXCollections.observableArrayList(a));
    }
    public void back(ActionEvent click) {
        change.changeScene("Teacher/TeacherDashboard.fxml", click, "Login");
    }

    public void Logout(ActionEvent actionEvent) {
        change.changeScene("LoginSignup/Login.fxml", actionEvent, "Login");
    }
}
