/* @test    @summary Test for InflaterOutputStream constructor with null OutputStream    @run main InflaterOutputStreamWitupTest */

import java.io.OutputStream;
import java.io.IOException;
import java.util.zip.Inflater;
import java.util.zip.InflaterOutputStream;

public class InflaterOutputStreamWitupTest {

    public static void main(String[] args) {
        testConstructorWithNullOutputStream();
    }

    public static void testConstructorWithNullOutputStream() {
        try {
            new InflaterOutputStream(null);
            System.err.println("Expected NullPointerException not thrown");
            System.exit(1);
        } catch (NullPointerException e) {
            // Expected exception
        } catch (Exception e) {
            System.err.println("Unexpected exception: " + e);
            System.exit(1);
        }
    }
}
