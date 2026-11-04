/*
 * @test
 * @summary Test Collections remove() method throws UnsupportedOperationException
 * @run main CollectionsWitupTest
 */

public class CollectionsWitupTest {
    public static void main(String[] args) {
        // The remove() method under test is from an anonymous inner class in Collections
        // that always throws UnsupportedOperationException.
        // We test that calling remove() indeed throws this exception.

        java.util.Iterator<Object> it = new java.util.Iterator<Object>() {
            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public Object next() {
                return null;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };

        boolean thrown = false;
        try {
            it.remove();
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }

        if (!thrown) {
            throw new RuntimeException("Expected UnsupportedOperationException was not thrown by remove()");
        }
    }
}
