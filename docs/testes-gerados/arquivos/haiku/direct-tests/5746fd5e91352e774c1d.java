/*
 * @test
 * @summary Verifies IllegalFormatFlagsException constructor null handling, getFlags and getMessage
 * @run main IllegalFormatFlagsExceptionWitupTest
 */

import java.util.IllegalFormatFlagsException;

public class IllegalFormatFlagsExceptionWitupTest {

    public static void main(String[] args) {
        testNullFlagsThrowsNPE();
        testGetFlagsReturnsSameValue();
        testGetMessageFormat();
        testEmptyFlags();
        testIsIllegalFormatException();
    }

    static void testNullFlagsThrowsNPE() {
        try {
            new IllegalFormatFlagsException(null);
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("Expected NullPointerException for null flags");
    }

    static void testGetFlagsReturnsSameValue() {
        String flags = "-+";
        IllegalFormatFlagsException e = new IllegalFormatFlagsException(flags);
        if (!flags.equals(e.getFlags())) {
            throw new RuntimeException("getFlags() mismatch: " + e.getFlags());
        }
    }

    static void testGetMessageFormat() {
        IllegalFormatFlagsException e = new IllegalFormatFlagsException("-+");
        String expected = "Flags = '-+'";
        if (!expected.equals(e.getMessage())) {
            throw new RuntimeException("getMessage() mismatch: " + e.getMessage());
        }
    }

    static void testEmptyFlags() {
        IllegalFormatFlagsException e = new IllegalFormatFlagsException("");
        if (!"".equals(e.getFlags())) {
            throw new RuntimeException("Empty getFlags() mismatch");
        }
        if (!"Flags = ''".equals(e.getMessage())) {
            throw new RuntimeException("Empty getMessage() mismatch: " + e.getMessage());
        }
    }

    static void testIsIllegalFormatException() {
        IllegalFormatFlagsException e = new IllegalFormatFlagsException("#");
        if (!(e instanceof java.util.IllegalFormatException)) {
            throw new RuntimeException("Not an IllegalFormatException");
        }
    }
}
