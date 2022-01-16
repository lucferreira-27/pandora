package com.panadora.pandora.repository;

import com.panadora.pandora.model.entities.collection.Collection;
import javafx.print.Collation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollectionRepository extends JpaRepository<Collection,Long> {
}
