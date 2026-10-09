/* @test    @summary Test the put method of AbstractMap to ensure it throws UnsupportedOperationException.    @run main AbstractMapWitupTest_P317e7bba9145 */

import java.util.AbstractMap;

public class AbstractMapWitupTest_P317e7bba9145 {

    public static void main(String[] args) {
        testPutThrowsException();
    }

    public static void testPutThrowsException() {
        AbstractMap<Object, Object> map = new AbstractMap<Object, Object>() {
            @Override
            public java.util.Set<java.util.Map.Entry<Object, Object>> entrySet() {
                return null;
            }
        };
        try {
            map.put("key", "value");
            throw new AssertionError("Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            // Test passes as exception is expected
        }
    }
}
