/* @test    @summary Test for FloatingDecimal.getDigits to handle IllegalArgumentException    @run main FloatingDecimalWitupTest */

import java.io.*;

public class FloatingDecimalWitupTest {

    public static void main(String[] args) {
        testGetDigitsThrowsException();
    }

    public static void testGetDigitsThrowsException() {
        jdk.internal.math.FloatingDecimal instance = new jdk.internal.math.FloatingDecimal() {
            public int getDigits(char[] digits) {
                throw new IllegalArgumentException("Exceptional value does not have digits");
            }
        };
        boolean exceptionThrown = false;
        try {
            char[] dummyDigits = new char[10];
            instance.getDigits(dummyDigits);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalArgumentException was not thrown");
        }
    }
}
