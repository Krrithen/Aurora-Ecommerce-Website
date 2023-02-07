package com.example.Orders.services.impl;

import com.example.Orders.dto.OrderProductsDTO;
import com.example.Orders.dto.OrdersDTO;
import com.example.Orders.entities.OrderHistory;
import com.example.Orders.entities.Orders;
import com.example.Orders.repository.OrderHistoryRepository;
import com.example.Orders.repository.OrdersRepository;
import com.example.Orders.services.OrdersService;
import lombok.ToString;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.print.attribute.standard.DateTimeAtCreation;
import java.time.LocalDateTime;
import java.util.*;

@ToString
@Service
public class OrdersServiceImpl implements OrdersService {

    @Autowired
    OrdersRepository ordersRepository;

    @Autowired
    OrderHistoryRepository orderHistoryRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public Orders addOrdersDetails(Orders order) { //hghjgchfg
//        Orders orders = new Orders();
//        BeanUtils.copyProperties(order,orders);
//        System.out.println(orders);
        Orders temp = ordersRepository.findByUserId(order.getUserId());
        temp.setProductCount(temp.getProductsDTOList().size());
        temp.setTotalPrice(order.getTotalPrice());
        temp.setOrderStatus(order.getOrderStatus());
        temp.setModeOfPayment(order.getModeOfPayment());
        temp.setShippingAddress(order.getShippingAddress());
        OrderHistory orderHistory = new OrderHistory();
        orderHistory.setUserId(temp.getUserId());
        orderHistory.setOrderId(temp.getUserId());
        List<Orders>tempList = new ArrayList<>();
        //List<Orders> tempList
           Optional OH    = orderHistoryRepository.findById(temp.getOrderId());//.getOrders();
        if(OH.isPresent()){
//            Object l = OH.get();
            tempList = orderHistoryRepository.findById(temp.getOrderId()).get().getOrders();
//            System.out.println(l);
        }else{
            orderHistoryRepository.save(orderHistory);

            System.out.println("order history added");
        }
//        if(tempList == null) {
//        System.out.println(tempList);
        tempList.add(temp);
//        System.out.println(temp);
        orderHistory.setOrders(tempList);
        orderHistoryRepository.save(orderHistory);


        return ordersRepository.save(temp);
    }

    @Override
    public List<Orders> getAllOrdersDetails() {
        return ordersRepository.findAll();
    }


    @Override
    public Orders addOrderProducts(List<OrderProductsDTO> orderProductsDTOS, String userId) {
        Orders orders = new Orders();
        orders.setProductsDTOList(orderProductsDTOS);
        orders.setUserId(userId);
        orders.setOrderId(userId);

        orders.setOrderStatus("Payment Pending");
        orders.setDateOfOrder(LocalDateTime.now());
        orders.setShippingAddress("");
        orders.setTotalPrice(0.0);
        orders.setModeOfPayment("");
//        ordersRepository.i
        return ordersRepository.save(orders);

    }

    @Override  // this id for order history nned to change to list
    public Orders getOrdersDetails(String userId) {
        return ordersRepository.findByUserId(userId);
    }

    public void sendEmail(String to, String subject, String body){
                SimpleMailMessage message = new SimpleMailMessage();
                message.setTo(to);
                message.setSubject(subject);
                message.setText(body);

                mailSender.send(message);
    }

    @Override
    public List<Orders> getOrderHistory(String userId) {
        if (orderHistoryRepository.findById(userId).isPresent()) {
            List oh = orderHistoryRepository.findById(userId).get().getOrders();
            Collections.reverse(oh);
            return oh;
        } else {
            return new ArrayList<>();
        }

    }
}




    // Mail Service
//    String to = "";
//    String subject = "";
//    String body = "";
//
//    SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo(to);
//                message.setSubject(subject);
//                message.setText(body);
//
//                mailSender.send(message);