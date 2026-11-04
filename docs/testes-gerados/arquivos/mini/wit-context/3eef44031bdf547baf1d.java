/*
 * @test
 * @summary Test AtomicReferenceFieldUpdater.run() method for expected exceptions and behavior
 * @run main AtomicReferenceFieldUpdaterWitupTest
 */

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

public class AtomicReferenceFieldUpdaterWitupTest {

    // Minimal subclass to expose run() method for testing
    static class TestUpdater extends AtomicReferenceFieldUpdater<Object, Object> {
        private final Class<?> tclass;
        private final String fieldName;

        protected TestUpdater(Class<?> tclass, String fieldName) {
            super();
            this.tclass = tclass;
            this.fieldName = fieldName;
        }

        @Override
        public Field run() throws NoSuchFieldException {
            return tclass.getDeclaredField(fieldName);
        }

        // Abstract methods must be implemented but can throw UnsupportedOperationException
        public boolean compareAndSet(Object obj, Object expect, Object update) {
            throw new UnsupportedOperationException();
        }
        public Object get(Object obj) {
            throw new UnsupportedOperationException();
        }
        public void set(Object obj, Object newValue) {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) throws Exception {
        testRunWithValidField();
        testRunWithNullFieldName();
        testRunWithNonexistentField();
    }

    private static void testRunWithValidField() throws Exception {
        // Use a known class and field
        TestUpdater updater = new TestUpdater(String.class, "value");
        Field f = updater.run();
        if (!"value".equals(f.getName())) {
            throw new AssertionError("Expected field name 'value', got: " + f.getName());
        }
    }

    private static void testRunWithNullFieldName() {
        try {
            TestUpdater updater = new TestUpdater(String.class, null);
            updater.run();
            throw new AssertionError("Expected NullPointerException for null fieldName");
        } catch (NullPointerException npe) {
            // Expected
        } catch (Exception e) {
            throw new AssertionError("Expected NullPointerException but got " + e);
        }
    }

    private static void testRunWithNonexistentField() {
        try {
            TestUpdater updater = new TestUpdater(String.class, "nonexistentField");
            updater.run();
            throw new AssertionError("Expected NoSuchFieldException for nonexistent field");
        } catch (NoSuchFieldException nsfe) {
            // Expected
        } catch (Exception e) {
            throw new AssertionError("Expected NoSuchFieldException but got " + e);
        }
    }
}
