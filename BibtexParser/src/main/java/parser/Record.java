package parser;

import java.util.Map;

/**
 * The abstract class is superclass for all Records in package.
 *
 * @author Dawid Majchrowski
 */


public abstract class Record implements Visitable {
    /**
     * Get Map from private mandatory Record field
     *
     * @return Map with mandatories Fields
     */
    abstract Map<FieldType, String> getMandatoryFields();

    /**
     * Get Map from private optional Record field
     *
     * @return Map with optionals Fields
     */
    abstract Map<FieldType, String> getOptionalFields();

    /**
     * Get QuoteKey of concrete record
     *
     * @return QuoteKey of concrete record
     */
    abstract String getQuoteKey();

    /**
     * Set value of quoteKey
     *
     * @param quoteKey quoteKey to set
     */

    abstract void setQuoteKey(String quoteKey);

    /**
     * Get RecordType of concrete record
     *
     * @return RecordType of concrete record
     */
    abstract RecordType getRecordType();

    /**
     * Check if all MandatoryMap contain all values
     *
     * @return True if Map miss necessary value
     */
    abstract boolean checkIfNotCorrect();
}