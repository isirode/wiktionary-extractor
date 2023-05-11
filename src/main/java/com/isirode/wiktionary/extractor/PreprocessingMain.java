package com.isirode.wiktionary.extractor;

import de.tudarmstadt.ukp.jwktl.JWKTL;

import java.io.File;

public class PreprocessingMain {

    static String PATH_TO_DUMP_FILE = "dump\\enwiktionary-20230501-pages-articles.xml.bz2";
    static String TARGET_DIRECTORY = "db";
    static boolean OVERWRITE_EXISTING_FILES = false;

    public static void main(String[] args) {
        File dumpFile = new File(PATH_TO_DUMP_FILE);
        File outputDirectory = new File(TARGET_DIRECTORY);
        boolean overwriteExisting = OVERWRITE_EXISTING_FILES;

        JWKTL.parseWiktionaryDump(dumpFile, outputDirectory, overwriteExisting);
    }

}
