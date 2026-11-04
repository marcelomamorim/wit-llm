/*
 * @test
 * @summary Test for AtomicReferenceFieldUpdater.run() method
 * @run main AtomicReferenceFieldUpdaterWitupTest
 */

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

public class AtomicReferenceFieldUpdaterWitupTest {

    public static void main(String[] args) throws Exception {
        testRunWithNullFieldName();
        testRunWithValidFieldName();
    }

    static void testRunWithNullFieldName() {
        AtomicReferenceFieldUpdater<Object, Object> updater = null;
        Object target = new Object();
        try {
            updater = AtomicReferenceFieldUpdater.newUpdater(Object.class, Object.class, "nonexistentField");
            // Intentionally passing null to simulate null field name
            updater.run();
            // If no exception, test fails
            System.err.println("Expected NullPointerException not thrown in testRunWithNullFieldName");
        } catch (NullPointerException e) {
            // Expected exception
            System.out.println("Caught expected NullPointerException in testRunWithNullFieldName");
        } catch (Exception e) {
            System.err.println("Unexpected exception in testRunWithNullFieldName: " + e);
        }
    }

    static void testRunWithValidFieldName() throws Exception {
        class TestClass {
            public volatile String testField = "initial";
        }
        TestClass obj = new TestClass();
        AtomicReferenceFieldUpdater<TestClass, String> updater = AtomicReferenceFieldUpdater.newUpdater(TestClass.class, String.class, "testField");
        Field field = updater.run();
        if (field != null && field.getName().equals("testField")) {
            System.out.println("Successfully retrieved field in testRunWithValidFieldName");
        } else {
            System.err.println("Failed to retrieve correct field in testRunWithValidFieldName");
        }
    }
}
