package parser;

import java.util.Map;
import java.util.TreeMap;


/**
 * Concrete implementation on Record.
 * A book with an explicit publisher.
 * Required fields: author/editor, title, publisher, year
 * Optional fields: volume, series, address, edition, month, note, key
 *
 * @author Dawid Majchrowski
 */
class Book extends Record {
    private final static RecordType recordType = RecordType.BOOK;
    private Map<FieldType, String> optionalFields;
    private Map<FieldType, String> mandatoryFields;
    private String quoteKey = null;

    public Map<FieldType, String> getOptionalFields() {

        return optionalFields;
    }

    public Book setOptionalFields(TreeMap<FieldType, String> optionalFields) {
        this.optionalFields = optionalFields;
        return this;
    }

    public Map<FieldType, String> getMandatoryFields() {
        return mandatoryFields;
    }

    public Book setMandatoryFields(TreeMap<FieldType, String> mandatoryFields) {
        this.mandatoryFields = mandatoryFields;
        return this;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
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
        for (Map.Entry<FieldType, String> entry : this.getMandatoryFields().entrySet()) {
            if (!entry.getKey().equals(FieldType.AUTHOR) && !entry.getKey().equals(FieldType.EDITOR) && entry.getValue().equals("")) {
                return true;
            }
        }
        return false;
    }

    public RecordType getRecordType() {
        return recordType;
    }
}
