package com.isirode.wiktionary.extractor.domains;

import de.tudarmstadt.ukp.jwktl.api.IWiktionaryEdition;

import java.sql.Connection;

public interface IProcessor {
    void process(IWiktionaryEdition wkt, Connection connection);
}
