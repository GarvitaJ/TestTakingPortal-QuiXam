package sample.ServerClient;

public class Session_Id {
    private static String userid;
    private static String type;
    private static int classid;
    private static String quizname;
    private static int totalqns;
    private static String sec;
    private static String sem;

    public  static String getUsername() {
        return userid;
    }
    public static void setUsername(String Username) {
        Session_Id.userid = Username;
    }
    public static String getUsertype(){ return type;}
    public static void setUsertype(String Username) { Session_Id.type = Username; }
    public  static int getClassid() { return classid; }
    public static void setClassid(int Classid) { Session_Id.classid =  Classid; }
    public static String getQuizname(){ return quizname; }
    public static void setQuizname(String Quizname){ Session_Id.quizname=Quizname;}
    public  static int getTotalqns() { return totalqns; }
    public static void setTotalqns(int totalqns) { Session_Id.totalqns =  totalqns; }
    public static void setSec(String Sec) { Session_Id.sec=Sec; }
    public static void setSem(String Sem) {Session_Id.sem=Sem; }
    public static String getSem() { return sem; }
    public static String getSec() { return sec; }
}
