/* @test
   @summary Testa o método forEach de ConcurrentSkipListMap para comportamento padrão.
   @run main ConcurrentSkipListMapWitupTest_Pa20fca6d9b95
 */
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.function.BiConsumer;

public class ConcurrentSkipListMapWitupTest_Pa20fca6d9b95 {
    public static void main(String[] args) {
        testForEachWithNullAction();
        testForEachWithValidAction();
    }

    private static void testForEachWithNullAction() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        map.put(2, "two");
        boolean exceptionThrown = false;
        try {
            map.forEach(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when action is null");
        }
    }

    private static void testForEachWithValidAction() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        map.put(2, "two");
        StringBuilder result = new StringBuilder();
        BiConsumer<Integer, String> consumer = (k, v) -> result.append(k).append(":").append(v).append(",");
        map.forEach(consumer);
        String output = result.toString();
        if (!output.contains("1:one") || !output.contains("2:two")) {
            throw new AssertionError("forEach did not process all entries correctly. Output: " + output);
        }
    }
}
