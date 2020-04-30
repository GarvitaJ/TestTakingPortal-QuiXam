package sample.Misc;

import java.io.Serializable;

public class Classes implements Serializable{
    public String Subject;
    public String Semester;
    public String Section;
    public Classes(String sem,String sec, String sub ){
        Subject=sub;
        Semester=sem;
        Section=sec;
    }

    public String getSection() {
        return Section;
    }

    public String getSemester() {
        return Semester;
    }

    public String getSubject() {
        return Subject;
    }
}
