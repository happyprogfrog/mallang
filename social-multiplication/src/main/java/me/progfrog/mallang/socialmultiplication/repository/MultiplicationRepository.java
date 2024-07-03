package me.progfrog.mallang.socialmultiplication.repository;

import me.progfrog.mallang.socialmultiplication.domain.Multiplication;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface MultiplicationRepository extends CrudRepository<Multiplication, Long> {
    Optional<Multiplication> findByFactorAAndFactorB(int factorA, int factorB);
}
