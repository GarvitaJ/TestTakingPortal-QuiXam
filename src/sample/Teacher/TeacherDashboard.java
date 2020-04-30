package sample.Teacher;

import sample.Misc.Classes;
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
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Main;
import sample.SceneChange;
import sample.ServerClient.Session_Id;

import java.util.ArrayList;

public class TeacherDashboard {

    @FXML
    private JFXTextField Sem;

    @FXML
    private JFXTextField Sec;

    @FXML
    private JFXTextField Sub;

    @FXML
    private JFXButton Ok;

    @FXML
    private JFXListView<?> classList;

    private SceneChange change = new SceneChange();

    @FXML
    public void create(ActionEvent click)
    {
        String sem=Sem.getText();
        String sec=Sec.getText();
        String subject=Sub.getText();
        String id= Session_Id.getUsername();

        Main.user.sendString("CreateClass");
        Main.user.sendString(sem);
        Main.user.sendString(sec);
        Main.user.sendString(subject);
        Main.user.sendString(id);
        Boolean createclass=Main.user.recieveBoolean();
        if(createclass)
        {
            System.out.println("New class created");
            change.changeScene("Teacher/TeacherDashboard.fxml", click, "Teacher");
        }
        else {
            Stage popup=new Stage();
            popup.initModality(Modality.APPLICATION_MODAL);
            VBox v=new VBox();
            v.setSpacing(12);
            v.setStyle("-fx-background-color: #fceae0;");
            Label l=new Label("This class already exists");
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
            System.out.println("New class could not be created");
        }
    }

    public void initialize(){
        Main.user.sendString("GetClasses");
        String Fid= Session_Id.getUsername();
        Main.user.sendString(Fid);
        ArrayList<Classes> classes=(ArrayList<Classes>)Main.user.recieveObject();
        ArrayList a=new ArrayList();
        if(classes.size()!=0)
        {   int i=1;
            for (Classes test : classes) {
                VBox vbox = new VBox();
               vbox.setPadding(new Insets(2, 2, 2, 2));
                if(i%2==0) vbox.setBackground(new Background(new BackgroundFill(Color.MISTYROSE, CornerRadii.EMPTY,Insets.EMPTY)));
                else vbox.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY,CornerRadii.EMPTY,Insets.EMPTY)));
                i++;
                HBox hb=new HBox(70);
                JFXButton del=new JFXButton("X");
                del.setBackground(new Background(new BackgroundFill(Color.DARKRED,CornerRadii.EMPTY,Insets.EMPTY)));
                Label lbl1 = new Label("Semester: "+test.getSemester());
                lbl1.setFont(Font.font("Baskerville Old Face",FontWeight.BOLD,17));
                hb.getChildren().addAll(lbl1,del);
                Label lbl2 = new Label("Section: "+test.getSection());
                lbl2.setFont(Font.font("Baskerville Old Face",FontWeight.BOLD,17));
                Label lbl3 = new Label("Subject: "+test.getSubject());
                lbl3.setFont(Font.font("Baskerville Old Face",FontWeight.BOLD,17));
                vbox.getChildren().addAll(hb,lbl2,lbl3);
                a.add(vbox);
                vbox.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        Main.user.sendString("GetClassId");
                        Main.user.sendString(test.getSemester());
                        Main.user.sendString(test.getSection());
                        Main.user.sendString(test.getSubject());
                        Session_Id.setClassid(Main.user.receiveInt());
                        change.changeScene("Teacher/TInsideClass.fxml", event, "Teacher");
                    }
                });
                del.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent click) {
                        Stage popup=new Stage();
                        popup.initModality(Modality.APPLICATION_MODAL);
                        VBox v=new VBox();
                        v.setSpacing(12);
                        v.setStyle("-fx-background-color: #fceae0;");
                        Label l=new Label("Confirm delete?");
                        l.setStyle("-fx-text-fill: #913131;");
                        l.setFont(Font.font("Baskerville Old Face", FontWeight.BOLD,27));
                        JFXButton b1=new JFXButton("OK");
                        b1.setButtonType(JFXButton.ButtonType.RAISED);
                        b1.setFont(Font.font("Bookman Old Style", FontWeight.BOLD,14));
                        b1.setStyle("-fx-background-color: #dfa490; -fx-text-fill: #801e1e;");
                        b1.setOnMouseClicked(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                                Main.user.sendString("GetClassId");
                                Main.user.sendString(test.getSemester());
                                Main.user.sendString(test.getSection());
                                Main.user.sendString(test.getSubject());
                                int cid=Main.user.receiveInt();
                                Main.user.sendString("DeleteClass");
                                Main.user.sendInt(cid);
                                Boolean d= Main.user.recieveBoolean();
                               if(d)
                               {
                                   System.out.println("Class deleted");
                               }
                               else{
                                   System.out.println("Couldnt delete class");
                               }
                               popup.close();
                               initialize();
                            }
                        });

                        v.getChildren().addAll(l,b1);
                        v.setAlignment(Pos.CENTER);
                        Scene scene=new Scene(v,300,150);
                        popup.setScene(scene);
                        popup.setTitle("Delete");
                        popup.showAndWait();
                    }
                });
            }
        }
        classList.setItems(FXCollections.observableArrayList(a));
    }
    public void back(ActionEvent click) {
        change.changeScene("LoginSignup/Login.fxml", click, "Login");
    }

    public void Logout(ActionEvent click) {
        change.changeScene("LoginSignup/Login.fxml", click, "Login");
    }
}
