package com.execise.estore.estore.mapper;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.execise.estore.estore.dto.CartItemRequest;
import com.execise.estore.estore.dto.CartItemResponse;
import com.execise.estore.estore.entity.Cart;
import com.execise.estore.estore.entity.CartItem;
import com.execise.estore.estore.entity.Product;
import com.execise.estore.estore.entity.ProductVariant;
import com.execise.estore.estore.entity.User;
import com.execise.estore.estore.exception.EStoreException;
import com.execise.estore.estore.repository.ProductRepository;
import com.execise.estore.estore.repository.ProductVariantRepository;
import com.execise.estore.estore.service.implementations.AuthService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class CartItemANDCartItemResponseRequestMapper {

	private final ProductRepository productRepository;
	private final ProductVariantRepository productVariantRepository;

	public Collection<? extends CartItemResponse> mapToResponseList(List<CartItem> content) {
		List<CartItemResponse> response = new ArrayList<>();
		CartItemResponse cartItemResponse = new CartItemResponse();

		for (CartItem cartItem : content) {
			cartItemResponse = new CartItemResponse();
			cartItemResponse.setProductName(cartItem.getProduct().getTitle());
			cartItemResponse.setProductColor(cartItem.getProduct().getColor());
			cartItemResponse.setProdictvariantSize(cartItem.getProductVariant().getSize());
			cartItemResponse.setQuantity(cartItem.getQuantity());
			cartItemResponse.setUnitPrice(cartItem.getUnitPrice());
			cartItemResponse.setUuid(cartItem.getUuid());
			cartItemResponse.setAvailable(cartItem.checkInStock());
			response.add(cartItemResponse);
		}
		return response;
	}

	public CartItemResponse mapToResponse(CartItem cartItem) {

		CartItemResponse cartItemResponse = new CartItemResponse();

		cartItemResponse.setProductName(cartItem.getProduct().getTitle());
		cartItemResponse.setProductColor(cartItem.getProduct().getColor());
		cartItemResponse.setProdictvariantSize(cartItem.getProductVariant().getSize());
		cartItemResponse.setQuantity(cartItem.getQuantity());
		cartItemResponse.setUnitPrice(cartItem.getUnitPrice());
		cartItemResponse.setUuid(cartItem.getUuid());
		cartItemResponse.setAvailable(cartItem.checkInStock());

		return cartItemResponse;
	}

	/*
	 * This method returns a CartItem without an id (auto-generated) and without
	 * checking the stock-availability the checking should be done BEFORE saving
	 * this new item through repo in the calling method (or stack).
	 */
	public CartItem mapToCartItem(CartItemRequest request, User user, Cart cart) {

		String itemTitle = request.getProductName();
		String itemColor = request.getProductColor();
		String itemSize = request.getProductVariantSize();
		int itemQuantity = request.getQuantity();

		Product product = productRepository.findByTitleEqualsAndColorEquals(itemTitle, itemColor)
				.orElseThrow(() -> new EStoreException(
						"Product Not Found with" + " title equals - " + itemTitle + ", color equals - " + itemColor));

		ProductVariant productVariant = productVariantRepository
				.findByProductAndSizeEquals(product, itemSize)
				.orElseThrow(() -> new EStoreException("Product Variant Not Found with" + " Product Title equals - "
						+ itemTitle + " Product Color equals - " + itemColor + " Product Size equals - " + itemSize));

		CartItem item = new CartItem();
		item.setCart(cart);
		item.setCreatedAt(new Date());
		item.setQuantity(itemQuantity);
		item.setProduct(product);
		item.setProductVariant(productVariant);
		item.setSize(itemSize);
		item.setUnitPrice(product.getPrice());
		item.setUser(user);
		item.setUuid(UUID.randomUUID());

		return item;
	}

}
