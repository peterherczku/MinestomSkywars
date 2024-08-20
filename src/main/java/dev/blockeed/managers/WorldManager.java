package dev.blockeed.managers;

import dev.blockeed.Main;
import lombok.Getter;
import net.minestom.server.MinecraftServer;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.LightingChunk;
import net.minestom.server.instance.anvil.AnvilLoader;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class WorldManager {

    @Getter
    private static InstanceContainer instanceContainer;

    public static void init() {
        instanceContainer = MinecraftServer.getInstanceManager().createInstanceContainer();
        Path worldPath = null;
        try {
            worldPath = Paths.get(Main.class.getClassLoader().getResource("aquamarine").toURI());
        } catch (URISyntaxException e) {
            System.out.println("Error loading world.");
            return;
        }

        AnvilLoader anvilLoader = new AnvilLoader(worldPath);
        instanceContainer.setChunkLoader(anvilLoader);
        instanceContainer.setChunkSupplier(LightingChunk::new);
    }

}
