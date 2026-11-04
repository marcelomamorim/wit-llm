/*
 * @test
 * @summary Basic observable behavior of the Vector(int initialCapacity) constructor
 * @run main VectorWitupTest
 */

import java.util.Vector;

public class VectorWitupTest {

    public static void main(String[] args) {
        Vector<String> v = new Vector<>(10);
        check(v.isEmpty(), "new Vector(10) should be empty");
        check(v.size() == 0, "new Vector(10) size should be 0");

        v.add("a");
        v.add("b");
        check(v.size() == 2, "size should be 2 after two adds");
        check("a".equals(v.get(0)), "first element should be a");
        check("b".equals(v.get(1)), "second element should be b");

        Vector<Integer> zero = new Vector<>(0);
        check(zero.isEmpty(), "new Vector(0) should be empty");
        for (int i = 0; i < 100; i++) {
            zero.add(i);
        }
        check(zero.size() == 100, "growth from initial capacity 0 should keep all elements");
        check(zero.get(99) == 99, "last element should be 99");

        Vector<Integer> one = new Vector<>(1);
        one.add(7);
        one.add(8);
        one.add(0, 6);
        check(one.size() == 3, "size should be 3 after insert");
        check(one.get(0) == 6 && one.get(1) == 7 && one.get(2) == 8,
              "insertion order should be preserved");

        check(new Vector<String>(5).equals(new Vector<String>()),
              "empty vectors created with different initial capacities should be equal");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
