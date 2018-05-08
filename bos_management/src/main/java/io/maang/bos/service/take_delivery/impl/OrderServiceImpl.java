package io.maang.bos.service.take_delivery.impl;

import io.maang.bos.dao.base.AreaRepository;
import io.maang.bos.dao.base.FixedAreaRepository;
import io.maang.bos.dao.take_delivery.OrderRepository;
import io.maang.bos.dao.take_delivery.WorkBillRepository;
import io.maang.bos.domain.base.Area;
import io.maang.bos.domain.base.Courier;
import io.maang.bos.domain.base.FixedArea;
import io.maang.bos.domain.base.SubArea;
import io.maang.bos.domain.constant.Constant;
import io.maang.bos.domain.take_delivery.Order;
import io.maang.bos.domain.take_delivery.WorkBill;
import io.maang.bos.service.take_delivery.OrderService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;
import javax.ws.rs.core.MediaType;
import java.util.Date;
import java.util.Iterator;
import java.util.UUID;

/**
 * 描述:
 *
 * @outhor ming
 * @create 2018-04-17 21:38
 */
@Service
public class OrderServiceImpl implements OrderService {


    @Autowired
    private AreaRepository areaRepository;

    @Autowired
    private FixedAreaRepository fixedAreaRepository;

    @Autowired
    private WorkBillRepository workBillRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    @Qualifier("jmsQueueTemplate")
    private JmsTemplate jmsTemplate;


    @Override
    public void saveOrder(Order order) {

        order.setOrderNum(UUID.randomUUID().toString()); //设置订单号
        order.setOrderTime(new Date()); //设置下单的时间
        order.setStatus("1"); //待取件

        //寄件人的省市区
        Area area = order.getSendArea();
        Area persistArea = areaRepository
                .findByProvinceAndCityAndDistrict(area.getProvince(), area.getCity(), area.getDistrict());

        //收件人的省市区
        Area recArea = order.getSendArea();
        Area persistRecArea = areaRepository
                .findByProvinceAndCityAndDistrict(recArea.getProvince(), recArea.getCity(), recArea.getDistrict());

        order.setSendArea(persistArea);
        order.setRecArea(persistRecArea);

        //自动分单的逻辑,基于crm系统地址库的完全匹配,获取定区,匹配快递员
        String fixedAreaId = WebClient
                .create(Constant.CRM_MANAGEMENT_URL
                        +"/services/customerService/customer/findFixedAreaIdByAddress?address="+order.getSendAddress())
                .accept(MediaType.APPLICATION_JSON)
                .get(String.class);
        if (fixedAreaId != null) {
            FixedArea fixedArea = fixedAreaRepository.findOne(fixedAreaId);
            Courier courier = fixedArea.getCouriers().iterator().next();
                if (courier != null) {
                    //自动分单成功
                    System.out.println("自动分单成功");
                    //保存订单
                    saveOrder(order,courier);
                    //生成工单发送短息
                    generateWorkBil(order);
                    return;
                }


        }

        //自动分单逻辑, 通过省市区 ,查询分区关键字,匹配地址,基于分区实现分单
        for (SubArea subArea : persistArea.getSubareas()) {
            //当前的客户下单地址 是否包含分区的关键字
            if (order.getSendAddress().contains(subArea.getKeyWords())) {
                //找分区,找到定区,找到快递员
                Iterator<Courier> iterator = subArea.getFixedArea().getCouriers().iterator();
                if (iterator.hasNext()) {
                    Courier courier = iterator.next();
                    if (courier != null) {
                    //自动分单成功
                    System.out.println("自动分单成功");
                    //保存订单
                    saveOrder(order,courier);
                    //生成工单发送短息
                    generateWorkBil(order);
                    //return;
                    }
                }
            }
        }
        //进入到人工分单
        order.setOrderType("2");
        orderRepository.save(order);

    }

    @Override
    public Order findByOrderNum(String orderNum) {

        return orderRepository.findByOrderNum(orderNum);
    }

    //生成工单发送短信
    private void generateWorkBil(Order order) {

        //生成工单
        WorkBill workBill = new WorkBill();
        workBill.setType("新");
        workBill.setPickstate("新单");
        workBill.setBuildtime(new Date());
        workBill.setRemark(order.getRemark());
        final String smsNumber = RandomStringUtils.randomNumeric(4);
        workBill.setSmsNumber(smsNumber);  //短信序号
        workBill.setOrder(order);
        workBill.setCourier(order.getCourier());
        workBillRepository.save(workBill);

        //发送短信
        //调用mq服务,发送一条消息
        jmsTemplate.send("bos_sms", new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                MapMessage mapMessage = session.createMapMessage();
                mapMessage.setString("telephone", order.getCourier().getTelephone());
                mapMessage.setString("msg", "短信序号:"+smsNumber+",取件地址:"
                        +order.getSendAddress()
                        +",联系人:"+order.getSendName()+",快递员捎话:"+order.getSendMobileMsg());
                return mapMessage;
            }
        });
        //修改工单的状态
        workBill.setPickstate("已通知");
    }
    //自动分单保存
    private void saveOrder(Order order, Courier courier) {

        //将快递员关联到订单上
        order.setCourier(courier);
        //设置自动分单
        order.setOrderType("1");
        //保存订单
        orderRepository.save(order);
    }
}
