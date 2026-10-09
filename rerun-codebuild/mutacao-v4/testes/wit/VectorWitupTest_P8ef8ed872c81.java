/* @test
   @summary Testa o método lastIndexOf de Vector para diferentes cenários.
   @run main VectorWitupTest_P8ef8ed872c81
 */
import java.util.Vector;
public class VectorWitupTest_P8ef8ed872c81 {
    public static void main(String[] args) {
        testLastIndexOfNullObject();
        testLastIndexOfExistingObject();
        testLastIndexOfNonExistingObject();
        testLastIndexOfNullObjectWithEmptyVector();
        testLastIndexOfObjectWithEmptyVector();
        testLastIndexOfObjectWithNullObject();
    }

    static void testLastIndexOfNullObject() {
        Vector<String> v = new Vector<>();
        v.add(null);
        v.add("a");
        v.add(null);
        int index = v.lastIndexOf(null, v.size() - 1);
        if (index != 2) {
            throw new AssertionError("Expected index 2, got " + index);
        }
    }

    static void testLastIndexOfExistingObject() {
        Vector<String> v = new Vector<>();
        v.add("x");
        v.add("y");
        v.add("z");
        v.add("y");
        int index = v.lastIndexOf("y", v.size() - 1);
        if (index != 3) {
            throw new AssertionError("Expected index 3, got " + index);
        }
    }

    static void testLastIndexOfNonExistingObject() {
        Vector<String> v = new Vector<>();
        v.add("a");
        v.add("b");
        v.add("c");
        int index = v.lastIndexOf("d", v.size() - 1);
        if (index != -1) {
            throw new AssertionError("Expected -1, got " + index);
        }
    }

    static void testLastIndexOfNullObjectWithEmptyVector() {
        Vector<String> v = new Vector<>();
        int index = v.lastIndexOf(null, 0);
        if (index != -1) {
            throw new AssertionError("Expected -1, got " + index);
        }
    }

    static void testLastIndexOfObjectWithEmptyVector() {
        Vector<String> v = new Vector<>();
        int index = v.lastIndexOf("a", 0);
        if (index != -1) {
            throw new AssertionError("Expected -1, got " + index);
        }
    }

    static void testLastIndexOfObjectWithNullObject() {
        Vector<String> v = new Vector<>();
        v.add("a");
        int index = v.lastIndexOf(null, 0);
        if (index != -1) {
            throw new AssertionError("Expected -1, got " + index);
        }
    }
}
