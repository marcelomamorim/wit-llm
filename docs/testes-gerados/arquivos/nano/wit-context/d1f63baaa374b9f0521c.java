/* @test    @summary Test DerInputStream constructor with valid and invalid inputs    @run main DerInputStreamWitupTest */

import sun.security.util.DerInputStream;
import java.io.IOException;

public class DerInputStreamWitupTest {

    public static void main(String[] args) {
        testValidConstructor();
        testConstructorWithInvalidLength();
    }

    private static void testValidConstructor() {
        byte[] data = {0x30, 0x0A, 0x02, 0x01, 0x01, 0x02, 0x01, 0x02, 0x02, 0x01, 0x03};
        try {
            DerInputStream dis = new DerInputStream(data);
            System.out.println("testValidConstructor passed");
        } catch (IOException e) {
            System.err.println("testValidConstructor failed: " + e);
        }
    }

    private static void testConstructorWithInvalidLength() {
        byte[] data = {0x30, 0x02, 0x02}; // length byte indicates more data than available
        try {
            DerInputStream dis = new DerInputStream(data);
            System.err.println("testConstructorWithInvalidLength failed: no exception thrown");
        } catch (IOException e) {
            System.out.println("testConstructorWithInvalidLength passed: " + e.getClass().getName());
        }
    }
}
