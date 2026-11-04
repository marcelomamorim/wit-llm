/*
 * @test
 * @summary ServiceLoader.hasNext returns false when no providers exist and is stable across repeated calls
 * @run main ServiceLoaderWitupTest
 */

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ServiceLoader;

public class ServiceLoaderWitupTest {

    public interface Svc {}

    public static void main(String[] args) {
        ServiceLoader<Svc> loader = ServiceLoader.load(Svc.class);
        Iterator<Svc> it = loader.iterator();

        if (it.hasNext()) {
            throw new RuntimeException("hasNext() should be false when no providers exist");
        }
        if (it.hasNext()) {
            throw new RuntimeException("hasNext() should be idempotent (second call)");
        }
        if (it.hasNext()) {
            throw new RuntimeException("hasNext() should be idempotent (third call)");
        }

        try {
            it.next();
            throw new RuntimeException("next() should throw NoSuchElementException when hasNext() is false");
        } catch (NoSuchElementException expected) {
            // expected
        }

        Iterator<Svc> fresh = ServiceLoader.load(Svc.class).iterator();
        if (fresh.hasNext()) {
            throw new RuntimeException("fresh iterator should also report no providers");
        }
    }
}
