package utils;

import common.Constants;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * This is a handler class used for getting
 * the public attributes from a given class
 */
public final class GetFieldsOfClass {
    private GetFieldsOfClass() {}

    public static Map<String, Object> getFieldObjectMap(Class<?> cls, String name) {
        List<Field> fields = Arrays.stream(cls.getDeclaredFields())
                .filter(f -> Modifier.isPublic(f.getModifiers()) &&
                        f.getName().toUpperCase().contains(name.toUpperCase()))
                .toList();

        if (fields.isEmpty()) {
            System.err.println(Constants.ERROR_LOG + "Field '" + name + "' doesn't exist");
            return Collections.emptyMap();
        }

        Map<String, Object> fieldObjectMap = new HashMap<>();
        for (Field field: fields) {
            try {
                fieldObjectMap.put(field.getName(), field.get(cls));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return fieldObjectMap;
    }
}
