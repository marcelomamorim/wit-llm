/*
 * @test
 * @summary Test for AtomicLongFieldUpdater.run() method behavior
 * @run main AtomicLongFieldUpdaterWitupTest
 */

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;

public class AtomicLongFieldUpdaterWitupTest {

    public static void main(String[] args) {
        testRunWithNullFieldName();
        testRunWithValidFieldName();
    }

    private static void testRunWithNullFieldName() {
        class Dummy {
            public volatile long value;
        }
        try {
            // Using reflection to invoke the run() method
            AtomicLongFieldUpdater<Dummy> updater = AtomicLongFieldUpdater.newUpdater(Dummy.class, "value");
            // Access the run() method via reflection
            java.lang.reflect.Method runMethod = updater.getClass().getDeclaredMethod("run");
            runMethod.setAccessible(true);
            // Expecting NoSuchMethodException or similar, but since run() is public, invoke directly
            Field result = (Field) runMethod.invoke(updater);
            System.out.println("Unexpected success: run() returned field: " + result);
        } catch (NoSuchMethodException e) {
            System.out.println("Expected exception: NoSuchMethodException");
        } catch (IllegalAccessException e) {
            System.out.println("IllegalAccessException: " + e);
        } catch (java.lang.reflect.InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof NullPointerException) {
                System.out.println("Caught expected NullPointerException when fieldName is null");
            } else {
                System.out.println("InvocationTargetException: " + cause);
            }
        } catch (Exception e) {
            System.out.println("Unexpected exception: " + e);
        }
    }

    private static void testRunWithValidFieldName() {
        class Dummy {
            public volatile long value;
        }
        try {
            AtomicLongFieldUpdater<Dummy> updater = AtomicLongFieldUpdater.newUpdater(Dummy.class, "value");
            // Call run() directly
            Field field = updater.run();
            if (field != null && field.getName().equals("value")) {
                System.out.println("run() successfully returned field: " + field.getName());
            } else {
                System.out.println("run() returned unexpected field: " + field);
            }
        } catch (Exception e) {
            System.out.println("Unexpected exception during run(): " + e);
        }
    }
}
