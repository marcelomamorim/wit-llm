/*
 * @test
 * @summary Witup-generated tests for ServiceLoader.loadInstalled
 * @run main ServiceLoaderWitupTest
 */

import java.util.Iterator;
import java.util.ServiceLoader;

public class ServiceLoaderWitupTest {

    public interface NoProviders { }

    public static void main(String[] args) {
        testNullServiceRejected();
        testReturnsLoaderWithoutProviders();
    }

    static void testNullServiceRejected() {
        try {
            ServiceLoader.loadInstalled(null);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException expected) {
        }
    }

    static void testReturnsLoaderWithoutProviders() {
        ServiceLoader<NoProviders> loader = ServiceLoader.loadInstalled(NoProviders.class);
        if (loader == null) {
            throw new RuntimeException("loadInstalled returned null");
        }
        Iterator<NoProviders> it = loader.iterator();
        if (it.hasNext()) {
            throw new RuntimeException("unexpected provider found");
        }
    }
}
