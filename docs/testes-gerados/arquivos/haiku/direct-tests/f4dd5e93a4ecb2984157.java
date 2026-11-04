/*
 * @test
 * @summary Witup-style tests for UnknownFormatFlagsException constructor, getFlags and getMessage
 * @run main UnknownFormatFlagsExceptionWitupTest
 */

import java.util.IllegalFormatException;
import java.util.UnknownFormatFlagsException;

public class UnknownFormatFlagsExceptionWitupTest {

    public static void main(String[] args) {
        testNullFlagsThrowsNullPointerException();
        testFlagsAreRetained();
        testEmptyFlagsAreAccepted();
        testGetMessageFormat();
        testIsIllegalFormatException();
    }

    static void testNullFlagsThrowsNullPointerException() {
        boolean thrown = false;
        try {
            new UnknownFormatFlagsException(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("expected NullPointerException for null flags");
        }
    }

    static void testFlagsAreRetained() {
        String flags = "-#q";
        UnknownFormatFlagsException e = new UnknownFormatFlagsException(flags);
        if (!flags.equals(e.getFlags())) {
            throw new AssertionError("getFlags() returned " + e.getFlags());
        }
    }

    static void testEmptyFlagsAreAccepted() {
        UnknownFormatFlagsException e = new UnknownFormatFlagsException("");
        if (!"".equals(e.getFlags())) {
            throw new AssertionError("getFlags() returned " + e.getFlags());
        }
        if (!"Flags = ".equals(e.getMessage())) {
            throw new AssertionError("getMessage() returned " + e.getMessage());
        }
    }

    static void testGetMessageFormat() {
        UnknownFormatFlagsException e = new UnknownFormatFlagsException("-y");
        if (!"Flags = -y".equals(e.getMessage())) {
            throw new AssertionError("getMessage() returned " + e.getMessage());
        }
    }

    static void testIsIllegalFormatException() {
        IllegalFormatException e = new UnknownFormatFlagsException("x");
        if (!(e instanceof UnknownFormatFlagsException)) {
            throw new AssertionError("unexpected type");
        }
    }
}
