# Aurora-Ecommerce-Website

### The Branch includes 5 Microservices :-
```
EcomProject - The Product Microservice
Merchant Microservice
Cart Microservice
Orders Microservice
Users Microservice
```

### DATA MODEL -

##### PRODUCTS:-
```
String productId
String productName
String description
String image
String category
String brand
String merchantId
Double price
Double productQuantity
```

##### MERCHANTS:-
```
String merchantEmail
String merchantId
String merchantName
String merchantProductId
```

##### USERS:-
```
String userAddress
String userEmail
String userId
String userName
String userPassword
String userPhoneNo
```

##### CART:-
```
String cartId
String productId
ProductDTO productsDTOList
String userId
```

##### ORDERS:-
```
localDateAndTime dateOfOrder
String modeOfPayment
String orderId
String orderStatus
Integer productCount
ProductDTO productsDTOList
String shippingAddress
Double totalPrice
String userId
```


### APIs USED - 

##### PRODUCTS:- Port 8080
```
products/addProducts/{} - adds products to the database 
products/deleteProduct/{productId} - deletes products based on its productId
products/GetAllProducts - retrieves all the product details from the database
products/GetProductsByCategory/{category} - retrieves products details based on the category
products/GetProductsByProductId/{productId} - retrieves products details based on the productId
products/GetProductsBySearch/{productName} - retrieves products details based on the productName using Solr Search(Solr Database)
products/reduceQuantity - reduces the quantity of the products when users click on buy now
```

##### MERCHANTS:- Port 8081
```
merchants/addMerchants - adds merchants to the databse
merchants/GetMerchantsByMerchantId/{MerchantId} - retrieves details of the merchants based on their Ids
```

##### CART:- Port 8082
```
/cart/AddCartDetails - adds cart details based on the user ids and products list based on the product ids selected
/cart/GetAllCartProducts/{userId} - retrieves cart details of the users based on their ids
/cart/DeleteByCartId/{userId} -
/cart/DeleteByCartId/{userId}/{productId} -
/cart/DeleteCartItems/{userId} - delete the user's cart items taking his userId as reference 
```

##### ORDERS:- Port 8083
```
/orders/AddOrderDetails - adds details of the orders from cart to database when the user places the order
/orders/AddOrderProducts/{userId} - adds the products purchased by the user based on his userId
/orders/GetAllDetails - retrieves the details of the order database
/orders/GetAllProductInfo/{userId} - get the products information of the specific user
/orders/GetOrderDetails/{userId} - get the order details based on the user id
```

##### USERS:- Port 8084
```
/users/AddUserDetails - checks the users authentication with FireBase and adds it(user details) to the database
/users/GetAllUserDetailsById/{userId} - gets the user details based on their user Id
/users/GetAllUserDetailsByName/{userName} - gets the user details based on their username
/users/GetMailByUserId/{userId} - gets the user emails based on their user Id for sending emails after purchasing
```
