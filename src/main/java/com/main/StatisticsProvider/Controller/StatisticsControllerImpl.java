package com.main.StatisticsProvider.Controller;

import com.main.APISchemas.StatisticsSchema;
import com.main.StatisticsProvider.BSL.StatisticsBSL;
import com.main.UserAccount.Database.UserDB;
import com.main.UserAccount.model.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatisticsControllerImpl implements StatisticsController {

    StatisticsBSL statisticsBSL;

    @Autowired
    public StatisticsControllerImpl(@Qualifier("statisticsBSLImpl") StatisticsBSL statisticsBSL){
        this.statisticsBSL = statisticsBSL;
    }

    @GetMapping("/statistics")
    @Override
    public StatisticsSchema systemStatistics() {
        return statisticsBSL.retriveStatistic();
    }


}
