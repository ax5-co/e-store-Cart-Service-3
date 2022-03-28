package com.execise.estore.estore.mapper;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.execise.estore.estore.dto.OrderItemPreview;
import com.execise.estore.estore.entity.CartItem;
import com.execise.estore.estore.exception.EStoreException;

@Component
public class CartItemOrderItemPreviewMapper {
	
	public OrderItemPreview mapOrderToPreview(CartItem cartItem) {

		try {
			String productName = cartItem.getProduct().getTitle();
			String productColor = cartItem.getProduct().getColor();
			String productVariantSize = cartItem.getProductVariant().getSize();
			BigDecimal productUnitPrice = cartItem.getUnitPrice();
			int quantity = cartItem.getQuantity();
			BigDecimal itemTotal = productUnitPrice.multiply(BigDecimal.valueOf(quantity));
			Date createdAt =Date.from(Instant.now());
			boolean inStock = cartItem.checkInStock();
			OrderItemPreview preview = new OrderItemPreview(productName,
					productColor, productVariantSize, productUnitPrice, 
					quantity, itemTotal, createdAt, inStock);
			
			return preview;
		} catch (Exception e) {
			throw new EStoreException("Mapping CartItem id - "+cartItem.getId() 
			+" to and OrderPreview Failed!");
		}
		
		
	}

}
