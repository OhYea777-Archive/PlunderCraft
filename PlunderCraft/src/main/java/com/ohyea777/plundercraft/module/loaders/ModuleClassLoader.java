package com.ohyea777.plundercraft.module.loaders;

import com.ohyea777.plundercraft.PlunderCraftCore;
import com.ohyea777.plundercraft.api.module.AbstractModule;
import com.ohyea777.plundercraft.api.module.IModule;
import com.ohyea777.plundercraft.api.module.loaders.AbstractModuleLoader;

import java.io.File;
import java.io.FilenameFilter;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

public class ModuleClassLoader extends AbstractModuleLoader {

    public ModuleClassLoader() {
        super(PlunderCraftCore.getInstance().getActualConfig().getModuleClassesDir());
    }

    @Override
    public List<IModule> onLoad(URLClassLoader classLoader) {
        List<IModule> modules = new ArrayList<IModule>();
        File file = ((PlunderCraftCore) getPlugin()).getActualConfig().getModuleClassesDir();

        if (file.exists() && file.isDirectory()) {
            try {
                for (File classFile : file.listFiles(new FilenameFilter() {
                    @Override
                    public boolean accept(File dir, String name) {
                        return name.endsWith(".class");
                    }
                })) {
                    Class<?> clazz = classLoader.loadClass(classFile.getName().replace(".class", ""));

                    if (AbstractModule.class.isAssignableFrom(clazz)) modules.add((AbstractModule) clazz.newInstance());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return modules;
    }

}
