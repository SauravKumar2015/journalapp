package com.saurav.journalApp.repository;

import com.saurav.journalApp.entity.User;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

@Transactional
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserName(String userName);

    @Modifying
    void deleteByUserName(String userName);

}
