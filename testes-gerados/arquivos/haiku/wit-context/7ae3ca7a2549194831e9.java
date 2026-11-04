/*
 * @test
 * @summary Witup checks for LongPipeline.mapToObj null-mapper handling and element mapping through public LongStream API
 * @run main LongPipelineWitupTest
 */
import java.util.Arrays;
import java.util.stream.LongStream;

public class LongPipelineWitupTest {

    public static void main(String[] args) {
        testNullMapperThrowsNPE();
        testMapToObjMapsElementsInOrder();
        testEmptyStreamMapsToEmpty();
    }

    static void testNullMapperThrowsNPE() {
        boolean thrown = false;
        try {
            LongStream.of(1L, 2L).mapToObj(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected NullPointerException for null mapper");
        }
    }

    static void testMapToObjMapsElementsInOrder() {
        Object[] result = LongStream.of(1L, 2L, 3L)
                .mapToObj(x -> "v" + x)
                .toArray();
        Object[] expected = new Object[] {"v1", "v2", "v3"};
        if (!Arrays.equals(result, expected)) {
            throw new RuntimeException("Unexpected mapping result: " + Arrays.toString(result));
        }
    }

    static void testEmptyStreamMapsToEmpty() {
        long count = LongStream.empty()
                .mapToObj(x -> x)
                .count();
        if (count != 0L) {
            throw new RuntimeException("Expected empty result, got count " + count);
        }
    }
}
