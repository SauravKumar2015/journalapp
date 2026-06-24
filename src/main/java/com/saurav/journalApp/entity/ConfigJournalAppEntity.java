package com.saurav.journalApp.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "config_journal_app")
@Data
@NoArgsConstructor
public class ConfigJournalAppEntity {

    @Id
    @Column(name = "config_key", nullable = false, unique = true)
    private String key;

    private String value;
}
