/*
 * @test
 * @summary AbstractMap.SimpleImmutableEntry.setValue must throw UnsupportedOperationException and keep the value unchanged
 * @run main AbstractMapWitupTest
 */

import java.util.AbstractMap;

public class AbstractMapWitupTest {

    public static void main(String[] args) {
        testSetValueThrowsAndKeepsValue();
        testSetValueWithNullThrows();
        testSetValueRepeatedCallsThrowAndKeepValue();
    }

    static void testSetValueThrowsAndKeepsValue() {
        AbstractMap.SimpleImmutableEntry<String, Integer> entry =
                new AbstractMap.SimpleImmutableEntry<>("k", 1);
        boolean thrown = false;
        try {
            entry.setValue(2);
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("setValue did not throw UnsupportedOperationException");
        }
        if (!entry.getValue().equals(1)) {
            throw new RuntimeException("value changed after failed setValue: " + entry.getValue());
        }
        if (!entry.getKey().equals("k")) {
            throw new RuntimeException("key changed after failed setValue: " + entry.getKey());
        }
    }

    static void testSetValueWithNullThrows() {
        AbstractMap.SimpleImmutableEntry<String, Integer> entry =
                new AbstractMap.SimpleImmutableEntry<>("k", 1);
        boolean thrown = false;
        try {
            entry.setValue(null);
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("setValue(null) did not throw UnsupportedOperationException");
        }
        if (!entry.getValue().equals(1)) {
            throw new RuntimeException("value changed after setValue(null): " + entry.getValue());
        }
    }

    static void testSetValueRepeatedCallsThrowAndKeepValue() {
        AbstractMap.SimpleImmutableEntry<String, Integer> entry =
                new AbstractMap.SimpleImmutableEntry<>("k", 7);
        for (int i = 0; i < 3; i++) {
            boolean thrown = false;
            try {
                entry.setValue(i);
            } catch (UnsupportedOperationException e) {
                thrown = true;
            }
            if (!thrown) {
                throw new RuntimeException("setValue did not throw on iteration " + i);
            }
        }
        if (!entry.getValue().equals(7)) {
            throw new RuntimeException("value changed after repeated setValue: " + entry.getValue());
        }
    }
}
