/*
 * @test
 * @summary Tests that the LinkPermission(String) constructor accepts only "hard" and "symbolic" names
 * @run main LinkPermissionWitupTest
 */

import java.nio.file.LinkPermission;

public class LinkPermissionWitupTest {

    public static void main(String[] args) {
        LinkPermission hard = new LinkPermission("hard");
        if (!"hard".equals(hard.getName())) {
            throw new RuntimeException("unexpected name for hard: " + hard.getName());
        }

        LinkPermission symbolic = new LinkPermission("symbolic");
        if (!"symbolic".equals(symbolic.getName())) {
            throw new RuntimeException("unexpected name for symbolic: " + symbolic.getName());
        }

        expectIllegalArgument("");
        expectIllegalArgument("read");
        expectIllegalArgument("Hard");
        expectIllegalArgument("SYMBOLIC");
        expectIllegalArgument("hard ");
        expectIllegalArgument("symbolic2");
    }

    static void expectIllegalArgument(String name) {
        try {
            new LinkPermission(name);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("expected IllegalArgumentException for name: \"" + name + "\"");
    }
}
