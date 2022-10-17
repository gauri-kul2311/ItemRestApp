package com.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.model.Item;

@Repository
public interface ItemDao extends JpaRepository<Item, Integer> {
	public Item findByPrice(float price);
	public Item findByItemName(String itemName);
	public int countByItemName(String itemName);
}
