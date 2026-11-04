/*
 * @test
 * @summary Tests the run() method of AtomicIntegerFieldUpdater to ensure it correctly retrieves the declared field.
 * @run main AtomicIntegerFieldUpdaterWitupTest
 */

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public class AtomicIntegerFieldUpdaterWitupTest {

    public static void main(String[] args) throws Exception {
        testRunMethodRetrievesCorrectField();
        testRunMethodThrowsWhenFieldDoesNotExist();
    }

    private static void testRunMethodRetrievesCorrectField() throws Exception {
        class Sample {
            public volatile int value;
        }
        AtomicIntegerFieldUpdater<Sample> updater = AtomicIntegerFieldUpdater.newUpdater(Sample.class, "value");
        Field field = updater.run();
        if (field == null) {
            throw new AssertionError("Field should not be null");
        }
        if (!field.getName().equals("value")) {
            throw new AssertionError("Expected field name 'value', but got: " + field.getName());
        }
        if (!field.getDeclaringClass().equals(Sample.class)) {
            throw new AssertionError("Field declaring class mismatch");
        }
        if (!field.getType().equals(int.class)) {
            throw new AssertionError("Field type mismatch");
        }
        if (!Modifier.isVolatile(field.getModifiers())) {
            throw new AssertionError("Field should be volatile");
        }
        System.out.println("testRunMethodRetrievesCorrectField passed.");
    }

    private static void testRunMethodThrowsWhenFieldDoesNotExist() {
        class Sample {
            public int otherField;
        }
        try {
            AtomicIntegerFieldUpdater<Sample> updater = AtomicIntegerFieldUpdater.newUpdater(Sample.class, "nonExistentField");
            updater.run();
            throw new AssertionError("Expected NoSuchFieldException to be thrown");
        } catch (NoSuchFieldException e) {
            System.out.println("testRunMethodThrowsWhenFieldDoesNotExist passed.");
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }
}
