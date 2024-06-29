package me.progfrog.mallang.repository;

import jakarta.persistence.LockModeType;
import me.progfrog.mallang.domain.Multiplication;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface MultiplicationRepository extends CrudRepository<Multiplication, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Multiplication> findByFactorAAndFactorB(int factorA, int factorB);
}
