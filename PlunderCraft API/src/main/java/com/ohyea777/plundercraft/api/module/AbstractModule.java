package com.ohyea777.plundercraft.api.module;

import com.ohyea777.plundercraft.api.PlunderCraft;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractModule<T> implements IModule<T> {

    private boolean isEnabled;
    private ICommandRegistry commandRegistry;

    @Override
    public abstract String getName();

    @Override
    public Class<T> getConfigClass() {
        return null;
    }

    @Override
    public boolean hasConfig() {
        return getConfigClass() != null;
    }

    @Override
    public String getConfigName() {
        return getName() + ".json";
    }

    @Override
    public List<Class<?>> getDatabaseClasses() {
        return new ArrayList<Class<?>>();
    }

    @Override
    public void onEnable(T config) { }

    @Override
    public void onEnable() {
        getPlugin().getLogger().info("Module: [" + getName() + "] has been enabled");

        if (hasConfig())
            onEnable(reloadConfig());
    }

    @Override
    public T reloadConfig() {
        return PlunderCraft.getGsonUtils().createFromFile(new File(getPlugin().getDataFolder(), getConfigName()), getConfigClass());
    }

    @Override
    public void onReload() {
        getPlugin().getLogger().info("Module: [" + getName() + "] has been reloaded");
    }

    @Override
    public void onDisable() {
        getPlugin().getLogger().info("Module: [" + getName() + "] has been disabled");
    }

    @Override
    public void saveConfig(T config) {
        PlunderCraft.getGsonUtils().saveToFile(new File(getPlugin().getDataFolder(), getConfigName()), config);
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    @Override
    public void setEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    @Override
    public ICommandRegistry getCommandRegistry() {
        return commandRegistry;
    }

    @Override
    public void setCommandRegistry(ICommandRegistry commandRegistry) {
        this.commandRegistry = commandRegistry;
    }

    @Override
    public void registerCommandHandler(Object commandHandler) {
        if (commandRegistry != null) commandRegistry.registerCommandHandler(commandHandler);
    }

    @Override
    public void deregisterCommandHandler(Object commandHandler) {
        if (commandRegistry != null) commandRegistry.deregisterCommandHandler(commandHandler);
    }

    @Override
    public Plugin getPlugin() {
        return PlunderCraft.getPlugin();
    }

}
