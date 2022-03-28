package com.execise.estore.estore.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.execise.estore.estore.entity.Product;
import com.execise.estore.estore.entity.ProductVariant;
import com.execise.estore.estore.repository.ProductRepository;
import com.execise.estore.estore.repository.ProductVariantRepository;

@RestController
@RequestMapping("api/")
public class ProductVariantController {

	@Autowired
	private ProductVariantRepository productvariantRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	// All product variants (all the stock) or a single product variant by its id
	@GetMapping("product-variants")
	public ResponseEntity<Map<String, Object>> retrieveAllProductVariantsByProductId(
			@RequestParam(required = false) Long id,
			@RequestParam(defaultValue = "0") int page, 
			@RequestParam(defaultValue = "3") int size,
			@RequestParam(defaultValue = "id,asc") String[] sort) {
		try {
			List<Order> sortingOrders = resolveSortingListToOrders(sort);
			List<ProductVariant> productVariants = new ArrayList<ProductVariant>();
			Pageable pagingSort = PageRequest.of(page, size, Sort.by(sortingOrders));

			Page<ProductVariant> pageProductVariants;

			
			if (id == null)
				pageProductVariants = productvariantRepository.findAll(pagingSort);
			else
				pageProductVariants = productvariantRepository.findById(id, pagingSort);

			productVariants = pageProductVariants.getContent();

			 if (productVariants.isEmpty()) {
			        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			      }
			 
			Map<String, Object> response = new HashMap<>();
			response.put("productVariants", productVariants);
			response.put("currentPage", pageProductVariants.getNumber());
			response.put("totalItems", pageProductVariants.getTotalElements());
			response.put("totalPages", pageProductVariants.getTotalPages());

			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("product-variants/product/{productId}")
	public ResponseEntity<Map<String, Object>> retrieveAllProductVariantsByProductId(
			@PathVariable Long productId,
			@RequestParam(required = false) String sku,
			@RequestParam(defaultValue = "0") int page, 
			@RequestParam(defaultValue = "3") int size,
			@RequestParam(defaultValue = "id,asc") String[] sort) {
		try {
			List<Order> sortingOrders = resolveSortingListToOrders(sort);
			List<ProductVariant> productVariants = new ArrayList<ProductVariant>();
			Pageable pagingSort = PageRequest.of(page, size, Sort.by(sortingOrders));

			Page<ProductVariant> pageProductVariants;

			Optional<Product> product = productRepository.findById(productId);
			
			if (sku == null)
				pageProductVariants = productvariantRepository.findAllByProduct(product.get(),pagingSort);
			else
				pageProductVariants = productvariantRepository.findAllBySku(sku, pagingSort);

			productVariants = pageProductVariants.getContent();

			 if (productVariants.isEmpty()) {
			        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			      }
			 
			Map<String, Object> response = new HashMap<>();
			response.put("productVariants", productVariants);
			response.put("currentPage", pageProductVariants.getNumber());
			response.put("totalItems", pageProductVariants.getTotalElements());
			response.put("totalPages", pageProductVariants.getTotalPages());

			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//get the product variant by sku. (exact vs. containing)
	
	@GetMapping("product-variants/sku-exact/{sku}")
	public ResponseEntity<Map<String, Object>> retrieveAllProductVariantsBySkuExact(
			@PathVariable String sku,
			@RequestParam(defaultValue = "0") int page, 
			@RequestParam(defaultValue = "3") int size,
			@RequestParam(defaultValue = "id,asc") String[] sort) {
		try {
			List<Order> sortingOrders = resolveSortingListToOrders(sort);
			List<ProductVariant> productVariants = new ArrayList<ProductVariant>();
			Pageable pagingSort = PageRequest.of(page, size, Sort.by(sortingOrders));

			Page<ProductVariant> pageProductVariants;
			
			if (sku == null)
				pageProductVariants = productvariantRepository.findAll(pagingSort);
			else
				pageProductVariants = productvariantRepository.findAllBySku(sku, pagingSort);

			productVariants = pageProductVariants.getContent();

			 if (productVariants.isEmpty()) {
			        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			      }
			 
			Map<String, Object> response = new HashMap<>();
			response.put("productVariants", productVariants);
			response.put("currentPage", pageProductVariants.getNumber());
			response.put("totalItems", pageProductVariants.getTotalElements());
			response.put("totalPages", pageProductVariants.getTotalPages());

			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("product-variants/sku-contains/{sku}")
	public ResponseEntity<Map<String, Object>> retrieveAllProductVariantsbySkuContains(
			@PathVariable String sku,
			@RequestParam(defaultValue = "0") int page, 
			@RequestParam(defaultValue = "3") int size,
			@RequestParam(defaultValue = "id,asc") String[] sort) {
		try {
			List<Order> sortingOrders = resolveSortingListToOrders(sort);
			List<ProductVariant> productVariants = new ArrayList<ProductVariant>();
			Pageable pagingSort = PageRequest.of(page, size, Sort.by(sortingOrders));

			Page<ProductVariant> pageProductVariants;
			
			if (sku == null)
				pageProductVariants = productvariantRepository.findAll(pagingSort);
			else
				pageProductVariants = productvariantRepository.findAllBySkuContaining(sku, pagingSort);

			productVariants = pageProductVariants.getContent();

			 if (productVariants.isEmpty()) {
			        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			      }
			 
			Map<String, Object> response = new HashMap<>();
			response.put("productVariants", productVariants);
			response.put("currentPage", pageProductVariants.getNumber());
			response.put("totalItems", pageProductVariants.getTotalElements());
			response.put("totalPages", pageProductVariants.getTotalPages());

			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	private List<Order> resolveSortingListToOrders(
			@RequestParam(defaultValue = "id,asc") String[] sort){
		List<Order> orders = new ArrayList<Order>();

	      if (sort[0].contains(",")) {
	        // will sort more than 2 fields
	        // sortOrder="field, direction"
	        for (String sortOrder : sort) {
	          String[] _sort = sortOrder.split(",");
	          orders.add(new Order(getSortDirection(_sort[1]), _sort[0]));
	        }
	      } else {
	        // sort=[field, direction]
	        orders.add(new Order(getSortDirection(sort[1]), sort[0]));
	      }
	      return orders;
	}
	
	private Sort.Direction getSortDirection(String direction) {
		//when the mapping accepts: ?sort=column1,direction1&sort=column2,direction2
		//such that each directionX="asc"/"desc"
		//this function translates this directionX into a Sort.Direction.value.
		//which we use in the functions above to determine the sorting direction
		//so a uri like: 
		//http://localhost:8080/e-store/products?size=6&sort=category_id,asc&sort=id,desc
		//will be resolved to identify the orders of: category_id (ascending) then id (descending)
		
	    if (direction.equals("asc")) {
	      return Sort.Direction.ASC;
	    } else if (direction.equals("desc")) {
	      return Sort.Direction.DESC;
	    }

	    return Sort.Direction.ASC;
	  }
}
