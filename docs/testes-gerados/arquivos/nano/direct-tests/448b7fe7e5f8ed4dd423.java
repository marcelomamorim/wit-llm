/*
 * @test
 * @summary Test for AtomicLongFieldUpdater.run() method
 * @run main AtomicLongFieldUpdaterWitupTest
 */

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;

public class AtomicLongFieldUpdaterWitupTest {

    public static void main(String[] args) throws Exception {
        testRunReturnsCorrectField();
        testRunThrowsNoSuchFieldException();
    }

    private static void testRunReturnsCorrectField() throws Exception {
        class Sample {
            public volatile long value;
        }
        AtomicLongFieldUpdater<Sample> updater = AtomicLongFieldUpdater.newUpdater(Sample.class, "value");
        Field field = updater.run();
        if (field == null) {
            throw new AssertionError("run() returned null");
        }
        if (!field.getDeclaringClass().equals(Sample.class)) {
            throw new AssertionError("Field declaring class mismatch");
        }
        if (!field.getName().equals("value")) {
            throw new AssertionError("Field name mismatch");
        }
        if (!field.getType().equals(long.class)) {
            throw new AssertionError("Field type mismatch");
        }
    }

    private static void testRunThrowsNoSuchFieldException() {
        class Sample {
            public volatile long value;
        }
        try {
            AtomicLongFieldUpdater<Sample> updater = AtomicLongFieldUpdater.newUpdater(Sample.class, "nonexistent");
            updater.run();
            throw new AssertionError("Expected NoSuchFieldException was not thrown");
        } catch (NoSuchFieldException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }
}
