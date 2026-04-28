package io.github.junyali.unfaircraft;

import io.github.junyali.unfaircraft.config.UnfairCraftConfig;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import org.slf4j.Logger;
import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(UnfairCraft.MODID)
public class UnfairCraft {
    public static final String MODID = "unfaircraft";
    public static final Logger LOGGER = LogUtils.getLogger();

    public UnfairCraft(IEventBus modEventBus, ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.COMMON, UnfairCraftConfig.SPEC, "unfaircraft.toml");
        modEventBus.addListener(this::registerNetworking);
    }

    private void registerNetworking(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar payloadRegistrar = event.registrar(MODID)
                .versioned("1.1.0");
    }
}
