package io.maang.bos.quartz;

import io.maang.bos.service.take_delivery.PromotionService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * 描述:
 *
 * @outhor ming
 * @create 2018-04-14 18:54
 */

public class PromotionJob implements Job {

    @Autowired
    private PromotionService promotionService;


    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("活动过期处理");
        //每分钟执行一次,当前时间大于promotion数据表中的endTime,活动过期,设置status为2
        promotionService.updeStatus(new Date());
    }
}
