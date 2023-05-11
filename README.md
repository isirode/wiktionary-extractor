# wiktionary-extractor

This project contains scripts to export a Wiktionary dump to a SQLite database.

It is not meant to be used as a JAR (for now), but as sandbox, inside an IDE.

You can pick the properties you want to extract, or play with the tool.

I've made some processor for the word-guessing game I am working on, but you can add another, for instance.

The database that you will create will be released with the same licensing as the one of Wiktionary (one of the license below):
- [GNU Free Documentation License (GFDL)](https://www.gnu.org/licenses/fdl-1.3.html)
- [Creative Commons Attribution-ShareAlike License](https://creativecommons.org/licenses/by-sa/3.0/)

You might want to read the [license information](https://dumps.wikimedia.org/legal.html) of the dumps.

## Main features

- Generate the word-guessing game SQLite database

## Installation

This project is using [dkpro-jwktl](https://github.com/dkpro/dkpro-jwktl), which is using [Berkeley DB Java Edition](https://en.wikipedia.org/wiki/Berkeley_DB).

- Download the [Berkeley DB Java Edition](https://www.oracle.com/database/technologies/related/berkeleydb-downloads.html), you will need an Oracle account
  - If you have read the Wikipedia page, you might think that it is under the AGPLv3 license, but the Java Edition is released under Apache License, Version 2.0
  - You want unzip it and to put it inside the [berkeley db java edition](./berkeley%20db%20java%20edition) folder
    - As you can see in the [build.gradle](build.gradle) file
  - If you did not download the same version, you might want to fix the path of the dependency

- Clone my [fork of dkpro-jwktl](https://github.com/isirode/dkpro-jwktl)
  - I did some modifications that you will need
- Do a mvn install

- Download a pages-articles.xml.bz2 dump
  - Go here https://dumps.wikimedia.org/backup-index.html
  - Search for the dump you want ("enwiktionary" for instance)
  - Download the one named "xxx-pages-articles.xml.bz2"
  - Put it in the dump folder of the project

- Open the project in an IDE, use Java 17

- Run the PreprocessingMain.java main
  - It should take a few minutes (to a few hours)
  - You might want to change the value of PATH_TO_DUMP_FILE if you did not use the same dump as me

- You can then use the "normal" [Main](./src/main/java/com/isirode/wiktionary/extractor/Main.java)
  - For now, you can indicate if you want english words only
  - And the format of the database, indicated by the enum 

More information is available [here](https://dkpro.github.io/dkpro-jwktl/documentation/getting-started/).

## Running the project

See above.

## Know issues

There is a few issues with the [dkpro-jwktl](https://github.com/dkpro/dkpro-jwktl) parsing:
- It can happen that the Wiktionary page has defined some text (sense, example etc) but it is not present in the SQLite database
  - The project is probably not quite up-to-date
  - There is issues reported in the project, they are not maintened

## Partipating

Open the [DEVELOPER.md](./DEVELOPER.md) section.

## License

It is provided with the GNU AFFERO GENERAL PUBLIC LICENSE.

wiktionary-extractor is a project which contains scripts to export a Wiktionary dump to a SQLite database.

Copyright (C) 2023  Isirode

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <https://www.gnu.org/licenses/>.
