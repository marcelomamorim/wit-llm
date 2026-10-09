/* @test    @summary Test the put method of AbstractMap for expected exception behavior    @run main AbstractMapWitupTest_Pa44320f56563 */

import java.util.AbstractMap;

public class AbstractMapWitupTest_Pa44320f56563 {
    public static void main(String[] args) {
        testPutMethodThrowsException();
    }

    public static void testPutMethodThrowsException() {
        AbstractMap<Object, Object> map = new AbstractMap<Object, Object>() {
            @Override
            public Set<Entry<Object, Object>> entrySet() {
                return null;
            }
        };
        try {
            map.put("key", "value");
            throw new AssertionError("Expected UnsupportedOperationException was not thrown");
        } catch (UnsupportedOperationException e) {
            // Test passes, exception was thrown as expected
        }
    }
}
