package sample.Student;

import sample.Misc.QuizInfo;
import com.jfoenix.controls.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Main;
import sample.SceneChange;
import sample.ServerClient.Session_Id;

import java.util.ArrayList;
import java.util.Random;

public class TakeQuiz {

    @FXML
    private JFXListView<?> qnList;

    @FXML
    private JFXButton submit;

    SceneChange change=new SceneChange();

    ArrayList<String> correct= new ArrayList<>();
    ArrayList<ToggleGroup> toggle= new ArrayList<ToggleGroup>();
    int qnshow;

    public void initialize(){
        Main.user.sendString("GetQuizInfo");
        Main.user.sendString(Session_Id.getQuizname());
        ArrayList<QuizInfo> q=(ArrayList<QuizInfo>)Main.user.recieveObject();

        Main.user.sendString("GetQnstoshow");
        Main.user.sendString(Session_Id.getQuizname());
        qnshow=Main.user.receiveInt();

        ArrayList al=new ArrayList();

        Random r=new Random();
         for(int i=q.size()-1;i>0;i--)
         {
             int newpos=r.nextInt(i);
             QuizInfo temp=q.get(i);
             q.set(i,q.get(newpos));
             q.set(newpos,temp);
         }
         for(int i=q.size()-1;i>=qnshow;i--){
             q.remove(i);
         }

        if(q.size()!=0){
           int i=1;
            for(QuizInfo test:q){
                correct.add(test.getOpncorr());
                VBox vbox=new VBox();
                vbox.setPadding(new Insets(10, 10, 10, 10));
                vbox.setSpacing(10);
                Label lb=new Label("Qn"+i);
                i++;
                lb.setFont(new Font(15));
                JFXTextField qn=new JFXTextField(test.getQn());
                ToggleGroup tg=new ToggleGroup();
                JFXRadioButton a=new JFXRadioButton(test.getOpna());
                JFXRadioButton b= new JFXRadioButton(test.getOpnb());
                JFXRadioButton c= new JFXRadioButton(test.getOpnc());
                JFXRadioButton d= new JFXRadioButton(test.getOpnd());
                a.setToggleGroup(tg);
                b.setToggleGroup(tg);
                c.setToggleGroup(tg);
                d.setToggleGroup(tg);
                HBox hbox=new HBox();
                hbox.setPadding(new Insets(10, 10, 10, 10));
                hbox.setSpacing(10);
                hbox.getChildren().addAll(lb,qn);
                vbox.getChildren().addAll(lb,qn,a,b,c,d);
                al.add(vbox);
                toggle.add(tg);
            }
        }
        qnList.setItems(FXCollections.observableArrayList(al));
    }
    public void validate(ActionEvent click){
        try{
            int score=0;
            for(int i=0;i<toggle.size();i++)
            {
                JFXRadioButton selected= (JFXRadioButton) toggle.get(i).getSelectedToggle();
                String value=selected.getText();
                if(value.equals(correct.get(i))){
                    score++;
                }
            }
            Main.user.sendString("SendScore");
            Main.user.sendString(Session_Id.getQuizname());
            Main.user.sendString(Session_Id.getUsername());
            Main.user.sendInt(score);

            Stage popup=new Stage();
            popup.initModality(Modality.APPLICATION_MODAL);
            VBox v=new VBox();
            v.setSpacing(12);
            v.setStyle("-fx-background-color: #fceae0;");
            Label l=new Label("Score: "+score+"/"+qnshow);
            l.setStyle("-fx-text-fill: #913131;");
            l.setFont(Font.font("Baskerville Old Face", FontWeight.BOLD,27));
            JFXButton b1=new JFXButton("OK");
            b1.setButtonType(JFXButton.ButtonType.RAISED);
            b1.setFont(Font.font("Bookman Old Style", FontWeight.BOLD,14));
            b1.setStyle("-fx-background-color: #dfa490; -fx-text-fill: #801e1e;");
            b1.setOnMouseClicked(event -> popup.close());
            v.getChildren().addAll(l,b1);
            v.setAlignment(Pos.CENTER);
            Scene scene=new Scene(v,200,150);
            popup.setScene(scene);
            popup.showAndWait();
            change.changeScene("Student/SInsideClass.fxml",click,"Quiz");

        }
        catch(NullPointerException e){
            Main.user.sendString("SendScore");
            Main.user.sendString(Session_Id.getQuizname());
            Main.user.sendString(Session_Id.getUsername());
            Main.user.sendInt(0);

            Stage popup=new Stage();
            popup.initModality(Modality.APPLICATION_MODAL);
            VBox v=new VBox();
            v.setSpacing(12);
            v.setStyle("-fx-background-color: #fceae0;");
            Label l=new Label("Score: 0");
            l.setStyle("-fx-text-fill: #913131;");
            l.setFont(Font.font("Baskerville Old Face", FontWeight.BOLD,27));
            JFXButton b1=new JFXButton("OK");
            b1.setButtonType(JFXButton.ButtonType.RAISED);
            b1.setFont(Font.font("Bookman Old Style", FontWeight.BOLD,14));
            b1.setStyle("-fx-background-color: #dfa490; -fx-text-fill: #801e1e;");
            b1.setOnMouseClicked(event -> popup.close());
            v.getChildren().addAll(l,b1);
            v.setAlignment(Pos.CENTER);
            Scene scene=new Scene(v,200,150);
            popup.setScene(scene);
            popup.showAndWait();
            change.changeScene("Student/SInsideClass.fxml",click,"Quiz");

        }

    }

}