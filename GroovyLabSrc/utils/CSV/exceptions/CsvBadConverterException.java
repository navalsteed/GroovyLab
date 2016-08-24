
package utils.CSV.exceptions;

public class CsvBadConverterException extends RuntimeException {
    private final Class converterClass;

    /**
     * Default constructor, in case no further information is necessary or
     * available.
     */
    public CsvBadConverterException() {
        converterClass = null;
    }

    /**
     * Constructor for specifying the class of the offending converter.
     *
     * @param converterClass The class of the converter that misbehaved
     */
    public CsvBadConverterException(Class converterClass) {
        this.converterClass = converterClass;
    }

    /**
     * Constructor with a simple text.
     *
     * @param message Human-readable error text
     */
    public CsvBadConverterException(String message) {
        super(message);
        converterClass = null;
    }

    /**
     * Constructor for setting the class of the converter and an error message.
     *
     * @param converterClass Class of the converter that misbehaved
     * @param message        Human-readable error text
     */
    public CsvBadConverterException(Class converterClass, String message) {
        super(message);
        this.converterClass = converterClass;
    }

    /**
     * @return The class of the provided custom converter
     */
    public Class getConverterClass() {
        return converterClass;
    }
}
