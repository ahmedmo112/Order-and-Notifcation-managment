package com.main.Notification.model;

public abstract class NotificationTemplate {
    protected String userName;
    protected Object[] args;

    public NotificationTemplate(String userName, Object[] args) {
        this.userName = userName;
        this.args = args;
    }
    public NotificationTemplate(String userName) {
        this.userName = userName;
        this.args = null;
    }
    public String applyTemplate(String language){
        if (language == "ar" || language == "AR"){
            return applyArabicTemplate();
        }
        else {
            return applyEnglishTemplate();
        }
    }
     abstract String applyEnglishTemplate();
     abstract String applyArabicTemplate();


}
