/*
 * @test
 * @summary ServiceLoader.next() throws NoSuchElementException when no provider is available
 * @run main ServiceLoaderWitupTest
 */

import java.util.NoSuchElementException;
import java.util.ServiceLoader;

public class ServiceLoaderWitupTest {

    public interface Svc {}

    public static void main(String[] args) {
        ServiceLoader<Svc> loader = ServiceLoader.load(Svc.class);
        if (loader.hasNext()) {
            throw new RuntimeException("unexpected provider found");
        }
        for (int i = 0; i < 2; i++) {
            try {
                loader.next();
                throw new RuntimeException("next() did not throw NoSuchElementException");
            } catch (NoSuchElementException e) {
                // expected
            }
        }
    }
}
