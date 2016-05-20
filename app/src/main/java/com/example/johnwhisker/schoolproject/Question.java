package com.example.johnwhisker.schoolproject;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by johnw on 4/13/2016.
 */

public class Question implements Parcelable {
    private String question, A, B, C, D;
    private String answer1;
    private int ID, answer;

    public Question() {
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setA(String a) {
        A = a;
    }

    public void setB(String b) {
        B = b;
    }

    public void setC(String c) {
        C = c;
    }

    public void setD(String d) {
        D = d;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }
    public void setAnswer1(String answer1){this.answer1 = answer1;}

    public String getQuestion() {
        return question;
    }

    public String getA() {
        return A;
    }

    public String getB() {
        return B;
    }

    public String getC() {
        return C;
    }

    public String getD() {
        return D;
    }

    public int getID() {
        return ID;
    }

    public int getAnswer() {
        return answer;
    }
    public String getAnswer1(){return answer1;}

    @Override
    public String toString() {
        return "Question: " + question + "\n"
                + "A: " + A + "\n"
                + "B: " + B + "\n"
                + "C: " + C + "\n"
                + "D: " + D + "\n"
                ;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.question);
        dest.writeString(this.A);
        dest.writeString(this.B);
        dest.writeString(this.C);
        dest.writeString(this.D);
        dest.writeInt(this.ID);
        dest.writeInt(this.answer);
    }

    protected Question(Parcel in) {
        this.question = in.readString();
        this.A = in.readString();
        this.B = in.readString();
        this.C = in.readString();
        this.D = in.readString();
        this.ID = in.readInt();
        this.answer = in.readInt();
    }

    public static final Parcelable.Creator<Question> CREATOR = new Parcelable.Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel source) {
            return new Question(source);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };
}
