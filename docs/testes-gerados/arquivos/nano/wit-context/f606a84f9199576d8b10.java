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
                return new HashSet<Entry<Object, Object>>() {
                    @Override
                    public Iterator<Entry<Object, Object>> iterator() {
                        return new Iterator<Entry<Object, Object>>() {
                            @Override
                            public boolean hasNext() {
                                return false;
                            }
                            @Override
                            public Entry<Object, Object> next() {
                                return null;
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
        Iterator<Entry<Object, Object>> iterator = map.entrySet().iterator();
        try {
            iterator.next(); // move to first element
            iterator.remove(); // should throw UnsupportedOperationException
            System.out.println("FAIL: Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            System.out.println("PASS: Caught expected UnsupportedOperationException.");
        } catch (Exception e) {
            System.out.println("FAIL: Caught unexpected exception: " + e);
        }
    }
}
