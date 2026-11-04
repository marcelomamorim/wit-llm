/* @test    @summary Test for CollectionCertStoreParameters constructor with null collection to verify NullPointerException is thrown.    @run main CollectionCertStoreParametersWitupTest */

import java.security.cert.CollectionCertStoreParameters;
import java.util.ArrayList;

public class CollectionCertStoreParametersWitupTest {

    public static void main(String[] args) {
        testConstructorWithNullCollection();
        testConstructorWithValidCollection();
    }

    private static void testConstructorWithNullCollection() {
        boolean exceptionThrown = false;
        try {
            new CollectionCertStoreParameters(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException was not thrown for null collection");
        }
    }

    private static void testConstructorWithValidCollection() {
        ArrayList<Object> list = new ArrayList<>();
        list.add(new Object());
        CollectionCertStoreParameters params = new CollectionCertStoreParameters(list);
        if (params == null) {
            throw new AssertionError("Constructor returned null for valid collection");
        }
    }
}
