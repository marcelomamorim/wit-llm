/*
 * @test
 * @summary Test ParserSAX.getProperty throws UnsupportedOperationException
 * @run main ParserSAXWitupTest
 */

public class ParserSAXWitupTest {
    public static void main(String[] args) {
        jdk.internal.util.xml.impl.ParserSAX parser = new jdk.internal.util.xml.impl.ParserSAX();
        try {
            parser.getProperty("anyName");
            throw new Error("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException e) {
            // expected
        }
    }
}
