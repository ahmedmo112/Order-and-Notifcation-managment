package com.main.Notification.model;

public abstract class NotificationTemplate {
    protected String userName;




    public NotificationTemplate(String userName) {


        this.userName = userName;
    }


    public String applyTemplate(String language){
        if (language.equals("ar") || language.equals("AR")){
            return applyArabicTemplate();
        }
        else {
            return applyEnglishTemplate();
        }
    }
     abstract String applyEnglishTemplate();
     abstract String applyArabicTemplate();


}
