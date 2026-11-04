/*
 * @test
 * @summary Test AtomicIntegerFieldUpdater run() method for declared field retrieval
 * @run main AtomicIntegerFieldUpdaterWitupTest
 */

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public class AtomicIntegerFieldUpdaterWitupTest {

    static class TestClass {
        volatile int testField = 42;
    }

    public static void main(String[] args) throws Exception {
        final Class<TestClass> clazz = TestClass.class;
        final String fieldName = "testField";

        AtomicIntegerFieldUpdater<TestClass> updater = new AtomicIntegerFieldUpdater<TestClass>() {
            public Field run() throws NoSuchFieldException {
                return clazz.getDeclaredField(fieldName);
            }
        };

        // Test that run() returns the correct Field object
        Field f = updater.run();
        if (!f.getName().equals(fieldName)) {
            throw new AssertionError("Field name mismatch: expected " + fieldName + " but got " + f.getName());
        }

        // Test that run() throws NoSuchFieldException for non-existent field
        boolean threw = false;
        try {
            clazz.getDeclaredField("nonExistentField");
        } catch (NoSuchFieldException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected NoSuchFieldException for non-existent field");
        }
    }
}
