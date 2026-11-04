/*
 * @test
 * @summary Verify that the iterator returned by PreHashedMap.entrySet() rejects remove() with UnsupportedOperationException
 * @modules java.base/sun.util
 * @run main PreHashedMapWitupTest
 */

import java.util.Iterator;
import java.util.Map;

public class PreHashedMapWitupTest {

    private static final class EmptyMap extends PreHashedMap<Integer> {
        EmptyMap() {
            super(1, 0, 0, 0);
        }

        @Override
        protected void init(Object[] ht) {
        }
    }

    public static void main(String[] args) {
        testRemoveThrowsOnFreshIterator();
        testRemoveThrowsAfterHasNext();
    }

    private static void testRemoveThrowsOnFreshIterator() {
        EmptyMap map = new EmptyMap();
        Iterator<Map.Entry<String, Integer>> it = map.entrySet().iterator();
        try {
            it.remove();
        } catch (UnsupportedOperationException e) {
            return;
        }
        throw new RuntimeException("Expected UnsupportedOperationException from remove()");
    }

    private static void testRemoveThrowsAfterHasNext() {
        EmptyMap map = new EmptyMap();
        Iterator<Map.Entry<String, Integer>> it = map.entrySet().iterator();
        it.hasNext();
        try {
            it.remove();
        } catch (UnsupportedOperationException e) {
            return;
        }
        throw new RuntimeException("Expected UnsupportedOperationException from remove() after hasNext()");
    }
}
