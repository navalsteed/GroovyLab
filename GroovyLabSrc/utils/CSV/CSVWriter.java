package utils.CSV;


import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class CSVWriter implements Closeable, Flushable {

   public static final int INITIAL_STRING_SIZE = 1024;
   /**
    * The character used for escaping quotes.
    */
   public static final char DEFAULT_ESCAPE_CHARACTER = '"';
   /**
    * The default separator to use if none is supplied to the constructor.
    */
   public static final char DEFAULT_SEPARATOR = ',';
   /**
    * The default quote character to use if none is supplied to the
    * constructor.
    */
   public static final char DEFAULT_QUOTE_CHARACTER = '"';
   /**
    * The quote constant to use when you wish to suppress all quoting.
    */
   public static final char NO_QUOTE_CHARACTER = '\u0000';
   /**
    * The escape constant to use when you wish to suppress all escaping.
    */
   public static final char NO_ESCAPE_CHARACTER = '\u0000';
   /**
    * Default line terminator.
    */
   public static final String DEFAULT_LINE_END = "\n";
   /**
    * RFC 4180 compliant line terminator.
    */
   public static final String RFC4180_LINE_END = "\r\n";

   protected final Writer writer;
   protected final char separator;
   protected final char quotechar;
   protected final char escapechar;
   protected String lineEnd;
    protected volatile IOException exception;

   /**
    * Constructs CSVWriter using a comma for the separator.
    *
    * @param writer The writer to an underlying CSV source.
    */
   public CSVWriter(Writer writer) {
      this(writer, DEFAULT_SEPARATOR);
   }

   /**
    * Constructs CSVWriter with supplied separator.
    *
    * @param writer    The writer to an underlying CSV source.
    * @param separator The delimiter to use for separating entries.
    */
   public CSVWriter(Writer writer, char separator) {
      this(writer, separator, DEFAULT_QUOTE_CHARACTER);
   }

   /**
    * Constructs CSVWriter with supplied separator and quote char.
    *
    * @param writer    The writer to an underlying CSV source.
    * @param separator The delimiter to use for separating entries
    * @param quotechar The character to use for quoted elements
    */
   public CSVWriter(Writer writer, char separator, char quotechar) {
      this(writer, separator, quotechar, DEFAULT_ESCAPE_CHARACTER);
   }

   /**
    * Constructs CSVWriter with supplied separator and quote char.
    *
    * @param writer     The writer to an underlying CSV source.
    * @param separator  The delimiter to use for separating entries
    * @param quotechar  The character to use for quoted elements
    * @param escapechar The character to use for escaping quotechars or escapechars
    */
   public CSVWriter(Writer writer, char separator, char quotechar, char escapechar) {
      this(writer, separator, quotechar, escapechar, DEFAULT_LINE_END);
   }


   /**
    * Constructs CSVWriter with supplied separator and quote char.
    *
    * @param writer    The writer to an underlying CSV source.
    * @param separator The delimiter to use for separating entries
    * @param quotechar The character to use for quoted elements
    * @param lineEnd   The line feed terminator to use
    */
   public CSVWriter(Writer writer, char separator, char quotechar, String lineEnd) {
      this(writer, separator, quotechar, DEFAULT_ESCAPE_CHARACTER, lineEnd);
   }


   /**
    * Constructs CSVWriter with supplied separator, quote char, escape char and line ending.
    *
    * @param writer     The writer to an underlying CSV source.
    * @param separator  The delimiter to use for separating entries
    * @param quotechar  The character to use for quoted elements
    * @param escapechar The character to use for escaping quotechars or escapechars
    * @param lineEnd    The line feed terminator to use
    */
   public CSVWriter(Writer writer, char separator, char quotechar, char escapechar, String lineEnd) {
      this.writer = writer;
      this.separator = separator;
      this.quotechar = quotechar;
      this.escapechar = escapechar;
      this.lineEnd = lineEnd;
   }

   /**
    * Writes iterable to a CSV file. The list is assumed to be a String[]
    *
    * @param allLines         an Iterable of String[], with each String[] representing a line of
    *                         the file.
    * @param applyQuotesToAll true if all values are to be quoted.  false if quotes only
    *                         to be applied to values which contain the separator, escape,
    *                         quote or new line characters.
    */
   public void writeAll(Iterable<String[]> allLines, boolean applyQuotesToAll) {
      StringBuilder sb = new StringBuilder(INITIAL_STRING_SIZE);
      try {
         for (String[] line : allLines) {
            writeNext(line, applyQuotesToAll, sb);
            sb.setLength(0);
         }
      } catch (IOException e){
         exception = e;
      }
   }

   /**
    * Writes the entire list to a CSV file.
    * The list is assumed to be a String[].
    *
    * @param allLines         A List of String[] with each String[] representing a line of
    *                         the file.
    * @param applyQuotesToAll True if all values are to be quoted. False if quotes only
    *                         to be applied to values which contain the separator, escape,
    *                         quote, or new line characters.
    */
   public void writeAll(List<String[]> allLines, boolean applyQuotesToAll) {
      writeAll((Iterable<String[]>)allLines, applyQuotesToAll);
   }

   /**
    * Writes iterable to a CSV file. The list is assumed to be a String[]
    *
    * @param allLines an Iterable of String[], with each String[] representing a line of
    *                 the file.
    */
   public void writeAll(Iterable<String[]> allLines) {
      StringBuilder sb = new StringBuilder(INITIAL_STRING_SIZE);
      try {
         for (String[] line : allLines) {
            writeNext(line, true, sb);
            sb.setLength(0);
         }
      } catch (IOException e){
         exception = e;
      }
   }

   /**
    * Writes the entire list to a CSV file.
    * The list is assumed to be a String[].
    *
    * @param allLines A List of String[] with each String[] representing a line of
    *                 the file.
    */
   public void writeAll(List<String[]> allLines) {
      writeAll((Iterable<String[]>)allLines);
   }



   /**
    * Writes the next line to the file.
    *
    * @param nextLine         A string array with each comma-separated element as a separate
    *                         entry.
    * @param applyQuotesToAll True if all values are to be quoted. False applies quotes only
    *                         to values which contain the separator, escape, quote, or new line characters.
    */
   public void writeNext(String[] nextLine, boolean applyQuotesToAll) {
      try {
         writeNext(nextLine, applyQuotesToAll, new StringBuilder(INITIAL_STRING_SIZE));
      } catch (IOException e) {
         exception = e;
      }
   }


   /**
    * Writes the next line to the file.  This method is a fail-fast method that will throw the
    * IOException of the writer supplied to the CSVWriter (if the Writer does not handle the exceptions itself like
    * the PrintWriter class).
    *
    * @param nextLine         a string array with each comma-separated element as a separate
    *                         entry.
    * @param applyQuotesToAll true if all values are to be quoted.  false applies quotes only
    *                         to values which contain the separator, escape, quote or new line characters.
    * @param appendable       Appendable used as buffer.
    * @throws IOException Exceptions thrown by the writer supplied to CSVWriter.
    */
   protected void writeNext(String[] nextLine, boolean applyQuotesToAll, Appendable appendable) throws IOException {
      if (nextLine == null) {
         return;
      }

      for (int i = 0; i < nextLine.length; i++) {

         if (i != 0) {
            appendable.append(separator);
         }

         String nextElement = nextLine[i];

         if (nextElement == null) {
            continue;
         }

         Boolean stringContainsSpecialCharacters = stringContainsSpecialCharacters(nextElement);

         if ((applyQuotesToAll || stringContainsSpecialCharacters) && quotechar != NO_QUOTE_CHARACTER) {
            appendable.append(quotechar);
         }

         if (stringContainsSpecialCharacters) {
            processLine(nextElement, appendable);
         } else {
            appendable.append(nextElement);
         }

         if ((applyQuotesToAll || stringContainsSpecialCharacters) && quotechar != NO_QUOTE_CHARACTER) {
            appendable.append(quotechar);
         }
      }

      appendable.append(lineEnd);
      writer.write(appendable.toString());
   }

   /**
    * Writes the next line to the file.
    *
    * @param nextLine A string array with each comma-separated element as a separate
    *                 entry.
    */
   public void writeNext(String[] nextLine) {
      writeNext(nextLine, true);
   }

   /**
    * Checks to see if the line contains special characters.
    * @param line Element of data to check for special characters.
    * @return True if the line contains the quote, escape, separator, newline, or return.
    */
   protected boolean stringContainsSpecialCharacters(String line) {
      return line.indexOf(quotechar) != -1
              || line.indexOf(escapechar) != -1
              || line.indexOf(separator) != -1
              || line.contains(DEFAULT_LINE_END)
              || line.contains("\r");
   }

   /**
    * Processes all the characters in a line.
    * @param nextElement Element to process.
    * @param appendable - Appendable holding the processed data.
    * @throws IOException - IOException thrown by the writer supplied to the CSVWriter
    */
   protected void processLine(String nextElement, Appendable appendable) throws IOException {
      for (int j = 0; j < nextElement.length(); j++) {
         char nextChar = nextElement.charAt(j);
         processCharacter(appendable, nextChar);
      }
   }

   /**
    * Appends the character to the StringBuilder adding the escape character if needed.
    * @param appendable - Appendable holding the processed data.
    * @param nextChar Character to process
    * @throws IOException - IOException thrown by the writer supplied to the CSVWriter.
    */
   protected void processCharacter(Appendable appendable, char nextChar) throws IOException {
      if (escapechar != NO_ESCAPE_CHARACTER && checkCharactersToEscape(nextChar)) {
         appendable.append(escapechar);
      }
      appendable.append(nextChar);
   }

   protected boolean checkCharactersToEscape(char nextChar) {
      return quotechar == NO_QUOTE_CHARACTER
              ? (nextChar == quotechar || nextChar == escapechar || nextChar == separator)
              : (nextChar == quotechar || nextChar == escapechar);
   }

   /**
    * Flush underlying stream to writer.
    *
    * @throws IOException If bad things happen
    */
   @Override
   public void flush() throws IOException {
      writer.flush();
   }

   /**
    * Close the underlying stream writer flushing any buffered content.
    *
    * @throws IOException If bad things happen
    */
   @Override
   public void close() throws IOException {
      flush();
      writer.close();
   }

   /**
    * Flushes the buffer and checks to see if the there has been an error in the printstream.
    *
    * @return True if the print stream has encountered an error
    *          either on the underlying output stream or during a format
    *          conversion.
    */
   public boolean checkError() {

      if (writer instanceof PrintWriter) {
         PrintWriter pw = (PrintWriter) writer;
         return pw.checkError();
      }

      flushQuietly();  // checkError in the PrintWriter class flushes the buffer so we shall too.
      return exception != null;
   }

   
   
   /**
    * Flushes the writer without throwing any exceptions.
    */
   public void flushQuietly() {
      try {
         flush();
      } catch (IOException e) {
         // catch exception and ignore.
      }
   }
}
