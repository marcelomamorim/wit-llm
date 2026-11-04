/*
 * @test
 * @summary Public API checks for UnpackerImpl null handling, properties() and toString()
 * @modules java.base/com.sun.java.util.jar.pack
 * @run main UnpackerImplWitupTest
 */

import com.sun.java.util.jar.pack.UnpackerImpl;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.SortedMap;
import java.util.jar.JarOutputStream;

public class UnpackerImplWitupTest {

    public static void main(String[] args) throws Exception {
        UnpackerImpl u = new UnpackerImpl();
        checkNullInput(u);
        checkNullOutput(u);
        checkProperties(u);
        checkToString(u);
    }

    static void checkNullInput(UnpackerImpl u) throws Exception {
        try {
            u.unpack(null, new JarOutputStream(new ByteArrayOutputStream()));
            throw new RuntimeException("expected NullPointerException for null input");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void checkNullOutput(UnpackerImpl u) throws Exception {
        try {
            u.unpack(new ByteArrayInputStream(new byte[0]), null);
            throw new RuntimeException("expected NullPointerException for null output");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void checkProperties(UnpackerImpl u) {
        SortedMap<String, String> p = u.properties();
        if (p == null) {
            throw new RuntimeException("properties() returned null");
        }
        if (p != u.properties()) {
            throw new RuntimeException("properties() should return the same map instance");
        }
    }

    static void checkToString(UnpackerImpl u) {
        String s = u.toString();
        if (s == null || s.isEmpty()) {
            throw new RuntimeException("toString() returned empty value");
        }
    }
}
