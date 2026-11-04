/* @test    @summary Test for RC5ParameterSpec constructors to verify exception paths and behavior    @run main RC5ParameterSpecWitupTest */

import javax.crypto.spec.RC5ParameterSpec;

public class RC5ParameterSpecWitupTest {

    public static void main(String[] args) {
        testConstructorWithNullIV();
        testConstructorWithInvalidIVSize();
    }

    private static void testConstructorWithNullIV() {
        try {
            new RC5ParameterSpec(1, 12, 16, null);
            System.out.println("FAIL: Expected IllegalArgumentException for null IV");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: Caught expected IllegalArgumentException for null IV");
        } catch (Throwable t) {
            System.out.println("FAIL: Caught unexpected exception: " + t);
        }
    }

    private static void testConstructorWithInvalidIVSize() {
        byte[] iv = new byte[4]; // IV size too small for wordSize=16
        try {
            new RC5ParameterSpec(1, 12, 16, iv);
            System.out.println("FAIL: Expected IllegalArgumentException for invalid IV size");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: Caught expected IllegalArgumentException for invalid IV size");
        } catch (Throwable t) {
            System.out.println("FAIL: Caught unexpected exception: " + t);
        }
    }
}
