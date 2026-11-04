/*
 * @test
 * @summary Tests for ECFieldF2m constructor behavior, especially exception handling for invalid inputs.
 * @run main ECFieldF2mWitupTest
 */

import java.security.spec.ECFieldF2m;

public class ECFieldF2mWitupTest {

    public static void main(String[] args) {
        testConstructorWithZero();
        testConstructorWithNegative();
        testConstructorWithValidValues();
    }

    private static void testConstructorWithZero() {
        try {
            new ECFieldF2m(0);
            System.out.println("FAIL: Expected IllegalArgumentException for m=0");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: Caught expected exception for m=0");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception for m=0: " + t);
        }
    }

    private static void testConstructorWithNegative() {
        try {
            new ECFieldF2m(-5);
            System.out.println("FAIL: Expected IllegalArgumentException for m=-5");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: Caught expected exception for m=-5");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception for m=-5: " + t);
        }
    }

    private static void testConstructorWithValidValues() {
        try {
            ECFieldF2m field = new ECFieldF2m(5);
            if (field != null && field instanceof ECFieldF2m) {
                System.out.println("PASS: Successfully created ECFieldF2m with m=5");
            } else {
                System.out.println("FAIL: ECFieldF2m instance invalid for m=5");
            }
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception for m=5: " + t);
        }
    }
}
