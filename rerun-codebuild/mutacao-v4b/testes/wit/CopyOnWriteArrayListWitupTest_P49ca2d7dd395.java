/* @test
   @summary Testa o método listIterator com índices válidos e inválidos.
   @run main CopyOnWriteArrayListWitupTest_P49ca2d7dd395
 */
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.ListIterator;
public class CopyOnWriteArrayListWitupTest_P49ca2d7dd395 {
    public static void main(String[] args) {
        testListIteratorValidIndex();
        testListIteratorNegativeIndex();
        testListIteratorIndexExceedsSize();
    }

    static void testListIteratorValidIndex() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        try {
            ListIterator<String> it = list.listIterator(1);
            if (it == null) {
                throw new AssertionError("listIterator returned null for valid index");
            }
            // Verifica se o iterador começa na posição correta
            if (!it.hasNext()) {
                throw new AssertionError("Iterator should have next element at index 1");
            }
            String first = it.next();
            if (!"b".equals(first)) {
                throw new AssertionError("Expected 'b' at index 1, got: " + first);
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid index: " + e, e);
        }
    }

    static void testListIteratorNegativeIndex() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        list.add("x");
        list.add("y");
        list.add("z");
        boolean thrown = false;
        try {
            list.listIterator(-1);
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        } catch (Exception e) {
            throw new AssertionError("Expected IndexOutOfBoundsException, got: " + e, e);
        }
        if (!thrown) {
            throw new AssertionError("Expected IndexOutOfBoundsException for negative index");
        }
    }

    static void testListIteratorIndexExceedsSize() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        list.add("foo");
        list.add("bar");
        boolean thrown = false;
        try {
            list.listIterator(3); // size é 2
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        } catch (Exception e) {
            throw new AssertionError("Expected IndexOutOfBoundsException, got: " + e, e);
        }
        if (!thrown) {
            throw new AssertionError("Expected IndexOutOfBoundsException for index exceeding size");
        }
    }
}
