/* @test    @summary Test for DuplicateFormatFlagsException constructor and getFlags method    @run main DuplicateFormatFlagsExceptionWitupTest */

import java.util.DuplicateFormatFlagsException;

public class DuplicateFormatFlagsExceptionWitupTest {

    public static void main(String[] args) {
        testConstructorWithValidFlag();
        testConstructorWithNull();
        testGetFlags();
        testGetMessage();
    }

    static void testConstructorWithValidFlag() {
        String flag = "abc";
        DuplicateFormatFlagsException ex = new DuplicateFormatFlagsException(flag);
        assert ex.getFlags().equals(flag) : "Expected flags to be 'abc'";
    }

    static void testConstructorWithNull() {
        try {
            new DuplicateFormatFlagsException(null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testGetFlags() {
        String flag = "xyz";
        DuplicateFormatFlagsException ex = new DuplicateFormatFlagsException(flag);
        assert ex.getFlags().equals(flag) : "getFlags() should return the flag string";
    }

    static void testGetMessage() {
        String flag = "test";
        DuplicateFormatFlagsException ex = new DuplicateFormatFlagsException(flag);
        String message = ex.getMessage();
        assert message.equals("Flags = 'test'") : "getMessage() should return 'Flags = \'test\''";
    }
}
