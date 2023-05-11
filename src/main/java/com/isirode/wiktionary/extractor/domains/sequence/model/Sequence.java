package com.isirode.wiktionary.extractor.domains.sequence.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class Sequence {
    @Getter
    @Setter
    String sequence;

    @Getter
    @Setter
    int occurrences;

    public void increment() {
        this.occurrences++;
    }
}
