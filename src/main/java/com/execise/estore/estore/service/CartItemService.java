package com.execise.estore.estore.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.execise.estore.estore.dto.CartItemRequest;
import com.execise.estore.estore.dto.CartItemResponse;
import com.execise.estore.estore.entity.Cart;
import com.execise.estore.estore.entity.CartItem;
import com.execise.estore.estore.entity.ProductVariant;
import com.execise.estore.estore.entity.User;

@Service
public interface CartItemService {

	CartItem getCartItemByCartItemUuid(UUID uuid);

	void removeCartItemByCartItemUuid(UUID uuid);

	CartItem updateCartItemQuantityByCartItemUuid(UUID uuid, int newQuantity);

	CartItemResponse addCartItemToCart(CartItemRequest cartItemRequest);

	CartItemResponse addCartItemToCart(String sku, int quantity);

	List<CartItem> getInStockCartItemsByCart(Cart cart);

	CartItemResponse getCartItemResponseByCartItemUuid(UUID uuid);

	Set<CartItem> getCartItemsByCart(Cart cart);


}