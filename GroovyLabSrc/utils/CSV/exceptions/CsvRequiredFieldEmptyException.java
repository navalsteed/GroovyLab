
package utils.CSV.exceptions;

import java.lang.reflect.Field;

/**
 * This exception should be thrown when a field marked as required is empty in
 * the CSV file.
 *
 *
 */
public class CsvRequiredFieldEmptyException extends CsvException {
    private final Class beanClass;
    private final Field destinationField;

    /**
     * Default constructor, in case no further information is necessary.
     */
    public CsvRequiredFieldEmptyException() {
        beanClass = null;
        destinationField = null;
    }

    /**
     * Constructor with a simple text.
     *
     * @param message Human-readable error text
     */
    public CsvRequiredFieldEmptyException(String message) {
        super(message);
        beanClass = null;
        destinationField = null;
    }

    /**
     * Constructor for setting the intended class and field of the target bean.
     * These may not be known in every context.
     *
     * @param beanClass        Class of the destination bean
     * @param destinationField Field of the destination field in the destination bean
     */
    public CsvRequiredFieldEmptyException(Class beanClass, Field destinationField) {
        this.beanClass = beanClass;
        this.destinationField = destinationField;
    }

    /**
     * Constructor for setting the intended class and field of the target bean
     * along with an error message.
     * The class and field may not be known in every context.
     *
     * @param beanClass        Class of the destination bean
     * @param destinationField Field of the destination field in the destination bean
     * @param message          Human-readable error text
     */
    public CsvRequiredFieldEmptyException(Class beanClass, Field destinationField, String message) {
        super(message);
        this.beanClass = beanClass;
        this.destinationField = destinationField;
    }

    /**
     * Gets the class of the bean to which the value was to be assigned.
     *
     * @return The class of the bean to which the destination field belongs
     */
    public Class getBeanClass() {
        return beanClass;
    }

    /**
     * Gets the field from the Reflection API that was to be assigned.
     *
     * @return The destination field that was to receive the empty value
     */
    public Field getDestinationField() {
        return destinationField;
    }
}
