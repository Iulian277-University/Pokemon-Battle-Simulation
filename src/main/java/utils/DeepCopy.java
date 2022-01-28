package utils;

import common.Constants;

import java.io.*;

public final class DeepCopy {
    private DeepCopy() {}

    public static <T> T deepCopy(Object objectToCopy) {
        if (!(objectToCopy instanceof Serializable)) {
            System.err.println(Constants.ERROR_LOG + objectToCopy.getClass() + " isn't serializable");
            return null;
        }

        // Serialize the object
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
            objectOutputStream.writeObject(objectToCopy);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Deserialize the object
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray())) {
            ObjectInputStream objectInputStream = new java.io.ObjectInputStream(byteArrayInputStream);
            return (T) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }
}
