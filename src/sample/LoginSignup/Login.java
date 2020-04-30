package sample.LoginSignup;

import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import sample.Main;
import sample.SceneChange;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;

import sample.ServerClient.ConnectionClass;
import sample.ServerClient.Session_Id;

import java.sql.Connection;


public class Login {
    @FXML
    private JFXTextField userid;

    @FXML
    private JFXPasswordField password;

    @FXML
    private JFXButton signupstud;

    @FXML
    private JFXButton signupteach;

    @FXML
    private JFXRadioButton typeteacher;

    @FXML
    private ToggleGroup type;

    @FXML
    private JFXRadioButton typestudent;

    @FXML
    private Label invid;

    @FXML
    private JFXButton Confirm;

    @FXML
    private JFXTextField password2;

    @FXML
    private JFXCheckBox passwordcheck;

    private SceneChange change = new SceneChange();

    @FXML
    public void Login(ActionEvent click){
        String username=userid.getText();
        String pwd;
        if(passwordcheck.isSelected()){
            pwd=password2.getText();
        }
        else{
            pwd=password.getText();
        }

        String type;
        if(typestudent.isSelected())
        {
            type="Student";
        }
        else
            type="Teacher";
       // System.out.println(username);
       // System.out.println(pwd);
       // System.out.println(type);
        Main.user.sendString("Login");
        Main.user.sendString(username);
        Main.user.sendString(pwd);
        Main.user.sendString(type);
        Boolean login=Main.user.recieveBoolean();
       // System.out.println(login);
        if(login){
            System.out.println("Login Successful");
            Session_Id.setUsername(username);
            Session_Id.setUsertype(type);
                if(type.equals("Student"))
                {
                    change.changeScene("Student/StudentDashboard.fxml", click, "Student");
                }
                else
                {
                    change.changeScene("Teacher/TeacherDashboard.fxml", click, "Teacher");
                }
        }
        else{
            invid.setOpacity(1);
            System.out.println("Login Unsuccessful");
        }
    }
    @FXML
    private void SignUpStud (MouseEvent click){
        change.changeScene("LoginSignup/SignUpStudent.fxml", click, "SignUp");
    }
    @FXML
    private void SignUpTeach (MouseEvent click){
        change.changeScene("LoginSignup/SignUpTeacher.fxml", click, "SignUp");
    }

    public void showpass(ActionEvent actionEvent) {

        if(passwordcheck.isSelected()){
            password2.setText(password.getText());
            password2.setOpacity(1);
            password.setOpacity(0);
            password.setDisable(true);
            password2.setDisable(false);
        }
        else
        {
            password.setText(password2.getText());
            password2.setOpacity(0);
            password.setOpacity(1);
            password2.setDisable(true);
            password.setDisable(false);
        }
    }
}


