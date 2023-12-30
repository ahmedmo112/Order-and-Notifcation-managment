package com.main.Notification.Controller;

import com.main.Notification.BSL.NotificationBSL;
import com.main.Notification.BSL.NotificationBSLImpl;
import com.main.Notification.Database.NotificationInMemoryDB;
import com.main.Notification.model.*;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class NotificationControllerImpl implements NotificationController{
    private NotificationBSL notificationBSL;
    public NotificationControllerImpl(){
        notificationBSL = new NotificationBSLImpl(
                new NotificationInMemoryDB()
        );
    }
    @PostMapping("/create/notification")
    @Override
    public String addNotification(@RequestBody Map<String, String> notify) {
/*
    name
    channel
    subject
    temp
 */
        int userID = 1;
        NotificationTemplate temp ;
        if(notify.get("temp").equals("order")) {
            String[] list = new String[2];
            list[0] = "Product 1";
            list[1] = "Product 2";
            temp = new OrderPlacementTemplate(notify.get("name"),list);
        }else{
            temp = new OrderPlacementTemplate(notify.get("name"));
        }
        Notification notification = new Notification();
        notification.setSubject(notify.get("subject"));
        notification.setChannel(NotificationChannels.valueOf(notify.get("channel")));
        notification.setTemplate(temp);
        notificationBSL.pushNotification(notification,userID);
        return "Success";
    }

    @GetMapping("/notification/{lang}/{channel}")
    @Override
    public List<String> retrieveNotification(@PathVariable("lang") String language,@PathVariable("channel") String channel) {
        List<Notification> userNotification = notificationBSL.retrieveNotification(1);
        List<String>  result = new ArrayList<>();
        for(Notification notification : userNotification){
            if(notification.getChannel().toString().equals(channel)){
                NotificationTemplate temp = notification.getTemplate();
                result.add(temp.applyTemplate(language));
            }
        }
        return result;
    }
}
