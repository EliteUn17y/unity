package me.eliteun17y.unity.plugin;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

public class PluginClassLoader extends ClassLoader {
    public HashMap<String, byte[]> classes = new HashMap<>();
    public HashMap<String, byte[]> resources = new HashMap<>();

    public PluginClassLoader(JarFile file) throws IOException {
        Enumeration<? extends JarEntry> entries = file.entries();

        while(entries.hasMoreElements()){
            JarEntry entry = entries.nextElement();
            if(entry == null) break;
            if(entry.getName().endsWith(".class")) {
                System.out.println(entry.getName());
                System.out.println(entry.getName().split(".class")[0]);
                classes.put(entry.getName().split(".class")[0].replace("/", "."), getData(file, entry));
            }else if(!entry.getName().endsWith("/")) {
                resources.put(entry.getName(), getData(file, entry));
            }
        }
    }

    public Class getClass(String name) {
        if(!classes.containsKey(name)) return null;
        byte[] bytes = classes.get(name);
        return defineClass(name, bytes, 0, bytes.length); // TODO: Change main
    }

    public byte[] getData(JarFile file, JarEntry entry) throws IOException {
        InputStream inputStream = file.getInputStream(entry);
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        int cur = 0;
        while((cur = inputStream.read()) != -1) {
            byteArray.write(cur);
        }

        byte[] array = byteArray.toByteArray();

        byteArray.close();
        inputStream.close();

        return array;
    }
}
