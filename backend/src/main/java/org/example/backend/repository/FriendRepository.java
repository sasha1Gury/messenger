package org.example.backend.repository;

import jakarta.transaction.Transactional;
import org.example.backend.model.Friend;
import org.example.backend.model.FriendId;
import org.example.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, FriendId> {

    @Query("SELECT f.friend FROM Friend f WHERE f.user.id = :userId")
    List<User> findFriendsByUserId(@Param("userId") Long userId);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO friends (user_id, friend_id) VALUES (:userId, :friendId) " +
            "ON CONFLICT (user_id, friend_id) DO NOTHING", nativeQuery = true)
    void addFriend(@Param("userId") Long userId, @Param("friendId") Long friendId);

    @Query(value = "SELECT EXISTS (SELECT 1 FROM friends " +
            "WHERE (user_id = :userId AND friend_id = :friendId) " +
            "OR (user_id = :friendId AND friend_id = :userId))", nativeQuery = true)
    boolean areFriends(@Param("userId") Long userId, @Param("friendId") Long friendId);
}