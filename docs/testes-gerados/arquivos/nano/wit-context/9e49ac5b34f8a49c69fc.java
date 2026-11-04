/* @test    @summary Test for URICertStoreParameters constructor null argument behavior    @run main URICertStoreParametersWitupTest */

import java.net.URI;
import java.security.cert.URICertStoreParameters;

public class URICertStoreParametersWitupTest {

    public static void main(String[] args) {
        testConstructorWithNullURI();
        testConstructorWithValidURI();
    }

    private static void testConstructorWithNullURI() {
        try {
            new URICertStoreParameters(null);
            System.err.println("Failed: Expected NullPointerException for null URI");
        } catch (NullPointerException e) {
            System.out.println("Passed: NullPointerException thrown as expected for null URI");
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception type: " + e);
        }
    }

    private static void testConstructorWithValidURI() {
        try {
            URI uri = URI.create("http://example.com");
            URICertStoreParameters params = new URICertStoreParameters(uri);
            if (params.getURI().equals(uri)) {
                System.out.println("Passed: Constructor with valid URI");
            } else {
                System.err.println("Failed: getURI() did not return the expected URI");
            }
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception: " + e);
        }
    }
}
