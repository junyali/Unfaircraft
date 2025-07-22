package io.github.junyali.unfaircraft;

import net.minecraft.client.Minecraft;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackRepository;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

import java.util.ArrayList;
import java.util.Collection;

@Mod(value = UnfairCraft.MODID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = UnfairCraft.MODID, value = Dist.CLIENT)
public class UnfairCraftClient {
    public UnfairCraftClient(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {
        UnfairCraft.LOGGER.info("HELLO FROM CLIENT SETUP");
        UnfairCraft.LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());

        event.enqueueWork(UnfairCraftClient::registerResourcePack);
    }

    private static void registerResourcePack() {
        PackRepository packRepository = Minecraft.getInstance().getResourcePackRepository();

        String packId = UnfairCraft.MODID;

        try {
            packRepository.reload();
            Pack pack = packRepository.getPack(packId);
            if (pack != null) {
                Collection<String> enabledPacks = new ArrayList<>(packRepository.getSelectedIds());
                enabledPacks.add(packId);
                packRepository.setSelected(enabledPacks);
            }
        } catch (Exception e) {
            UnfairCraft.LOGGER.error(String.valueOf(e));
        }
    }
}
