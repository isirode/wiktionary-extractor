package com.isirode.wiktionary.extractor.domains.one_definition.dao;

import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.Statement;

public class OneDefinitionDao {
    @SneakyThrows
    public void createTable(Connection connection) {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("drop table if exists words");
            String createWordTable = """
            create table words (
                id INTEGER PRIMARY KEY,
                word TEXT,
                normalized_word TEXT,
                definition TEXT
            )
            """;

            statement.executeUpdate(createWordTable);
        }
    }
}
