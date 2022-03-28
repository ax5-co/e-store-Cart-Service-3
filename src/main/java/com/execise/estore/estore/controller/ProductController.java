package com.execise.estore.estore.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.execise.estore.estore.entity.Brand;
import com.execise.estore.estore.entity.Category;
import com.execise.estore.estore.entity.Product;
import com.execise.estore.estore.repository.BrandRepository;
import com.execise.estore.estore.repository.CategoryRepository;
import com.execise.estore.estore.repository.ProductRepository;

@RestController
@RequestMapping("api/cat/products")
public class ProductController {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private BrandRepository brandRepository;

	@PostMapping("/test-product")
	public void saveTestProduct() {

		Product product = new Product();
		product.setActive(true);
		product.setNew(false);
		product.setCategory(categoryRepository.findById(1L).get());
		product.setBrand(brandRepository.findById(1L).get());

		System.out.println(product.toString());
		productRepository.save(product);
		/*
		for(Long i = 1L; i< productRepository.count()/2; i++) {
			System.out.println("i= "+i);
			Product product = productRepository.findById(i).get();
			product.setFeatured(true);
			productRepository.save(product);
		}
		*/
	}

	@GetMapping("/")
	public ResponseEntity<Map<String, Object>> retrieveAllProducts(
			@RequestParam(required = false) String title,
			@RequestParam(defaultValue = "0") int page, 
			@RequestParam(defaultValue = "3") int size,
			@RequestParam(defaultValue = "id,asc") String[] sort) {
		try {
			List<Order> sortingOrders = resolveSortingListToOrders(sort);
			List<Product> products = new ArrayList<Product>();
			Pageable pagingSort = PageRequest.of(page, size, Sort.by(sortingOrders));

			Page<Product> pageProducts;

			if (title == null)
				pageProducts = productRepository.findAll(pagingSort);
			else
				pageProducts = productRepository.findByTitleContaining(title, pagingSort);

			products = pageProducts.getContent();

			 if (products.isEmpty()) {
			        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			      }
			 
			Map<String, Object> response = new HashMap<>();
			response.put("products", products);
			response.put("currentPage", pageProducts.getNumber());
			response.put("totalItems", pageProducts.getTotalElements());
			response.put("totalPages", pageProducts.getTotalPages());

			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/new")
	public ResponseEntity<Map<String, Object>> retrieveAllNewProducts(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "3") int size) {
		try {
			List<Product> products = new ArrayList<Product>();
			Pageable paging = PageRequest.of(page, size);

			Page<Product> pageProducts;

			pageProducts = productRepository.findAllByIsNew(true, paging);

			products = pageProducts.getContent();

			Map<String, Object> response = new HashMap<>();
			response.put("products", products);
			response.put("currentPage", pageProducts.getNumber());
			response.put("totalItems", pageProducts.getTotalElements());
			response.put("totalPages", pageProducts.getTotalPages());

			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/featured")
	public ResponseEntity<Map<String, Object>> retrieveAllFeaturedProducts(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "3") int size) {
		try {
			List<Product> products = new ArrayList<Product>();
			Pageable paging = PageRequest.of(page, size);

			Page<Product> pageProducts;

			pageProducts = productRepository.findAllByIsFeatured(true, paging);

			products = pageProducts.getContent();

			Map<String, Object> response = new HashMap<>();
			response.put("products", products);
			response.put("currentPage", pageProducts.getNumber());
			response.put("totalItems", pageProducts.getTotalElements());
			response.put("totalPages", pageProducts.getTotalPages());

			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	@GetMapping("/category-id/{categoryId}")
	public ResponseEntity<Map<String, Object>> retrieveAllProductsByCategoryId(
		@PathVariable Long categoryId,
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "3") int size) {
	try {
		List<Product> products = new ArrayList<Product>();
		Pageable paging = PageRequest.of(page, size);

		Page<Product> pageProducts;

		Category category = categoryRepository.findById(categoryId).get();
		
		pageProducts = productRepository.findAllByCategory(category, paging);

		products = pageProducts.getContent();

		Map<String, Object> response = new HashMap<>();
		response.put("products", products);
		response.put("currentPage", pageProducts.getNumber());
		response.put("totalItems", pageProducts.getTotalElements());
		response.put("totalPages", pageProducts.getTotalPages());

		return new ResponseEntity<>(response, HttpStatus.OK);
	} catch (Exception e) {
		return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	}
		
	@GetMapping("/category-name/{categoryName}")
	public ResponseEntity<Map<String, Object>> retrieveAllProductsByCategoryName(
			@PathVariable String categoryName,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "3") int size) {
		try {
			List<Product> products = new ArrayList<Product>();
			Pageable paging = PageRequest.of(page, size);

			Page<Product> pageProducts;

			Category category = categoryRepository.findTop1ByTitleContaining(categoryName);
			
			pageProducts = productRepository.findAllByCategory(category, paging);

			products = pageProducts.getContent();

			Map<String, Object> response = new HashMap<>();
			response.put("products", products);
			response.put("currentPage", pageProducts.getNumber());
			response.put("totalItems", pageProducts.getTotalElements());
			response.put("totalPages", pageProducts.getTotalPages());

			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		}
	
	@GetMapping("/brand-name/{brandName}")
	public ResponseEntity<Map<String, Object>> retrieveAllProductsByBrandName(
			@PathVariable String brandName,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "3") int size) {
		try {
			List<Product> products = new ArrayList<Product>();
			Pageable paging = PageRequest.of(page, size);

			Page<Product> pageProducts;

			Brand brand = brandRepository.findTop1ByTitleContaining(brandName);
			
			pageProducts = productRepository.findAllByBrand(brand, paging);

			products = pageProducts.getContent();

			Map<String, Object> response = new HashMap<>();
			response.put("products", products);
			response.put("currentPage", pageProducts.getNumber());
			response.put("totalItems", pageProducts.getTotalElements());
			response.put("totalPages", pageProducts.getTotalPages());

			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		}

	
	@GetMapping("/sort-by/price/low")
	public ResponseEntity<Map<String, Object>> retrieveAllProductsSortedAscendingPrice(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "3") int size) {

		try {
			List<Product> products = new ArrayList<Product>();
			Pageable pagingSort = PageRequest.of(page, size, Sort.by("price"));

			Page<Product> pageProducts;
			
			pageProducts = productRepository.findAll(pagingSort);

			products = pageProducts.getContent();

			Map<String, Object> response = new HashMap<>();
			response.put("products", products);
			response.put("currentPage", pageProducts.getNumber());
			response.put("totalItems", pageProducts.getTotalElements());
			response.put("totalPages", pageProducts.getTotalPages());

			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		}
	
	
	@GetMapping("/sort-by/price/high")
	public ResponseEntity<Map<String, Object>> retrieveAllProductsSortedDescendingPrice(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "3") int size) {

		try {
			List<Product> products = new ArrayList<Product>();
			Pageable pagingSort = PageRequest.of(page, size, Sort.by("price").descending());

			Page<Product> pageProducts;
			
			pageProducts = productRepository.findAll(pagingSort);

			products = pageProducts.getContent();

			Map<String, Object> response = new HashMap<>();
			response.put("products", products);
			response.put("currentPage", pageProducts.getNumber());
			response.put("totalItems", pageProducts.getTotalElements());
			response.put("totalPages", pageProducts.getTotalPages());

			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		}
	
	// Return the Product Image from the database using ResponseEntity
		@GetMapping("/{id}/image")
		public ResponseEntity<byte[]> fromDatabaseAsResEntity(@PathVariable("id") Long id) throws SQLException {

			
			Optional<Product> foundProduct = productRepository.findById(id);
			byte[] imageBytes = null;
			if (foundProduct.isPresent()) {

				// imageBytes = foundBarnd.get().getBannerImage().getBytes(1,
				// (int) foundBarnd.get().getBannerImage().length());
				imageBytes = foundProduct.get().getImage();
			}

			return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageBytes);
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
