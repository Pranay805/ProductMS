package com.emerge.emart.product.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.emerge.emart.product.dto.ProductDTO;
import com.emerge.emart.product.entity.Product;
import com.emerge.emart.product.exception.EmergeEmartException;
import com.emerge.emart.product.repository.ProductRepository;
import com.emerge.emart.product.repository.SubscribedProdRepository;

@Service
public class ProductService {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	ProductRepository productrepo;

	@Autowired
	SubscribedProdRepository subscribedprodrepo;

	// Get products by name
	public List<ProductDTO> getProductByName(@PathVariable String productname) throws EmergeEmartException {

		logger.info("Product request with name {}", productname);

		Iterable<Product> product = productrepo.findByproductname(productname);
		List<ProductDTO> productDTO = new ArrayList<ProductDTO>();

		product.forEach(Pro -> {
			productDTO.add(ProductDTO.valueOf(Pro));
		});
		if (productDTO.isEmpty())
			throw new EmergeEmartException("Service.PRODUCTS_NOT_FOUND");
		return productDTO;
	}

	//Get products by category
	public List<ProductDTO> getProductBycategory(@PathVariable String category) throws EmergeEmartException {
		logger.info("Product request of category {}", category);
		Iterable<Product> product = productrepo.findBycategory(category);
		List<ProductDTO> productDTO = new ArrayList<ProductDTO>();

		product.forEach(Pro -> {
			productDTO.add(ProductDTO.valueOf(Pro));
		});
		if (productDTO.isEmpty())
			throw new EmergeEmartException("Service.PRODUCTS_NOT_FOUND");
		return productDTO;
	}

	//Get products by seller id
	public List<ProductDTO> getProductBySellerId(@PathVariable String sellerid) throws EmergeEmartException {
		logger.info("Product request of seller {}", sellerid);
		Iterable<Product> product = productrepo.findBysellerid(sellerid);
		List<ProductDTO> productDTO = new ArrayList<ProductDTO>();

		product.forEach(Pro -> {
			productDTO.add(ProductDTO.valueOf(Pro));
		});
		if (productDTO.isEmpty())
			throw new EmergeEmartException("Service.PRODUCTS_NOT_FOUND");
		return productDTO;
	}

	//Get all product details
	public List<ProductDTO> getAllProduct() throws EmergeEmartException {

		Iterable<Product> products = productrepo.findAll();
		List<ProductDTO> productDTOs = new ArrayList<>();

		products.forEach(product -> {
			ProductDTO productDTO = ProductDTO.valueOf(product);
			productDTOs.add(productDTO);
		});
		if (productDTOs.isEmpty())
			throw new EmergeEmartException("Service.PRODUCTS_NOT_FOUND");
		logger.info("Product Details : {}", productDTOs);
		return productDTOs;
	}

	//Update stock
	public Product updateProductStock(Product product, String prodid) throws EmergeEmartException{
        Product existingProduct = productrepo.findById(prodid).orElse(null);
        if(existingProduct != null) {
            existingProduct.setStock(product.getStock());
            return productrepo.save(existingProduct); 
        }else {
        	throw new EmergeEmartException("Service.PRODUCTS_NOT_FOUND");
        }
    
    }
	
	//Get product by id
	public ProductDTO getByProdid(String prodid) throws EmergeEmartException{
		logger.info("Profile request for customer {}", prodid);
		Product pro = productrepo.findByProdid(prodid);
		if(pro != null) {
			ProductDTO proDTO = ProductDTO.valueOf(pro);
			logger.info("Profile for customer : {}", proDTO);
			return proDTO;
		} else{
			throw new EmergeEmartException("Service.PRODUCTS_NOT_FOUND");
		}
	}
	
	//Add product
	public void saveProduct(ProductDTO productDTO) throws EmergeEmartException {
		logger.info("Adding product with data {}", productDTO);
		Product product = productDTO.createProduct();
		productrepo.save(product);
	}

	//Delete product
	public void deleteProduct(String productid) throws EmergeEmartException {
	if(productid!=null) {
		Optional<Product> product = productrepo.findById(productid);
		//product.orElseThrow(() -> new EmergeEmartException("Service.Seller_NOT_FOUND"));
		productrepo.deleteById(productid);
	}else{
		throw new EmergeEmartException("Service.Seller_NOT_FOUND");
	}
	}

}
