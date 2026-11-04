/* @test    @summary Test constructor PrintStream(String fileName) with valid filename    @run main PrintStreamWitupTest */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

public class PrintStreamWitupTest {

    public static void main(String[] args) {
        testValidFileName();
        testInvalidFileName();
    }

    static void testValidFileName() {
        String filename = "test_output.txt";
        File file = new File(filename);
        if (file.exists()) {
            file.delete();
        }
        try {
            PrintStream ps = new PrintStream(filename);
            ps.println("Test line");
            ps.close();
            assert file.exists() : "File should be created";
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Unexpected FileNotFoundException", e);
        }
    }

    static void testInvalidFileName() {
        String invalidFilename = "/invalid_path/test_output.txt";
        try {
            new PrintStream(invalidFilename);
            assert false : "Expected FileNotFoundException";
        } catch (FileNotFoundException e) {
            // expected exception
        }
    }
}
