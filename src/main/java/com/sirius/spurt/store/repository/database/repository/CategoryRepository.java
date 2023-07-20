package com.sirius.spurt.store.repository.database.repository;

import com.sirius.spurt.store.repository.database.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, String> {}
