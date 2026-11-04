/*
 * @test
 * @summary Smoke test of PolicyFile construction with java.security.policy pointing to a missing file, with and without the "=" override prefix
 * @modules java.base/sun.security.provider
 * @run main PolicyFileWitupTest
 */
import java.io.File;
import sun.security.provider.PolicyFile;

public class PolicyFileWitupTest {
    public static void main(String[] args) throws Exception {
        String key = "java.security.policy";
        String missing = new File("nonexistent-policy-file-witup.policy").getAbsolutePath();
        try {
            System.setProperty(key, missing);
            PolicyFile pf = new PolicyFile();
            if (pf == null) {
                throw new RuntimeException("PolicyFile() returned null");
            }

            System.setProperty(key, "=" + missing);
            PolicyFile pf2 = new PolicyFile();
            if (pf2 == null) {
                throw new RuntimeException("PolicyFile() with override prefix returned null");
            }
        } finally {
            System.clearProperty(key);
        }
    }
}
