package sample.Teacher;

import sample.Misc.QnDetails;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
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

public class CreateQuiz {

    @FXML
    private JFXListView<?> quizTemplate;

    @FXML
    private JFXButton Confirm;

    SceneChange change = new SceneChange();

    ArrayList<JFXTextField> q = new ArrayList();
    ArrayList<JFXTextField> opna = new ArrayList();
    ArrayList<JFXTextField> opnb= new ArrayList();
    ArrayList<JFXTextField> opnc = new ArrayList();
    ArrayList<JFXTextField> opnd = new ArrayList();
    ArrayList<ComboBox> cr = new ArrayList();

    public void initialize() {
        Main.user.sendString("GetTotalqns");
        Main.user.sendString(Session_Id.getQuizname());
        int totalqns = Main.user.receiveInt();
        //System.out.println(totalqns);
        Session_Id.setTotalqns(totalqns);
        //System.out.println(Session_Id.getTotalqns());
        ArrayList al = new ArrayList();
        //String qnid,aid,bid,cid,did,crid;
        if (totalqns != 0) {
            for (int i = 0; i < totalqns; i++) {
                VBox vbox = new VBox();
                vbox.setPadding(new Insets(10, 10, 10, 10));
                vbox.setSpacing(10);
                HBox hbox=new HBox();
                hbox.setPadding(new Insets(10, 10, 10, 10));
                hbox.setSpacing(5);
                Label lb=new Label((i+1)+".");
                lb.setFont(new Font(15));
                JFXTextField qn = new JFXTextField();
                qn.setPromptText("Question");
                JFXTextField a = new JFXTextField();
                a.setPromptText("Option A");
                JFXTextField b = new JFXTextField();
                b.setPromptText("Option B");
                JFXTextField c = new JFXTextField();
                c.setPromptText("Option C");
                JFXTextField d = new JFXTextField();
                d.setPromptText("Option D");
                ObservableList<String> options =
                        FXCollections.observableArrayList(
                                "A",
                                "B",
                                "C",
                                "D"
                        );
                final JFXComboBox correct = new JFXComboBox(options);
                correct.setPromptText("Correct");
                vbox.getChildren().addAll(lb,qn,a,b,c,d,correct);
                al.add(vbox);
                q.add(qn);
                opna.add(a);
                opnb.add(b);
                opnc.add(c);
                opnd.add(d);
                cr.add(correct);

                quizTemplate.setItems(FXCollections.observableArrayList(al));
            }
        }
    }


    public void back(ActionEvent click) {
        Main.user.sendString("DeleteQuiz");
        Main.user.sendString(Session_Id.getQuizname());
        Boolean d=Main.user.recieveBoolean();
        if(d)
        {
            System.out.println("Quiz deleted");
        }
        else{
            System.out.println("Quiz couldn't be deleted");
        }
        change.changeScene("Teacher/TInsideClass.fxml", click, "Teacher");

    }

    @FXML
    public void getquizelements(ActionEvent click){
        try{
            int totalqns=Session_Id.getTotalqns();
            for(int i=0;i<totalqns;i++)
            {
                QnDetails qn = new QnDetails();
                qn.qn=q.get(i).getText();
                qn.opa=opna.get(i).getText();
                qn.opb=opnb.get(i).getText();
                qn.opc=opnc.get(i).getText();
                qn.opd=opnd.get(i).getText();
                switch(cr.get(i).getValue().toString())
                {
                    case "A":
                        qn.corr=opna.get(i).getText();
                        break;
                    case "B":
                        qn.corr=opnb.get(i).getText();
                        break;
                    case "C":
                        qn.corr=opnc.get(i).getText();
                        break;
                    case "D":
                        qn.corr=opnd.get(i).getText();
                        break;
                    default:
                        System.out.println("Select an option");
                }
                Main.user.sendString("InsertQuestions");
                Main.user.sendObject(qn);
                Main.user.sendString(Session_Id.getQuizname());
                Main.user.sendInt(Session_Id.getClassid());
            }
            change.changeScene("Teacher/TInsideClass.fxml",click,"Quiz");
        }
        catch(NullPointerException e){
            Stage popup=new Stage();
            popup.initModality(Modality.APPLICATION_MODAL);
            VBox v=new VBox();
            v.setSpacing(12);
            v.setStyle("-fx-background-color: #fceae0;");
            Label l=new Label("Please enter all questions and options");
            l.setStyle("-fx-text-fill: #913131;");
            l.setFont(Font.font("Baskerville Old Face", FontWeight.BOLD,27));
            JFXButton b1=new JFXButton("OK");
            b1.setButtonType(JFXButton.ButtonType.RAISED);
            b1.setFont(Font.font("Bookman Old Style", FontWeight.BOLD,14));
            b1.setStyle("-fx-background-color: #dfa490; -fx-text-fill: #801e1e;");
            b1.setOnMouseClicked(event -> popup.close());
            v.getChildren().addAll(l,b1);
            v.setAlignment(Pos.CENTER);
            Scene scene=new Scene(v,400,150);
            popup.setScene(scene);
            popup.setTitle("Failed");
            popup.showAndWait();
        }
        }

}
