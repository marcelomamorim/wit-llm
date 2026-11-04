/* @test    @summary Test for ZipOutputStream constructor with null OutputStream.    @run main ZipOutputStreamWitupTest */

import java.io.OutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.zip.ZipOutputStream;

public class ZipOutputStreamWitupTest {

    public static void main(String[] args) {
        testConstructorWithNullOutputStream();
    }

    public static void testConstructorWithNullOutputStream() {
        try {
            new ZipOutputStream(null);
            // If no exception, test fails
            System.err.println("Expected NullPointerException was not thrown.");
            System.exit(1);
        } catch (NullPointerException e) {
            // Expected exception, test passes
            System.out.println("NullPointerException correctly thrown.");
        } catch (Exception e) {
            System.err.println("Unexpected exception: " + e);
            System.exit(1);
        }
    }
}
