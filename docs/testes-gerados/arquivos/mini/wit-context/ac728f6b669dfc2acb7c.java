/*
 * @test
 * @summary Test ParserSAX.getProperty throws UnsupportedOperationException
 * @run main ParserSAXWitupTest
 */

import jdk.internal.util.xml.impl.ParserSAX;

public class ParserSAXWitupTest {
    public static void main(String[] args) {
        ParserSAX parser = new ParserSAX();
        try {
            parser.getProperty("anyName");
            throw new Error("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException e) {
            // Expected exception
        }
    }
}
