package com.lzrc.ecommerce.db.repositories.custom;

import java.util.Optional;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lzrc.ecommerce.db.entities.Client;
import com.lzrc.ecommerce.db.repositories.ClientRepository;

import jakarta.persistence.LockModeType;

@Repository
public interface CustomClientRepository extends ClientRepository {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT c FROM Client c WHERE c.cpf = ?1")
    Optional<Client> findByIdWithWriteLock(String cpf);

}
