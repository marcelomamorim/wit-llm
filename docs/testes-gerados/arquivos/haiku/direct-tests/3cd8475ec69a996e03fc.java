/*
 * @test
 * @summary Tests observable behavior of ReferencePipeline.toArray(IntFunction)
 * @run main ReferencePipelineWitupTest
 */
import java.util.stream.Stream;

public class ReferencePipelineWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        String[] arr = Stream.of("a", "b", "c").toArray(String[]::new);
        check(arr.length == 3, "expected length 3 but was " + arr.length);
        check("a".equals(arr[0]), "unexpected element at index 0");
        check("b".equals(arr[1]), "unexpected element at index 1");
        check("c".equals(arr[2]), "unexpected element at index 2");

        String[] empty = Stream.<String>empty().toArray(String[]::new);
        check(empty.length == 0, "expected empty array but was length " + empty.length);

        int[] requested = {-1};
        Integer[] ints = Stream.of(1, 2, 3).toArray(n -> {
            requested[0] = n;
            return new Integer[n];
        });
        check(requested[0] == 3, "generator expected size 3 but got " + requested[0]);
        check(ints.length == 3, "expected length 3 but was " + ints.length);
        check(ints[0] == 1 && ints[1] == 2 && ints[2] == 3, "unexpected Integer contents");

        boolean thrown = false;
        try {
            Stream.of("x").toArray(Integer[]::new);
        } catch (ArrayStoreException e) {
            thrown = true;
        }
        check(thrown, "expected ArrayStoreException when storing String into Integer[]");
    }
}
