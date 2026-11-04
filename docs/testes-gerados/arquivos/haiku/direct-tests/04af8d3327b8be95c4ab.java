/*
 * @test
 * @summary ServiceLoader iterator and stream report no providers when none are configured, and iterator next() throws NoSuchElementException
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
            throw new AssertionError("unexpected provider found");
        }
        try {
            it.next();
            throw new AssertionError("expected NoSuchElementException");
        } catch (NoSuchElementException expected) {
            // expected
        }
        long count = ServiceLoader.load(Svc.class).stream().count();
        if (count != 0L) {
            throw new AssertionError("expected no providers but got " + count);
        }
    }
}
