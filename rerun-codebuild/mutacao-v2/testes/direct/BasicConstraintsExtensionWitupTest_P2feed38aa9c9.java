/*
 * @test
 * @summary Test sun.security.x509.BasicConstraintsExtension.set method
 * @run main BasicConstraintsExtensionWitupTest_P2feed38aa9c9
 */

import sun.security.x509.BasicConstraintsExtension;
import java.io.IOException;

public class BasicConstraintsExtensionWitupTest_P2feed38aa9c9 {
    public static void main(String[] args) throws Exception {
        testSetIsCaValid();
        testSetPathLenValid();
        testSetIsCaInvalidType();
        testSetPathLenInvalidType();
        testSetInvalidName();
    }

    private static void testSetIsCaValid() throws Exception {
        BasicConstraintsExtension ext = new BasicConstraintsExtension(false, -1);
        ext.set("is_ca", Boolean.TRUE);
        if (!Boolean.TRUE.equals(getIsCa(ext))) {
            throw new RuntimeException("Failed to set is_ca to true");
        }
        ext.set("is_ca", Boolean.FALSE);
        if (!Boolean.FALSE.equals(getIsCa(ext))) {
            throw new RuntimeException("Failed to set is_ca to false");
        }
    }

    private static void testSetPathLenValid() throws Exception {
        BasicConstraintsExtension ext = new BasicConstraintsExtension(false, -1);
        ext.set("path_len", Integer.valueOf(5));
        if (getPathLen(ext) != 5) {
            throw new RuntimeException("Failed to set path_len to 5");
        }
        ext.set("path_len", Integer.valueOf(0));
        if (getPathLen(ext) != 0) {
            throw new RuntimeException("Failed to set path_len to 0");
        }
    }

    private static void testSetIsCaInvalidType() throws Exception {
        BasicConstraintsExtension ext = new BasicConstraintsExtension(false, -1);
        try {
            ext.set("is_ca", "notBoolean");
            throw new RuntimeException("Expected IOException for invalid is_ca type");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.contains("Boolean")) {
                throw new RuntimeException("Wrong exception message for is_ca invalid type: " + msg);
            }
        }
    }

    private static void testSetPathLenInvalidType() throws Exception {
        BasicConstraintsExtension ext = new BasicConstraintsExtension(false, -1);
        try {
            ext.set("path_len", "notInteger");
            throw new RuntimeException("Expected IOException for invalid path_len type");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.contains("Integer")) {
                throw new RuntimeException("Wrong exception message for path_len invalid type: " + msg);
            }
        }
    }

    private static void testSetInvalidName() throws Exception {
        BasicConstraintsExtension ext = new BasicConstraintsExtension(false, -1);
        try {
            ext.set("invalid_name", Boolean.TRUE);
            throw new RuntimeException("Expected IOException for invalid attribute name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.contains("not recognized")) {
                throw new RuntimeException("Wrong exception message for invalid attribute name: " + msg);
            }
        }
    }

    // Reflection helpers to get private fields ca and pathLen
    private static Boolean getIsCa(BasicConstraintsExtension ext) throws Exception {
        java.lang.reflect.Field f = BasicConstraintsExtension.class.getDeclaredField("ca");
        f.setAccessible(true);
        return (Boolean) f.get(ext);
    }

    private static int getPathLen(BasicConstraintsExtension ext) throws Exception {
        java.lang.reflect.Field f = BasicConstraintsExtension.class.getDeclaredField("pathLen");
        f.setAccessible(true);
        return f.getInt(ext);
    }
}
