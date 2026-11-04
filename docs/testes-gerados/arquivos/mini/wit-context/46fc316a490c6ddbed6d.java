/*
 * @test
 * @summary Test constructor CollectionCertStoreParameters(Collection<?>) for null and valid input
 * @run main CollectionCertStoreParametersWitupTest
 */

import java.security.cert.CollectionCertStoreParameters;
import java.util.ArrayList;
import java.util.Collection;

public class CollectionCertStoreParametersWitupTest {
    public static void main(String[] args) {
        testConstructorWithNullCollection();
        testConstructorWithValidCollection();
    }

    private static void testConstructorWithNullCollection() {
        try {
            new CollectionCertStoreParameters(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testConstructorWithValidCollection() {
        Collection<Object> coll = new ArrayList<>();
        CollectionCertStoreParameters params = new CollectionCertStoreParameters(coll);
        if (params == null) {
            throw new RuntimeException("Constructor returned null instance");
        }
    }
}
