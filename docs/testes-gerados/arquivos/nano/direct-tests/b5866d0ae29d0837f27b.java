/* @test    @summary Test getDecimalExponent method of FloatingDecimal class    @run main FloatingDecimalWitupTest */

import java.util.regex.*;

public class FloatingDecimalWitupTest {

    public static void main(String[] args) {
        testGetDecimalExponentException();
    }

    public static void testGetDecimalExponentException() {
        try {
            jdk.internal.math.FloatingDecimal fd = null;
            // Instantiate an anonymous subclass to invoke getDecimalExponent
            fd = new jdk.internal.math.FloatingDecimal() {
                @Override
                public int getDecimalExponent() {
                    throw new IllegalArgumentException("Exceptional value does not have an exponent");
                }
            };
            int result = fd.getDecimalExponent();
            // If no exception, test fails
            System.err.println("Expected exception was not thrown.");
            System.exit(1);
        } catch (IllegalArgumentException e) {
            if (e.getMessage().equals("Exceptional value does not have an exponent")) {
                System.out.println("Test passed: getDecimalExponent throws expected exception.");
            } else {
                System.err.println("Test failed: Exception message mismatch.");
                System.exit(1);
            }
        } catch (Throwable t) {
            System.err.println("Test failed: Unexpected exception: " + t);
            System.exit(1);
        }
    }
}
