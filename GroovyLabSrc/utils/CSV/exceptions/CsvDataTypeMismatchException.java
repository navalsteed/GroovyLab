
package utils.CSV.exceptions;

/**
 * This exception should be thrown when the provided string value for conversion
 * cannot be converted to the required type of the destination field.
 *

 */
public class CsvDataTypeMismatchException extends CsvException {
    private final Object sourceObject;
    private final Class destinationClass;

    /**
     * Default constructor, in case no further information is necessary or
     * available.
     */
    public CsvDataTypeMismatchException() {
        sourceObject = null;
        destinationClass = null;
    }

    /**
     * Constructor for setting the data and the class of the intended
     * destination field.
     *
     * @param sourceObject     Object that was to be assigned to the destination field
     * @param destinationClass Class of the destination field
     */
    public CsvDataTypeMismatchException(Object sourceObject, Class destinationClass) {
        this.sourceObject = sourceObject;
        this.destinationClass = destinationClass;
    }

    /**
     * Constructor with a simple text.
     *
     * @param message Human-readable error text
     */
    public CsvDataTypeMismatchException(String message) {
        super(message);
        sourceObject = null;
        destinationClass = null;
    }

    /**
     * Constructor for setting the data and the class of the intended
     * destination field along with an error message.
     *
     * @param sourceObject     Object that was to be assigned to the destination field
     * @param destinationClass Class of the destination field
     * @param message          Human-readable error text
     */
    public CsvDataTypeMismatchException(Object sourceObject, Class destinationClass, String message) {
        super(message);
        this.sourceObject = sourceObject;
        this.destinationClass = destinationClass;
    }

    /**
     * Gets the object that was to be assigned to a field of the wrong type.
     *
     * @return The data that could not be assigned
     */
    public Object getSourceObject() {
        return sourceObject;
    }

    /**
     * Gets the type of the field to which the data were to be assigned.
     *
     * @return The class of the destination field
     */
    public Class getDestinationClass() {
        return destinationClass;
    }
}
