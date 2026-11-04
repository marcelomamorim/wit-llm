/* @test    @summary Test for java.security.URIParameter constructor handling null URI    @run main URIParameterWitupTest */

import java.net.URI;
import java.security.URIParameter;

public class URIParameterWitupTest {

    public static void main(String[] args) {
        testConstructorWithNullURI();
        testConstructorWithValidURI();
    }

    private static void testConstructorWithNullURI() {
        try {
            new URIParameter(null);
            System.err.println("Failed: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("Passed: NullPointerException thrown as expected");
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception type: " + t);
        }
    }

    private static void testConstructorWithValidURI() {
        try {
            URI uri = URI.create("http://example.com/data");
            URIParameter param = new URIParameter(uri);
            if (param.getURI().equals(uri)) {
                System.out.println("Passed: Valid URI accepted and returned correctly");
            } else {
                System.err.println("Failed: Returned URI does not match input");
            }
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception: " + t);
        }
    }
}
