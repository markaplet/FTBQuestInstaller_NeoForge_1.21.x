package com.hogbits.questinstaller;

import net.neoforged.bus.api.Event;
import net.neoforged.fml.event.lifecycle.FMLConstructModEvent;
import net.neoforged.fml.loading.FMLPaths;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraft.client.Minecraft;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod("questinstaller")
public class QuestInstaller {

    public QuestInstaller(IEventBus modEventBus) {
        modEventBus.addListener(this::setup);
    }

    private void setup(final FMLConstructModEvent event) {
        // This is called during the setup phase
        copyDefaultFiles();
    }

    private void copyDefaultFiles() {
        // Your file-copying logic here
        // FTB QUESTS FILES
        copyIfMissing("ftbquests/quests/chapters/minecolonies.snbt",
                FMLPaths.CONFIGDIR.get().resolve("ftbquests/quests/chapters/minecolonies.snbt"));

        copyIfMissing("ftbquests/quests/reward_tables/choice_compost.snbt",
                FMLPaths.CONFIGDIR.get().resolve("ftbquests/quests/reward_tables/choice_compost.snbt"));

        copyIfMissing("ftbquests/quests/reward_tables/choice_crush.snbt",
                FMLPaths.CONFIGDIR.get().resolve("ftbquests/quests/reward_tables/choice_crush.snbt"));

        copyIfMissing("ftbquests/quests/reward_tables/choice_flowers.snbt",
                FMLPaths.CONFIGDIR.get().resolve("ftbquests/quests/reward_tables/choice_flowers.snbt"));

        copyIfMissing("ftbquests/quests/reward_tables/choice_food.snbt",
                FMLPaths.CONFIGDIR.get().resolve("ftbquests/quests/reward_tables/choice_food.snbt"));

        copyIfMissing("ftbquests/quests/reward_tables/choice_hospital.snbt",
                FMLPaths.CONFIGDIR.get().resolve("ftbquests/quests/reward_tables/choice_hospital.snbt"));

        copyIfMissing("ftbquests/quests/reward_tables/choice_logs.snbt",
                FMLPaths.CONFIGDIR.get().resolve("ftbquests/quests/reward_tables/choice_logs.snbt"));

        copyIfMissing("ftbquests/quests/reward_tables/choice_sapling.snbt",
                FMLPaths.CONFIGDIR.get().resolve("ftbquests/quests/reward_tables/choice_sapling.snbt"));

        copyIfMissing("ftbquests/quests/reward_tables/choice_stone.snbt",
                FMLPaths.CONFIGDIR.get().resolve("ftbquests/quests/reward_tables/choice_stone.snbt"));

        copyIfMissing("ftbquests/quests/reward_tables/minecolonies_common.snbt",
                FMLPaths.CONFIGDIR.get().resolve("ftbquests/quests/reward_tables/minecolonies_common.snbt"));

        // KUBE JS FILES
        copyIfMissing("kubejs/assets/minecolonies/textures/quests/hc_build_tool.png",
                FMLPaths.GAMEDIR.get().resolve("kubejs/assets/minecolonies/textures/quests/hc_build_tool.png"));

        copyIfMissing("kubejs/assets/minecolonies/textures/quests/hc_defense.png",
                FMLPaths.GAMEDIR.get().resolve("kubejs/assets/minecolonies/textures/quests/hc_defense.png"));

        copyIfMissing("kubejs/assets/minecolonies/textures/quests/hc_feed_the_machine.png",
                FMLPaths.GAMEDIR.get().resolve("kubejs/assets/minecolonies/textures/quests/hc_feed_the_machine.png"));

        copyIfMissing("kubejs/assets/minecolonies/textures/quests/hc_getting_started.png",
                FMLPaths.GAMEDIR.get().resolve("kubejs/assets/minecolonies/textures/quests/hc_getting_started.png"));

        copyIfMissing("kubejs/assets/minecolonies/textures/quests/hc_logistics.png",
                FMLPaths.GAMEDIR.get().resolve("kubejs/assets/minecolonies/textures/quests/hc_logistics.png"));

        copyIfMissing("kubejs/assets/minecolonies/textures/quests/hc_production.png",
                FMLPaths.GAMEDIR.get().resolve("kubejs/assets/minecolonies/textures/quests/hc_production.png"));

        copyIfMissing("kubejs/assets/minecolonies/textures/quests/hc_the_builder.png",
                FMLPaths.GAMEDIR.get().resolve("kubejs/assets/minecolonies/textures/quests/hc_the_builder.png"));

        copyIfMissing("kubejs/assets/minecolonies/textures/quests/hc_university_research.png",
                FMLPaths.GAMEDIR.get().resolve("kubejs/assets/minecolonies/textures/quests/hc_university_research.png"));

        copyIfMissing("kubejs/assets/minecolonies/textures/quests/hc_utility.png",
                FMLPaths.GAMEDIR.get().resolve("kubejs/assets/minecolonies/textures/quests/hc_utility.png"));

        copyIfMissing("kubejs/assets/minecolonies/textures/quests/minecolonies_logo_medium.png",
                FMLPaths.GAMEDIR.get().resolve("kubejs/assets/minecolonies/textures/quests/minecolonies_logo_medium.png"));
    }

    private void copyIfMissing(String resourcePath, Path targetPath) {
        try {
            if (!Files.exists(targetPath)) {
                Files.createDirectories(targetPath.getParent());
                try (InputStream in = getClass().getClassLoader()
                        .getResourceAsStream("data/questinstaller/defaults/" + resourcePath)) {
                    if (in != null) {
                        Files.copy(in, targetPath);
                        System.out.println("[QuestInstaller] Copied: " + targetPath);
                    } else {
                        System.err.println("[QuestInstaller] Missing resource in JAR: " + resourcePath);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}