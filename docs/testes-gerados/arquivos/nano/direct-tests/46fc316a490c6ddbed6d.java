/* @test    @summary Test CollectionCertStoreParameters constructor with non-null collection    @run main CollectionCertStoreParametersWitupTest */

import java.security.cert.CollectionCertStoreParameters;
import java.util.ArrayList;
import java.util.List;

public class CollectionCertStoreParametersWitupTest {

    public static void main(String[] args) {
        testConstructorWithValidCollection();
        testConstructorWithNullCollection();
    }

    private static void testConstructorWithValidCollection() {
        List<Object> list = new ArrayList<>();
        list.add("Certificate1");
        list.add("CRL1");
        CollectionCertStoreParameters params = new CollectionCertStoreParameters(list);
        assert params != null : "Constructor with valid collection returned null";
    }

    private static void testConstructorWithNullCollection() {
        try {
            new CollectionCertStoreParameters(null);
            assert false : "Expected NullPointerException not thrown";
        } catch (NullPointerException e) {
            // Expected exception
        }
    }
}
