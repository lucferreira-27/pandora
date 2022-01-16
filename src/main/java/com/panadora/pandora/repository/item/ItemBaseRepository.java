package com.panadora.pandora.repository.item;

import com.panadora.pandora.model.entities.collection.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface ItemBaseRepository<T extends Item> extends JpaRepository<T, Long> {
}
