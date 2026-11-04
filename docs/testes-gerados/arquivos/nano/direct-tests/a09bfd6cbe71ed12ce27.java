/* @test    @summary Test for HeaderParser.remove() method to ensure it throws UnsupportedOperationException.    @run main HeaderParserWitupTest */

import sun.net.www.HeaderParser;

public class HeaderParserWitupTest {

    public static void main(String[] args) {
        testRemoveThrowsException();
    }

    public static void testRemoveThrowsException() {
        HeaderParser parser = new HeaderParser("timeout=15, max=5");
        try {
            parser.remove();
            throw new AssertionError("Expected UnsupportedOperationException was not thrown");
        } catch (UnsupportedOperationException e) {
            // Test passes if exception is thrown
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception type: " + t, t);
        }
    }
}
