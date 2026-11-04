/* @test    @summary Test constructor of URICertStoreParameters with valid URI    @run main URICertStoreParametersWitupTest */

import java.net.URI;
import java.security.cert.URICertStoreParameters;

public class URICertStoreParametersWitupTest {

    public static void main(String[] args) {
        testConstructorWithValidURI();
        testConstructorWithNullURI();
        testGetURI();
        testClone();
        testHashCodeConsistency();
        testEquals();
    }

    static void testConstructorWithValidURI() {
        URI uri = URI.create("http://example.com");
        URICertStoreParameters params = new URICertStoreParameters(uri);
        assert params.getURI().equals(uri) : "URI should match the one provided";
    }

    static void testConstructorWithNullURI() {
        boolean exceptionThrown = false;
        try {
            new URICertStoreParameters(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "NullPointerException should be thrown when URI is null";
    }

    static void testGetURI() {
        URI uri = URI.create("https://test.org");
        URICertStoreParameters params = new URICertStoreParameters(uri);
        assert params.getURI().equals(uri) : "getURI() should return the URI used in constructor";
    }

    static void testClone() {
        URI uri = URI.create("http://clone.com");
        URICertStoreParameters original = new URICertStoreParameters(uri);
        URICertStoreParameters clone = original.clone();
        assert clone != null : "Clone should not be null";
        assert clone.getURI().equals(uri) : "Cloned object should have the same URI";
        assert clone != original : "Clone should be a different instance";
    }

    static void testHashCodeConsistency() {
        URI uri = URI.create("http://hashcode.com");
        URICertStoreParameters params = new URICertStoreParameters(uri);
        int hash1 = params.hashCode();
        int hash2 = params.hashCode();
        assert hash1 == hash2 : "Hash code should be consistent across multiple calls";
    }

    static void testEquals() {
        URI uri1 = URI.create("http://equal.com");
        URI uri2 = URI.create("http://equal.com");
        URICertStoreParameters params1 = new URICertStoreParameters(uri1);
        URICertStoreParameters params2 = new URICertStoreParameters(uri2);
        assert params1.hashCode() == params2.hashCode() : "Equal objects should have same hash code";
        assert params1.getURI().equals(params2.getURI()) : "URIs should be equal";
    }
}
