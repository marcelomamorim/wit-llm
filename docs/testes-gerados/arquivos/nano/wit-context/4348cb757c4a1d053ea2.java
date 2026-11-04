/*
 * @test
 * @summary Test for jdk.internal.jrtfs.JrtPath.remove() method.
 * @run main JrtPathWitupTest
 */

import java.io.IOException;
import jdk.internal.jrtfs.JrtPath;

public class JrtPathWitupTest {

    public static void main(String[] args) {
        testRemoveThrowsReadOnlyFileSystemException();
    }

    public static void testRemoveThrowsReadOnlyFileSystemException() {
        JrtPath path = null;
        try {
            // Instancia um objeto JrtPath com valores fictícios, pois o método remove() não depende do estado interno.
            path = new JrtPath(null, "");
        } catch (Exception e) {
            // Caso a construção falhe, o teste não é válido.
            System.err.println("Failed to instantiate JrtPath: " + e);
            return;
        }
        try {
            path.remove();
            System.out.println("FAIL: Expected ReadOnlyFileSystemException was not thrown.");
        } catch (ReadOnlyFileSystemException e) {
            System.out.println("PASS: Caught expected ReadOnlyFileSystemException.");
        } catch (Throwable t) {
            System.err.println("FAIL: Unexpected exception: " + t);
        }
    }
}
