package parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The BibParser class is responsible to parse file into the BibObject class.
 *
 * @author Dawid Majchrowski
 */
class BibParser {
    private RecordFactory recordFactory;

    BibParser() {
        this.recordFactory = new RecordFactory();
    }

    /**
     * Converts path to file to string that contains all bytes from file.
     *
     * @param path path to file
     * @return String with all string file
     * @throws IOException
     */
    String readToString(String path) throws IOException {
        return new String(Files.readAllBytes(Paths.get(path)));
    }

    /**
     * Converts filter String by deleting non record addnotations, also adding StringAddnotations into bibOject
     *
     * @param fileAsString file to be filtered
     * @param bibObject    Object to insert StringAddnotations
     * @return List of String of records starting with @
     */
    List<String> filter(String fileAsString, BibObject bibObject) {
        List<String> parts = Arrays.asList(fileAsString.split("@"));
        List<String> result = new LinkedList<>();
        for (String part : parts) {
            Pattern p = Pattern.compile("\\w+");
            Matcher m = p.matcher(part);
            if (m.find()) {
                String tmp = m.group();
                if (tmp.toLowerCase().equals("string")) {
                    if (m.find()) {
                        String key = m.group();
                        if (m.find()) {
                            bibObject.addToMapAddnotations(key, m.group());
                        }
                    }
                } else if (Arrays.stream(RecordType.values()).anyMatch(x -> x.toString().equals(tmp.toLowerCase()))) {
                    result.add(part);
                }
            }
        }
        return result;
    }


    /**
     * Converts given person names into proper formar using string formatting
     *
     * @param person String with names with inproper format
     * @return String with names in proper format
     */
    String clearString(String person) {
        StringBuilder result = new StringBuilder();
        List<String> parts = Arrays.asList(person.split("and"));
        String prefix = "";
        for (String part : parts) {
            result.append(prefix);
            if (prefix.equals(""))
                prefix = System.getProperty("line.separator");
            if (part.contains("|")) {
                result.append(part.substring(part.indexOf("|") + 1).trim());
                result.append(" ");
                result.append(part.substring(0, part.indexOf("|") - 1).trim());
            } else {
                result.append(part.trim());
            }
        }
        return result.toString();
    }

    /**
     * Convert given file into parsed BibObject
     *
     * @param path path to file
     * @return Converted BibObject
     * @throws Exception
     */
    BibObject parseBibFile(String path) throws Exception {
        BibObject result = new BibObject();
        String fileAsString = readToString(path);
        List<String> parts = filter(fileAsString, result);
        for (String part : parts) {
            Pattern pattern = Pattern.compile("\\w+");
            Matcher matcher = pattern.matcher(part);
            matcher.find();
            RecordType type = RecordType.valueOf(matcher.group().toUpperCase());
            Record curRecord = recordFactory.getRecord(type);
            result.addToMapRecords(type, curRecord);
            String requiredString = part.substring(part.indexOf("{") + 1, part.indexOf("}"));
            StringTokenizer st = new StringTokenizer(requiredString, ",");
            curRecord.setQuoteKey(st.nextToken().trim());
            while (st.hasMoreElements()) {
                String line = st.nextToken();
                StringTokenizer st2 = new StringTokenizer(line, "=");
                String word = st2.nextToken().trim();
                if (word.equals("")) break;
                FieldType fieldType = FieldType.valueOf(word.toUpperCase());
                word = st2.nextToken().trim();
                if (word.indexOf("\"") >= 0) {
                    word = word.substring(1, word.length() - 1);
                } else {
                    if (result.stringToAddnotation(word) != null) {
                        word = result.stringToAddnotation(word);
                    }
                }
                if (fieldType.equals(FieldType.AUTHOR) || fieldType.equals(FieldType.EDITOR)) {
                    word = clearString(word);
                }
                String finalWord = word;
                curRecord.getMandatoryFields().computeIfPresent(fieldType, (k, v) -> finalWord);
                curRecord.getOptionalFields().computeIfPresent(fieldType, (k, v) -> finalWord);
            }
            if (curRecord.checkIfNotCorrect()) {
                System.out.println(curRecord.getRecordType().name() + "(" + curRecord.getQuoteKey() + ")" + " missing mandatory field");
                throw new InputMismatchException();
            }

        }
        return result;
    }

}
