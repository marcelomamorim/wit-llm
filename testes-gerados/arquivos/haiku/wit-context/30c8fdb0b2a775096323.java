/*
 * @test
 * @summary Checks type and name validation of BasicConstraintsExtension.set and that valid values are accepted
 * @modules java.base/sun.security.x509
 * @run main BasicConstraintsExtensionWitupTest
 */
import java.io.IOException;
import sun.security.x509.BasicConstraintsExtension;

public class BasicConstraintsExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        BasicConstraintsExtension ext = new BasicConstraintsExtension(true, 2);

        ext.set("IS_CA", Boolean.FALSE);
        ext.set(BasicConstraintsExtension.IS_CA, Boolean.TRUE);
        ext.set(BasicConstraintsExtension.PATH_LEN, Integer.valueOf(3));

        expectIOException(ext, BasicConstraintsExtension.IS_CA, "x");
        expectIOException(ext, BasicConstraintsExtension.IS_CA, Integer.valueOf(1));
        expectIOException(ext, BasicConstraintsExtension.PATH_LEN, Boolean.TRUE);
        expectIOException(ext, BasicConstraintsExtension.PATH_LEN, "3");
        expectIOException(ext, "unknown", Boolean.TRUE);
    }

    static void expectIOException(BasicConstraintsExtension ext, String name, Object obj) {
        try {
            ext.set(name, obj);
        } catch (IOException e) {
            return;
        }
        throw new RuntimeException("Expected IOException for name=" + name + " value=" + obj);
    }
}
