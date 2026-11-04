/*
 * @test
 * @summary Tests for InflaterInputStream constructor with InputStream and Inflater
 * @run main InflaterInputStreamWitupTest
 */

import java.io.InputStream;
import java.io.IOException;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

public class InflaterInputStreamWitupTest {

    public static void main(String[] args) {
        testConstructorWithNullInputStream();
        testConstructorWithNullInflater();
        testConstructorWithValidParameters();
    }

    static void testConstructorWithNullInputStream() {
        try {
            new InflaterInputStream(null, new Inflater());
            System.err.println("Failed: Expected NullPointerException for null InputStream");
        } catch (NullPointerException e) {
            // Expected
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception for null InputStream: " + e);
        }
    }

    static void testConstructorWithNullInflater() {
        InputStream dummyIn = new InputStream() {
            @Override
            public int read() throws IOException {
                return -1;
            }
        };
        try {
            new InflaterInputStream(dummyIn, null);
            System.err.println("Failed: Expected NullPointerException for null Inflater");
        } catch (NullPointerException e) {
            // Expected
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception for null Inflater: " + e);
        }
    }

    static void testConstructorWithValidParameters() {
        InputStream dummyIn = new InputStream() {
            @Override
            public int read() throws IOException {
                return -1;
            }
        };
        Inflater inflater = new Inflater();
        try {
            InflaterInputStream iis = new InflaterInputStream(dummyIn, inflater);
            if (iis == null) {
                System.err.println("Failed: InflaterInputStream instance is null");
            }
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception for valid parameters: " + e);
        }
    }
}
