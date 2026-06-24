package com.saurav.journalApp.repository;

import com.saurav.journalApp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserName(String userName);

    void deleteByUserName(String userName);

}
