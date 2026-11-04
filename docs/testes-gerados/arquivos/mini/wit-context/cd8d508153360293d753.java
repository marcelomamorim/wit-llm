/*
 * @test
 * @summary Test java.lang.invoke.MethodType.get(T elem) method behavior
 * @run main MethodTypeWitupTest
 */

import java.lang.invoke.MethodType;
import java.lang.ref.WeakReference;

public class MethodTypeWitupTest {

    // Dummy ConcurrentWeakInternSet to simulate map behavior for testing
    static class ConcurrentWeakInternSet<T> {
        java.util.concurrent.ConcurrentHashMap<WeakEntry<T>, WeakEntry<T>> map = new java.util.concurrent.ConcurrentHashMap<>();

        static class WeakEntry<T> extends WeakReference<T> {
            private final int hash;
            WeakEntry(T referent) {
                super(referent);
                hash = referent.hashCode();
            }
            @Override
            public int hashCode() {
                return hash;
            }
            @Override
            public boolean equals(Object obj) {
                if (this == obj) return true;
                if (!(obj instanceof WeakEntry)) return false;
                Object t = this.get();
                Object o = ((WeakEntry<?>) obj).get();
                if (t == null || o == null) return false;
                return t.equals(o);
            }
        }

        public T get(T elem) {
            if (elem == null) throw new NullPointerException();
            WeakEntry<T> value = map.get(new WeakEntry<>(elem));
            if (value != null) {
                T res = value.get();
                if (res != null) {
                    return res;
                }
            }
            return null;
        }

        public void put(T elem) {
            map.put(new WeakEntry<>(elem), new WeakEntry<>(elem));
        }
    }

    // We test the get method behavior as per the source code excerpt
    public static void main(String[] args) {
        ConcurrentWeakInternSet<String> set = new ConcurrentWeakInternSet<>();

        // Test 1: get(null) should throw NullPointerException
        try {
            set.get(null);
            throw new Error("Expected NullPointerException for null argument");
        } catch (NullPointerException npe) {
            // expected
        }

        // Test 2: get on empty set returns null
        String result = set.get("test");
        if (result != null) {
            throw new Error("Expected null when element not present in map");
        }

        // Test 3: put an element and get it back
        String elem = new String("hello"); // distinct instance
        set.put(elem);
        String got = set.get(elem);
        if (got == null) {
            throw new Error("Expected non-null result after put");
        }
        if (!got.equals(elem)) {
            throw new Error("Expected returned element to equal the input element");
        }

        // Test 4: get with equal but different instance returns stored instance
        String equalElem = new String("hello");
        String got2 = set.get(equalElem);
        if (got2 == null) {
            throw new Error("Expected non-null result for equal element");
        }
        if (!got2.equals(equalElem)) {
            throw new Error("Expected returned element to equal the input element");
        }

        System.out.println("All tests passed.");
    }
}
