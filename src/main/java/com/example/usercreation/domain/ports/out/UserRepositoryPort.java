package com.example.usercreation.domain.ports.out;

import com.example.usercreation.domain.entities.UserEntity;
import com.example.usercreation.domain.exceptions.DatabaseException;
import java.util.List;
import java.util.Optional;

public interface UserRepositoryPort {

    UserEntity save(UserEntity userEntity) throws DatabaseException;

    Optional<UserEntity> findById(Long id);

    void deleteById(Long id);

    Optional<UserEntity> findByEmail(String email);

    List<UserEntity> findAll();

    UserEntity update(UserEntity userEntity) throws DatabaseException;

    long count();
}
