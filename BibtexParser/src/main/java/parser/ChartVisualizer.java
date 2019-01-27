package parser;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Class to visualize Records as a Tables.
 *
 * @author Dawid Majchrowski
 */
class ChartVisualizer {
    private static final String HORIZONTAL_LINE = "-".repeat(150);
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");

    /**
     * Creates a name part of table.
     *
     * @param name      Names of authors or editors.
     * @param fieldType concrete fieldType of given record (author or editor).
     * @return String with part of table connected with authors.
     */
    private static String buildNamePart(String name, final FieldType fieldType) {
        StringBuilder stringBuilder = new StringBuilder();
        List<String> parts = Arrays.asList(name.split(LINE_SEPARATOR));
        String prefix = fieldType.toString();
        for (String part : parts) {
            stringBuilder.append(String.format("| %-24s", prefix));
            if (!prefix.equals(""))
                prefix = "";
            stringBuilder.append(String.format("| %-121s|", part));
            stringBuilder.append(LINE_SEPARATOR);
        }
        stringBuilder.append(HORIZONTAL_LINE);
        stringBuilder.append(LINE_SEPARATOR);
        return stringBuilder.toString();
    }

    /**
     * Creates a given map part of .
     *
     * @param toShow given map with optional/mandatory fields.
     * @return String with part of table connected given map.
     */
    private static String buildMapPart(Map<FieldType, String> toShow) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<FieldType, String> entry : toShow.entrySet()) {
            if (!entry.getValue().equals("")) {
                if (entry.getKey().equals(FieldType.AUTHOR)) {
                    stringBuilder.append(buildNamePart(entry.getValue(), FieldType.AUTHOR));
                } else if (entry.getKey().equals(FieldType.EDITOR)) {
                    stringBuilder.append(buildNamePart(entry.getValue(), FieldType.EDITOR));
                } else {
                    stringBuilder.append(String.format("| %-24s", entry.getKey().toString()));
                    stringBuilder.append(String.format("| %-121s|", entry.getValue()));
                    stringBuilder.append(LINE_SEPARATOR);
                    stringBuilder.append(HORIZONTAL_LINE);
                    stringBuilder.append(LINE_SEPARATOR);
                }
            }
        }
        return stringBuilder.toString();
    }

    /**
     * Creates a full table using private methods buildNamePart and buildMapPart.
     *
     * @return String with full table of given Record.
     */
    public static String buildChart(Record record) {
        if(record == null) return "";
        return HORIZONTAL_LINE +
                LINE_SEPARATOR +
                String.format("|%-148s|", record.getRecordType() +"(" + record.getQuoteKey() + ")") +
                LINE_SEPARATOR +
                HORIZONTAL_LINE +
                LINE_SEPARATOR +
                buildMapPart(record.getMandatoryFields()) +
                buildMapPart(record.getOptionalFields());
    }
}
