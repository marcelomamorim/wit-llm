/*
 * @test
 * @summary Verifies that the ContentInfo(ObjectIdentifier, DerValue) constructor stores its arguments as given, including null values
 * @modules java.base/sun.security.pkcs java.base/sun.security.util
 * @run main/othervm --add-opens java.base/sun.security.pkcs=ALL-UNNAMED ContentInfoWitupTest
 */

import java.lang.reflect.Field;
import sun.security.pkcs.ContentInfo;
import sun.security.util.DerValue;
import sun.security.util.ObjectIdentifier;

public class ContentInfoWitupTest {

    public static void main(String[] args) throws Exception {
        ObjectIdentifier oid = new ObjectIdentifier("1.2.840.113549.1.7.1");
        DerValue value = new DerValue((byte) 0x04, new byte[] {1, 2, 3});

        ContentInfo ci = new ContentInfo(oid, value);
        check(get(ci, "contentType") == oid, "contentType not stored as given");
        check(get(ci, "content") == value, "content not stored as given");

        ContentInfo nulls = new ContentInfo(null, null);
        check(get(nulls, "contentType") == null, "null contentType not preserved");
        check(get(nulls, "content") == null, "null content not preserved");

        ContentInfo noContent = new ContentInfo(oid, null);
        check(get(noContent, "contentType") == oid, "contentType lost when content is null");
        check(get(noContent, "content") == null, "null content not preserved");

        ContentInfo noType = new ContentInfo(null, value);
        check(get(noType, "contentType") == null, "null contentType not preserved");
        check(get(noType, "content") == value, "content lost when contentType is null");
    }

    private static Object get(Object target, String name) throws Exception {
        Field f = target.getClass().getDeclaredField(name);
        f.setAccessible(true);
        return f.get(target);
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
