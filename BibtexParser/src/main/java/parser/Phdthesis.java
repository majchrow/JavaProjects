package parser;

import java.util.Map;
import java.util.TreeMap;

/**
 * Concrete implementation on Record.
 * A Ph.D. thesis.
 * Required fields: author, title, school, year
 * Optional fields: type, address, month, note, key
 *
 * @author Dawid Majchrowski
 */
class Phdthesis extends Record {
    private final static RecordType recordType = RecordType.PHDTHESIS;
    private Map<FieldType, String> optionalFields;
    private Map<FieldType, String> mandatoryFields;
    private String quoteKey = null;

    public Map<FieldType, String> getOptionalFields() {

        return optionalFields;
    }

    public Phdthesis setOptionalFields(TreeMap<FieldType, String> optionalFields) {
        this.optionalFields = optionalFields;
        return this;
    }

    public Map<FieldType, String> getMandatoryFields() {

        return mandatoryFields;
    }

    public Phdthesis setMandatoryFields(TreeMap<FieldType, String> mandatoryFields) {
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
