/*
 * @test
 * @summary Test AtomicLongFieldUpdater.run() method behavior
 * @run main AtomicLongFieldUpdaterWitupTest
 */

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;

public class AtomicLongFieldUpdaterWitupTest {

    static class TestClass {
        volatile long testField;
    }

    public static void main(String[] args) throws Exception {
        AtomicLongFieldUpdater<TestClass> updater = new AtomicLongFieldUpdater<TestClass>() {
            final Class<TestClass> tclass = TestClass.class;
            final String fieldName = "testField";

            @Override
            public Field run() throws NoSuchFieldException {
                return tclass.getDeclaredField(fieldName);
            }
        };

        // Test that run() returns the correct Field object
        Field f = updater.run();
        if (!"testField".equals(f.getName())) {
            throw new RuntimeException("Field name mismatch: expected 'testField' but got " + f.getName());
        }

        // Test that run() throws NoSuchFieldException for non-existent field
        AtomicLongFieldUpdater<TestClass> updaterBad = new AtomicLongFieldUpdater<TestClass>() {
            final Class<TestClass> tclass = TestClass.class;
            final String fieldName = "nonExistentField";

            @Override
            public Field run() throws NoSuchFieldException {
                return tclass.getDeclaredField(fieldName);
            }
        };

        boolean threw = false;
        try {
            updaterBad.run();
        } catch (NoSuchFieldException e) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("Expected NoSuchFieldException was not thrown");
        }
    }
}
