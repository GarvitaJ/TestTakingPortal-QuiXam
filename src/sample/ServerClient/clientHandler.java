package sample.ServerClient;

import sample.Misc.*;

import java.io.*;
import java.net.Socket;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class clientHandler implements Runnable {

    private Socket client;

    private ObjectInputStream ObjectInput;
    private ObjectOutputStream ObjectOutput;
    private DataInputStream dataInput;
    private DataOutputStream dataOutput;
    private String name;
    private ConnectionClass Student = new ConnectionClass();
    private Connection connection = Student.getconnection();
    private PasswordUtils check = new PasswordUtils();


    public clientHandler(Socket client, String name, ObjectOutputStream ObjectOutput, ObjectInputStream ObjectInput, DataOutputStream dataOutput, DataInputStream dataInput) {
        this.name = name;
        this.client = client;
        this.ObjectOutput = ObjectOutput;
        this.ObjectInput = ObjectInput;
        this.dataInput = dataInput;
        this.dataOutput = dataOutput;
    }

    @Override
    public void run() {
        while (true) {
            try {
                String recieved = dataInput.readUTF();
                if (recieved.equalsIgnoreCase("Exit")) {
                    System.out.println("Client Connection Terminating");
                    this.client.close();
                    break;
                }
                switch (recieved) {
                    case "Login":
                        dataOutput.writeBoolean(Login());
                        System.out.println("Login Was entered");
                        break;
                    case "StudentSignUp":
                        dataOutput.writeBoolean(StudentSignUp());
                        break;
                    case "TeacherSignUp":
                        dataOutput.writeBoolean(TeacherSignUp());
                        break;
                    case "CreateClass":
                        dataOutput.writeBoolean(CreateClass());
                        break;
                    case "GetClasses":
                        ObjectOutput.writeObject(GetClasses());
                        break;
                    case "GetClassId":
                        dataOutput.writeInt(GetClassId());
                        break;
                    case "CreateQuiz":
                        dataOutput.writeBoolean(CreateNewQuiz());
                        break;
                        case "GetTotalqns":
                        dataOutput.writeInt(GetTotalqns());
                      break;
                      case "GetQuizzes":
                        ObjectOutput.writeObject(GetQuizzes());
                        break;
                    case  "InsertQuestions":
                        InsertQuestions();
                        break;
                    case "GetClassInfo":
                        ObjectOutput.writeObject(GetClassInfo());
                        break;
                    case "getClassidForStudent":
                        dataOutput.writeInt(getClassidForStudent());
                        break;
                    case "GetQuizInfo":
                        ObjectOutput.writeObject(GetQuizInfo());
                        break;
                    case "GetQnstoshow":
                        dataOutput.writeInt(GetQnstoshow());
                        break;
                    case "SendScore":
                        SendString();
                        break;
                    case "GetScores":
                        ObjectOutput.writeObject(GetScores());
                        break;
                    case "QuizTable":
                        dataOutput.writeBoolean(QuizTable());
                        break;
                    case "DeleteClass" :
                        dataOutput.writeBoolean(DeleteClass());
                        break;
                    case "DeleteQuiz":
                        dataOutput.writeBoolean(DeleteQuiz());
                        break;
                    case "GetTime":
                        dataOutput.writeInt(GetTime());
                }
            } catch (IOException e) {
                System.out.println(e);
            }

        }
        try {
            ObjectOutput.close();
            ObjectInput.close();
            dataOutput.close();
            dataInput.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private int GetTime() {
        try{
            String qname=this.dataInput.readUTF();
            String sql="Select Time from quiz where Quizname=\""+qname+"\";";
            Statement statement=connection.createStatement();
            ResultSet rs=statement.executeQuery(sql);
            rs.next();
            return rs.getInt("Time");
        }
        catch (IOException | SQLException e) {
            System.out.println(e);
        }
        return 0;
    }

    private boolean DeleteQuiz() {
        try{
            String quizname=this.dataInput.readUTF();
            String sql="Delete from quiz where Quizname=\""+quizname+"\";";
            Statement statement=connection.createStatement();
            statement.execute(sql);
            return true;
        }
        catch (IOException | SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    private boolean DeleteClass() {
        try{
            int classid=this.dataInput.readInt();
            String sql="Delete from questions where classid=\""+classid+"\";";
            Statement statement=connection.createStatement();
            statement.execute(sql);
            String sql2="Delete from quiz where classid=\""+classid+"\";";
            statement.execute(sql2);
            String sql1="Delete from class where classid=\""+classid+"\";";
            statement.execute(sql1);
            return true;
        } catch (SQLException | IOException e) {
            System.out.println(e);
        }
        return false;
    }

    private boolean QuizTable() {
        try{
            String usn=this.dataInput.readUTF();
            String qname=this.dataInput.readUTF();
            String sql="Select exists  (select score from scoreboard where USN=\""+usn+"\" and Quizname=\""+qname+"\");";
            Statement statement=connection.createStatement();
            ResultSet rs=statement.executeQuery(sql);
            rs.next();
            String s="exists  (select score from scoreboard where USN=\""+usn+"\" and Quizname=\""+qname+"\")";
            return rs.getBoolean(s);
        }
        catch (IOException | SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Object GetScores() {
        ArrayList<Score> sc=new ArrayList<>();
        Score s1;
        try {
            String quizname = this.dataInput.readUTF();
            String sql="Select USN,score from scoreboard where Quizname=\""+quizname+"\" order by score desc;";
            Statement statement=connection.createStatement();
            ResultSet rs=statement.executeQuery(sql);
            while(rs.next()){
                String usn=rs.getString("USN");
                int scores=rs.getInt("score");
                s1=new Score(usn,scores);
                sc.add(s1);
            }
        }
        catch (IOException | SQLException e) {
            System.out.println(e);
        }
        return sc;
    }

    private void SendString() {
        try{
            String quizname=this.dataInput.readUTF();
            String usn1=this.dataInput.readUTF();
            String usn=usn1.toUpperCase();
            int score=this.dataInput.readInt();
            String sql="Insert into scoreboard(Quizname, USN, score)values(\""+quizname+"\",\""+usn+"\",\""+score+"\");";
            Statement statement=connection.createStatement();
            statement.execute(sql);
        }
        catch (IOException | SQLException e) {
            System.out.println(e);
        }
    }

    private int GetQnstoshow() {
        try{
            String quizname=this.dataInput.readUTF();
            String sql="Select Qnstoshow from quiz where quizname=\""+quizname+"\";";
            Statement statement=connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            rs.next();
            return rs.getInt("Qnstoshow");
        }
        catch (IOException | SQLException e) {
            System.out.println(e);
        }
        return 0;
    }

    private Object GetQuizInfo() {
        ArrayList<QuizInfo> q=new ArrayList<>();
        QuizInfo q1;
        try{
            String quizname=this.dataInput.readUTF();
            String sql="Select Qn, A, B, C, D, correct from questions where Quizname=\""+quizname+"\";";
            Statement statement=connection.createStatement();
            ResultSet rs=statement.executeQuery(sql);
            while(rs.next()){
                String qn=rs.getString("Qn");
                String a=rs.getString("A");
                String b=rs.getString("B");
                String c=rs.getString("C");
                String d=rs.getString("D");
                String cr=rs.getString("correct");
                q1=new QuizInfo(qn,a,b,c,d,cr);
                q.add(q1);
            }
        }
        catch (IOException | SQLException e) {
            System.out.println(e);
        }
        return q;
    }

    private int getClassidForStudent(){
        try{
            String Fname=this.dataInput.readUTF();
            String Sub=  this.dataInput.readUTF();
            String USN=   this.dataInput.readUTF();
            String sql1="Select Sem,Sec from studentreg where USN=\""+USN+"\";";
            Statement statement=connection.createStatement();
            ResultSet rs = statement.executeQuery(sql1);
            rs.next();
            String sec=rs.getString("Sec");
            String sem=rs.getString("Sem");
            String sql2="Select c.Classid from class c, teacherreg t where t.Fid=c.Fid and t.Name=\""+Fname+"\" and c.Sec=\""+sec+"\" and c.Sem=\""+sem+"\" and c.Sub=\""+Sub+"\";" ;
            Statement statement1=connection.createStatement();
            ResultSet rs1 = statement.executeQuery(sql2);
            rs1.next();
            return rs1.getInt("Classid");
        }
        catch (IOException | SQLException e) {
            System.out.println(e);
        }
        return 0;
    }

    private ArrayList<ClassInfoStudent> GetClassInfo() {
        ArrayList<ClassInfoStudent> classes=new ArrayList<>();
        ClassInfoStudent c1;

        try{
            String USN=this.dataInput.readUTF();
            String sql1="Select Sem,Sec from studentreg where USN=\""+USN+"\";";
            Statement statement=connection.createStatement();
            ResultSet rs = statement.executeQuery(sql1);
            rs.next();
            String sec=rs.getString("Sec");
            String sem=rs.getString("Sem");
            String sql2="Select t.Name,c.Sub from class c,teacherreg t where c.Fid=t.Fid and c.Sem=\'"+sem+"\' and c.Sec=\'"+sec+"\';";
            ResultSet rs1 = statement.executeQuery(sql2);
            while(rs1.next()){
                String fname=rs1.getString("Name");
                String Sub=rs1.getString("Sub");
                c1=new ClassInfoStudent(Sub,fname);
                classes.add(c1);
            }
        } catch (IOException | SQLException e) {
            System.out.println(e);
        }
        System.out.println(classes.size());
        return classes;
    }

    private void InsertQuestions() {
        try{
            QnDetails q= (QnDetails) ObjectInput.readObject();
            String qn=q.qn;
            String a=q.opa;
            String b=q.opb;
            String c=q.opc;
            String d=q.opd;
            String correct=q.corr;
            String quizname=this.dataInput.readUTF();
            int classid=this.dataInput.readInt();
            String sql="Insert into questions(Classid, Qn, A, B, C, D, correct, Quizname) values ('"+classid+"','"+qn+"','"+a+"','"+b+"','"+c+"','"+d+"','"+correct+"','"+quizname+"');";
            Statement statement = connection.createStatement();
            statement.execute(sql);
        } catch (IOException | SQLException | ClassNotFoundException e) {
            System.out.println(e);
        }
    }

    private ArrayList GetQuizzes() {
        ArrayList<String> quizzes = new ArrayList<>();
        try{
            int classid=this.dataInput.readInt();
            String sql="Select Quizname from quiz where Classid=\""+classid+"\";";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                quizzes.add(rs.getString("Quizname"));
            }
        }
        catch (IOException | SQLException e) {
            System.out.println(e);
        }
        return quizzes;
    }

    private int GetTotalqns() {
        try{
            String quizname=this.dataInput.readUTF();
           String sql="Select Totalqns from quiz where quizname=\""+quizname+"\";";
           Statement statement=connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            rs.next();
            return rs.getInt("Totalqns");
        }
        catch (IOException | SQLException e) {
            System.out.println(e);
        }
        return 0;
    }

    private boolean CreateNewQuiz() {
        try{
            String quizname=dataInput.readUTF();
            int totalqns=dataInput.readInt();
            int quizqns=dataInput.readInt();
            int classid=dataInput.readInt();
            String sql="Insert into quiz(`Classid`,`Quizname`,`Totalqns`,`Qnstoshow`) values('"+classid+"','"+quizname+"','"+totalqns+"','"+quizqns+"');";
            Statement statement = connection.createStatement();
            statement.execute(sql);
            return true;
        } catch (IOException | SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    private int GetClassId(){
        try{
            String Sem = dataInput.readUTF();
            String Sec = dataInput.readUTF();
            String Sub = dataInput.readUTF();
            String sql="SELECT Classid FROM class where Sem=\""+Sem+"\" and Sub=\""+Sub+"\" and Sec=\""+Sec+"\" ;";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            rs.next();
            int classid= rs.getInt("ClassId");
            return classid;
        } catch (IOException | SQLException e) {
            System.out.println(e);
        }
        return 0;
    }


    private ArrayList<Classes> GetClasses() {
        ArrayList<Classes> classes = new ArrayList<>();
        Classes class1;
        try{
            String Fid=dataInput.readUTF();
            String sql="SELECT Sem,Sub,Sec FROM class WHERE Fid=\""+ Fid +"\";";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next())
            {
                String sem=rs.getString("Sem");
                String sub=rs.getString("Sub");
                String sec=rs.getString("Sec");
                class1=new Classes(sem, sec, sub);
                classes.add(class1);
            }
        }

        catch (IOException | SQLException e) {
            System.out.println(e);
        }
        return classes;
    }

    private boolean Login() {
        try {
            String username1 = dataInput.readUTF();
            String username=username1.toUpperCase();
            String password = dataInput.readUTF();
            String type = dataInput.readUTF();
            String table,column;
            if (type.equals("Teacher")) {
                table = "`teacherreg`";
                column="`Fid`";
            } else {
                table = "`studentreg`";
                column="`USN`";
            }
            String sql = "SELECT `Password`, `Salt` FROM " + table + " WHERE "+ column +"=\"" + username + "\";";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            String Spassword = "";
            String Salt = "ABC2";

            while (rs.next()) {
                Spassword = rs.getString("Password");
                Salt = rs.getString("Salt");
            }
            if (check.verifyUserPassword(password, Spassword, Salt)) {
                return true;
            } else return false;

        } catch (IOException | SQLException | InvalidKeySpecException | IllegalArgumentException e) {
            System.out.println(e);
            return false;
        }
    }

    private boolean StudentSignUp() {
        try {
            String Name= this.dataInput.readUTF();
            String USN1 = this.dataInput.readUTF();
            String USN=USN1.toUpperCase();
            String Sem = this.dataInput.readUTF();
            String Sec1 = this.dataInput.readUTF();
            String Sec=Sec1.toUpperCase();
            String Email1 = this.dataInput.readUTF();
            String Email=Email1.toLowerCase();
            String Password = this.dataInput.readUTF();
            String salt = this.dataInput.readUTF();
            String sql = "INSERT INTO `studentreg`(`Name`, `USN`, `Sem`,`Sec`,`Email`, `Password`, `Salt`) VALUES('" + Name + "','" + USN + "','" + Sem + "','" + Sec + "','" + Email + "','" + Password + "','" + salt + "');";
            Statement statement = connection.createStatement();
            statement.execute(sql);
            return true;
        } catch (IOException | SQLException e) {
            System.out.println(e);
        }
        return false;
    }
    private boolean TeacherSignUp() {
        try {
            String Name= this.dataInput.readUTF();
            String Fid1 = this.dataInput.readUTF();
            String Fid=Fid1.toUpperCase();
            String Dept1 = this.dataInput.readUTF();
            String Dept=Dept1.toUpperCase();
            String Email1 = this.dataInput.readUTF();
            String Email=Email1.toLowerCase();
            String Password = this.dataInput.readUTF();
            String salt = this.dataInput.readUTF();
            String sql = "INSERT INTO `teacherreg`(`Name`, `Fid`, `Dept`,`Email`, `Password`, `Salt`) VALUES('" + Name + "','" + Fid + "','" + Dept + "','" + Email + "','" + Password + "','" + salt + "');";

            Statement statement = connection.createStatement();
            statement.execute(sql);
            return true;
        } catch (IOException | SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    private boolean CreateClass(){
        try{
            String sem= this.dataInput.readUTF();
            String sec1= this.dataInput.readUTF();
            String sec=sec1.toUpperCase();
            String sub1= this.dataInput.readUTF();
            String sub=sub1.toUpperCase();
            String id= this.dataInput.readUTF();
            String sql="INSERT INTO `class`(`Fid`,`Sem`,`Sec`,`Sub`) VALUES('"+id+"','"+ sem +"','"+ sec +"','"+ sub+"');";
            Statement statement = connection.createStatement();
            statement.execute(sql);
            return true;
        }
        catch (IOException | SQLException e) {
            System.out.println(e);
        }
        return false;
    }
}
