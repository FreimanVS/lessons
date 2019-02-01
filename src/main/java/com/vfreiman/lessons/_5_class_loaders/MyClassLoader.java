package com.vfreiman.lessons._5_class_loaders;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * MyClassLoader is for loading classes from a project's root directory
 */
public class MyClassLoader extends ClassLoader {

    private final String prefix;

    public MyClassLoader(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        return findClass(name);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String path = prefix
                .concat(File.separator)
                .concat(name.replace(".", File.separator).concat(".class"));

        if (!name.startsWith("com.vfreiman.lessons"))
            return findSystemClass(name);

        byte[] bytes = loadAsBytes(path);

        return defineClass(name, bytes, 0, bytes.length);
    }

    private byte[] loadAsBytes(String path) {
        try (InputStream is = new FileInputStream(path);
             ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            int b;
            while ((b = is.read()) != -1)
                os.write(b);
            return os.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
