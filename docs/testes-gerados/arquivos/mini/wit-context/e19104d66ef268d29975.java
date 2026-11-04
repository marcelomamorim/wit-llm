/*
 * @test
 * @summary Test CopyOnWriteArrayList.COWSubListIterator.remove() throws UnsupportedOperationException
 * @run main CopyOnWriteArrayListWitupTest
 */

public class CopyOnWriteArrayListWitupTest {
    public static void main(String[] args) {
        // The remove() method in COWSubListIterator always throws UnsupportedOperationException
        // We test that calling remove() indeed throws this exception
        boolean thrown = false;
        java.util.concurrent.CopyOnWriteArrayList<String> list = new java.util.concurrent.CopyOnWriteArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");

        // Obtain a subList and its iterator
        java.util.List<String> subList = list.subList(0, 2);
        java.util.Iterator<String> it = subList.iterator();

        if (it.hasNext()) {
            it.next();
            try {
                it.remove();
            } catch (UnsupportedOperationException e) {
                thrown = true;
            }
        }

        if (!thrown) {
            throw new AssertionError("Expected UnsupportedOperationException was not thrown by remove()");
        }
    }
}
