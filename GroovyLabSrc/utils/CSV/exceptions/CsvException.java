
package utils.CSV.exceptions;

/**
 * This is the base class for all exceptions 
 *
 
 */
public class CsvException extends Exception {

    private long lineNumber;

    /**
     * Default constructor, in case no parameters are required.
     */
    public CsvException() {
        lineNumber = -1;
    }

    /**
     * Constructor that allows a human-readable message.
     *
     * @param message The error text
     */
    public CsvException(String message) {
        super(message);
        lineNumber = -1;
    }

    /**
     * @return The line number that caused the error. This should be the
     * one-based number of the line that caused the error, not including the
     * header line, if present.
     */
    public long getLineNumber() {
        return lineNumber;
    }

    /**
     * @param lineNumber The line number that caused the error. This should be the
     *                   one-based number of the line that caused the error, not including the
     *                   header line, if present.
     */
    public void setLineNumber(long lineNumber) {
        this.lineNumber = lineNumber;
    }

}
