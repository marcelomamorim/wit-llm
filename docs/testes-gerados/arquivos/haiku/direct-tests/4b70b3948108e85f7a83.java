/*
 * @test
 * @summary Verify Field.getAnnotationsByType returns directly and indirectly present annotations and rejects null
 * @run main FieldWitupTest
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

public class FieldWitupTest {

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @Repeatable(Tags.class)
    @interface Tag {
        String value();
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @interface Tags {
        Tag[] value();
    }

    public static class Holder {
        @Tag("a") @Tag("b")
        public int repeated;

        @Tag("single")
        public int single;

        public int plain;
    }

    public static void main(String[] args) throws Exception {
        Field repeated = Holder.class.getField("repeated");
        Tag[] repeatedTags = repeated.getAnnotationsByType(Tag.class);
        if (repeatedTags.length != 2) {
            throw new RuntimeException("Expected 2 tags, got " + repeatedTags.length);
        }
        if (!repeatedTags[0].value().equals("a") || !repeatedTags[1].value().equals("b")) {
            throw new RuntimeException("Unexpected tag values");
        }

        Field single = Holder.class.getField("single");
        Tag[] singleTags = single.getAnnotationsByType(Tag.class);
        if (singleTags.length != 1 || !singleTags[0].value().equals("single")) {
            throw new RuntimeException("Expected one 'single' tag");
        }

        Field plain = Holder.class.getField("plain");
        Tag[] plainTags = plain.getAnnotationsByType(Tag.class);
        if (plainTags.length != 0) {
            throw new RuntimeException("Expected no tags on plain field");
        }

        boolean npeThrown = false;
        try {
            repeated.getAnnotationsByType(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new RuntimeException("Expected NullPointerException for null annotation class");
        }
    }
}
