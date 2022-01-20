package com.ohyea777.plundercraft.module.loaders;

import com.ohyea777.plundercraft.api.module.AbstractModule;
import com.ohyea777.plundercraft.api.module.IModule;
import com.ohyea777.plundercraft.api.module.loaders.AbstractModuleLoader;
import org.reflections.Reflections;

import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

public class ModuleReflectionsLoader extends AbstractModuleLoader {

    @Override
    public List<IModule> onLoad(URLClassLoader classLoader) {
        List<IModule> modules = new ArrayList<IModule>();
        Reflections reflections = new Reflections("com.ohyea777.plundercraft.module.modules");

        for (Class<? extends AbstractModule> moduleClass : reflections.getSubTypesOf(AbstractModule.class)) {
            try {
                modules.add(moduleClass.newInstance());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return modules;
    }

}
