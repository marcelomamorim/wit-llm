/*
 * @test
 * @summary ServiceLoader iterator hasNext returns false without providers and throws ConcurrentModificationException after reload
 * @run main ServiceLoaderWitupTest
 */

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.ServiceLoader;

public class ServiceLoaderWitupTest {

    public interface Svc {
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        ServiceLoader<Svc> loader = ServiceLoader.load(Svc.class);
        Iterator<Svc> it = loader.iterator();
        check(!it.hasNext(), "hasNext should be false when no providers exist");
        check(!it.hasNext(), "hasNext should be idempotent when no providers exist");

        ServiceLoader<Svc> loader2 = ServiceLoader.load(Svc.class);
        Iterator<Svc> it2 = loader2.iterator();
        loader2.reload();
        boolean thrown = false;
        try {
            it2.hasNext();
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        check(thrown, "hasNext should throw ConcurrentModificationException after reload");
    }
}
