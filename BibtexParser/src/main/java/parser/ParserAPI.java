package parser;

import org.apache.commons.cli.*;
import org.apache.commons.collections4.ListUtils;


/**
 * ParserAPI gives the publc methods that provides and should be used.
 *
 * @author Dawid Majchrowski
 */
public class ParserAPI {
    BibObject bibObject;

    /**
     * Constructor creating bibObject form given path
     *
     * @param path path to given file
     * @throws java.io.FileNotFoundException
     * @throws Exception
     */
    public ParserAPI(String path) throws Exception {
        bibObject = new BibParser().parseBibFile(path);

    }

    public static void main(String[] args) throws Exception {
        CommandLineParser parser = new DefaultParser();
        Options options = new Options();
        options.addOption(Option.builder("p")
                .required(true)
                .hasArg(true)
                .longOpt("path")
                .desc("Path to file")
                .build());
        options.addOption(Option.builder("c")
                .required(false)
                .hasArgs()
                .longOpt("categories")
                .desc("List of categories")
                .build());
        options.addOption(Option.builder("a")
                .required(false)
                .hasArgs()
                .longOpt("authors")
                .desc("List of authors")
                .build());

        try {
            CommandLine line = parser.parse(options, args);
            ParserAPI parserAPI = new ParserAPI(line.getOptionValue("p"));
            if (line.hasOption("a")) {
                String[] authors = line.getOptionValues("a");
                if (line.hasOption("c")) {
                    String[] categories = line.getOptionValues("c");
                    parserAPI.printAllByAUthorsAndCategories(categories, authors);
                } else {
                    parserAPI.printAllByAuthors(authors);
                }
            } else if (line.hasOption("c")) {
                String[] categories = line.getOptionValues("c");
                parserAPI.printAllByCategories(categories);
            } else {
                parserAPI.printAllRecord();
            }

        } catch (ParseException exp) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("javac ParserAPI", options, true);
        }
    }

    /**
     * Shows all Records when no parameters are specified
     */
    public void printAllRecord() {
        System.out.println(bibObject);
    }

    /**
     * Shows all Records when only categories are specified
     *
     * @param categories categories to be found in records
     */
    public void printAllByCategories(String[] categories) {
        for (Record record : bibObject.getRecordByCategory(categories))
            System.out.println(record);
    }

    /**
     * Shows all Records when only authors are specified
     *
     * @param authors authors to be found in records
     */
    public void printAllByAuthors(String[] authors) {
        for (Record record : bibObject.getRecordByAuthors(authors)) {
            System.out.println(record);
        }
    }

    /**
     * Shows all Records when both authors and categories are specified
     *
     * @param authors    categories to be found in records
     * @param categories authors to be found in categories
     */
    public void printAllByAUthorsAndCategories(String[] categories, String[] authors) {
        for (Record record : ListUtils.intersection(bibObject.getRecordByAuthors(authors), (bibObject.getRecordByCategory(categories)))) {
            System.out.println(record);
        }
    }
}
