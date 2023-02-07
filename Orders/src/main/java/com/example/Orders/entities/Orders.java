package com.example.Orders.entities;

import com.example.Orders.dto.OrderProductsDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@ToString
@Document(collection = Orders.COLLECTION_NAME4)
public class Orders {
    public static final String COLLECTION_NAME4 = "Orders";

    @Id
    private String orderId;
    private String userId;
    private List<OrderProductsDTO> productsDTOList;
    private LocalDateTime dateOfOrder;
    private String orderStatus;
    private String shippingAddress;
    private Integer productCount;
    private Double totalPrice;
    private String modeOfPayment;

}