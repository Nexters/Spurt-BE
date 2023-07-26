package com.sirius.spurt.store.repository.database.repository;

import com.sirius.spurt.store.repository.database.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, String> {}
