package com.main.APISchemas;

public class StatisticsSchema {
     String mostUsedTemplete;
    String mostUsedChanal;

    public void setMostUsedChanal(String mostUsedChanal) {

        mostUsedChanal += mostUsedChanal.charAt(0) < 'a' ? "(SMS)" : "(E-MAIL)";
        this.mostUsedChanal = mostUsedChanal;
    }

    public void setMostUsedTemplete(String mostUsedTemplete) {
        this.mostUsedTemplete = mostUsedTemplete;
    }

    public String getMostUsedChanal() {
        return mostUsedChanal;
    }

    public String getMostUsedTemplete() {
        return mostUsedTemplete;
    }
}
