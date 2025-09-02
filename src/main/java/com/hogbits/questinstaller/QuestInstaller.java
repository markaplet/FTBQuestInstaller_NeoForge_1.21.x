package com.hogbits.questinstaller;

import net.neoforged.fml.event.lifecycle.FMLConstructModEvent;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
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