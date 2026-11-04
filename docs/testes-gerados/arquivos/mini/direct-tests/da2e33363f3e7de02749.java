/*
 * @test
 * @summary Test JarFile(File, boolean, int, Runtime.Version) constructor behavior
 * @run main JarFileWitupTest
 */

import java.io.File;
import java.io.IOException;
import java.util.jar.JarFile;
import java.lang.Runtime.Version;

public class JarFileWitupTest {
    public static void main(String[] args) throws IOException {
        testNullVersionThrowsNPE();
        testVersionFeatureEqualsRuntimeVersion();
        testVersionFeatureLessOrEqualBaseVersion();
        testVersionFeatureGreaterThanBaseVersion();
    }

    private static void testNullVersionThrowsNPE() {
        File file = new File(".");
        try {
            new JarFile(file, false, 0, null);
            throw new AssertionError("Expected NullPointerException for null version");
        } catch (NullPointerException expected) {
            // expected
        } catch (IOException ioe) {
            throw new AssertionError("Unexpected IOException", ioe);
        }
    }

    private static void testVersionFeatureEqualsRuntimeVersion() throws IOException {
        File file = new File(".");
        Version runtimeVersion = getRuntimeVersion();
        JarFile jarFile = new JarFile(file, false, 0, runtimeVersion);
        if (!jarFile.version().equals(runtimeVersion)) {
            throw new AssertionError("JarFile version should be equal to runtimeVersion");
        }
        jarFile.close();
    }

    private static void testVersionFeatureLessOrEqualBaseVersion() throws IOException {
        File file = new File(".");
        Version baseVersion = getBaseVersion();
        JarFile jarFile = new JarFile(file, false, 0, baseVersion);
        if (!jarFile.version().equals(baseVersion)) {
            throw new AssertionError("JarFile version should be equal to baseVersion");
        }
        jarFile.close();
    }

    private static void testVersionFeatureGreaterThanBaseVersion() throws IOException {
        File file = new File(".");
        int baseFeature = getBaseVersion().feature();
        int testFeature = baseFeature + 1;
        Version testVersion = Version.parse(Integer.toString(testFeature));
        JarFile jarFile = new JarFile(file, false, 0, testVersion);
        if (jarFile.version().feature() != testFeature) {
            throw new AssertionError("JarFile version feature should be canonicalized to " + testFeature);
        }
        jarFile.close();
    }

    // Helper methods to access JarFile static fields via reflection since they are package-private
    private static Version getRuntimeVersion() {
        try {
            java.lang.reflect.Field f = JarFile.class.getDeclaredField("RUNTIME_VERSION");
            f.setAccessible(true);
            return (Version) f.get(null);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get RUNTIME_VERSION", e);
        }
    }

    private static Version getBaseVersion() {
        try {
            java.lang.reflect.Field f = JarFile.class.getDeclaredField("BASE_VERSION");
            f.setAccessible(true);
            return (Version) f.get(null);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get BASE_VERSION", e);
        }
    }
}