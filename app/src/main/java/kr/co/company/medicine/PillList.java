package kr.co.company.medicine;

public class PillList {
    String mediList;
    String mediName;
    String startDate;
    String endDate;
    String oneTime, twoTime, threeTime;
    int timesPerDay;
    int mediId;

    private int resId;

    public PillList() {
    }

    public PillList(String mediList, String mediName, String startDate, String endDate, int timesPerDay) {
        this.mediList = mediList;
        this.mediName = mediName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.timesPerDay = timesPerDay;
    }

    public int getMediId() {return mediId;}

    public void setMediId(int mediId) {
        this.mediId = mediId;
    }

    public String getMediList() {
        return mediList;
    }

    public void setMediList(String mediList) {
        this.mediList = mediList;
    }

    public String getMediName() {
        return mediName;
    }

    public void setMediName(String mediName) {
        this.mediName = mediName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getOneTime() {
        return oneTime;
    }

    public void setOneTime(String oneTime) {
        this.oneTime = oneTime;
    }

    public String getTwoTime() {
        return twoTime;
    }

    public void setTwoTime(String twoTime) {
        this.twoTime = twoTime;
    }

    public String getThreeTime() {
        return threeTime;
    }

    public void setThreeTime(String threeTime) {
        this.threeTime = threeTime;
    }


    public int getTimesPerDay() {
        return timesPerDay;
    }

    public void setTimesPerDay(int timesPerDay) {
        this.timesPerDay = timesPerDay;
    }

}
