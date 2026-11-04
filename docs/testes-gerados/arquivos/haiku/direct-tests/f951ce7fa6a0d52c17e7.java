/*
 * @test
 * @summary Verifies Stream.Builder.add(T) appends elements in order and returns the same builder
 * @run main StreamsWitupTest
 */
import java.util.Arrays;
import java.util.stream.Stream;

public class StreamsWitupTest {

    public static void main(String[] args) {
        testAddReturnsSameBuilder();
        testAddPreservesOrder();
        testEmptyBuilderYieldsEmptyStream();
        testSingleElement();
        testAddNullElement();
    }

    static void testAddReturnsSameBuilder() {
        Stream.Builder<String> b = Stream.builder();
        if (b.add("x") != b) {
            throw new RuntimeException("add must return the same builder instance");
        }
    }

    static void testAddPreservesOrder() {
        Stream.Builder<String> b = Stream.builder();
        b.add("a").add("b").add("c");
        Object[] arr = b.build().toArray();
        if (!Arrays.equals(arr, new Object[]{"a", "b", "c"})) {
            throw new RuntimeException("unexpected elements: " + Arrays.toString(arr));
        }
    }

    static void testEmptyBuilderYieldsEmptyStream() {
        long count = Stream.<String>builder().build().count();
        if (count != 0) {
            throw new RuntimeException("expected 0 elements but got " + count);
        }
    }

    static void testSingleElement() {
        Object[] arr = Stream.<Integer>builder().add(7).build().toArray();
        if (!Arrays.equals(arr, new Object[]{7})) {
            throw new RuntimeException("unexpected elements: " + Arrays.toString(arr));
        }
    }

    static void testAddNullElement() {
        Object[] arr = Stream.<String>builder().add(null).build().toArray();
        if (arr.length != 1 || arr[0] != null) {
            throw new RuntimeException("expected single null element but got " + Arrays.toString(arr));
        }
    }
}
