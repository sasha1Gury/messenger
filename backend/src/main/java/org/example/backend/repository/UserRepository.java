package org.example.backend.repository;

import org.example.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByName(String username);
    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.id <> :currentUserId")
    List<User> findAllUsersExceptCurrent(@Param("currentUserId") Long currentUserId);
}