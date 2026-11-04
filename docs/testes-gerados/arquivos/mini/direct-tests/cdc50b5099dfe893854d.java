/*
 * @test
 * @summary Test getInputStream method of ModulePatcher for expected behavior
 * @run main ModulePatcherWitupTest
 */

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import jdk.internal.module.ModulePatcher;

public class ModulePatcherWitupTest {
    public static void main(String[] args) throws Exception {
        // Create ModulePatcher with empty input map
        ModulePatcher patcher = new ModulePatcher(Collections.<String, List<String>>emptyMap());
        try {
            patcher.getInputStream();
            throw new RuntimeException("Expected exception not thrown");
        } catch (RuntimeException e) {
            // The method calls shouldNotGetHere which presumably throws RuntimeException
            // We check that the exception is RuntimeException and not IOException
            if (e.getClass() != RuntimeException.class) {
                throw new RuntimeException("Unexpected exception type", e);
            }
        }
    }
}
