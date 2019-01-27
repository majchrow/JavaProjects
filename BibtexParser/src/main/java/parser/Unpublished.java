package parser;

import java.util.Map;
import java.util.TreeMap;

/**
 * Concrete implementation on Record.
 * A document having an author and title, but not formally published.
 * Required fields: author, title, note
 * Optional fields: month, year, key
 *
 * @author Dawid Majchrowski
 */

class Unpublished extends Record {
    private final static RecordType recordType = RecordType.UNPUBLISHED;
    private Map<FieldType, String> optionalFields;
    private Map<FieldType, String> mandatoryFields;
    private String quoteKey = null;

    public Map<FieldType, String> getOptionalFields() {
        return optionalFields;
    }

    public Unpublished setOptionalFields(TreeMap<FieldType, String> optionalFields) {
        this.optionalFields = optionalFields;
        return this;
    }

    public Map<FieldType, String> getMandatoryFields() {
        return mandatoryFields;
    }

    public Unpublished setMandatoryFields(TreeMap<FieldType, String> mandatoryFields) {
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
        return mandatoryFields.values().contains("");
    }

    public RecordType getRecordType() {
        return recordType;
    }
}
