/* @test    @summary Test for each() method in ConcurrentSkipListMap ensuring NullPointerException is thrown when null is passed as action    @run main ConcurrentSkipListMapWitupTest_P6a07da82e8e7 */

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.function.BiConsumer;

public class ConcurrentSkipListMapWitupTest_P6a07da82e8e7 {

    public static void main(String[] args) {
        testForEachWithNullAction();
    }

    public static void testForEachWithNullAction() {
        ConcurrentSkipListMap<String, Integer> map = new ConcurrentSkipListMap<>();
        map.put("a", 1);
        map.put("b", 2);
        try {
            map.forEach(null);
            System.err.println("Expected NullPointerException was not thrown.");
        } catch (NullPointerException expected) {
            System.out.println("NullPointerException correctly thrown when passing null to forEach.");
        } catch (Throwable t) {
            System.err.println("Unexpected exception type: " + t);
        }
    }
}
