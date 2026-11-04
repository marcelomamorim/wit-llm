/*
 * @test
 * @summary Verifies that VM.getSavedProperties() returns a non-null, stable saved-properties map after VM boot
 * @modules java.base/jdk.internal.misc
 * @run main VMWitupTest
 */

import jdk.internal.misc.VM;
import java.util.Map;

public class VMWitupTest {
    public static void main(String[] args) {
        Map<String, String> first = VM.getSavedProperties();
        if (first == null) {
            throw new RuntimeException("getSavedProperties() returned null");
        }

        Map<String, String> second = VM.getSavedProperties();
        if (first != second) {
            throw new RuntimeException("getSavedProperties() returned different instances across calls");
        }

        if (!first.containsKey("java.home")) {
            throw new RuntimeException("Saved properties do not contain java.home");
        }
    }
}
