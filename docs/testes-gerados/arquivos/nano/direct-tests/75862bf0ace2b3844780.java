/* @test    @summary Test SystemModuleFinders.read method for existing module resource    @run main SystemModuleFindersWitupTest */

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Optional;

public class SystemModuleFindersWitupTest {

    public static void main(String[] args) throws Exception {
        testReadExistingResource();
        testReadNonExistingResource();
    }

    static void testReadExistingResource() throws Exception {
        String resourceName = "java/lang/Object.class";
        Optional<ByteBuffer> result = jdk.internal.module.SystemModuleFinders.read(resourceName);
        if (result.isPresent()) {
            ByteBuffer buffer = result.get();
            assert buffer.remaining() > 0 : "Buffer should contain data";
        } else {
            throw new AssertionError("Expected resource to be found: " + resourceName);
        }
    }

    static void testReadNonExistingResource() throws Exception {
        String resourceName = "non/existent/resource.txt";
        Optional<ByteBuffer> result = jdk.internal.module.SystemModuleFinders.read(resourceName);
        assert result.isEmpty() : "Resource should not be found: " + resourceName;
    }
}
