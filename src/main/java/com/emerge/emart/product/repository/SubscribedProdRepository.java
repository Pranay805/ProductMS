package com.emerge.emart.product.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emerge.emart.product.entity.SubscribedProd;

	public interface SubscribedProdRepository extends JpaRepository<SubscribedProd, String>{
		List<SubscribedProd> findByBuyerid(String buyerid);
	}

