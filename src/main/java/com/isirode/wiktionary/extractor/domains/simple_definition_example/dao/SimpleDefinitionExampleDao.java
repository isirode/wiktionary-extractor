package com.isirode.wiktionary.extractor.domains.simple_definition_example.dao;

import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.Statement;

public class SimpleDefinitionExampleDao {
    @SneakyThrows
    public void createTable(Connection connection) {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("drop table if exists words");
            String createWordTable = """
            create table words (
                id INTEGER PRIMARY KEY,
                word TEXT,
                normalized_word TEXT,
                definition TEXT,
                example TEXT
            )
            """;

            statement.executeUpdate(createWordTable);
        }
    }
}
