package model;

import java.util.Date;

public class Customer {

    private String Name;
    private int YOB;
    private String AddressCus;
    private Date DayVisit;
    private Date ExpectedDOB;
    private String Result;
    private String Note;

    public String getName() {
        return Name;
    }

    public int getYOB() {
        return YOB;
    }

    public String getAddressCus() {
        return AddressCus;
    }

    public Date getDayVisit() {
        return DayVisit;
    }

    public Date getExpectedDOB() {
        return ExpectedDOB;
    }

    public String getResult() {
        return Result;
    }

    public String getNote() {
        return Note;
    }

    public void setName(String Name) {
        this.Name = Name.trim();
    }

    public void setYOB(int YOB) {
        this.YOB = YOB;
    }

    public void setAddressCus(String AddressCus) {
        this.AddressCus = AddressCus.trim();
    }

    public void setDayVisit(Date DayVisit) {
        this.DayVisit = DayVisit;
    }

    public void setExpectedDOB(Date ExpectedDOB) {
        this.ExpectedDOB = ExpectedDOB;
    }

    public void setResult(String Result) {
        this.Result = Result.trim();
    }

    public void setNote(String Note) {
        this.Note = Note.trim();
    }

}
