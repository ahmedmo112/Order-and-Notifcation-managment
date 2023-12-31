package com.main.StatisticsProvider.BSL;

import com.main.APISchemas.StatisticsSchema;
import com.main.Notification.Database.NotificationInMemoryDB;
import com.main.Notification.model.Notification;
import com.main.Notification.model.NotificationChannels;
import com.main.Notification.model.OrderPlacementTemplate;
import com.main.Notification.model.OrderShipmentTemplate;
import com.main.UserAccount.BSL.AccountMangerBSL;
import com.main.UserAccount.Database.AccountMangerDB;
import com.main.UserAccount.Database.AccountMangerInMemoryDB;
import com.main.UserAccount.model.AccountManger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatisticsBSLImpl implements StatisticsBSL {

    AccountMangerDB accountMangerDB;
    NotificationInMemoryDB notificationInMemoryDB;
    @Autowired
    public StatisticsBSLImpl(@Qualifier("accountMangerInMemoryDB") AccountMangerDB accountMangerDB, @Qualifier("notificationInMemoryDB") NotificationInMemoryDB notificationInMemoryDB){
        this.accountMangerDB = accountMangerDB;
        this.notificationInMemoryDB = notificationInMemoryDB;

    }
    @Override
    public StatisticsSchema retriveStatistic() {
        Map<NotificationChannels, Integer> mostUsedChannal = new HashMap<>();
        Map<String, Integer> mostUsedTemplete = new HashMap<>();
        StatisticsSchema statisticsSchema = new StatisticsSchema();
        String mostTemplete = "", mostChannal = "";
        Integer maxTemplete=0,maxChannal=0;


        List<AccountManger> accountMangerAccounts = accountMangerDB.getAccounts();

        for (int i = 0; i < accountMangerAccounts.size(); i++) {
            Integer ID = accountMangerAccounts.get(i).getUserAccountID();
            List<Notification> userQueue = notificationInMemoryDB.getNotification(ID);
            if (userQueue == null)
                continue;


            for (int j = 0; j < userQueue.size(); j++) {
                Notification notification = userQueue.get(j);

                if (notification.getTemplate() instanceof OrderPlacementTemplate) {
                    if (mostUsedTemplete.containsKey("OrderPlacementTemplate")) {
                        Integer curVal = mostUsedTemplete.get("OrderPlacementTemplate");
                        mostUsedTemplete.put("OrderPlacementTemplate", curVal + 1);
                    } else {
                        mostUsedTemplete.put("OrderPlacementTemplate", 1);
                    }
                    if(mostUsedTemplete.get("OrderPlacementTemplate")>maxTemplete) {
                        maxTemplete=mostUsedTemplete.get("OrderPlacementTemplate");
                        mostTemplete="OrderPlacementTemplate";
                    }
                } else if (notification.getTemplate() instanceof OrderShipmentTemplate) {
                    if (mostUsedTemplete.containsKey("OrderShipmentTemplate")) {
                        Integer curVal = mostUsedTemplete.get("OrderShipmentTemplate");
                        mostUsedTemplete.put("OrderShipmentTemplate", curVal + 1);
                    } else {
                        mostUsedTemplete.put("OrderShipmentTemplate", 1);
                    }
                    if(mostUsedTemplete.get("OrderShipmentTemplate")>maxTemplete) {
                        maxTemplete=mostUsedTemplete.get("OrderShipmentTemplate");
                        mostTemplete="OrderShipmentTemplate";
                    }
                } else {
                    if (mostUsedTemplete.containsKey("OrderShippedTemplete")) {
                        Integer curVal = mostUsedTemplete.get("OrderShippedTemplete");
                        mostUsedTemplete.put("OrderShippedTemplete", curVal + 1);
                    } else {
                        mostUsedTemplete.put("OrderShippedTemplete", 1);
                    }
                    if(mostUsedTemplete.get("OrderShippedTemplete")>maxTemplete) {
                        maxTemplete=mostUsedTemplete.get("OrderShippedTemplete");
                        mostTemplete="OrderShippedTemplete";
                    }
                }

                if (mostUsedChannal.containsKey(notification.getChannel())) {
                    Integer curVal = mostUsedChannal.get(notification.getChannel());
                    mostUsedChannal.put(notification.getChannel(), curVal + 1);
                } else {
                    mostUsedChannal.put(notification.getChannel(), 1);
                }

                if(mostUsedChannal.get(notification.getChannel())>maxChannal) {
                    maxChannal=mostUsedChannal.get(notification.getChannel());
                    mostChannal=notification.getChannel().toString();
                }
            }
        }
        statisticsSchema.setMostUsedChanal(mostChannal);
        statisticsSchema.setMostUsedTemplete(mostTemplete);
        return statisticsSchema;
    }
}
