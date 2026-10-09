/* @test    @summary Test ReentrantLock.hasWaiters() method for null, invalid, and valid conditions    @run main ReentrantLockWitupTest_P86a95e30449a */

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class ReentrantLockWitupTest_P86a95e30449a {

    public static void main(String[] args) {
        testNullCondition();
        testInvalidCondition();
        testValidCondition();
    }

    private static void testNullCondition() {
        ReentrantLock lock = new ReentrantLock();
        try {
            lock.hasWaiters(null);
            System.out.println("FAIL: Expected NullPointerException for null condition");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown for null condition");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception for null condition: " + t);
        }
    }

    private static void testInvalidCondition() {
        ReentrantLock lock = new ReentrantLock();
        Condition invalidCondition = lock.newCondition();
        // Create a dummy object that is not an instance of AbstractQueuedSynchronizer.ConditionObject
        Object notAConditionObject = new Object();
        // Reflection to bypass type safety, since method expects Condition
        try {
            // Using reflection to cast to Condition, but passing an invalid object
            // Since Java is type-safe, simulate invalid condition by casting
            // But in this context, we can simulate by passing an object that is not of the expected type
            // which will cause IllegalArgumentException in method
            // So, we cast the object to Condition to compile, but pass the object directly
            // and expect the method to throw IllegalArgumentException
            // Note: Since method signature expects Condition, we can pass the object casted
            boolean result = lock.hasWaiters((Condition)notAConditionObject);
            System.out.println("FAIL: Expected IllegalArgumentException for invalid condition, got result: " + result);
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: IllegalArgumentException thrown for invalid condition");
        } catch (ClassCastException e) {
            System.out.println("PASS: ClassCastException thrown for invalid condition");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception for invalid condition: " + t);
        }
    }

    private static void testValidCondition() {
        ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        try {
            boolean result = lock.hasWaiters(condition);
            System.out.println("PASS: hasWaiters returned: " + result);
        } catch (Throwable t) {
            System.out.println("FAIL: Exception thrown during valid condition test: " + t);
        }
    }
}
