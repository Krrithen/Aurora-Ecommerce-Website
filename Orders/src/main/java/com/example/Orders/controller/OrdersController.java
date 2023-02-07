package com.example.Orders.controller;

import com.example.Orders.dto.OrderProductsDTO;
import com.example.Orders.dto.OrdersDTO;
import com.example.Orders.dto.ProductsDTO;
import com.example.Orders.entities.Orders;
import com.example.Orders.feignServices.FeignMailService;
import com.example.Orders.feignServices.FeignOrderService;
import com.example.Orders.feignServices.FeignProductService;
import com.example.Orders.services.OrdersService;
import com.example.Orders.services.impl.OrdersServiceImpl;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.Subject;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;

@RestController
@RequestMapping("/orders")
@Slf4j

public class OrdersController {

    @Autowired
    OrdersService ordersService;

    @Autowired
    FeignOrderService feignOrderService;

    @Autowired
    FeignMailService feignMailService;

    @Autowired
    FeignProductService feignProductService;

    Gson gson = new Gson();

    @PostMapping("/AddOrderDetails")
    public ResponseEntity<String> addOrdersDetails(@RequestBody Orders ordersDTO){
        ordersService.addOrdersDetails(ordersDTO);
        feignOrderService.deleteByCartId(ordersDTO.getUserId());//added now
        String to = feignMailService.getUserMailByUserId(ordersDTO.getUserId()).getBody();

        String name=feignMailService.getUserNameByUserId(ordersDTO.getUserId()).getBody();
        String body="Hey! " +name+ "\n This is a email for your order. \n";

        String subject="Order Summary";
        for (int i = 0; i < ordersService.getOrdersDetails(ordersDTO.getUserId()).getProductsDTOList().size() ; i++) {
            body += ordersService.getOrdersDetails(ordersDTO.getUserId()).getProductsDTOList().get(i).getProductName();
            body += "\n";
            body += ordersService.getOrdersDetails(ordersDTO.getUserId()).getProductsDTOList().get(i).getPrice();
//            body += "\n Quantity: ";
//            body += ordersService.getOrdersDetails(ordersDTO.getUserId()).getProductsDTOList().get(i).getQuantity();
            body += "\n";
        }

        body +="Total Price: " +ordersService.getOrdersDetails(ordersDTO.getUserId()).getTotalPrice()+ " purchased on "
                +ordersService.getOrdersDetails(ordersDTO.getUserId()).getDateOfOrder()+ " shipping to "
                +ordersService.getOrdersDetails(ordersDTO.getUserId()).getShippingAddress()+ " .";
        System.out.println(body);
        ordersService.sendEmail(to,subject,body);
        return new ResponseEntity<>("Order Placed Successfully!!!",HttpStatus.OK);
    }

    @GetMapping("/GetAllDetails") //not required
    public ResponseEntity<List<Orders>> getAllOrderDetails(){
        List<Orders> ordersList=ordersService.getAllOrdersDetails();
        return new ResponseEntity<List<Orders>>(ordersList,HttpStatus.OK);
    }

    @GetMapping("/GetOrderDetails/{userId}")// to display order
    public ResponseEntity<Orders> GetOrderDetails(@PathVariable("userId") String userId) {
        Orders orderList = ordersService.getOrdersDetails(userId);
        return new ResponseEntity<>(orderList, HttpStatus.OK);
    }


    @PostMapping("/AddOrderProducts/{userId}")
    public ResponseEntity<String> addOrderProducts(@PathVariable("userId") String userId){
        List<ProductsDTO> cartList =  feignOrderService.getAllCartProducts(userId).getBody();
//        ObjectMapper mapper = new ObjectMapper();
//        MerchantDTO merchantDTO = mapper.convertValue(response.getBody(), MerchantDTO.class);
        List<OrderProductsDTO>  productsList = new ArrayList<>();
//        ProductsDTO PElement = new ProductsDTO();
        for(int i=0;i<cartList.size();i++){
            OrderProductsDTO OElement = new OrderProductsDTO();
            BeanUtils.copyProperties(cartList.get(i),OElement);
//            System.out.println(cartList.get(i)+""+OElement);
            productsList.add(OElement);
        }
        System.out.println(productsList);
        ordersService.addOrderProducts(productsList,userId);

        return new ResponseEntity<>("Processing Your Order",HttpStatus.OK);
    }

    @GetMapping("/GetAllProductInfo/{userId}") //not required
    public ResponseEntity<List<OrderProductsDTO>> getAllProductInfo(@PathVariable("userId") String userId){
        List<ProductsDTO> cartList =  feignOrderService.getAllCartProducts(userId).getBody();
        List<OrderProductsDTO> orderProductsDTOList = new ArrayList<>();
        for(int i=0;i<cartList.size();i++){
            OrderProductsDTO OElement = new OrderProductsDTO();
            BeanUtils.copyProperties(cartList.get(i),OElement);
            orderProductsDTOList.add(OElement);
        }
        System.out.println(orderProductsDTOList);
        return new ResponseEntity<>(orderProductsDTOList,HttpStatus.OK);
    }


    // Order History
    @GetMapping("/GetOrderHistory/{userId}")
    public ResponseEntity<List<OrderProductsDTO>> getOrderHistory(@PathVariable("userId") String userId){
        List list = ordersService.getOrderHistory(userId);
        return new ResponseEntity<>(list,HttpStatus.OK);
    }

}




//     /orders/AddOrderDetails/{userId}