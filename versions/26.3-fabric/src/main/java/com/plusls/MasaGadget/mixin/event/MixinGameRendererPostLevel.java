package com.plusls.MasaGadget.mixin.event;

import com.plusls.MasaGadget.impl.feature.entityInfo.EntityInfoRenderer;
import com.plusls.MasaGadget.impl.feature.entityTrace.EntityTraceRenderer;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.joml.Matrix4fStack;
import top.hendrixshen.magiclib.impl.render.TextRenderer;

import com.mojang.blaze3d.ProjectionType;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.renderpearl.api.buffers.GpuBufferSlice;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// CHECKSTYLE.OFF: JavadocStyle
/**
 * <li>mc1.14 ~ mc26.2: subproject 1.16.5 (main project) [dummy]</li>
 * <li>mc26.3+        : subproject 26.3        &lt;--------</li>
 */
// CHECKSTYLE.ON: JavadocStyle
@Mixin(GameRenderer.class)
public abstract class MixinGameRendererPostLevel {
    @Unique
    private static @Nullable GpuBufferSlice masa_gadget_mod$levelProjection;

    @ModifyArg(
            method = "renderLevel",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/mojang/blaze3d/systems/RenderSystem;setProjectionMatrix(Lcom/mojang/renderpearl/api/buffers/GpuBufferSlice;Lcom/mojang/blaze3d/ProjectionType;)V"
            ),
            index = 0
    )
    private GpuBufferSlice masa_gadget_mod$captureLevelProjection(GpuBufferSlice projection) {
        MixinGameRendererPostLevel.masa_gadget_mod$levelProjection = projection;
        return projection;
    }

    @Inject(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/GameRenderer;applyPostEffects()V",
                    shift = At.Shift.AFTER
            )
    )
    private void masa_gadget_mod$drawEntityInfo(CallbackInfo ci) {
        GpuBufferSlice levelProjection = MixinGameRendererPostLevel.masa_gadget_mod$levelProjection;
        Matrix4fStack modelViewStack = RenderSystem.getModelViewStack();
        modelViewStack.pushMatrix();

        try {
            Camera camera = Minecraft.getInstance().gameRenderer.mainCamera();
            modelViewStack.set(camera.getViewRotationMatrix(new Matrix4f()));

            if (levelProjection != null) {
                RenderSystem.setProjectionMatrix(levelProjection, ProjectionType.PERSPECTIVE);
            }

            TextRenderer.beginBatch();

            try {
                EntityInfoRenderer.getInstance().drawQueued();
                EntityTraceRenderer.getInstance().drawQueued();
            } finally {
                TextRenderer.endBatch();
            }
        } finally {
            modelViewStack.popMatrix();
        }
    }
}
