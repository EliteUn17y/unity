package me.eliteun17y.unity.event.impl;

import me.eliteun17y.unity.event.Event;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelSheep1;
import net.minecraft.client.renderer.entity.RenderSheep;
import net.minecraft.entity.passive.EntitySheep;

public class EventRenderSheepWool extends Event {
    public ModelSheep1 sheepModel;
    public EntitySheep entitylivingbaseIn;
    public float limbSwing;
    public float limbSwingAmount;
    public float partialTicks;
    public float ageInTicks;
    public float netHeadYaw;
    public float headPitch;
    public float scale;
    public RenderSheep modelBase;

    public EventRenderSheepWool(ModelSheep1 sheepModel, EntitySheep entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale, RenderSheep modelBase) {
        this.sheepModel = sheepModel;
        this.entitylivingbaseIn = entitylivingbaseIn;
        this.limbSwing = limbSwing;
        this.limbSwingAmount = limbSwingAmount;
        this.partialTicks = partialTicks;
        this.ageInTicks = ageInTicks;
        this.netHeadYaw = netHeadYaw;
        this.headPitch = headPitch;
        this.scale = scale;
        this.modelBase = modelBase;
    }

    public ModelSheep1 getSheepModel() {
        return sheepModel;
    }

    public void setSheepModel(ModelSheep1 sheepModel) {
        this.sheepModel = sheepModel;
    }

    public EntitySheep getEntitylivingbaseIn() {
        return entitylivingbaseIn;
    }

    public void setEntitylivingbaseIn(EntitySheep entitylivingbaseIn) {
        this.entitylivingbaseIn = entitylivingbaseIn;
    }

    public float getLimbSwing() {
        return limbSwing;
    }

    public void setLimbSwing(float limbSwing) {
        this.limbSwing = limbSwing;
    }

    public float getLimbSwingAmount() {
        return limbSwingAmount;
    }

    public void setLimbSwingAmount(float limbSwingAmount) {
        this.limbSwingAmount = limbSwingAmount;
    }

    public float getPartialTicks() {
        return partialTicks;
    }

    public void setPartialTicks(float partialTicks) {
        this.partialTicks = partialTicks;
    }

    public float getAgeInTicks() {
        return ageInTicks;
    }

    public void setAgeInTicks(float ageInTicks) {
        this.ageInTicks = ageInTicks;
    }

    public float getNetHeadYaw() {
        return netHeadYaw;
    }

    public void setNetHeadYaw(float netHeadYaw) {
        this.netHeadYaw = netHeadYaw;
    }

    public float getHeadPitch() {
        return headPitch;
    }

    public void setHeadPitch(float headPitch) {
        this.headPitch = headPitch;
    }

    public RenderSheep getModelBase() {
        return modelBase;
    }

    public void setModelBase(RenderSheep modelBase) {
        this.modelBase = modelBase;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }
}
