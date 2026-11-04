/*
 * @test
 * @summary Test ModulePatcher.getName() returns expected value
 * @run main ModulePatcherWitupTest
 */

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import jdk.internal.module.ModulePatcher;

public class ModulePatcherWitupTest {
    public static void main(String[] args) {
        // Create empty input map for constructor
        ModulePatcher patcher = new ModulePatcher(Collections.<String, List<String>>emptyMap());

        // Call getName and verify it returns null (shouldNotGetHere returns null)
        String name = patcher.getName();
        if (name != null) {
            throw new RuntimeException("Expected getName() to return null but got: " + name);
        }
    }
}
