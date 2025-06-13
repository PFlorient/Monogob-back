package com.example.monoGoblin.repository;

import com.example.monoGoblin.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserModel, UUID> {
    public Optional<UserModel> findByUsername(String username);

    public Optional<UserModel> findByEmail(String email);


    public Optional<UserModel> findByEmailOrUsername(String email, String username);

    public Boolean existsByUsername(String username);

    public Boolean existsByEmail(String email);

}
