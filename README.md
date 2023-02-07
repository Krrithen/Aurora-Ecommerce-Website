# Aurora Ecommerce Website Documentation

##### MICROSERVICES - PRODUCTS,MERCHANTS,USERS,ORDERS,CART,SEARCH

##### DATABASE - MongoDB

##### Other Configs

##### WORK FLOW

![image](https://user-images.githubusercontent.com/82010731/215069721-a82c6201-ca6d-49ae-8b92-3f53e9a59ad9.png)

1. The user can view the various products and the categories in the home page
2. He can click on a specific product or the category and he is redirected to the target page he selects
3. The user can also check the products by using search which is implemented using Solr
4. In the product page/ the user requested page he can view the products
5. He can chose a product and add it to his cart or buy it directly from the products page
6. For the user to add the products to the cart or buy them he must be a authenticated user. 
7. His authorization is checked using FireBase.
8. If he is an authorized user he will be able to add the products to the cart or buy them.
9. The user can view the products he placed in the cart both from the website as well as the mobile App
10. Once the user buys a product he will be redirected to the checkout page
11. after confirming and placing the order an email is sent to the registered mail id specifing the user's order details and the product quantity is reduced.
