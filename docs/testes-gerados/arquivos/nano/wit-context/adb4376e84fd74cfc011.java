/*
 * @test
 * @summary Test for java.util.Formatter constructors and behaviors
 * @run main FormatterWitupTest
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Locale;

public class FormatterWitupTest {

    public static void main(String[] args) {
        testConstructorWithFile();
        testConstructorWithFileAndCsn();
        testConstructorWithFileAndCsnAndLocale();
    }

    public static void testConstructorWithFile() {
        try {
            File tempFile = File.createTempFile("test", ".txt");
            tempFile.deleteOnExit();
            Formatter formatter = new Formatter(tempFile);
            // Verifica se o objeto foi criado sem lançar exceções
            if (formatter != null) {
                System.out.println("testConstructorWithFile: PASSED");
            } else {
                System.out.println("testConstructorWithFile: FAILED");
            }
        } catch (FileNotFoundException e) {
            System.out.println("testConstructorWithFile: FAILED with exception: " + e);
        } catch (Exception e) {
            System.out.println("testConstructorWithFile: FAILED with unexpected exception: " + e);
        }
    }

    public static void testConstructorWithFileAndCsn() {
        try {
            File tempFile = File.createTempFile("test", ".txt");
            tempFile.deleteOnExit();
            String csn = null; // Teste com csn nulo para verificar NullPointerException
            Formatter formatter = null;
            try {
                formatter = new Formatter(tempFile, csn);
                System.out.println("testConstructorWithFileAndCsn: FAILED - expected NullPointerException");
            } catch (NullPointerException e) {
                System.out.println("testConstructorWithFileAndCsn: PASSED");
            }
        } catch (Exception e) {
            System.out.println("testConstructorWithFileAndCsn: FAILED with exception: " + e);
        }
    }

    public static void testConstructorWithFileAndCsnAndLocale() {
        try {
            File tempFile = File.createTempFile("test", ".txt");
            tempFile.deleteOnExit();
            String csn = "UTF-8";
            Locale locale = Locale.US;
            Formatter formatter = new Formatter(tempFile, csn, locale);
            if (formatter != null) {
                System.out.println("testConstructorWithFileAndCsnAndLocale: PASSED");
            } else {
                System.out.println("testConstructorWithFileAndCsnAndLocale: FAILED");
            }
        } catch (Exception e) {
            System.out.println("testConstructorWithFileAndCsnAndLocale: FAILED with exception: " + e);
        }
    }
}
