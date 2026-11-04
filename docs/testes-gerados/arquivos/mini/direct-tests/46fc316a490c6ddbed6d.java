/*
 * @test
 * @summary Test CollectionCertStoreParameters constructor with Collection argument
 * @run main CollectionCertStoreParametersWitupTest
 */

import java.security.cert.CollectionCertStoreParameters;
import java.util.ArrayList;
import java.util.Collection;

public class CollectionCertStoreParametersWitupTest {
    public static void main(String[] args) {
        testConstructorWithValidCollection();
        testConstructorWithNullCollection();
    }

    private static void testConstructorWithValidCollection() {
        Collection<Object> coll = new ArrayList<>();
        coll.add("test");
        CollectionCertStoreParameters params = new CollectionCertStoreParameters(coll);
        if (params == null) {
            throw new RuntimeException("Constructor returned null");
        }
    }

    private static void testConstructorWithNullCollection() {
        try {
            new CollectionCertStoreParameters(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
