/* @test    @summary Test PBEKeySpec constructor with salt and iterationCount for exception scenarios    @run main PBEKeySpecWitupTest */

import javax.crypto.spec.PBEKeySpec;
import java.security.spec.KeySpec;

public class PBEKeySpecWitupTest {

    public static void main(String[] args) {
        testConstructorWithNullSalt();
        testConstructorWithEmptySalt();
        testConstructorWithInvalidIterationCount();
    }

    private static void testConstructorWithNullSalt() {
        try {
            new PBEKeySpec(new char[] {'p', 'a', 's', 's'}, null, 1000);
            System.out.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    private static void testConstructorWithEmptySalt() {
        try {
            new PBEKeySpec(new char[] {'p', 'a', 's', 's'}, new byte[0], 1000);
            System.out.println("FAIL: Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: IllegalArgumentException thrown as expected");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    private static void testConstructorWithInvalidIterationCount() {
        try {
            new PBEKeySpec(new char[] {'p', 'a', 's', 's'}, new byte[] {1, 2, 3}, 0);
            System.out.println("FAIL: Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: IllegalArgumentException thrown as expected");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }
}
