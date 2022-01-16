package com.panadora.pandora.repository.title;

import com.panadora.pandora.model.entities.collection.title.Title;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface TitleBaseRepository<T extends Title> extends JpaRepository<T, Long> {
    public Optional<T> findById(Long id);
}
