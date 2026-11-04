/*
 * @test
 * @summary Verify that setValue on entries created by Map.entry always throws UnsupportedOperationException and leaves the entry unchanged
 * @run main KeyValueHolderWitupTest
 */

import java.util.Map;

public class KeyValueHolderWitupTest {

    public static void main(String[] args) {
        Map.Entry<String, String> e = Map.entry("key", "value");
        expectUnsupported(e, "other");
        expectUnsupported(e, null);
        checkUnchanged(e, "key", "value");

        Map.Entry<Integer, Integer> n = Map.entry(1, 2);
        expectUnsupported(n, 3);
        if (n.getKey().intValue() != 1 || n.getValue().intValue() != 2) {
            throw new RuntimeException("entry changed after failed setValue");
        }
    }

    private static <K, V> void expectUnsupported(Map.Entry<K, V> e, V v) {
        try {
            e.setValue(v);
        } catch (UnsupportedOperationException expected) {
            return;
        }
        throw new RuntimeException("setValue did not throw UnsupportedOperationException");
    }

    private static void checkUnchanged(Map.Entry<String, String> e, String k, String v) {
        if (!k.equals(e.getKey()) || !v.equals(e.getValue())) {
            throw new RuntimeException("entry changed after failed setValue");
        }
    }
}
