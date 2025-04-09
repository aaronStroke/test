package mx.loal.pharmacy_admin_api.utils.printer;

import javax.print.DocFlavor;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.SimpleDoc;
import java.awt.print.PrinterJob;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public final class PrintUtility {

    public static void printPDFFile(String filePath) throws IOException {

        var in = new FileInputStream(filePath);
        var doc = new SimpleDoc(in, DocFlavor.INPUT_STREAM.AUTOSENSE, null);
        var ps = findPrintService("EPSON");

        try {
            var printJob = ps.createPrintJob();
            printJob.print(doc, null);
        } catch (PrintException e) {
            throw new RuntimeException(e);
        } finally {
            in.close();
        }
    }

    /**
     * Retrieve a Print Service with a name containing the specified PrinterName; will return null if not found.
     *
     * @return service
     */
    private static PrintService findPrintService(String printerName) {

        String finalPrinterName = printerName.toLowerCase();

        var printers = Arrays.stream(PrinterJob.lookupPrintServices());

        return printers
            .filter(s -> s.getName().toLowerCase().contains(finalPrinterName))
            .toList()
            .stream()
            .findFirst()
            .orElseThrow(() -> new RuntimeException("No printer with name " + finalPrinterName + "found"));
    }

    /**
     * Retrieves a List of Printer Service Names.
     *
     * @return List
     */
    private static List<String> getPrinterServiceNameList() {
        return Arrays
            .stream(PrinterJob.lookupPrintServices())
            .map(PrintService::getName)
            .toList();
    }

    /**
     * Utility class; no construction!
     */
    private PrintUtility() {}

}
