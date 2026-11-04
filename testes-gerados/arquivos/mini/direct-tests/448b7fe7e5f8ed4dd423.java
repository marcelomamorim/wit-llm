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
        AtomicLongFieldUpdaterWitupTest test = new AtomicLongFieldUpdaterWitupTest();
        test.testRunReturnsDeclaredField();
        test.testRunThrowsNoSuchFieldException();
    }

    // Test that run() returns the declared field when it exists
    public void testRunReturnsDeclaredField() throws Exception {
        final Class<TestClass> tclass = TestClass.class;
        final String fieldName = "testField";

        AtomicLongFieldUpdater<TestClass> updater = new AtomicLongFieldUpdater<TestClass>() {
            public Field run() throws NoSuchFieldException {
                return tclass.getDeclaredField(fieldName);
            }
        };

        Field f = updater.run();
        if (!f.getName().equals(fieldName)) {
            throw new AssertionError("Expected field name '" + fieldName + "' but got '" + f.getName() + "'");
        }
        if (!f.getDeclaringClass().equals(tclass)) {
            throw new AssertionError("Expected declaring class '" + tclass + "' but got '" + f.getDeclaringClass() + "'");
        }
    }

    // Test that run() throws NoSuchFieldException when field does not exist
    public void testRunThrowsNoSuchFieldException() {
        final Class<TestClass> tclass = TestClass.class;
        final String fieldName = "nonExistentField";

        AtomicLongFieldUpdater<TestClass> updater = new AtomicLongFieldUpdater<TestClass>() {
            public Field run() throws NoSuchFieldException {
                return tclass.getDeclaredField(fieldName);
            }
        };

        try {
            updater.run();
            throw new AssertionError("Expected NoSuchFieldException was not thrown");
        } catch (NoSuchFieldException e) {
            // Expected exception
        }
    }
}
