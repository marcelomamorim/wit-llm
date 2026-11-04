/*
 * @test
 * @summary Test for AtomicIntegerFieldUpdater behavior, especially null fieldName.
 * @run main AtomicIntegerFieldUpdaterWitupTest
 */

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public class AtomicIntegerFieldUpdaterWitupTest {

    public static void main(String[] args) {
        testNullFieldName();
        testValidFieldName();
    }

    static void testNullFieldName() {
        try {
            AtomicIntegerFieldUpdater.newUpdater(TestClass.class, null);
            System.out.println("FAIL: Expected NullPointerException for null fieldName");
        } catch (NullPointerException e) {
            System.out.println("PASS: Caught expected NullPointerException for null fieldName");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    static void testValidFieldName() {
        try {
            AtomicIntegerFieldUpdater<TestClass> updater = AtomicIntegerFieldUpdater.newUpdater(TestClass.class, "value");
            TestClass obj = new TestClass();
            int oldVal = updater.getAndSet(obj, 42);
            if (oldVal == 0 && updater.get(obj) == 42) {
                System.out.println("PASS: Successfully updated field 'value'");
            } else {
                System.out.println("FAIL: Unexpected values after update");
            }
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    static class TestClass {
        public volatile int value = 0;
    }
}
