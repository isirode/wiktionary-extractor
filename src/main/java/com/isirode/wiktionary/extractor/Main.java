package com.isirode.wiktionary.extractor;

import com.isirode.wiktionary.extractor.domains.Configuration;
import com.isirode.wiktionary.extractor.domains.IProcessor;
import com.isirode.wiktionary.extractor.domains.ProcessingType;
import com.isirode.wiktionary.extractor.domains.simple.Processor;
import de.tudarmstadt.ukp.jwktl.JWKTL;
import de.tudarmstadt.ukp.jwktl.api.IWiktionaryEdition;
import de.tudarmstadt.ukp.jwktl.api.IWiktionaryEntry;
import de.tudarmstadt.ukp.jwktl.api.IWiktionaryPage;
import de.tudarmstadt.ukp.jwktl.api.filter.WiktionaryEntryFilter;
import de.tudarmstadt.ukp.jwktl.api.util.Language;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.sql.*;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class Main {

    static ProcessingType processingType = ProcessingType.SimpleDefinitionExample;

    // Info : sizes
    // 169 mo with all entries, simple schema
    // 23 mo with english entries only, simple schema
    // 258 mo with all entries, normalized schema
    // 36 mo with english entries only, normalized schema
    // 67 mo with english entries only, one definition schema
    // 70 mo with english entries only, one definition and an example schema

    // Info : data
    // Some entries are not parsed correctly
    // Need to identify the words, do the test, then fix the parsing
    static String TARGET_DIRECTORY = "db";

    public static void main(String... args) {
        IWiktionaryEdition wkt = null;
        try {
            File wiktionaryDirectory = new File(TARGET_DIRECTORY);
            wkt = JWKTL.openEdition(wiktionaryDirectory);

            Connection connection = DriverManager.getConnection("jdbc:sqlite:sqlite-exports/sample-eng-one-definition-example.db");

            IProcessor processor;

            Configuration configuration = Configuration.builder()
                .allowEnglishWordsOnly(true)
                .build();
            
            switch (processingType) {
                case Simple -> {

                    processor = new Processor(configuration);
                    
                }
                case Normalized -> {

                    processor = new com.isirode.wiktionary.extractor.domains.normalized.Processor(configuration);
                    
                }
                case OneDefinition -> {
                    
                    processor = new com.isirode.wiktionary.extractor.domains.one_definition.Processor(configuration);
                    
                }
                case SimpleDefinitionExample -> {
                    
                    processor = new com.isirode.wiktionary.extractor.domains.simple_definition_example.Processor(configuration);
                    
                }
                default -> throw new IllegalStateException("Unexpected value: " + processingType);
            }

            processor.process(wkt, connection);

        } catch (Throwable err) {
            log.error("An error occurred", err);
        } finally {
            // Close the database connection.
            if (wkt != null) {
                wkt.close();
            }
        }
    }

}
