/*
 * @test
 * @summary Verify that entries from Collections.unmodifiableMap reject setValue with UnsupportedOperationException
 * @run main CollectionsWitupTest
 */
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CollectionsWitupTest {
    public static void main(String[] args) throws Exception {
        Map<String, String> base = new HashMap<>();
        base.put("k", "v");
        Map<String, String> um = Collections.unmodifiableMap(base);
        Iterator<Map.Entry<String, String>> it = um.entrySet().iterator();
        Map.Entry<String, String> e = it.next();

        if (!"k".equals(e.getKey()) || !"v".equals(e.getValue())) {
            throw new RuntimeException("unexpected initial entry state");
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
            throw new RuntimeException("entry value changed after rejected setValue");
        }
        if (!"v".equals(base.get("k"))) {
            throw new RuntimeException("backing map changed after rejected setValue");
        }

        thrown = false;
        try {
            e.setValue(null);
        } catch (UnsupportedOperationException ex) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("setValue(null) did not throw UnsupportedOperationException");
        }
    }
}
