package parser;

import java.util.Map;
import java.util.TreeMap;

/**
 * Concrete implementation on Record.
 * A part of a book, usually untitled. May be a chapter (or section, etc.) and/or a range of pages.
 * Required fields: author/editor, title, chapter/pages, publisher, year
 * Optional fields: volume/number, series, type, address, edition, month, note, key
 *
 * @author Dawid Majchrowski
 */
class Inbook extends Record {
    private final static RecordType recordType = RecordType.INBOOK;
    private Map<FieldType, String> optionalFields;
    private Map<FieldType, String> mandatoryFields;
    private String quoteKey = null;

    public Map<FieldType, String> getOptionalFields() {

        return optionalFields;
    }

    public Inbook setOptionalFields(TreeMap<FieldType, String> optionalFields) {
        this.optionalFields = optionalFields;
        return this;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public Map<FieldType, String> getMandatoryFields() {

        return mandatoryFields;
    }

    public Inbook setMandatoryFields(TreeMap<FieldType, String> mandatoryFields) {
        this.mandatoryFields = mandatoryFields;
        return this;
    }

    @Override
    public String toString() {
        return ChartVisualizer.buildChart(this);
    }

    @Override
    public String getQuoteKey() {
        return quoteKey;
    }

    public void setQuoteKey(String quoteKey) {
        this.quoteKey = quoteKey;
    }

    @Override
    boolean checkIfNotCorrect() {
        if (mandatoryFields.get(FieldType.AUTHOR).equals("") && mandatoryFields.get(FieldType.EDITOR).equals(""))
            return true;
        if (mandatoryFields.get(FieldType.CHAPTER).equals("") && mandatoryFields.get(FieldType.PAGES).equals(""))
            return true;
        for (Map.Entry<FieldType, String> entry : mandatoryFields.entrySet()) {
            if (entry.getKey() != FieldType.AUTHOR && entry.getKey() != FieldType.EDITOR &&
                    entry.getKey() != FieldType.CHAPTER && entry.getKey() != FieldType.PAGES && entry.getValue().equals("")) {
                return true;
            }
        }
        return false;
    }

    public RecordType getRecordType() {
        return recordType;
    }
}
