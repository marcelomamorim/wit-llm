/*
 * @test    @summary Test for setEntityResolver method in ParserSAX
 * @run main ParserSAXWitupTest
 */

import jdk.internal.util.xml.impl.ParserSAX;
import jdk.internal.org.xml.sax.EntityResolver;

public class ParserSAXWitupTest {

    public static void main(String[] args) {
        testSetEntityResolverWithNull();
        testSetEntityResolverWithValidResolver();
    }

    private static void testSetEntityResolverWithNull() {
        ParserSAX parser = new ParserSAX();
        try {
            parser.setEntityResolver(null);
            System.out.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    private static void testSetEntityResolverWithValidResolver() {
        ParserSAX parser = new ParserSAX();
        EntityResolver resolver = new EntityResolver() {
            // empty implementation
        };
        try {
            parser.setEntityResolver(resolver);
            System.out.println("PASS: setEntityResolver with valid resolver succeeded");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }
}
