/*
 * @test
 * @summary Verify ArrayList/subList hashCode matches the List contract and fails fast on comodification
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.List;

public class ArrayListWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static int expectedHash(List<?> list) {
        int h = 1;
        for (Object o : list) {
            h = 31 * h + (o == null ? 0 : o.hashCode());
        }
        return h;
    }

    public static void main(String[] args) {
        List<String> list = new ArrayList<>(Arrays.asList("a", null, "c", "d"));
        check(list.hashCode() == expectedHash(list), "ArrayList hashCode differs from List contract");
        check(list.hashCode() == Arrays.asList("a", null, "c", "d").hashCode(),
              "ArrayList hashCode differs from Arrays.asList equivalent");

        List<String> sub = list.subList(1, 3);
        check(sub.hashCode() == expectedHash(sub), "subList hashCode differs from List contract");
        check(sub.hashCode() == Arrays.asList(null, "c").hashCode(),
              "subList hashCode differs from equivalent list");

        check(new ArrayList<Integer>().hashCode() == 1, "empty ArrayList hashCode must be 1");
        check(list.subList(1, 1).hashCode() == 1, "empty subList hashCode must be 1");

        List<String> base = new ArrayList<>(Arrays.asList("x", "y", "z"));
        List<String> view = base.subList(0, 2);
        base.add("w");
        boolean thrown = false;
        try {
            view.hashCode();
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        check(thrown, "subList hashCode must throw ConcurrentModificationException after structural change");
    }
}
