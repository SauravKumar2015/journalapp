package com.saurav.journalApp.repository;

import com.saurav.journalApp.entity.JournalEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JournalEntryRepository extends JpaRepository<JournalEntry, Long> {

}
