package parser;

import org.apache.commons.collections4.ListUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * The BibObject class represent one parsed file ends with .bib
 *
 * @author Dawid Majchrowski
 */
class BibObject {
    private Map<RecordType, List<Record>> records;
    private Map<String, String> stringAddnotations;

    /**
     * Basic constructor constructing Map that represent Records objects
     */
    BibObject() {
        records = new TreeMap<RecordType, List<Record>>();
        stringAddnotations = new HashMap<>();
        for (RecordType recordType : RecordType.values()) {
            records.put(recordType, new LinkedList<Record>());
        }
    }

    /**
     * adding {key,value} into the stringAddnotations map
     *
     * @param key   key to be added
     * @param value value to be added
     */
    public void addToMapAddnotations(String key, String value) {
        stringAddnotations.put(key, value);
    }

    /**
     * Adding record to the list of thier type by using records map.
     *
     * @param record     record to be added
     * @param recordType key to the map to return proper list
     */
    public void addToMapRecords(RecordType recordType, Record record) {
        records.get(recordType).add(record);
    }

    /**
     * Get value from stringAddnotation map by given key
     *
     * @param key key to stringAddnotation map
     * @return value of given key from stringAddnotation map
     */
    public String stringToAddnotation(String key) {
        return stringAddnotations.get(key);
    }

    /**
     * toString method returning table of every Record in file by using toString Record method on every Record.
     *
     * @return String of builded tables
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        records.values().stream().
                forEach( list -> {
                    stringBuilder.append(
                            list.stream()
                                    .map(Object::toString)
                                    .collect(Collectors.joining()
                            ));
                });
        return stringBuilder.toString();
    }

    /**
     * getter that returns private records Map
     *
     * @return records Map
     */
    public Map<RecordType, List<Record>> getRecords() {
        return records;
    }

    /**
     * getter that returns priavte stringAddnotations Map
     *
     * @return stringAddnotations Map
     */
    public Map<String, String> getStringAddnotations() {
        return stringAddnotations;
    }

    /**
     * filter categoreis to return list of records by using union on list of given categories
     *
     * @param categories categories to be found
     * @return list of records from given categories
     */
    List<Record> getRecordByCategory(String[] categories) {
        List<RecordType> fieldCategories = Arrays.stream(categories)
                .map(string -> RecordType.valueOf(string.toUpperCase()))
                .collect(Collectors.toList());
        List<Record> result = new LinkedList<>();
        for (RecordType type : fieldCategories) {
            result = ListUtils.union(result, this.getRecords().get(type));
        }
        return result;

    }

    /**
     * filter record by given authors by using AuthorVisitor
     *
     * @param authors authors to be found
     * @return list of records from given authors
     */
    List<Record> getRecordByAuthors(String[] authors) {
        AuthorVisitor authorVisitor = new AuthorVisitor(authors);
        for (List<Record> list : records.values()) {
            for (Record record : list) {
                authorVisitor.visit(record);
            }
        }

        return authorVisitor.getRecordTypes();
    }

}
