package com.saurav.journalApp.repository;


import com.saurav.journalApp.entity.JournalEntry;
import com.saurav.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface UserRepository extends MongoRepository<User, ObjectId> {

     User findByUserName(String userName);

     void deleteByUserName(String userName);

}
