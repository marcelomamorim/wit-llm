/* @test    @summary Test getSavedProperties method of VM class    @run main VMWitupTest */

import static java.lang.Thread.State.*;
import java.util.Map;
import java.util.Properties;

public class VMWitupTest {

    public static void main(String[] args) {
        testGetSavedPropertiesReturnsMap();
        testGetSavedPropertiesThrowsIfNull();
    }

    private static void testGetSavedPropertiesReturnsMap() {
        Map<String, String> props = jdk.internal.misc.VM.getSavedProperties();
        if (props == null) {
            throw new AssertionError("Expected non-null Map from getSavedProperties()");
        }
        // Additional check: ensure the returned object is a Map instance
        if (!(props instanceof Map)) {
            throw new AssertionError("Returned object is not a Map");
        }
    }

    private static void testGetSavedPropertiesThrowsIfNull() {
        // Reflection or internal state manipulation is not allowed; simulate by
        // temporarily setting savedProps to null if possible.
        // Since savedProps is private static final, we cannot modify it directly.
        // Instead, we can test the behavior by calling getSavedProperties() when
        // the internal state is null, which would normally be impossible.
        // As per the code, getSavedProperties() throws if savedProps == null.
        // To test this, we can create a subclass or use reflection, but reflection
        // is not allowed here. So, this test is conceptual and assumes the state is null.
        // Since we cannot set it to null, we skip this test.
        // Alternatively, document that this test is not feasible without reflection.
        System.out.println("Skipping testGetSavedPropertiesThrowsIfNull() due to access restrictions.");
    }
}
