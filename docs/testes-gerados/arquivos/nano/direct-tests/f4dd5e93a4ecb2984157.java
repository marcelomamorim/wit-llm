/* @test    @summary Test UnknownFormatFlagsException constructor with valid and null input    @run main UnknownFormatFlagsExceptionWitupTest */

import java.util.UnknownFormatFlagsException;

public class UnknownFormatFlagsExceptionWitupTest {

    public static void main(String[] args) {
        testConstructorWithValidFlag();
        testConstructorWithNullFlag();
    }

    private static void testConstructorWithValidFlag() {
        String flag = "abc";
        UnknownFormatFlagsException ex = new UnknownFormatFlagsException(flag);
        assert "abc".equals(ex.getFlags()) : "Expected flags to be 'abc'";
        assert "Flags = abc".equals(ex.getMessage()) : "Expected message to be 'Flags = abc'";
    }

    private static void testConstructorWithNullFlag() {
        try {
            new UnknownFormatFlagsException(null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
