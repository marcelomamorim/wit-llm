/*
 * @test
 * @summary Verifies Properties.forEach rejects a null action and visits all stored entries
 * @run main PropertiesWitupTest
 */

import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

public class PropertiesWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNPE();
        testForEachVisitsAllEntries();
        testForEachOnEmptyPropertiesDoesNotInvokeAction();
    }

    static void testNullActionThrowsNPE() {
        Properties props = new Properties();
        props.setProperty("a", "1");
        boolean thrown = false;
        try {
            props.forEach(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected NullPointerException for null action");
        }
    }

    static void testForEachVisitsAllEntries() {
        Properties props = new Properties();
        props.setProperty("a", "1");
        props.setProperty("b", "2");
        Properties seen = new Properties();
        AtomicInteger count = new AtomicInteger();
        props.forEach((k, v) -> {
            count.incrementAndGet();
            seen.put(k, v);
        });
        if (count.get() != 2) {
            throw new RuntimeException("Expected 2 visits but got " + count.get());
        }
        if (!seen.equals(props)) {
            throw new RuntimeException("Visited entries differ from stored entries");
        }
    }

    static void testForEachOnEmptyPropertiesDoesNotInvokeAction() {
        Properties props = new Properties();
        AtomicInteger count = new AtomicInteger();
        props.forEach((k, v) -> count.incrementAndGet());
        if (count.get() != 0) {
            throw new RuntimeException("Action invoked on empty Properties");
        }
    }
}
