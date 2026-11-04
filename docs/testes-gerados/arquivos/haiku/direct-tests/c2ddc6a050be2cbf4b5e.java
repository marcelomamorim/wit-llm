/*
 * @test
 * @summary ServiceLoader.loadInstalled returns a new loader that finds no providers for an interface without installed providers
 * @run main ServiceLoaderWitupTest
 */

import java.util.Iterator;
import java.util.ServiceLoader;

public class ServiceLoaderWitupTest {

    public interface NoProviders {
    }

    public static void main(String[] args) {
        ServiceLoader<NoProviders> loader = ServiceLoader.loadInstalled(NoProviders.class);
        if (loader == null) {
            throw new RuntimeException("loadInstalled returned null");
        }

        Iterator<NoProviders> it = loader.iterator();
        if (it.hasNext()) {
            throw new RuntimeException("Expected no installed providers");
        }

        long count = loader.stream().count();
        if (count != 0) {
            throw new RuntimeException("Expected empty stream but got " + count);
        }

        ServiceLoader<NoProviders> other = ServiceLoader.loadInstalled(NoProviders.class);
        if (other == loader) {
            throw new RuntimeException("loadInstalled should return a new ServiceLoader on each call");
        }
    }
}
