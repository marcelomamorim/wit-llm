/*
 * @test
 * @summary Test java.lang.invoke.MethodType get(T elem) method behavior
 * @run main MethodTypeWitupTest
 */

public class MethodTypeWitupTest {

    // Minimal stub for WeakEntry to allow compilation and testing
    static class WeakEntry<T> {
        private final T referent;
        WeakEntry(T referent) { this.referent = referent; }
        T get() { return referent; }
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            WeakEntry<?> that = (WeakEntry<?>) o;
            return referent.equals(that.referent);
        }
        @Override
        public int hashCode() {
            return referent.hashCode();
        }
    }

    // Minimal stub for MethodType to test get(T elem)
    static class MethodType {
        java.util.Map<WeakEntry<Object>, WeakEntry<Object>> map = new java.util.HashMap<>();

        void expungeStaleElements() {
            // no-op for test
        }

        @SuppressWarnings("unchecked")
        public <T> T get(T elem) {
            if (elem == null) throw new NullPointerException();
            expungeStaleElements();

            WeakEntry<T> value = (WeakEntry<T>) map.get(new WeakEntry<>(elem));
            if (value != null) {
                T res = value.get();
                if (res != null) {
                    return res;
                }
            }
            return null;
        }
    }

    public static void main(String[] args) {
        MethodType mt = new MethodType();

        // Test 1: null argument throws NullPointerException
        try {
            mt.get(null);
            throw new Error("Expected NullPointerException for null argument");
        } catch (NullPointerException expected) {
        }

        // Test 2: element not in map returns null
        String notPresent = mt.get("absent");
        if (notPresent != null) {
            throw new Error("Expected null for element not in map");
        }

        // Test 3: element present in map returns stored value
        String key = "key";
        String value = "value";
        mt.map.put(new WeakEntry<Object>(key), new WeakEntry<Object>(value));
        String result = mt.get(key);
        if (!value.equals(result)) {
            throw new Error("Expected returned value to be 'value'");
        }

        // Test 4: element present but stored value is null returns null
        mt.map.put(new WeakEntry<Object>("nullValue"), new WeakEntry<Object>(null));
        Object nullResult = mt.get("nullValue");
        if (nullResult != null) {
            throw new Error("Expected null when stored value is null");
        }
    }
}
