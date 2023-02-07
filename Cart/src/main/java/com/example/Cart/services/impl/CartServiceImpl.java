package com.example.Cart.services.impl;

import com.example.Cart.dto.ProductsDTO;
import com.example.Cart.entites.Cart;
import com.example.Cart.repository.CartRepository;
import com.example.Cart.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    CartRepository cartRepository;

    @Override
    public Cart addCartDetails(Cart cart,ProductsDTO productsDTO)
    {
        Optional<Cart> temp = cartRepository.findById(cart.getUserId());
        System.out.println(temp);
        productsDTO.setProductQuantity(1);
        List<ProductsDTO> productList;

        if(temp.isPresent())
        {
            Cart tempCart = temp.get();
            System.out.println(tempCart);
            productList = tempCart.getProductsDTOList();

            if(productList == null)
            {
                productList = new ArrayList<>();
            }
            System.out.println(productList);
            System.out.println(productsDTO);
            for(int i=0;i<productList.size();i++)
            {
                System.out.println("Inside For Loop");
                System.out.println(productList.get(i).getProductId());
                System.out.println(productsDTO.getProductId());

                if(productList.get(i).getProductId().equals(productsDTO.getProductId()))
                {
                    System.out.println("Same Product Found");
                    productsDTO.setProductQuantity(productList.get(i).getProductQuantity()+1);
                    productList.remove(i);
                    break;
                }
            }
            productsDTO.setPrice(productsDTO.getPrice() * productsDTO.getProductQuantity());
            System.out.println(productsDTO.getPrice());
        }
        else
            {
            System.out.println("No New Cart Created");
            productList = new ArrayList<>();

        }
        //now added for cart error
        productList.add(productsDTO);
        cart.setProductsDTOList(productList);
        System.out.println(cart);
        return cartRepository.save(cart);
    }


    @Override
    public List<Cart> getAllCartDetails() {
        return cartRepository.findAll();
    }

    public void deleteCartById(String cartId) {
        cartRepository.deleteById(cartId);
    }

    @Override
    public void deleteUserCart(String userId)
    {
        Optional<Cart> userCart  = cartRepository.findById(userId);
        if(userCart.isPresent())
        {
            Cart tempCart = userCart.get();
//            List l = tempCart.getProductsDTOList();
            tempCart.setProductsDTOList(new ArrayList<>());
            cartRepository.save(tempCart);
        }
        else{
            System.out.println("Failed to delete cart");
        }
    }

    @Override
    public List<ProductsDTO> getProductsByUserId(String userId)
    {   //Cart userCart=cartRepository.findByUserId(userId).get(0);   //same
        Cart userCart = new Cart();
        if(cartRepository.findByUserId(userId).size()!=0) {
            userCart = cartRepository.findByUserId(userId).get(0);   // App Crash Due to no user found
        }
        System.out.println(userCart);
        return userCart.getProductsDTOList();
    }

    @Override
    public boolean deleteCartItem(String userId, String productId) {
        //Cart userCart=cartRepository.findByUserId(userId).get(0);

        Cart userCart=cartRepository.findByUserId(userId).get(0);
        List<ProductsDTO> productList= userCart.getProductsDTOList();
        System.out.println("List");
        System.out.println(productList);
        System.out.println(userId);
        System.out.println(productId);

        for(int i=0;i<productList.size();i++)
        {
            if(productList.get(i).getProductId().equals(productId))
            {
                productList.remove(i);
                break;
            }

            else
                {
                    System.out.println("Dint Match");
                }
        }

        userCart.setProductsDTOList(productList);
        cartRepository.save(userCart);
        return true;
    }

    @Override
    public void increaseCartQuantity(String cartId, String productId,double price) {
        System.out.println(cartId);
        System.out.println(productId);
        System.out.println(price);
        Optional cart = cartRepository.findById(cartId);
        if(cart.isPresent()){
            List<ProductsDTO> list = cartRepository.findById(cartId).get().getProductsDTOList();
            for(int i=0;i<list.size();i++){
                if(list.get(i).getProductId().equals(productId)) {
                    System.out.println("increase here");
                    int quant = list.get(i).getProductQuantity();
                    System.out.println(quant);
                    list.get(i).setProductQuantity(quant+1);
                    System.out.println(list.get(i).getProductQuantity());

                    list.get(i).setPrice(price*(list.get(i).getProductQuantity()));
                    System.out.println(list.get(i).getPrice());
                }
            }
            Cart newCart = new Cart();
            newCart.setProductsDTOList(list);
            newCart.setCartId(cartId);
            newCart.setUserId(cartId);

            cartRepository.save(newCart);
        }else {
            System.out.println("Cart not Found");
        }
    }

    @Override
    public void decreaseCartQuantity(String cartId, String productId,double price) {
        System.out.println(cartId);
        System.out.println(productId);
        System.out.println(price);
        Optional cart = cartRepository.findById(cartId);
        if(cart.isPresent()){
            List<ProductsDTO> list = cartRepository.findById(cartId).get().getProductsDTOList();
            for(int i=0;i<list.size();i++){
                if(list.get(i).getProductId().equals(productId)) {
                    System.out.println("decrease here");
                    int quant = list.get(i).getProductQuantity();
                    System.out.println(quant);
                    list.get(i).setProductQuantity(quant-1);
                    System.out.println(list.get(i).getProductQuantity());

                    list.get(i).setPrice((list.get(i).getPrice())-price);
                    System.out.println(list.get(i).getPrice());
                }
            }
            Cart newCart = new Cart();
            newCart.setProductsDTOList(list);
            newCart.setCartId(cartId);
            newCart.setUserId(cartId);

            cartRepository.save(newCart);
        }else {
            System.out.println("Cart not Found");
        }
    }

}

