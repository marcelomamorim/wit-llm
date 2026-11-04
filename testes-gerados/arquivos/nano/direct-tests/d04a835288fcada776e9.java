/* @test    @summary Test for getContentLength method in ModulePatcher class    @run main ModulePatcherWitupTest */

import java.io.IOException;
import jdk.internal.module.ModulePatcher;

public class ModulePatcherWitupTest {

    public static void main(String[] args) {
        testGetContentLengthThrows();
    }

    public static void testGetContentLengthThrows() {
        ModulePatcher patcher = new ModulePatcher(Collections.emptyMap());
        try {
            patcher.getContentLength();
            throw new AssertionError("Expected IOException was not thrown");
        } catch (IOException e) {
            // Expected exception, test passes
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception type: " + t, t);
        }
    }
}
