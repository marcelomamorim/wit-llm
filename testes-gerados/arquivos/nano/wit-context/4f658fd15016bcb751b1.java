/*
 * @test
 * @summary Tests for sun.security.util.DerOutputStream
 * @run main DerOutputStreamWitupTest
 */

import sun.security.util.DerOutputStream;
import java.io.IOException;

public class DerOutputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        testPutIntegerWithNull();
        testPutIntegerWithLargeInteger();
    }

    public static void testPutIntegerWithNull() {
        DerOutputStream dos = new DerOutputStream();
        try {
            dos.putInteger((Integer) null);
        } catch (NullPointerException e) {
            System.out.println("Passed null input test");
            return;
        } catch (Exception e) {
            System.out.println("Failed null input test: " + e);
            return;
        }
        System.out.println("Failed null input test: no exception thrown");
    }

    public static void testPutIntegerWithLargeInteger() throws IOException {
        DerOutputStream dos = new DerOutputStream();
        int largeValue = Integer.MAX_VALUE;
        try {
            dos.putInteger(largeValue);
            System.out.println("putInteger with large value succeeded");
        } catch (Exception e) {
            System.out.println("putInteger with large value failed: " + e);
        }
    }
}
