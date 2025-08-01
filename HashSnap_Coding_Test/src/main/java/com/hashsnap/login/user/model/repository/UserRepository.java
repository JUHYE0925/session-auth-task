package com.hashsnap.login.user.model.repository;

import com.hashsnap.login.user.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("""
        SELECT u FROM User u WHERE u.userPhone = :userPhone
    """)
    User findByUserPhone(@Param("userPhone") String userPhone);

    @Query("""
        SELECT u FROM User u WHERE u.nickname = :nickname
    """)
    User findByNickname(@Param("nickname") String nickname);

    @Query("""
        SELECT u FROM User u WHERE u.userEmail = :userEmail
    """)
    User findUserEmail(@Param("userEmail") String userEmail);
}
