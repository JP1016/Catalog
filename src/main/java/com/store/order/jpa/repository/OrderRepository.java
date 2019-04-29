package com.store.order.jpa.repository;

import com.store.order.jpa.entity.Catalog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Catalog, Long> {
}
