package org.example.grad_gateway2.DAO;

import org.example.grad_gateway2.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    <Optional>User findByEmail(String email);

    boolean existsByEmail(String email);
}
