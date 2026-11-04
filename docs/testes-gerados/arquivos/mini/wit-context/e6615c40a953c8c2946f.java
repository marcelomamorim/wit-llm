/*
 * @test
 * @summary Test ParserSAX.setProperty throws UnsupportedOperationException
 * @run main ParserSAXWitupTest
 */

import jdk.internal.util.xml.impl.ParserSAX;

public class ParserSAXWitupTest {
    public static void main(String[] args) {
        ParserSAX parser = new ParserSAX();
        try {
            parser.setProperty("anyName", new Object());
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException expected) {
            // Expected exception, test passes
        }
    }
}
