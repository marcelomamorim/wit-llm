/*
 * @test
 * @summary Witup-generated checks for MethodHandleInfo.referenceKindToString(int): valid kinds yield distinct non-empty names and invalid kinds throw IllegalArgumentException
 * @run main MethodHandleInfoWitupTest
 */

import java.lang.invoke.MethodHandleInfo;
import java.util.HashSet;
import java.util.Set;

public class MethodHandleInfoWitupTest {

    public static void main(String[] args) {
        Set<String> names = new HashSet<>();
        for (int kind = 1; kind <= 9; kind++) {
            String name = MethodHandleInfo.referenceKindToString(kind);
            if (name == null || name.isEmpty()) {
                throw new RuntimeException("empty name for kind " + kind);
            }
            if (!names.add(name)) {
                throw new RuntimeException("duplicate name " + name + " for kind " + kind);
            }
        }

        int[] invalidKinds = {0, 10, -1, 255};
        for (int kind : invalidKinds) {
            boolean thrown = false;
            try {
                MethodHandleInfo.referenceKindToString(kind);
            } catch (IllegalArgumentException expected) {
                thrown = true;
            }
            if (!thrown) {
                throw new RuntimeException("no IllegalArgumentException for kind " + kind);
            }
        }
    }
}
