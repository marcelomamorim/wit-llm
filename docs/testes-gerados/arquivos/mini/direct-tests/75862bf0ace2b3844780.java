/*
 * @test
 * @summary Test jdk.internal.module.SystemModuleFinders.read(String) method
 * @run main SystemModuleFindersWitupTest
 */

import java.nio.ByteBuffer;
import java.util.Optional;
import java.io.IOException;

public class SystemModuleFindersWitupTest {

    public static void main(String[] args) throws IOException {
        SystemModuleFinders finder = new SystemModuleFinders();

        // Test with a name that likely does not exist, expecting Optional.empty
        Optional<ByteBuffer> result = finder.read("nonexistent-resource-name-xyz");
        if (result.isPresent()) {
            throw new AssertionError("Expected empty Optional for nonexistent resource name");
        }

        // Since we cannot predict actual image locations, test that method does not throw
        // and returns Optional.empty or Optional with ByteBuffer
        // Use a dummy name that might exist in the image (e.g. "module-info.class")
        try {
            Optional<ByteBuffer> opt = finder.read("module-info.class");
            if (opt == null) {
                throw new AssertionError("Returned Optional must not be null");
            }
            // If present, ByteBuffer should not be null
            if (opt.isPresent() && opt.get() == null) {
                throw new AssertionError("ByteBuffer inside Optional must not be null");
            }
        } catch (IOException e) {
            // IOException is declared, so allowed
        }
    }
}
