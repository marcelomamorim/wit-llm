/*
 * @test
 * @summary Verify Vector.lastElement returns the last element and throws NoSuchElementException when empty
 * @run main VectorWitupTest
 */
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Vector;

public class VectorWitupTest {

    public static void main(String[] args) {
        testEmptyVectorThrows();
        testSingleElement();
        testMultipleElements();
        testAfterRemoval();
        testAfterRemoveAllElements();
        testFromCollection();
    }

    static void testEmptyVectorThrows() {
        Vector<String> v = new Vector<>();
        expectNoSuchElement(v);
    }

    static void testSingleElement() {
        Vector<String> v = new Vector<>();
        v.add("only");
        check("only".equals(v.lastElement()), "single element");
    }

    static void testMultipleElements() {
        Vector<String> v = new Vector<>();
        v.add("a");
        v.add("b");
        v.add("c");
        check("c".equals(v.lastElement()), "last of three");
        check(v.size() == 3, "size unchanged by lastElement");
    }

    static void testAfterRemoval() {
        Vector<String> v = new Vector<>();
        v.add("a");
        v.add("b");
        v.add("c");
        v.remove("c");
        check("b".equals(v.lastElement()), "last after removal");
    }

    static void testAfterRemoveAllElements() {
        Vector<String> v = new Vector<>();
        v.add("x");
        v.removeAllElements();
        expectNoSuchElement(v);
    }

    static void testFromCollection() {
        Vector<Integer> v = new Vector<>(Arrays.asList(1, 2, 3));
        check(Integer.valueOf(3).equals(v.lastElement()), "last from collection constructor");
    }

    static void expectNoSuchElement(Vector<String> v) {
        boolean thrown = false;
        try {
            v.lastElement();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected NoSuchElementException");
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("failed: " + message);
        }
    }
}
