package com.vfreiman.lessons._5_class_loaders;

import java.io.*;
import java.util.Objects;
/**
 * MyDynamicReloadingClassLoader dynamically reloads classes
 */
public class MyDynamicReloadingClassLoader extends ClassLoader{

    protected Class loadClass(String name,boolean resolve) throws ClassNotFoundException {
        Class result= findClass(name);
        if (resolve)
            resolveClass(result);
        return result;
    }

    protected Class findClass(String name) throws ClassNotFoundException {
        String path = name.replace(".", File.separator).concat(".class");

        if (!name.startsWith("com.vfreiman.lessons"))
            return findSystemClass(name);

        byte[] classBytes = loadAsBytes(path);
        if (Objects.isNull(classBytes)) throw new ClassNotFoundException();
        return defineClass(name,classBytes,0,classBytes.length);
    }

    private static byte[] loadAsBytes(String path) {
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
