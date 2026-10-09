/*
 * @test
 * @summary Test put method of ConcurrentSkipListMap for basic insertion behavior
 * @run main ConcurrentSkipListMapWitupTest_Pa4acd5a458ec
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest_Pa4acd5a458ec {

    public static void main(String[] args) {
        testPutBasic();
        testPutNullValue();
    }

    static void testPutBasic() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        String previous = map.put(1, "one");
        assert previous == null : "Expected null for first insertion";
        String value = map.get(1);
        assert "one".equals(value) : "Expected value 'one' after insertion";
    }

    static void testPutNullValue() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        boolean exceptionThrown = false;
        try {
            map.put(2, null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Expected NullPointerException when inserting null value";
    }
}
