/*
 * @test
 * @summary Test AtomicReferenceFieldUpdater.run() method for correct Field retrieval and exception
 * @run main AtomicReferenceFieldUpdaterWitupTest
 */

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

public class AtomicReferenceFieldUpdaterWitupTest {

    static class TestClass {
        private volatile String testField;
    }

    public static void main(String[] args) throws Exception {
        testRunReturnsDeclaredField();
        testRunThrowsNoSuchFieldException();
    }

    private static void testRunReturnsDeclaredField() throws Exception {
        AtomicReferenceFieldUpdater<TestClass, String> updater = new AtomicReferenceFieldUpdater<TestClass, String>() {
            private final Class<TestClass> tclass = TestClass.class;
            private final String fieldName = "testField";

            @Override
            public Field run() throws NoSuchFieldException {
                return tclass.getDeclaredField(fieldName);
            }

            @Override
            public boolean compareAndSet(TestClass obj, String expect, String update) {
                throw new UnsupportedOperationException();
            }

            @Override
            public String get(TestClass obj) {
                throw new UnsupportedOperationException();
            }

            @Override
            public void set(TestClass obj, String newValue) {
                throw new UnsupportedOperationException();
            }
        };

        Field f = updater.run();
        if (!"testField".equals(f.getName())) {
            throw new AssertionError("Field name mismatch: expected 'testField' but got " + f.getName());
        }
        if (!f.getDeclaringClass().equals(TestClass.class)) {
            throw new AssertionError("Field declaring class mismatch");
        }
    }

    private static void testRunThrowsNoSuchFieldException() {
        AtomicReferenceFieldUpdater<TestClass, String> updater = new AtomicReferenceFieldUpdater<TestClass, String>() {
            private final Class<TestClass> tclass = TestClass.class;
            private final String fieldName = "nonExistentField";

            @Override
            public Field run() throws NoSuchFieldException {
                return tclass.getDeclaredField(fieldName);
            }

            @Override
            public boolean compareAndSet(TestClass obj, String expect, String update) {
                throw new UnsupportedOperationException();
            }

            @Override
            public String get(TestClass obj) {
                throw new UnsupportedOperationException();
            }

            @Override
            public void set(TestClass obj, String newValue) {
                throw new UnsupportedOperationException();
            }
        };

        try {
            updater.run();
            throw new AssertionError("Expected NoSuchFieldException not thrown");
        } catch (NoSuchFieldException e) {
            // Expected exception
        }
    }
}
