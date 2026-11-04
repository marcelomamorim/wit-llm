/* @test    @summary Test FileOutputStream constructor with valid and null filename    @run main FileOutputStreamWitupTest */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class FileOutputStreamWitupTest {

    public static void main(String[] args) {
        testValidFileName();
        testNullFileName();
    }

    static void testValidFileName() {
        String filename = "testfile.txt";
        File file = new File(filename);
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream fos = new FileOutputStream(filename);
            fos.close();
            assert file.exists() : "File should be created";
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Unexpected FileNotFoundException for valid filename", e);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception", e);
        } finally {
            if (file.exists()) {
                file.delete();
            }
        }
    }

    static void testNullFileName() {
        try {
            new FileOutputStream((String) null);
            throw new AssertionError("Expected NullPointerException for null filename");
        } catch (NullPointerException e) {
            // expected
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception", e);
        }
    }
}
