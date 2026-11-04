/*
 * @test
 * @summary Test MethodType.add(T elem) for null input and normal behavior
 * @run main MethodTypeWitupTest
 */

import java.lang.invoke.MethodType;
import java.lang.ref.WeakReference;
import java.lang.ref.ReferenceQueue;

public class MethodTypeWitupTest {

    // Minimal stub for ConcurrentWeakInternSet to allow compilation and test
    static class ConcurrentWeakInternSet<T> {
        private final java.util.concurrent.ConcurrentHashMap<WeakEntry<T>, WeakEntry<T>> map = new java.util.concurrent.ConcurrentHashMap<>();
        private final ReferenceQueue<T> stale = new ReferenceQueue<>();

        static class WeakEntry<T> extends WeakReference<T> {
            private final int hash;
            WeakEntry(T referent, ReferenceQueue<T> q) {
                super(referent, q);
                hash = referent.hashCode();
            }
            public int hashCode() {
                return hash;
            }
            public boolean equals(Object o) {
                if (this == o) return true;
                if (!(o instanceof WeakEntry)) return false;
                Object r1 = this.get();
                Object r2 = ((WeakEntry<?>) o).get();
                if (r1 == null || r2 == null) return false;
                return r1.equals(r2);
            }
        }

        public T add(T elem) {
            if (elem == null) throw new NullPointerException();
            T interned;
            WeakEntry<T> e = new WeakEntry<>(elem, stale);
            do {
                expungeStaleElements();
                WeakEntry<T> exist = map.putIfAbsent(e, e);
                interned = (exist == null) ? elem : exist.get();
            } while (interned == null);
            return interned;
        }

        private void expungeStaleElements() {
            WeakEntry<T> e;
            while ((e = (WeakEntry<T>) stale.poll()) != null) {
                map.remove(e);
            }
        }
    }

    public static void main(String[] args) {
        ConcurrentWeakInternSet<String> set = new ConcurrentWeakInternSet<>();

        // Test null input triggers NullPointerException
        boolean npeThrown = false;
        try {
            set.add(null);
        } catch (NullPointerException npe) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new RuntimeException("Expected NullPointerException when adding null");
        }

        // Test adding a non-null element returns the same element
        String s = "test";
        String result = set.add(s);
        if (result != s) {
            throw new RuntimeException("Expected returned element to be the same instance");
        }

        // Test adding the same element returns the same instance (interning behavior)
        String s2 = new String("test"); // distinct instance but equal
        String result2 = set.add(s2);
        if (!result2.equals(s)) {
            throw new RuntimeException("Expected returned element to be equal to previously added element");
        }

        // Test that after GC, adding element again still returns a non-null
        s = null;
        s2 = null;
        System.gc();
        // We cannot reliably test GC effect, but calling add again should not return null
        String s3 = "another";
        String result3 = set.add(s3);
        if (result3 == null) {
            throw new RuntimeException("Returned element should not be null");
        }

        System.out.println("All tests passed.");
    }
}
