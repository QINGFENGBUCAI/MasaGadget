package com.plusls.MasaGadget.mixin.mod_tweak.malilib.fixConfigScreenRegistryCrash;

//#if MC >= 26.1
//$$ import com.plusls.MasaGadget.SharedConstants;
//$$ import com.plusls.MasaGadget.util.ModId;
//$$ import fi.dy.masa.malilib.gui.GuiBase;
//$$ import fi.dy.masa.malilib.gui.config.registry.ConfigScreenRegistry;
//$$ import fi.dy.masa.malilib.util.data.ModInfo;
//$$ import org.jetbrains.annotations.NotNull;
//$$ import org.jetbrains.annotations.Nullable;
//$$ import top.hendrixshen.magiclib.api.dependency.annotation.Dependencies;
//$$ import top.hendrixshen.magiclib.api.dependency.annotation.Dependency;
//#else
import top.hendrixshen.magiclib.api.preprocess.DummyClass;
//#endif

//#if MC >= 26.1
//$$ import org.spongepowered.asm.mixin.Final;
//$$ import org.spongepowered.asm.mixin.Mixin;
//$$ import org.spongepowered.asm.mixin.Overwrite;
//$$ import org.spongepowered.asm.mixin.Shadow;
//#else
import org.spongepowered.asm.mixin.Mixin;
//#endif

//#if MC >= 26.1
//$$ import java.util.Map;
//$$ import java.util.Objects;
//$$ import java.util.function.Supplier;
//#endif

// CHECKSTYLE.OFF: JavadocStyle
/**
 * <li>mc1.14 ~ mc26.1: subproject 1.16.5 (main project) [dummy]        &lt;--------</li>
 * <li>mc26.1.2+      : subproject 1.16.5 (main project)</li>
 */
// CHECKSTYLE.ON: JavadocStyle
//#if MC >= 26.1
//$$ @Dependencies(require = @Dependency(value = ModId.malilib, versionPredicates = ">=0.30.0"))
//$$ @Mixin(value = ConfigScreenRegistry.class, remap = false)
//#else
@Mixin(DummyClass.class)
//#endif
public abstract class MixinConfigScreenRegistry {
    //#if MC >= 26.1
    //$$ @Shadow
    //$$ @Final
    //$$ protected Map<String, ModInfo> modsMap;
    //$$
    //$$ /**
    //$$  * Malilib eagerly creates every registered config screen here just to figure out which
    //$$  * mod owns the current one. A single broken factory (e.g. litematica-printer on 26.3,
    //$$  * whose config screen class fails to initialize) then throws out of
    //$$  * GuiConfigsBase#initGui and aborts the whole config screen build, leaving it without
    //$$  * category buttons. Make the lookup tolerant against broken factories instead.
    //$$  */
    //$$ @Overwrite
    //$$ public @Nullable ModInfo getModInfoFromConfigScreen(@NotNull Class<? extends GuiBase> clazz) {
    //$$     for (ModInfo modInfo : this.modsMap.values()) {
    //$$         try {
    //$$             Supplier<GuiBase> supplier = modInfo.configScreenSupplier();
    //$$
    //$$             if (supplier != null && Objects.requireNonNull(supplier.get()).getClass() == clazz) {
    //$$                 return modInfo;
    //$$             }
    //$$         } catch (Throwable t) {
    //$$             SharedConstants.getLogger().warn("Config screen factory of mod {} is broken, skipping it.",
    //$$                     modInfo.modId(), t);
    //$$         }
    //$$     }
    //$$
    //$$     return null;
    //$$ }
    //#endif
}
