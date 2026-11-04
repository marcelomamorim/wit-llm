/*
 * @test
 * @summary Test AtomicLongFieldUpdater.run() method for correct field retrieval.
 * @run main AtomicLongFieldUpdaterWitupTest
 */

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;

public class AtomicLongFieldUpdaterWitupTest {

    public static void main(String[] args) throws Exception {
        testRunMethodReturnsCorrectField();
        testRunMethodThrowsWhenFieldDoesNotExist();
    }

    static class Sample {
        public volatile long value;
        private volatile long hidden;
    }

    static void testRunMethodReturnsCorrectField() throws Exception {
        AtomicLongFieldUpdater<Sample> updater = AtomicLongFieldUpdater.newUpdater(Sample.class, "value");
        Field field = updater.run();
        if (field == null) {
            throw new AssertionError("Field should not be null");
        }
        if (!field.getName().equals("value")) {
            throw new AssertionError("Expected field name 'value', got: " + field.getName());
        }
        if (!field.getDeclaringClass().equals(Sample.class)) {
            throw new AssertionError("Field declaring class mismatch");
        }
    }

    static void testRunMethodThrowsWhenFieldDoesNotExist() {
        try {
            AtomicLongFieldUpdater<Sample> updater = AtomicLongFieldUpdater.newUpdater(Sample.class, "nonExistentField");
            updater.run();
            throw new AssertionError("Expected NoSuchFieldException to be thrown");
        } catch (NoSuchFieldException e) {
            // expected
        }
    }
}
