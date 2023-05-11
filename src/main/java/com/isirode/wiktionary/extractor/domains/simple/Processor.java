package com.isirode.wiktionary.extractor.domains.simple;

import com.isirode.wiktionary.extractor.domains.Configuration;
import com.isirode.wiktionary.extractor.domains.IProcessor;
import com.isirode.wiktionary.extractor.domains.sequence.SequenceProcessor;
import com.isirode.wiktionary.extractor.domains.sequence.dao.SequenceDao;
import com.isirode.wiktionary.extractor.domains.simple.dao.SimpleWordDao;
import de.tudarmstadt.ukp.jwktl.api.IWiktionaryEdition;
import de.tudarmstadt.ukp.jwktl.api.IWiktionaryEntry;
import de.tudarmstadt.ukp.jwktl.api.IWiktionaryPage;
import de.tudarmstadt.ukp.jwktl.api.filter.WiktionaryEntryFilter;
import de.tudarmstadt.ukp.jwktl.api.util.ILanguage;
import de.tudarmstadt.ukp.jwktl.api.util.Language;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

@RequiredArgsConstructor
@Slf4j
public class Processor implements IProcessor {

    final Configuration configuration;

    @SneakyThrows
    @Override
    public void process(IWiktionaryEdition wkt, Connection connection) {

        SequenceDao sequenceDao = new SequenceDao();
        sequenceDao.createTable(connection);

        SimpleWordDao simpleWordDao = new SimpleWordDao();
        simpleWordDao.createTable(connection);

        String preparedQuery = "insert into words values (null, ?)";

        connection.setAutoCommit(false);

        SequenceProcessor sequenceProcessor = new SequenceProcessor();

        try (PreparedStatement ps = connection.prepareStatement(preparedQuery)) {
            int pageCount = 0;

            WiktionaryEntryFilter filter = new WiktionaryEntryFilter();
            // filtering english for an english dump does nothing
            // 178mo for word only
            filter.setAllowedWordLanguages(Language.ENGLISH);
            // Info : this seems to be doing nothing
            // filter.setAllowedEntryLanguages(Language.ENGLISH);

            for (IWiktionaryPage page : wkt.getAllPages(filter)) {
                String word = page.getTitle();

                boolean hasEnglishEntry = false;

                for (IWiktionaryEntry entry : page.getEntries()) {
                    ILanguage language = entry.getWordLanguage();
                    // FIXME : some of the entries do not have a language setted
                    // Checkout where the language is parsed from, try to fix it
//                    if (language == null) {
//                        log.info("Language is null, word: {}, key: {}", word, entry.getKey());
//                    }
                    if (language != null) {
                        String languageAsString = language.getISO639_3();
                        if (Objects.equals(languageAsString, "eng")) {
                            hasEnglishEntry = true;
                            break;
                        }
                    }
                }
                // Info : lots of the words are foreign languages words
//                if (!hasEnglishEntry) {
//                    log.info("Do not have an english entry : {}", word);
//                }
                if (configuration.isAllowEnglishWordsOnly() && !hasEnglishEntry) {
                    continue;
                }

                ps.setString(1, word);
                ps.executeUpdate();

                sequenceProcessor.processWord(word);

                pageCount++;

                if (pageCount % 100000 == 0) {
                    log.info("Progress: {} pages inserted, {} sequences found", pageCount, sequenceProcessor.getSequenceMap().size());
                }
            }

            log.info("Found {} sequences", sequenceProcessor.getSequenceMap().size());

            sequenceDao.insertSequences(connection, sequenceProcessor.getSequenceMap());
        }

    }
}
