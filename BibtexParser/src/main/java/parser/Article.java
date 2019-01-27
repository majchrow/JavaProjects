package parser;

import java.util.Map;
import java.util.TreeMap;

/**
 * Concrete implementation on Record.
 * An article from a journal or magazine.
 * Required fields: author, title, journal, year
 * Optional fields: volume, number, pages, month, note, key
 *
 * @author Dawid Majchrowski
 */
class Article extends Record {
    private final static RecordType recordType = RecordType.ARTICLE;
    private Map<FieldType, String> optionalFields;
    private Map<FieldType, String> mandatoryFields;
    private String quoteKey = null;

    public Map<FieldType, String> getOptionalFields() {
        return optionalFields;
    }

    public Article setOptionalFields(TreeMap<FieldType, String> optionalFields) {
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

    public Article setMandatoryFields(TreeMap<FieldType, String> mandatoryFields) {
        this.mandatoryFields = mandatoryFields;
        return this;
    }


    @Override
    public String toString() {
        return ChartVisualizer.buildChart(this);
    }

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
