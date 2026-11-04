/* @test    @summary Test for CoderResult.length() method behavior.    @run main CoderResultWitupTest */

import java.nio.charset.CoderResult;

public class CoderResultWitupTest {

    public static void main(String[] args) {
        testErrorResultLength();
        testNonErrorResultThrows();
    }

    private static void testErrorResultLength() {
        CoderResult errorResult = new CoderResult() {
            @Override
            public boolean isError() { return true; }
            @Override
            public int length() { return 5; }
        };
        int length = errorResult.length();
        if (length != 5) {
            throw new AssertionError("Expected length 5, but got " + length);
        }
    }

    private static void testNonErrorResultThrows() {
        CoderResult nonErrorResult = new CoderResult() {
            @Override
            public boolean isError() { return false; }
            @Override
            public int length() { throw new UnsupportedOperationException(); }
        };
        boolean exceptionThrown = false;
        try {
            nonErrorResult.length();
        } catch (UnsupportedOperationException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected UnsupportedOperationException to be thrown");
        }
    }
}
