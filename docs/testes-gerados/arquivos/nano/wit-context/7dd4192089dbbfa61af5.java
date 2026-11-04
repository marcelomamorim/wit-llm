/* @test    @summary Tests for InflaterOutputStream constructor with null parameters to verify exception throwing behavior.    @run main InflaterOutputStreamWitupTest */

import java.io.OutputStream;
import java.io.IOException;
import java.util.zip.Inflater;
import java.util.zip.InflaterOutputStream;

public class InflaterOutputStreamWitupTest {

    public static void main(String[] args) {
        testConstructorNullOut();
        testConstructorNullInflator();
    }

    private static void testConstructorNullOut() {
        try {
            new InflaterOutputStream(null);
            System.err.println("Failed: Expected NullPointerException for null OutputStream");
        } catch (NullPointerException expected) {
            // Test passes
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception for null OutputStream: " + t);
        }
    }

    private static void testConstructorNullInflator() {
        OutputStream dummyOut = new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                // no-op
            }
        };
        try {
            new InflaterOutputStream(dummyOut, null);
            System.err.println("Failed: Expected NullPointerException for null Inflater");
        } catch (NullPointerException expected) {
            // Test passes
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception for null Inflater: " + t);
        }
    }
}
