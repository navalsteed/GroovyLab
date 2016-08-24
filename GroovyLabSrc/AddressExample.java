


import utils.CSV.CSVWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import utils.CSV.CSVReader;


public class AddressExample {

    private static final String ADDRESS_FILE = "/Users/sterg/NBProjects/GroovyLab/GroovyLabSrc/addresses.csv";

    public static void main(String[] args) throws IOException {

        CSVReader reader = new CSVReader(new FileReader(ADDRESS_FILE));
        String[] nextLine;
        while ((nextLine = reader.readNext()) != null) {
            System.out.println("Name: [" + nextLine[0] + "]\nAddress: [" + nextLine[1] + "]\nEmail: [" + nextLine[2] + "]");
        }

        // Try writing it back out as CSV to the console
        CSVReader reader2 = new CSVReader(new FileReader(ADDRESS_FILE));
        List<String[]> allElements = reader2.readAll();
        StringWriter sw = new StringWriter();
        CSVWriter writer = new CSVWriter(sw);
        writer.writeAll(allElements);

        System.out.println("\n\nGenerated CSV File:\n\n");
        System.out.println(sw.toString());


    }
}
