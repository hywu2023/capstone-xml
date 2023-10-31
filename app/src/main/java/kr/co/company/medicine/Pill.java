package kr.co.company.medicine;

public class Pill {

    private String pill_name; //품목명
    private String pill_company; //업소명

    private String pill_className; //분류명

    private String pill_etcOtcName; // 전문/일반

    private String pill_image; //이미지 주소

    public String getPill_name(){
        return  pill_name;
    }
    public String getPill_company(){
        return  pill_company;
    }

    public String getPill_className(){
        return  pill_className;
    }
    public String getPill_etcOtcName(){
        return  pill_etcOtcName;
    }
    public String getPill_image(){
        return  pill_image;
    }

    public void setPill_name(String pill_name){
        this.pill_name = pill_name;
    }
    public void setPill_company(String pill_company){
        this.pill_company = pill_company;
    }

    public void setPill_className(String pill_className){
        this.pill_className = pill_className;
    }
    public void setPill_etcOtcName(String pill_etcOtcName){
        this.pill_etcOtcName = pill_etcOtcName;
    }
    public void setPill_image(String pill_image){
        this.pill_image = pill_image;
    }

}
