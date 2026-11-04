/*
 * @test
 * @summary Test AtomicIntegerFieldUpdater run() method for expected exceptions and behavior
 * @run main AtomicIntegerFieldUpdaterWitupTest
 */

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public class AtomicIntegerFieldUpdaterWitupTest {

    static class TestUpdater extends AtomicIntegerFieldUpdater<Object> {
        private final Class<?> tclass;
        private final String fieldName;

        TestUpdater(Class<?> tclass, String fieldName) {
            this.tclass = tclass;
            this.fieldName = fieldName;
        }

        // Implement the run method as per the source excerpt
        public Field run() throws NoSuchFieldException {
            return tclass.getDeclaredField(fieldName);
        }
    }

    public static void main(String[] args) throws Exception {
        // Test 1: Normal case - fieldName is a valid field
        TestUpdater updater = new TestUpdater(TestClass.class, "value");
        Field f = updater.run();
        if (!"value".equals(f.getName())) {
            throw new AssertionError("Expected field 'value', got: " + f.getName());
        }

        // Test 2: NoSuchFieldException when field does not exist
        boolean noSuchFieldThrown = false;
        try {
            TestUpdater updater2 = new TestUpdater(TestClass.class, "nonExistentField");
            updater2.run();
        } catch (NoSuchFieldException e) {
            noSuchFieldThrown = true;
        }
        if (!noSuchFieldThrown) {
            throw new AssertionError("Expected NoSuchFieldException for non-existent field");
        }

        // Test 3: NullPointerException when fieldName is null
        boolean npeThrown = false;
        try {
            TestUpdater updater3 = new TestUpdater(TestClass.class, null);
            updater3.run();
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("Expected NullPointerException when fieldName is null");
        }

        System.out.println("All tests passed.");
    }

    static class TestClass {
        volatile int value = 42;
    }
}
