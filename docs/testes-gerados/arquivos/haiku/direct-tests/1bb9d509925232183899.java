/*
 * @test
 * @summary Basic observable behavior of ServiceLoader.load(Class) for a service with no providers
 * @run main ServiceLoaderWitupTest
 */

import java.util.Iterator;
import java.util.ServiceLoader;

public class ServiceLoaderWitupTest {

    public interface NoSuchService {
    }

    public static void main(String[] args) {
        ServiceLoader<NoSuchService> loader = ServiceLoader.load(NoSuchService.class);
        if (loader == null) {
            throw new RuntimeException("load returned null");
        }

        Iterator<NoSuchService> it = loader.iterator();
        if (it == null) {
            throw new RuntimeException("iterator returned null");
        }
        if (it.hasNext()) {
            throw new RuntimeException("expected no providers for NoSuchService");
        }

        long count = ServiceLoader.load(NoSuchService.class).stream().count();
        if (count != 0L) {
            throw new RuntimeException("expected empty stream, got " + count);
        }

        ServiceLoader<NoSuchService> second = ServiceLoader.load(NoSuchService.class);
        if (second == loader) {
            throw new RuntimeException("load should return a new ServiceLoader instance per call");
        }
    }
}
