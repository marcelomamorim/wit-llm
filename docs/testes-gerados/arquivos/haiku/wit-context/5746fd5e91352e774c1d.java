/*
 * @test
 * @summary Verify IllegalFormatFlagsException constructor null-check, getFlags and getMessage observable behavior
 * @run main IllegalFormatFlagsExceptionWitupTest
 */

import java.util.IllegalFormatException;
import java.util.IllegalFormatFlagsException;

public class IllegalFormatFlagsExceptionWitupTest {

    public static void main(String[] args) {
        testNullFlagsThrowsNPE();
        testFlagsPreserved();
        testEmptyFlags();
        testMessageFormat();
        testIsIllegalFormatException();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullFlagsThrowsNPE() {
        boolean thrown = false;
        try {
            new IllegalFormatFlagsException(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "Expected NullPointerException for null flags");
    }

    static void testFlagsPreserved() {
        IllegalFormatFlagsException e = new IllegalFormatFlagsException("-+");
        check("-+".equals(e.getFlags()), "getFlags() should return the flags given to the constructor");
    }

    static void testEmptyFlags() {
        IllegalFormatFlagsException e = new IllegalFormatFlagsException("");
        check("".equals(e.getFlags()), "getFlags() should return empty string when constructed with empty string");
    }

    static void testMessageFormat() {
        IllegalFormatFlagsException e = new IllegalFormatFlagsException("-0");
        check("Flags = '-0'".equals(e.getMessage()), "Unexpected getMessage(): " + e.getMessage());
    }

    static void testIsIllegalFormatException() {
        IllegalFormatFlagsException e = new IllegalFormatFlagsException("#");
        IllegalFormatException base = e;
        check(base instanceof IllegalFormatFlagsException, "Instance should be an IllegalFormatFlagsException");
    }
}
