package com.example.usercreation.infrastructure.adapters;

import com.example.usercreation.domain.entities.UserEntity;
import com.example.usercreation.domain.exceptions.DatabaseException;
import com.example.usercreation.domain.exceptions.NotFoundException;
import com.example.usercreation.domain.ports.out.UserRepositoryPort;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import java.util.List;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
public class PostgresUserRepository implements UserRepositoryPort {

    private final EntityManager entityManager;

    @Override
    public UserEntity save(UserEntity userEntity) {
        try {
            entityManager.persist(userEntity);
            return userEntity;
        } catch (PersistenceException e) {
            log.error("Unable to save user to the database", e);
            throw new DatabaseException("Unable to save user to the database", e);
        }
    }

    @Override
    public Optional<UserEntity> findById(Long id) {
        UserEntity userEntity = entityManager.find(UserEntity.class, id);
        return Optional.ofNullable(userEntity);
    }

    @Override
    public void deleteById(Long id) {
        UserEntity userEntity = entityManager.find(UserEntity.class, id);
        if (userEntity == null) {
            throw new NotFoundException("User with id " + id + " not found");
        }
        entityManager.remove(userEntity);
    }

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        List<UserEntity> users = entityManager.createQuery("SELECT u FROM UserEntity u WHERE u.email = :email", UserEntity.class)
                .setParameter("email", email)
                .getResultList();
        return users.stream().findFirst().orElseThrow(() -> new NotFoundException("User with email " + email + " not found"));
    }

    @Override
    public List<UserEntity> findAll() {
        return entityManager.createQuery("SELECT u FROM UserEntity u", UserEntity.class).getResultList();
    }

    @Override
    public UserEntity update(UserEntity userEntity) throws DatabaseException {
        if (findById(userEntity.getId()).isEmpty()) {
            throw new NotFoundException("User with id " + userEntity.getId() + " not found");
        }
        try {
            return entityManager.merge(userEntity);
        } catch (PersistenceException e) {
            log.error("Unable to update user in the database", e);
            throw new DatabaseException("Unable to update user in the database", e);
        }
    }

    @Override
    public long count() {
        return entityManager.createQuery("SELECT COUNT(u) FROM UserEntity u", Long.class).getSingleResult();
    }
}
