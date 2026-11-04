/*
 * @test
 * @summary Test for java.util.Formatter constructor with filename
 * @run main FormatterWitupTest
 */

import java.util.Formatter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FormatterWitupTest {

    public static void main(String[] args) {
        testConstructorWithValidFileName();
        testConstructorWithNullFileName();
    }

    private static void testConstructorWithValidFileName() {
        String filename = "test_output.txt";
        File file = new File(filename);
        if (file.exists()) {
            file.delete();
        }
        try {
            Formatter formatter = new Formatter(filename);
            formatter.close();
            assert file.exists() : "File should be created";
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException", e);
        }
    }

    private static void testConstructorWithNullFileName() {
        try {
            Formatter formatter = new Formatter((String) null);
            formatter.close();
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException", e);
        }
    }
}
