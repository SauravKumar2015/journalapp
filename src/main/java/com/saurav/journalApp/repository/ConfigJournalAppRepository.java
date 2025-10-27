package com.saurav.journalApp.repository;


import com.saurav.journalApp.entity.ConfigJournalAppEntity;
import com.saurav.journalApp.entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface ConfigJournalAppRepository extends MongoRepository<ConfigJournalAppEntity, ObjectId> {

}
