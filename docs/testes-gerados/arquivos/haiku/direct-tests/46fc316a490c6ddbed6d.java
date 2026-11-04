/*
 * @test
 * @summary Basic behavior of the CollectionCertStoreParameters(Collection) constructor
 * @run main CollectionCertStoreParametersWitupTest
 */

import java.security.cert.CertStoreParameters;
import java.security.cert.CollectionCertStoreParameters;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CollectionCertStoreParametersWitupTest {

    public static void main(String[] args) throws Exception {
        testNullCollectionThrowsNPE();
        testEmptyCollectionAccepted();
        testMutableCollectionAccepted();
        testDefaultConstructorCreatesInstance();
    }

    static void testNullCollectionThrowsNPE() {
        try {
            new CollectionCertStoreParameters((Collection<?>) null);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("Expected NullPointerException for null collection");
    }

    static void testEmptyCollectionAccepted() {
        Collection<?> empty = Collections.emptyList();
        CollectionCertStoreParameters p = new CollectionCertStoreParameters(empty);
        if (!(p instanceof CertStoreParameters)) {
            throw new RuntimeException("Instance must implement CertStoreParameters");
        }
    }

    static void testMutableCollectionAccepted() {
        List<String> list = new ArrayList<>();
        list.add("not a certificate");
        CollectionCertStoreParameters p = new CollectionCertStoreParameters(list);
        if (p == null) {
            throw new RuntimeException("Constructor returned null");
        }
        list.clear();
        list.add("another entry");
    }

    static void testDefaultConstructorCreatesInstance() {
        CollectionCertStoreParameters p = new CollectionCertStoreParameters();
        if (!(p instanceof CertStoreParameters)) {
            throw new RuntimeException("Default instance must implement CertStoreParameters");
        }
    }
}
