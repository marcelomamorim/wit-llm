/*
 * @test
 * @summary PreHashedMap entry setValue must throw UnsupportedOperationException
 * @modules java.base/sun.util
 * @run main PreHashedMapWitupTest
 */

import java.util.Map;
import java.util.Set;
import sun.util.PreHashedMap;

public class PreHashedMapWitupTest {

    static class Sample extends PreHashedMap<String> {
        Sample() {
            super(1, 1, 0, 0);
        }

        @Override
        protected void init(Object[] ht) {
            ht[0] = new Object[] { "k", "v" };
        }
    }

    public static void main(String[] args) {
        Sample m = new Sample();
        Set<Map.Entry<String, String>> es = m.entrySet();
        if (es.size() != 1) {
            throw new RuntimeException("unexpected size: " + es.size());
        }
        Map.Entry<String, String> e = es.iterator().next();
        if (!"k".equals(e.getKey()) || !"v".equals(e.getValue())) {
            throw new RuntimeException("unexpected entry contents");
        }
        boolean thrown = false;
        try {
            e.setValue("x");
        } catch (UnsupportedOperationException ex) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("setValue did not throw UnsupportedOperationException");
        }
        if (!"v".equals(e.getValue())) {
            throw new RuntimeException("value changed after rejected setValue");
        }
    }
}
