/* @test    @summary Test setValue method of AbstractMap throws UnsupportedOperationException    @run main AbstractMapWitupTest */

import java.util.AbstractMap;

public class AbstractMapWitupTest {

    public static void main(String[] args) {
        testSetValueThrowsException();
    }

    public static void testSetValueThrowsException() {
        AbstractMap<Object, Object> map = new AbstractMap<Object, Object>() {
            @Override
            public java.util.Set<java.util.Map.Entry<Object, Object>> entrySet() {
                return null;
            }
        };
        try {
            map.setValue(null);
            // If no exception, test fails
            throw new AssertionError("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException e) {
            // Expected exception, test passes
        }
    }
}
