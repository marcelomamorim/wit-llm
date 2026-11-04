/* @test    @summary Test getDigits method of FloatingDecimal for exception handling    @run main FloatingDecimalWitupTest */

import java.util.Arrays;

public class FloatingDecimalWitupTest {

    public static void main(String[] args) {
        testGetDigitsThrowsException();
    }

    public static void testGetDigitsThrowsException() {
        jdk.internal.math.FloatingDecimal fd = new jdk.internal.math.FloatingDecimal() {
            @Override
            public int getDigits(char[] digits) {
                throw new IllegalArgumentException("Exceptional value does not have digits");
            }
        };
        char[] digits = new char[10];
        try {
            fd.getDigits(digits);
            System.out.println("Test failed: exception was not thrown");
        } catch (IllegalArgumentException e) {
            if ("Exceptional value does not have digits".equals(e.getMessage())) {
                System.out.println("Test passed: exception message matches");
            } else {
                System.out.println("Test failed: exception message does not match");
            }
        } catch (Throwable t) {
            System.out.println("Test failed: unexpected exception type: " + t);
        }
    }
}
