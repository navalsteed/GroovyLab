
package utils.CSV.exceptions;

/**
 * This exception is thrown when logical connections between data fields would
 * be violated by the imported data.
 * <p>This can be for constraints like making certain a number is in a certain
 * range, or it can even be thrown by code using opencsv when constraints
 * outside of opencsv would be violated. An example of the latter is importing
 * into a database when one of the field in the CSV is supposed to contain the
 * primary key for a foreign table, but the foreign key cannot be satisfied.</p>
 * <p>This exception is not currently used by opencsv itself, since opencsv has
 * no concept of what data consistency means in the context of the application
 * using it. It is meant more for custom converters.</p>
 *

 */
public class CsvConstraintViolationException extends CsvException {
    private final Object sourceObject;

    /**
     * Default constructor, in case no further information is necessary or
     * available.
     */
    public CsvConstraintViolationException() {
        sourceObject = null;
    }

    /**
     * Constructor for setting the source object that triggered the constraint
     * violation.
     *
     * @param sourceObject The offending source object
     */
    public CsvConstraintViolationException(Object sourceObject) {
        this.sourceObject = sourceObject;
    }

    /**
     * Constructor with a simple text.
     *
     * @param message Human-readable error text
     */
    public CsvConstraintViolationException(String message) {
        super(message);
        sourceObject = null;
    }

    /**
     * Constructor for setting the source object and an error message.
     *
     * @param sourceObject The offending source object
     * @param message      Human-readable error text
     */
    public CsvConstraintViolationException(Object sourceObject, String message) {
        super(message);
        this.sourceObject = sourceObject;
    }

    /**
     * @return The source object that triggered the constraint violation
     */
    public Object getSourceObject() {
        return sourceObject;
    }
}
