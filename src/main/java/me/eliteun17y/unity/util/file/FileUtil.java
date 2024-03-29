package me.eliteun17y.unity.util.file;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import me.eliteun17y.unity.Unity;
import net.minecraft.client.Minecraft;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class FileUtil {
    public static File unity = new File(Minecraft.getMinecraft().gameDir.getPath() + "/unity");
    public static File configs = new File(Minecraft.getMinecraft().gameDir.getPath() + "/unity/configs");
    public static File plugins = new File(Minecraft.getMinecraft().gameDir.getPath() + "/unity/plugins");
    public static File auth = new File(unity.getPath() + "/auth.json");
    public static File notFirstRun = new File(unity.getPath() + "/notfirstrun");

    public FileUtil() {
        if(!unity.exists())
            unity.mkdirs();
        if(!configs.exists())
            configs.mkdirs();
        if(!plugins.exists())
            plugins.mkdirs();
        if(!notFirstRun.exists()) {
            Unity.instance.firstRun = true;
            try {
                notFirstRun.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(!auth.exists()) {
            try {
                auth.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getContent(File file) {
        try {
            return Files.asCharSource(file, Charsets.UTF_8).read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void writeContent(File file, String content) {
        try {
            FileWriter writer = new FileWriter(file);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<File> getFilesInDirectory(File dir) {
        return new ArrayList<>(Arrays.asList(Objects.requireNonNull(dir.listFiles())));
    }
}
