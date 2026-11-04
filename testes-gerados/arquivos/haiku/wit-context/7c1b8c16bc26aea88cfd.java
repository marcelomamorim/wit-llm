/*
 * @test
 * @summary Smoke test for ASMifier.visitMainClass using only the public constructor
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main ASMifierWitupTest
 */

import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        ASMifier asm = new ASMifier();
        asm.visitMainClass("java/lang/Object");
        asm.visitMainClass("");
        asm.visitMainClass("a/b/C");
        asm.visitMainClass(null);
        asm.visitMainClass("java/lang/Object");
    }
}
