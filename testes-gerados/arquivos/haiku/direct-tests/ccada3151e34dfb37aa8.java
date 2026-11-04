/*
 * @test
 * @summary Verify that the iterator returned by Collections.emptyIterator() throws NoSuchElementException from next()
 * @run main CollectionsWitupTest
 */
import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        Iterator<String> it = Collections.emptyIterator();
        if (it.hasNext()) {
            throw new AssertionError("hasNext should be false");
        }
        checkNext(it);
        checkNext(it);
        checkNext(Collections.emptyIterator());
    }

    static void checkNext(Iterator<?> it) {
        try {
            it.next();
        } catch (NoSuchElementException e) {
            return;
        }
        throw new AssertionError("expected NoSuchElementException");
    }
}
