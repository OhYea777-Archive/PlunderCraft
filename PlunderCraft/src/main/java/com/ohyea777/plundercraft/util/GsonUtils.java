package com.ohyea777.plundercraft.util;

import com.ohyea777.plundercraft.libs.com.google.gson.Gson;
import com.ohyea777.plundercraft.libs.com.google.gson.GsonBuilder;
import com.ohyea777.plundercraft.libs.org.apache.commons.io.FileUtils;
import com.ohyea777.plundercraft.api.util.IGsonUtils;

import java.io.File;

public class GsonUtils implements IGsonUtils {

    private static Gson gson;

    static {
        GsonBuilder builder = new GsonBuilder();

        builder.setPrettyPrinting();
        builder.disableHtmlEscaping();

        gson = builder.create();
    }

    /**
     * Returns a {@link com.ohyea777.plundercraft.libs.com.google.gson.Gson} instance.
     */
    public static Gson getGson() {
        return gson;
    }

    @Override
    public <T> T createFromFile(File file, Class<T> type) {
        try {
            if (!file.exists()) {
                file.getParentFile().mkdirs();

                T newInstance = type.newInstance();

                FileUtils.writeStringToFile(file, getGson().toJson(newInstance));

                return newInstance;
            }

            String json = FileUtils.readFileToString(file);

            return getGson().fromJson(json, type);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> T saveToFile(File file, T toSave) {
        try {
            FileUtils.writeStringToFile(file, getGson().toJson(toSave));

            return toSave;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
