
package utils.CSV;


import utils.CSV.enums.CSVReaderNullFieldIndicator;

import java.io.Reader;

/**
 * Builder for creating a CSVReader.
 * <p>This should be the preferred method of creating a Reader as there are so many
 * possible values to be set it is impossible to have constructors for all of
 * them and keep backwards compatibility with previous constructors.<br>
 *
 * <code>
 * final CSVParser parser =<br>
 * new CSVParserBuilder()<br>
 * .withSeparator('\t')<br>
 * .withIgnoreQuotations(true)<br>
 * .build();<br>
 * final CSVReader reader =<br>
 * new CSVReaderBuilder(new StringReader(csv))<br>
 * .withSkipLines(1)<br>
 * .withCSVParser(parser)<br>
 * .build();<br>
 * </code></p>
 *
 * @see com.opencsv.CSVReader
 */
public class CSVReaderBuilder {

    private final CSVParserBuilder parserBuilder = new CSVParserBuilder();
    private final Reader reader;
    private int skipLines = CSVReader.DEFAULT_SKIP_LINES;
    /*@Nullable*/private CSVParser csvParser = null;
    private boolean keepCR;
    private boolean verifyReader = CSVReader.DEFAULT_VERIFY_READER;
    private CSVReaderNullFieldIndicator nullFieldIndicator = CSVReaderNullFieldIndicator.NEITHER;

   /**
    * Sets the reader to an underlying CSV source.
    *
    * @param reader The reader to an underlying CSV source.
    */
   public CSVReaderBuilder(
         final Reader reader) {
      if (reader == null) {
         throw new IllegalArgumentException("Reader may not be null");
      }
      this.reader = reader;
   }

    /**
     * Used by unit tests.
     *
     * @return The reader.
     */
    protected Reader getReader() {
        return reader;
    }

    /**
     * Used by unit tests.
     *
     * @return The set number of lines to skip
     */
    protected int getSkipLines() {
        return skipLines;
    }

    /**
     * Used by unit tests.
     *
     * @return The CSVParser used by the builder.
     */
    protected CSVParser getCsvParser() {
        return csvParser;
    }

    /**
     * Sets the number of lines to skip before reading.
     *
     * @param skipLines The number of lines to skip before reading.
     * @return The CSVReaderBuilder with skipLines set.
    */
    public CSVReaderBuilder withSkipLines(
         final int skipLines) {
      this.skipLines = (skipLines <= 0 ? 0 : skipLines);
      return this;
   }


    /**
     * Sets the parser to use to parse the input.
     *
     * @param csvParser The parser to use to parse the input.
     * @return The CSVReaderBuilder with the CSVParser set.
    */
    public CSVReaderBuilder withCSVParser(
         final /*@Nullable*/ CSVParser csvParser) {
      this.csvParser = csvParser;
       return this;
   }


    /**
     * Creates the CSVReader.
     * @return The CSVReader based on the set criteria.
     */
    public CSVReader build() {
      final CSVParser parser =
              (csvParser != null ? csvParser : parserBuilder.withFieldAsNull(nullFieldIndicator).build());
       return new CSVReader(reader, skipLines, parser, keepCR, verifyReader);
   }

    /**
     * Sets if the reader will keep or discard carriage returns.
     *
     * @param keepCR True to keep carriage returns, false to discard.
     * @return The CSVReaderBuilder based on the set criteria.
     */
    public CSVReaderBuilder withKeepCarriageReturn(boolean keepCR) {
        this.keepCR = keepCR;
        return this;
    }

    /**
     * Returns if the reader built will keep or discard carriage returns.
     *
     * @return True if the reader built will keep carriage returns, false otherwise.
     */
    protected boolean keepCarriageReturn() {
        return this.keepCR;
    }

    /**
     * Checks to see if the CSVReader should verify the reader state before
     * reads or not.
     *
     * <p>This should be set to false if you are using some form of asynchronous
     * reader (like readers created by the java.nio.* classes).</p>
     *
     * <p>The default value is true.</p>
     *
     * @param verifyReader True if CSVReader should verify reader before each read, false otherwise.
     * @return The CSVReaderBuilder based on this criteria.
     */
    public CSVReaderBuilder withVerifyReader(boolean verifyReader) {
        this.verifyReader = verifyReader;
        return this;
    }

    /**
     * Checks to see if it should treat a field with two separators, two quotes, or both as a null field.
     *
     * @param indicator CSVReaderNullFieldIndicator set to what should be considered a null field.
     * @return The CSVReaderBuilder based on this criteria.
     */
    public CSVReaderBuilder withFieldAsNull(CSVReaderNullFieldIndicator indicator) {
        this.nullFieldIndicator = indicator;
        return this;
    }
}