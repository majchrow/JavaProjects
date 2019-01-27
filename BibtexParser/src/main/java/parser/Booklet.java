package parser;

import java.util.Map;
import java.util.TreeMap;

/**
 * Concrete implementation on Record.
 * A work that is printed and bound, but without a named publisher or sponsoring institution.
 * Required fields: title
 * Optional fields: author, howpublished, address, month, year, note, key
 *
 * @author Dawid Majchrowski
 */
class Booklet extends Record {
    private final static RecordType recordType = RecordType.BOOKLET;
    private Map<FieldType, String> optionalFields;
    private Map<FieldType, String> mandatoryFields;
    private String quoteKey = null;

    public Map<FieldType, String> getOptionalFields() {

        return optionalFields;
    }

    public Booklet setOptionalFields(TreeMap<FieldType, String> optionalFields) {
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

    public Booklet setMandatoryFields(TreeMap<FieldType, String> mandatoryFields) {
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
        return mandatoryFields.values().contains("");
    }

    public RecordType getRecordType() {
        return recordType;
    }
}
