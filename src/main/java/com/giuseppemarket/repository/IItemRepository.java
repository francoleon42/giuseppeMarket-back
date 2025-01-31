package com.giuseppemarket.repository;

import com.giuseppemarket.model.Item;
import com.giuseppemarket.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IItemRepository extends JpaRepository<Item, Integer> {
}
