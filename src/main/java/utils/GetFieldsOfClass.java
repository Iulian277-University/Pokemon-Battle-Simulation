package utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

public final class GetFieldsOfClass {
    private GetFieldsOfClass() {}

    public static Map<String, Object> getFieldObjectMap(Class<?> cls, String name) {
        List<Field> fields = Arrays.stream(cls.getDeclaredFields())
                .filter(f -> Modifier.isPublic(f.getModifiers()) &&
                        f.getName().toUpperCase().contains(name.toUpperCase()))
                .toList();

        if (fields.isEmpty()) {
            System.err.println("Field '" + name + "' doesn't exist");
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
