package me.eliteun17y.unity.plugin;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.org.apache.bcel.internal.classfile.ClassParser;
import com.sun.org.apache.bcel.internal.classfile.JavaClass;
import com.sun.org.apache.bcel.internal.classfile.Method;
import me.eliteun17y.unity.util.file.FileUtil;
import org.apache.commons.io.IOUtils;
import scala.tools.nsc.MainClass;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class PluginLoader {
    public static void loadPlugins() throws IOException {
        ArrayList<File> files = FileUtil.getFilesInDirectory(FileUtil.plugins);
        for(File file : files) {
            PluginClassLoader pluginClassLoader = new PluginClassLoader(new JarFile(file));
            ZipFile zipFile = new ZipFile(file);

            Enumeration<? extends ZipEntry> entries = zipFile.entries();

            while(entries.hasMoreElements()){
                ZipEntry entry = entries.nextElement();
                InputStream stream = zipFile.getInputStream(entry);
                if(entry.getName().equalsIgnoreCase("plugin.json")) {
                    String result = IOUtils.toString(stream, StandardCharsets.UTF_8);
                    JsonParser parser = new JsonParser();
                    JsonElement element = parser.parse(result);
                    JsonObject object = element.getAsJsonObject();
                    String name = object.get("name").getAsString();
                    String version = object.get("version").getAsString();
                    String mainClassLocation = object.get("main-class").getAsString();
                    Class mainClass = pluginClassLoader.getClass(mainClassLocation);
                    try {
                        mainClass.getMethod("test").invoke(mainClass);
                    } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Plugin found: " + name + ", version: " + version + ".");
                }
            }
        }
    }
}