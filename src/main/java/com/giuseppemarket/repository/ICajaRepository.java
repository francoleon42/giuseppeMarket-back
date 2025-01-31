package com.giuseppemarket.repository;

import com.giuseppemarket.model.Caja;
import com.giuseppemarket.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICajaRepository extends JpaRepository<Caja, Integer> {
}
