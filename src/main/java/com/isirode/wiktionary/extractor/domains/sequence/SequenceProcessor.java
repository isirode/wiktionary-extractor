package com.isirode.wiktionary.extractor.domains.sequence;

import com.isirode.wiktionary.extractor.domains.sequence.model.Sequence;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class SequenceProcessor {

    @Getter
    Map<String, Sequence> sequenceMap = new HashMap<>();

    public void processWord(String word) {
        StringBuilder seqBuilder = new StringBuilder();
        String seq = "";
        int length = word.length();
        for (int i = 0; i < length; i++) {
            seqBuilder.append(word.charAt(i));
            if (seqBuilder.length() >= 4) {
                seq = StringUtils.stripAccents(seqBuilder.toString()).toUpperCase();
                seqBuilder.deleteCharAt(0);
                if (sequenceMap.containsKey(seq)) {
                    Sequence sequence = sequenceMap.get(seq);
                    sequence.increment();
                } else {
                    sequenceMap.put(seq, new Sequence(seq, 1));
                }
            }
        }
    }

}
