package com.isirode.wiktionary.extractor.domains;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class Configuration {
    boolean allowEnglishWordsOnly;
}
