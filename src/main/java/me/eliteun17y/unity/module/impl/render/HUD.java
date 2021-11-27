package me.eliteun17y.unity.module.impl.render;

import me.eliteun17y.unity.Unity;
import me.eliteun17y.unity.event.Subscribe;
import me.eliteun17y.unity.event.impl.EventRenderGame;
import me.eliteun17y.unity.module.Category;
import me.eliteun17y.unity.module.Module;
import me.eliteun17y.unity.util.Reference;
import me.eliteun17y.unity.util.font.manager.FontManager;
import me.eliteun17y.unity.util.ui.RenderHelper;
import me.eliteun17y.unity.util.ui.UIUtil;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import org.lwjgl.input.Keyboard;

public class HUD extends Module {
    public HUD() {
        super("HUD", "Heads up display.", Category.RENDER, Keyboard.KEY_NONE);
        toggle();
    }

    @Subscribe
    public void onRenderGame(EventRenderGame event) {
        if(event.getElementType() == RenderGameOverlayEvent.ElementType.TEXT) {

            /*
            int sort = 0; // 1 is longest to shortest, 0 is shortest to longest

            if(autoSort.getObject()) {
                if((this.y + height / 2) * scale < sr.getScaledHeight() / 2.0f)
                    sort = 1;
            }else{
                sort = sortMode.getMode().equals("Longest to shortest") ? 1 : 0;
            }

            float y = 0;

            Unity.instance.moduleManager.getModules().sort(new ModuleComparator(sort));

            for(Module m : Unity.instance.moduleManager.getModules()) {
                if(m.animationPlaying) {
                    if(m.animationValue > 0 && m.isToggled()) {
                        if(m.animationValue < FontManager.instance.robotoRegularSmall.getStringWidth(m.getDisplayName()) + 6) {
                            m.animationValue += 0.5f;
                        }else{
                            m.animationPlaying = false;
                        }
                    }else if(!m.isToggled()){
                        if(sr.getScaledWidth() - m.animationValue < sr.getScaledWidth()) {
                            m.animationValue -= 0.5f;
                        }else{
                            m.animationPlaying = false;
                        }
                    }
                }
                    if (m.isToggled() || m.animationPlaying) {
                        int align = 0; // 0 is left, 1 is center and 2 is right

                        if(autoAlign.getObject()) {
                            if((x + width / 2) * scale == sr.getScaledWidth() / 2.0f)
                                align = 1;

                            if((x + width / 2) * scale > sr.getScaledWidth() / 2.0f)
                                align = 2;
                        }else{
                            align = alignMode.getMode().equals("Left") ? 0 : alignMode.getMode().equals("Center") ? 1 : 2;
                        }
                        if(align == 0)
                            FontManager.instance.robotoRegularSmall.drawString(m.getDisplayName(), x, this.y + y, UIUtil.getOppositeFontColor().getRGB());
                        else if(align == 1)
                            FontManager.instance.robotoRegularSmall.drawString(m.getDisplayName(), x + width / 2 - FontManager.instance.robotoRegularSmall.getStringWidth(m.getDisplayName()) / 2, this.y + y, UIUtil.getOppositeFontColor().getRGB());
                        else if(align == 2)
                            FontManager.instance.robotoRegularSmall.drawString(m.getDisplayName(), x + width - FontManager.instance.robotoRegularSmall.getStringWidth(m.getDisplayName()), this.y + y, UIUtil.getOppositeFontColor().getRGB());

                        y += FontManager.instance.robotoRegularSmall.getStringHeight(m.getDisplayName()) * scale;
                    }
                }

                float temp = 0;
                for(Module module : Unity.instance.moduleManager.getModules()) {
                    if(module.isToggled())
                        if(FontManager.instance.robotoRegularSmall.getStringWidth(module.getDisplayName()) * scale > temp)
                            temp = (FontManager.instance.robotoRegularSmall.getStringWidth(module.getDisplayName()) * scale);
                }

                width = temp;

                height = y;
             */
        }
    }
}
