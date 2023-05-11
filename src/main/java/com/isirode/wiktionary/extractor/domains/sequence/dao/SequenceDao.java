package com.isirode.wiktionary.extractor.domains.sequence.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

import java.sql.Connection;
import java.sql.Statement;

import com.isirode.wiktionary.extractor.domains.sequence.model.Sequence;
import lombok.SneakyThrows;

public class SequenceDao {

    @SneakyThrows
    public void createTable(Connection connection) {
        try (Statement statement = connection.createStatement()) {
            statement.setQueryTimeout(30);

            statement.executeUpdate("drop table if exists sequences");

            String createSequencesTable = """
                    create table sequences (
                        id INTEGER PRIMARY KEY,
                        sequence STRING,
                        occurences INTEGER
                    )
                    """;
            statement.executeUpdate(createSequencesTable);
        }
    }

    public void insertSequences(Connection connection, Map<String, Sequence> sequenceMap) {
        try {
            String insertSequencesQuery = "insert into sequences values (null, ?, ?)";
            PreparedStatement insertSequencesStatement= connection.prepareStatement(insertSequencesQuery);
            sequenceMap.forEach((sequence, sequenceObject) -> {
                try {
                    insertSequencesStatement.setString(1, sequence);
                    insertSequencesStatement.setInt(2, sequenceObject.getOccurrences());
                    insertSequencesStatement.executeUpdate();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });

            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
