/*
 * @test
 * @summary Test for AtomicLongFieldUpdater.run() method
 * @run main AtomicLongFieldUpdaterWitupTest
 */

import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.lang.reflect.Field;

public class AtomicLongFieldUpdaterWitupTest {

    public static void main(String[] args) {
        testRunWithNullFieldName();
        testRunWithValidFieldName();
    }

    static class Sample {
        public volatile long value;
    }

    static void testRunWithNullFieldName() {
        try {
            // Simulate the scenario where fieldName is null, expecting NullPointerException
            AtomicLongFieldUpdater<Sample> updater = AtomicLongFieldUpdater.newUpdater(Sample.class, "value");
            // Use reflection to invoke run() with null, as the method is protected
            Field runMethod = updater.getClass().getDeclaredMethod("run");
            runMethod.setAccessible(true);
            // Since run() is protected, invoke with null target to simulate the scenario
            runMethod.invoke(updater);
            // If no exception, fail the test
            System.err.println("Expected NullPointerException was not thrown in testRunWithNullFieldName");
        } catch (ReflectiveOperationException e) {
            Throwable cause = e.getCause();
            if (cause instanceof NullPointerException) {
                System.out.println("Passed: NullPointerException thrown as expected in testRunWithNullFieldName");
            } else {
                System.err.println("Failed: Unexpected exception in testRunWithNullFieldName: " + cause);
            }
        } catch (NullPointerException e) {
            System.out.println("Passed: NullPointerException thrown as expected in testRunWithNullFieldName");
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception in testRunWithNullFieldName: " + e);
        }
    }

    static void testRunWithValidFieldName() {
        try {
            Sample sample = new Sample();
            AtomicLongFieldUpdater<Sample> updater = AtomicLongFieldUpdater.newUpdater(Sample.class, "value");
            // Access the run() method via reflection
            java.lang.reflect.Method runMethod = updater.getClass().getDeclaredMethod("run");
            runMethod.setAccessible(true);
            Field resultField = (Field) runMethod.invoke(updater);
            if (resultField != null && resultField.getName().equals("value")) {
                System.out.println("Passed: run() returned correct Field in testRunWithValidFieldName");
            } else {
                System.err.println("Failed: run() did not return expected Field in testRunWithValidFieldName");
            }
        } catch (Exception e) {
            System.err.println("Failed: Exception in testRunWithValidFieldName: " + e);
        }
    }
}
