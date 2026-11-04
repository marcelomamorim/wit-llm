/*
 * @test
 * @summary Verifies that PolicyFile loads a policy from the java.security.policy system property when it is prefixed with '=' and grants the declared permission
 * @modules java.base/sun.security.provider
 * @run main PolicyFileWitupTest
 */

import java.io.File;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.CodeSource;
import java.security.Permission;
import java.security.PermissionCollection;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.PropertyPermission;

import sun.security.provider.PolicyFile;

public class PolicyFileWitupTest {

    static final String PROP = "java.security.policy";
    static final String GRANTED_KEY = "witup.test.granted.key";
    static final String OTHER_KEY = "witup.test.other.key";

    public static void main(String[] args) throws Exception {
        String previous = System.getProperty(PROP);
        Path policy = Files.createTempFile("witup", ".policy");
        try {
            String content = "grant {\n"
                + "  permission java.util.PropertyPermission \"" + GRANTED_KEY + "\", \"read\";\n"
                + "};\n";
            Files.write(policy, content.getBytes(StandardCharsets.UTF_8));

            // The leading '=' requests that this policy override all others.
            System.setProperty(PROP, "=" + policy.toAbsolutePath().toString());

            PolicyFile pf = new PolicyFile();
            CodeSource cs = new CodeSource(new URL("file:/witup/any.jar"), (java.security.cert.Certificate[]) null);
            Collection<Permission> perms = pf.getPermissions(cs).elements() == null
                ? null : toList(pf.getPermissions(cs));

            if (!contains(perms, new PropertyPermission(GRANTED_KEY, "read"))) {
                throw new RuntimeException("Expected granted permission not found");
            }
            if (contains(perms, new PropertyPermission(OTHER_KEY, "read"))) {
                throw new RuntimeException("Unexpected permission found: " + OTHER_KEY);
            }
        } finally {
            if (previous == null) {
                System.clearProperty(PROP);
            } else {
                System.setProperty(PROP, previous);
            }
            Files.deleteIfExists(policy);
        }
    }

    static Collection<Permission> toList(PermissionCollection pc) {
        java.util.List<Permission> list = new java.util.ArrayList<>();
        Enumeration<Permission> e = pc.elements();
        while (e.hasMoreElements()) {
            list.add(e.nextElement());
        }
        return list;
    }

    static boolean contains(Collection<Permission> perms, Permission p) {
        if (perms == null) {
            return false;
        }
        for (Permission x : perms) {
            if (x.equals(p)) {
                return true;
            }
        }
        return false;
    }
}
