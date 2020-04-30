package sample.Misc;

import java.io.Serializable;

public class ClassInfoStudent implements Serializable {
    public String Sub,Fname;
    public ClassInfoStudent(String Subject, String Fname){
        this.Sub=Subject;
        this.Fname=Fname;
    }
    public String getSub(){
        return Sub;
    }
    public String getFname(){
        return Fname;
    }

}
