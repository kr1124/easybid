package com.easybid.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    
    /** user_email로 User를 검색하는 메서드*/
    Optional<User> findByUserEmail(String userEmail);
    /** user_name으로 User를 검색하는 메서드*/
    Optional<User> findByUserName(String userName);
    /** user_name과 user_email으로 User를 검색하는 메서드*/
    Optional<User> findByUserNameAndUserEmail(String userName, String userEmail);

    /** user_email이 존재하는지 확인*/
    boolean existsByUserEmail(String userEmail);

}

