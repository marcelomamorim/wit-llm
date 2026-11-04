/*
 * @test
 * @summary Test CopyOnWriteArrayList.COWIterator.remove() throws UnsupportedOperationException
 * @run main CopyOnWriteArrayListWitupTest
 */

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.Iterator;

public class CopyOnWriteArrayListWitupTest {
    public static void main(String[] args) {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        list.add("a");
        Iterator<String> it = list.iterator();
        try {
            it.remove();
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException expected) {
            // Expected exception
        }
        System.out.println("Test passed");
    }
}
