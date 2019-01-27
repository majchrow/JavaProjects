package parser;

import java.util.Map;
import java.util.TreeMap;

/**
 * Concrete implementation on Record.
 * A part of a book having its own title.
 * Required fields: author, title, booktitle, publisher, year
 * Optional fields: editor, volume/number, series, type, chapter, pages, address, edition, month, note, key
 *
 * @author Dawid Majchrowski
 */
class Incollection extends Record {
    private final static RecordType recordType = RecordType.INCOLLECTION;
    private Map<FieldType, String> optionalFields;
    private Map<FieldType, String> mandatoryFields;
    private String quoteKey = null;

    public Map<FieldType, String> getOptionalFields() {

        return optionalFields;
    }

    public Incollection setOptionalFields(TreeMap<FieldType, String> optionalFields) {
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

    public Incollection setMandatoryFields(TreeMap<FieldType, String> mandatoryFields) {
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
