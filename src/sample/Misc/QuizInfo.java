package sample.Misc;

import java.io.Serializable;

public class QuizInfo implements Serializable {
    public String qn,opna,opnb,opnc,opnd,opncorr;
    public QuizInfo(String qn,String opna,String opnb,String opnc,String opnd,String opncorr){
        this.qn=qn;
        this.opna=opna;
        this.opnb=opnb;
        this.opnc=opnc;
        this.opnd=opnd;
        this.opncorr=opncorr;
    }

    public String getOpna() {
        return opna;
    }

    public String getQn() {
        return qn;
    }

    public String getOpnb() {
        return opnb;
    }

    public String getOpnc() {
        return opnc;
    }

    public String getOpnd() {
        return opnd;
    }

    public String getOpncorr() {
        return opncorr;
    }
}
