/*
 * @test
 * @summary Test sun.net.www.HeaderParser.remove() throws UnsupportedOperationException
 * @run main HeaderParserWitupTest
 */

public class HeaderParserWitupTest {
    public static void main(String[] args) {
        sun.net.www.HeaderParser parser = new sun.net.www.HeaderParser("timeout=15, max=5");
        try {
            parser.remove();
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException e) {
            // Expected exception
        }
    }
}
