/*
 * @test
 * @summary Verifies that VM.getSavedProperties() returns a non-null, stable map in a booted VM
 * @modules java.base/jdk.internal.misc
 * @run main VMWitupTest
 */

import java.util.Map;

import jdk.internal.misc.VM;

public class VMWitupTest {

    public static void main(String[] args) {
        if (!VM.isBooted()) {
            throw new RuntimeException("VM expected to be booted when test runs");
        }

        Map<String, String> first = VM.getSavedProperties();
        if (first == null) {
            throw new RuntimeException("getSavedProperties() returned null");
        }

        Map<String, String> second = VM.getSavedProperties();
        if (second == null) {
            throw new RuntimeException("second call to getSavedProperties() returned null");
        }

        if (!first.equals(second)) {
            throw new RuntimeException("getSavedProperties() returned inconsistent contents across calls");
        }
    }
}
