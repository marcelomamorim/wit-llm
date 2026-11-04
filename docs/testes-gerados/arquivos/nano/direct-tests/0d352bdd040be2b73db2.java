/* @test    @summary Test default constructor of WeakHashMap    @run main WeakHashMapWitupTest */

import java.util.WeakHashMap;

public class WeakHashMapWitupTest {
    public static void main(String[] args) {
        WeakHashMap<Object, Object> map = new WeakHashMap<>();
        assert map != null : "WeakHashMap instance should not be null";
        assert map.size() == 0 : "New WeakHashMap should be empty";
    }
}
