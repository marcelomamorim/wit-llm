/*
 * @test
 * @summary Verifies null handling and acceptance of collections in the CollectionCertStoreParameters constructor
 * @run main CollectionCertStoreParametersWitupTest
 */
import java.security.cert.CollectionCertStoreParameters;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CollectionCertStoreParametersWitupTest {
    public static void main(String[] args) {
        testNullCollectionThrowsNPE();
        testEmptyCollectionAccepted();
        testCollectionWithNonCertificateElementsAccepted();
        System.out.println("PASSED");
    }

    static void testNullCollectionThrowsNPE() {
        boolean thrown = false;
        try {
            Collection<?> c = null;
            new CollectionCertStoreParameters(c);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected NullPointerException for null collection");
        }
    }

    static void testEmptyCollectionAccepted() {
        Collection<?> c = new ArrayList<Object>();
        CollectionCertStoreParameters params = new CollectionCertStoreParameters(c);
        if (params == null) {
            throw new RuntimeException("constructor returned null for empty collection");
        }
    }

    static void testCollectionWithNonCertificateElementsAccepted() {
        List<Object> list = new ArrayList<Object>();
        list.add("not a certificate");
        list.add(Integer.valueOf(42));
        CollectionCertStoreParameters params = new CollectionCertStoreParameters(list);
        if (params == null) {
            throw new RuntimeException("constructor returned null for collection with non-certificate elements");
        }
    }
}
