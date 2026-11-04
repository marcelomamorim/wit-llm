/* @test    @summary Test for AbstractMap.remove() method to verify UnsupportedOperationException behavior    @run main AbstractMapWitupTest */

import java.util.AbstractMap;
import java.util.Iterator;

public class AbstractMapWitupTest {

    public static void main(String[] args) {
        testRemoveThrowsException();
    }

    public static void testRemoveThrowsException() {
        AbstractMap<Object, Object> map = new AbstractMap<Object, Object>() {
            @Override
            public Set<Entry<Object, Object>> entrySet() {
                return new java.util.HashSet<Entry<Object, Object>>() {
                    @Override
                    public Iterator<Entry<Object, Object>> iterator() {
                        return new Iterator<Entry<Object, Object>>() {
                            @Override
                            public boolean hasNext() {
                                return true;
                            }
                            @Override
                            public Entry<Object, Object> next() {
                                return new Entry<Object, Object>() {
                                    @Override
                                    public Object getKey() {
                                        return "key";
                                    }
                                    @Override
                                    public Object getValue() {
                                        return "value";
                                    }
                                    @Override
                                    public Object setValue(Object value) {
                                        return null;
                                    }
                                };
                            }
                            @Override
                            public void remove() {
                                throw new UnsupportedOperationException();
                            }
                        };
                    }
                };
            }
        };
        try {
            map.remove();
            // If no exception, fail the test
            throw new AssertionError("Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            // Test passes
        }
    }
}
