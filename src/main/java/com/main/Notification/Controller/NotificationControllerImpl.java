package com.main.Notification.Controller;

import com.main.APISchemas.ErrorMessageSchema;
import com.main.APISchemas.NotificationResponseSchema;
import com.main.APISchemas.SuccessSchema;
import com.main.Notification.BSL.NotificationBSL;
import com.main.Notification.BSL.NotificationBSLImpl;
import com.main.Notification.Database.NotificationInMemoryDB;
import com.main.Notification.model.*;
import com.main.UserAccount.BSL.AuthenticationBSL;
import com.main.UserAccount.BSL.AuthenticationBSLImpl;
import com.main.UserAccount.BSL.ValidationBSLImpl;
import com.main.UserAccount.Database.AccountMangerInMemoryDB;
import com.main.UserAccount.Database.UserInMemoryDB;
import com.main.UserAccount.model.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class NotificationControllerImpl implements NotificationController {
    private NotificationBSL notificationBSL;
    private AuthenticationBSL authenticationBSL;

    @Autowired
    public NotificationControllerImpl(@Qualifier("notificationBSLImpl") NotificationBSL notificationBSL, @Qualifier("authenticationBSLImpl") AuthenticationBSL authenticationBSL) {
        this.notificationBSL = notificationBSL;
        this.authenticationBSL = authenticationBSL;

    }
//    @PostMapping("/create/notification")
//    @Override
//    public Object addNotification(@RequestBody Map<String, String> notify) {
///*
//    name
//    channel
//    subject
//    temp
// */
//
//        NotificationTemplate temp ;
//        if(notify.get("temp").equals("order")) {
//            String[] list = new String[2];
//            list[0] = "Product 1";
//            list[1] = "Product 2";
//            temp = new OrderPlacementTemplate(notify.get("name"),list);
//        }else{
//            temp = new OrderShipmentTemplate(notify.get("name"));
//        }
//        Notification notification = new Notification();
//        notification.setSubject(notify.get("subject"));
//        notification.setChannel(NotificationChannels.valueOf(notify.get("channel")));
//        notification.setTemplate(temp);
//        notificationBSL.pushNotification(notification,authenticationBSL.getCurrUserAccount().getId());
//
//        return new SuccessSchema("Notification Added");
//    }

    @GetMapping("/notification/{lang}")
    @Override
    public Object retrieveNotification(@PathVariable("lang") String language) {

        UserAccount user = authenticationBSL.getCurrUserAccount();
        if (user == null) {
            return new ErrorMessageSchema("User not logged in");
        }
        List<Notification> userNotification = notificationBSL.retrieveNotification(user.getId());
        if (userNotification == null) {
            return new ErrorMessageSchema("No Notifications Found");
        }
        List<NotificationResponseSchema> result = new ArrayList<>();
        for (Notification notification : userNotification) {

            NotificationTemplate temp = notification.getTemplate();
            NotificationResponseSchema tempSchema = new NotificationResponseSchema(
                    notification.getSubject(),
                    notification.getChannel().toString(),
                    temp.applyTemplate(language)
            );
            result.add(tempSchema);
        }

        return result;
    }

    @GetMapping("/notification/queue")
    @Override
    public Object retrieveNotificationQueue() {
        List<NotificationResponseSchema> notificationResponseSchemas = new ArrayList<>();
//        if (notificationBSL.getNotificationQueue().isEmpty()) {
//            return new ErrorMessageSchema("No Notifications Found");
//        }
        for (Notification notification : notificationBSL.getNotificationQueue()) {

            NotificationTemplate temp = notification.getTemplate();
            NotificationResponseSchema tempSchema = new NotificationResponseSchema(
                    notification.getSubject(),
                    notification.getChannel().toString(),
                    temp.applyTemplate("en")
            );
            notificationResponseSchemas.add(tempSchema);
        }
        return notificationResponseSchemas;
    }
}
