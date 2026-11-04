/*
 * @test
 * @summary Test getInputStream method of ModulePatcher for expected behavior.
 * @run main ModulePatcherWitupTest
 */

import java.io.IOException;
import java.io.InputStream;
import jdk.internal.module.ModulePatcher;

public class ModulePatcherWitupTest {

    public static void main(String[] args) {
        testGetInputStreamReturnsInputStream();
    }

    public static void testGetInputStreamReturnsInputStream() {
        ModulePatcher patcher = new ModulePatcher(Collections.emptyMap());
        try {
            InputStream is = patcher.getInputStream();
            if (is == null) {
                throw new AssertionError("getInputStream() returned null");
            }
            System.out.println("Test passed: getInputStream() returned a non-null InputStream.");
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException thrown", e);
        }
    }
}
