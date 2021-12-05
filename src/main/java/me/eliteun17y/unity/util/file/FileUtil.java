package me.eliteun17y.unity.util.file;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import net.minecraft.client.Minecraft;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtil {
    public static File unity = new File(Minecraft.getMinecraft().mcDataDir.getPath() + "/unity");
    public static File configs = new File(Minecraft.getMinecraft().mcDataDir.getPath() + "/unity/configs");
    public static File auth = new File(unity.getPath() + "/auth.json");

    public FileUtil() {
        if(!unity.exists())
            unity.mkdirs();
        if(!configs.exists())
            configs.mkdirs();
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
}
