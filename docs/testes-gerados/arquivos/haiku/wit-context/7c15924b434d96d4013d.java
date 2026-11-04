/*
 * @test
 * @summary Verify ServiceLoader iterator and stream report no providers and next() throws NoSuchElementException when the service has none
 * @run main ServiceLoaderWitupTest
 */
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ServiceLoader;

public class ServiceLoaderWitupTest {

    public interface EmptyService {}

    public static void main(String[] args) {
        ServiceLoader<EmptyService> loader = ServiceLoader.load(EmptyService.class);
        Iterator<EmptyService> it = loader.iterator();
        if (it.hasNext()) {
            throw new RuntimeException("iterator reported a provider for an empty service");
        }
        try {
            it.next();
            throw new RuntimeException("expected NoSuchElementException");
        } catch (NoSuchElementException expected) {
        }
        long count = loader.stream().count();
        if (count != 0) {
            throw new RuntimeException("stream reported " + count + " providers for an empty service");
        }
    }
}
