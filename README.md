# BSL CSV Reader Command Line Tool

CLI tool for reading .csv records, sorting according to two fields and outputting in the form of .yaml file.
Java 8, JCommander, JacksonCSV, Gradle. Contains Eclipse project files for project import.

## How to run
### See Usage
```bash
# Clone repository
git clone https://github.com/spelexander/BSL-CSVReader-CLI.git

# Enter directory
cd BSL-CSVReader-CLI

# See usage
java -jar BslCsvReader.jar -h
```

### Usage:
Usage: <main class> [options]
  Options:
    -h, -help
      Show usage/help information for tool
      Default: false
    -file
      Input csv file containing entries to be sorted
    -length, -l
      Top -l records to display when sorting
      Default: 3
    -output
      Destination file of sorted entries (defaults to printf)
    -log, -verbose
      Level of verbosity
      Default: 1
    -p, -progress
      Show progress of file processing
      Default: false
    -cache
      Cache entries in memory for later retrieval
      Default: false


### Run with defaults
```bash
# Run on test file with with defaults 
$ java -jar BslCsvReader.jar -file ./test_input.csv -p
```

### Output:
![Output Image](/images/Loading-Bar.png)
