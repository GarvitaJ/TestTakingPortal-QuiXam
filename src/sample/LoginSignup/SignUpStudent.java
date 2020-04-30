package sample.LoginSignup;

        import com.jfoenix.controls.JFXButton;
        import com.jfoenix.controls.JFXTextField;
        import javafx.event.ActionEvent;
        import javafx.event.EventHandler;
        import javafx.fxml.FXML;
        import javafx.fxml.FXMLLoader;
        import javafx.geometry.Pos;
        import javafx.scene.Node;
        import javafx.scene.Parent;
        import javafx.scene.Scene;
        import javafx.scene.control.Alert;
        import javafx.scene.control.Label;
        import javafx.scene.input.MouseEvent;
        import javafx.scene.layout.VBox;
        import javafx.scene.text.Font;
        import javafx.scene.text.FontWeight;
        import javafx.stage.Modality;
        import javafx.stage.Stage;
        import sample.Main;
        import sample.SceneChange;
        import sample.ServerClient.PasswordUtils;

        import java.io.IOException;
        import java.security.spec.InvalidKeySpecException;
        import java.sql.SQLException;

public class SignUpStudent {

    @FXML
    private JFXTextField usn;

    @FXML
    private JFXTextField sem;

    @FXML
    private JFXTextField sec;

    @FXML
    private JFXTextField semail;

    @FXML
    private JFXTextField spassword;

    @FXML
    private JFXTextField sname;

    @FXML
    private JFXButton sconfirm;

    @FXML
    private JFXTextField sconfirmpass;

    @FXML
    void SSignUp(ActionEvent click) throws SQLException, IOException, InvalidKeySpecException {
        //Action of the Button Signup to Register the User
        if ((spassword.getText()).equals(sconfirmpass.getText())){// IF passwod and confirm password fields match
            PasswordUtils pass= new PasswordUtils();
            //Gets the salt required for hashing
            String salt= pass.getSalt(30);
            String securePass= pass.generateSecurePassword(spassword.getText(), salt);// Hashes the password using the salt generated
            Main.user.sendString("StudentSignUp");
            Main.user.sendString(sname.getText());
            Main.user.sendString(usn.getText());
            Main.user.sendString(sem.getText());
            Main.user.sendString(sec.getText());
            Main.user.sendString(semail.getText());
            Main.user.sendString(securePass);
            Main.user.sendString(salt);
            Boolean SignUp=Main.user.recieveBoolean();
            if(SignUp){
                //creates dialog box to show success message
                Stage popup=new Stage();
                popup.initModality(Modality.APPLICATION_MODAL);
                VBox v=new VBox();
                v.setSpacing(12);
                v.setStyle("-fx-background-color: #fceae0;");
                Label l=new Label("Registration Successful");
                l.setStyle("-fx-text-fill: #913131;");
                l.setFont(Font.font("Baskerville Old Face", FontWeight.BOLD,27));
                JFXButton b1=new JFXButton("OK");
                b1.setButtonType(JFXButton.ButtonType.RAISED);
                b1.setFont(Font.font("Bookman Old Style", FontWeight.BOLD,14));
                b1.setStyle("-fx-background-color: #dfa490; -fx-text-fill: #801e1e;");
                b1.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        popup.close();
                        change.changeScene("LoginSignup/Login.fxml", click, "Login");
                    }
                });
                v.getChildren().addAll(l,b1);
                v.setAlignment(Pos.CENTER);
                Scene scene=new Scene(v,300,150);
                popup.setScene(scene);
                popup.setTitle("Success");
                popup.showAndWait();
                return;
            }
            else{
                Stage popup=new Stage();
                popup.initModality(Modality.APPLICATION_MODAL);
                VBox v=new VBox();
                v.setSpacing(12);
                v.setStyle("-fx-background-color: #fceae0;");
                Label l=new Label("Registration Failed");
                l.setStyle("-fx-text-fill: #913131;");
                l.setFont(Font.font("Baskerville Old Face", FontWeight.BOLD,27));
                JFXButton b1=new JFXButton("OK");
                b1.setButtonType(JFXButton.ButtonType.RAISED);
                b1.setFont(Font.font("Bookman Old Style", FontWeight.BOLD,14));
//        b1.setBackground(new Background(new BackgroundFill(Color.A)));
                b1.setStyle("-fx-background-color: #dfa490; -fx-text-fill: #801e1e;");
                b1.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        popup.close();
                    }
                });
                v.getChildren().addAll(l,b1);
                v.setAlignment(Pos.CENTER);
                Scene scene=new Scene(v,300,150);
                popup.setScene(scene);
                popup.setTitle("Failed");
                popup.showAndWait();
                return;
            }

        }
        else{
            Stage popup=new Stage();
            popup.initModality(Modality.APPLICATION_MODAL);
            VBox v=new VBox();
            v.setSpacing(12);
            v.setStyle("-fx-background-color: #fceae0;");
            Label l=new Label("Passwords mismatch");
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
            Scene scene=new Scene(v,300,150);
            popup.setScene(scene);
            popup.setTitle("Failed");
            popup.showAndWait();
            return;
        }

    }
    SceneChange change=new SceneChange();
    public void back(ActionEvent click) {
        change.changeScene("LoginSignup/Login.fxml", click, "Login");
    }
}
