/* @test    @summary Test SelectorImpl.select(long) method for non-negative timeouts    @run main SelectorImplWitupTest */

import java.io.IOException;
import sun.nio.ch.SelectorImpl;

public class SelectorImplWitupTest {

    public static void main(String[] args) throws IOException {
        testSelectWithZeroTimeout();
        testSelectWithPositiveTimeout();
        testSelectWithNegativeTimeout();
    }

    static void testSelectWithZeroTimeout() throws IOException {
        SelectorImpl selector = new SelectorImpl(null);
        int result = selector.select(0);
        if (result != 0) {
            throw new AssertionError("Expected result 0 for zero timeout, but got: " + result);
        }
    }

    static void testSelectWithPositiveTimeout() throws IOException {
        SelectorImpl selector = new SelectorImpl(null);
        int result = selector.select(100);
        if (result != 0) {
            throw new AssertionError("Expected result 0 for positive timeout, but got: " + result);
        }
    }

    static void testSelectWithNegativeTimeout() {
        try {
            SelectorImpl selector = new SelectorImpl(null);
            selector.select(-1);
            throw new AssertionError("Expected IllegalArgumentException for negative timeout");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException", e);
        }
    }
}
