package pl.torobolin.autoattack;

import net.fabricmc.api.ClientModInitializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.*;
import java.nio.file.*;

public class TorosAutoAttackClient implements ClientModInitializer {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("toros-auto-attack.json");
    private static AutoAttackConfigModel config = new AutoAttackConfigModel();

    @Override
    public void onInitializeClient() {
        loadConfig();
        AutoAttackHandler handler = new AutoAttackHandler();
        new KeybindsHandler().registerKeyInputs(handler);
        handler.handleAttack();
    }

    private static void loadConfig() {
        if (Files.exists(CONFIG_PATH)) {
            try (Reader reader = Files.newBufferedReader(CONFIG_PATH)) {
                config = GSON.fromJson(reader, AutoAttackConfigModel.class);
                if (config == null) config = new AutoAttackConfigModel();
            } catch (IOException e) {
                config = new AutoAttackConfigModel();
            }
        } else {
            saveConfig();
        }
    }

    public static void saveConfig() {
        try (Writer writer = Files.newBufferedWriter(CONFIG_PATH)) {
            GSON.toJson(config, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static AutoAttackConfigModel getConfig() {
        return config;
    }
}
