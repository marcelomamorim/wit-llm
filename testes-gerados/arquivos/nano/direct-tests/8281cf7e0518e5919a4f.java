/* @test    @summary Test getSavedProperty method for various keys    @run main VMWitupTest */

import static java.lang.Thread.State.*;
import java.util.Map;
import java.util.Properties;

public class VMWitupTest {

    public static void main(String[] args) {
        testGetSavedPropertyWithNullProps();
        testGetSavedPropertyWithEmptyProps();
        testGetSavedPropertyWithExistingKey();
        testGetSavedPropertyWithNonExistingKey();
    }

    public static void testGetSavedPropertyWithNullProps() {
        try {
            jdk.internal.misc.VM.savedProps = null;
            jdk.internal.misc.VM.getSavedProperty("testKey");
            throw new AssertionError("Expected IllegalStateException not thrown");
        } catch (IllegalStateException e) {
            // expected
        }
    }

    public static void testGetSavedPropertyWithEmptyProps() {
        jdk.internal.misc.VM.savedProps = new java.util.HashMap<>();
        String result = jdk.internal.misc.VM.getSavedProperty("anyKey");
        if (result != null) {
            throw new AssertionError("Expected null for non-existing key, but got: " + result);
        }
    }

    public static void testGetSavedPropertyWithExistingKey() {
        jdk.internal.misc.VM.savedProps = new java.util.HashMap<>();
        jdk.internal.misc.VM.savedProps.put("existingKey", "value123");
        String result = jdk.internal.misc.VM.getSavedProperty("existingKey");
        if (!"value123".equals(result)) {
            throw new AssertionError("Expected 'value123', but got: " + result);
        }
    }

    public static void testGetSavedPropertyWithNonExistingKey() {
        jdk.internal.misc.VM.savedProps = new java.util.HashMap<>();
        String result = jdk.internal.misc.VM.getSavedProperty("nonExistingKey");
        if (result != null) {
            throw new AssertionError("Expected null for non-existing key, but got: " + result);
        }
    }
}
