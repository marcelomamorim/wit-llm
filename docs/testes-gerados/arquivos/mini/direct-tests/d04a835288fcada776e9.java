/*
 * @test
 * @summary Test getContentLength method of ModulePatcher
 * @run main ModulePatcherWitupTest
 */

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import jdk.internal.module.ModulePatcher;

public class ModulePatcherWitupTest {

    public static void main(String[] args) throws IOException {
        // Create ModulePatcher with empty map to avoid NPE
        ModulePatcher patcher = new ModulePatcher(Collections.<String, List<String>>emptyMap());

        // Call getContentLength and verify it throws IOException
        try {
            patcher.getContentLength();
            throw new RuntimeException("Expected IOException not thrown");
        } catch (IOException e) {
            // Expected exception, test passes
        }
    }
}
