/*
 * @test
 * @summary Test delete method of JrtFileSystemProvider for null Path
 * @run main JrtFileSystemProviderWitupTest
 */

import java.io.IOException;
import java.nio.file.Path;
import jdk.internal.jrtfs.JrtFileSystemProvider;

public class JrtFileSystemProviderWitupTest {

    public static void main(String[] args) {
        testDeleteNullPath();
    }

    public static void testDeleteNullPath() {
        JrtFileSystemProvider provider = new JrtFileSystemProvider();
        try {
            provider.delete(null);
            System.out.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (IOException e) {
            System.out.println("FAIL: Unexpected IOException: " + e);
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }
}
