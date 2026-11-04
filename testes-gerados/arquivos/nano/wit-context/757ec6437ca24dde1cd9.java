/* @test    @summary Test for DerInputStream constructor with valid and invalid inputs    @run main DerInputStreamWitupTest */

import sun.security.util.DerInputBuffer;
import sun.security.util.DerInputStream;
import java.io.IOException;

public class DerInputStreamWitupTest {

    public static void main(String[] args) {
        testValidConstructor();
        testConstructorWithInvalidData();
    }

    private static void testValidConstructor() {
        byte[] data = new byte[10];
        try {
            DerInputBuffer buf = new DerInputBuffer(data);
            DerInputStream dis = new DerInputStream(buf);
            System.out.println("testValidConstructor: PASSED");
        } catch (IOException e) {
            System.err.println("testValidConstructor: FAILED with IOException: " + e);
        }
    }

    private static void testConstructorWithInvalidData() {
        byte[] data = new byte[1];
        int offset = 0;
        int len = 2; // intentionally larger than data length to trigger exception
        boolean allowBER = false;
        try {
            DerInputBuffer buf = new DerInputBuffer(data);
            DerInputStream dis = new DerInputStream(data, offset, len, allowBER);
            System.err.println("testConstructorWithInvalidData: FAILED - exception expected but not thrown");
        } catch (IOException e) {
            System.out.println("testConstructorWithInvalidData: PASSED with expected IOException: " + e);
        }
    }
}
