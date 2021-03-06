# genre-matcher

An incomplete trivial genre matcher for matching genres to books.

## (ATTENTION: This tool is not complete)

Due to time constraints, I have been unable to complete this tool. That is why the output is as a raw
map and not pretty-printed to the console or a file. Additionally, this tool failed to meet a number
of specifications, (i.e., calculate a trivial genre score and list the top three genres associated with
a book). When time permits, I plan to come back and finish/rewrite the parts of this tool that I feel
need more work. Additionally, this tool needs a number of test cases written to ensure the correctness
of the program (e.g., that the top three listed genres for a books are what was expected, the genre
score calculation is correct, etc.). Finally, bear in mind that the current state of this tool was
the result of only three hours of work.

## Installation

Download the ZIP file [here](https://github.com/mccurdyc/genre-matcher/archive/master.zip) or if you are
interested in receiving updates versions of the source code or want to contribute, clone the repository
using the following commands (in the directory where you want the tool to be cloned):

```
$ git clone https://github.com/mccurdyc/genre-matcher.git
```

## Usage

To see the help menu, either run the .jar file with no parameters or run it with the `-h` flag
shown by the following command:

```
$ java -jar matcher.jar -h

  -b, --books BOOKS        * [Required] File containing the JSON with book titles and descriptions.
  -k, --keywords KEYWORDS  * [Required] File containing the genres and keywords.
  -h, --help               Print this help menu
```

To determine the genre for each book in a list of books, provide the list of books as a JSON file --- sample
are shown below --- and a CSV file with the keywords and their respective genre and score.

```
$ java -jar matcher.jar -b path/to/books.json -k path/to/keywords.csv
```

## Examples

In this repository, I have provided a `data/` directory with the necessary sample files for which you can
test this tool. The sample directory contains the following files:

```
genre-matcher
└───data/
  │   genre-keywords.csv
  │   books.json
  ...
```

```
$ cat data/books.json

[{
  "title": "Hunger Games",
  "description": "In a not-too-distant future, the United States of America has collapsed, weakened by
  drought, fire, famine, and war, to be replaced by Panem, a country divided into the Capitol and 12
  districts. Each year, two young representatives from each district are selected by lottery to participate
  in The Hunger Games. Part entertainment, part brutal intimidation of the subjugated districts, the
  televised games are broadcasted throughout Panem as the 24 participants are forced to eliminate their
  competitors, literally, with all citizens required to watch. When 16-year-old Katniss's young sister,
  Prim, is selected as the mining district's female representative, Katniss volunteers to take her place.
  She and her male counterpart, Peeta, the son of the town baker who seems to have all the fighting
  skills of a lump of bread dough, will be pitted against bigger, stronger representatives who have
  trained for this their whole lives. Collins's characters are completely realistic and sympathetic
  as they fight and form alliances and friendships in the face of overwhelming odds; the plot is
  tense, dramatic, and engrossing. This book will definitely resonate with the generation raised on
  reality shows like 'Survivor' and 'American Gladiator.' Book one of a planned trilogy."
},
...
]
```

```
$ cat data/genre-keywords.csv

Genre,Keyword,Points
action,fast paced,7
action,distant future,4
action,fight,6
mystery,murder,5
mystery,death,8
mystery,explosive,4
biography,great man,7
biography,great woman,7
sci-fi,distant future,8
sci-fi,dystopian,7
sci-fi,space,6
literary fiction,subplot,5
literary fiction,cerebral,7
literary fiction,literary,9

```

This is necessary to demonstrate what the output will look like for the sample input.
To identify the genre of a book, given a list of keywords and books, run the following in your shell:

```
$ java -jar matcher.jar -b data/books.json -k data/genre-keywords.csv
```

This will produce the following output:

```
({:title "Infinite Jest", :literary fiction 5} {:title "Hunger Games", :action 6})
```

## Development Environment

The genre-matcher has been created and run on an Arch Linux 4.8.10-1 workstation with Clojure 1.8.0 and the
OpenJDK 1.8.0_112 version of Java. It was also run on an Arch Linux 4.8.10-1 workstation with Oracle's
Java SE Runtime Environment version 1.8.0_102 as well as OpenJDK 1.7.0_111 version of Java. To further ensure
usability, this genre-matcher was run on a Mid-2012 MacBook Pro running MacOS Sierra (Version 10.12.1)
with Oracle's Java SE Runtime Environment version 1.8.0_66. Finally, I ran this tool on a machine running
Ubuntu 16.04 LTS running Oracle's Java SE Runtime Environment version 1.8.0_111. If you are unable to
run this tool with your development tools and your execution environment, then please open a new issue
and I will attempt to resolve your concerns.

## License

Distributed under the GNU GENERAL PUBLIC LICENSE version 3.0.

## Problems or Praise?

If you have any problems with downloading or understanding the tool, then please create an issue associated
with this Git repository using the "Issues" link at the top of this site. As the sole contributor to this
repository, I will do everything possible to resolve your issue and provide help where I can to get the
tool working for you in your in your development environment. If you find that this tool helps you
--- as a point of reference --- then I also encourage you to "star" and "watch" this project! Enjoy!
