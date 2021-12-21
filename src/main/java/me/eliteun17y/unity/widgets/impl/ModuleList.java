package me.eliteun17y.unity.widgets.impl;

import me.eliteun17y.unity.Unity;
import me.eliteun17y.unity.event.Subscribe;
import me.eliteun17y.unity.event.impl.EventRenderGame;
import me.eliteun17y.unity.module.Category;
import me.eliteun17y.unity.module.Module;
import me.eliteun17y.unity.util.font.manager.FontManager;
import me.eliteun17y.unity.util.setting.impl.BooleanValue;
import me.eliteun17y.unity.util.setting.impl.ModeValue;
import me.eliteun17y.unity.util.ui.UIUtil;
import me.eliteun17y.unity.widgets.Widget;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

import java.util.ArrayList;
import java.util.Comparator;

public class ModuleList extends Widget {
    public BooleanValue autoAlign = new BooleanValue(this, "Auto Align", true);
    public BooleanValue autoSort = new BooleanValue(this, "Auto Sort", true);
    public ModeValue alignMode = new ModeValue("Align Mode", "Left", "Left", "Center", "Right");
    public ModeValue sortMode = new ModeValue("Sorting Mode", "Longest to shortest", "Longest to shortest", "Shortest to longest");

    public float lastWidth = 0;
    public float lastHeight = 0;

    public ModuleList() {
        super("Module List", "Displays all your enabled modules in a list.", 0, 0, 0, 0);
        addValueOnValueChange(autoAlign, alignMode, false);
        addValueOnValueChange(autoSort, sortMode, false);
    }

    @Subscribe
    public void onRenderGame(EventRenderGame event) {
        ScaledResolution sr = new ScaledResolution(mc);

        if(event.getElementType() == RenderGameOverlayEvent.ElementType.TEXT) {
            scale();

            int sort = 0; // 1 is longest to shortest, 0 is shortest to longest

            if(autoSort.getObject()) {
                if((this.y + height / 2) < sr.getScaledHeight() / 2.0f)
                    sort = 1;
            }else{
                sort = sortMode.getMode().equals("Longest to shortest") ? 1 : 0;
            }

            int align = 0; // 0 is left, 1 is center and 2 is right

            if(autoAlign.getObject()) {
                if((x + width / 2) == sr.getScaledWidth() / 2.0f)
                    align = 1;

                if((x + width / 2) > sr.getScaledWidth() / 2.0f)
                    align = 2;
            }else{
                align = alignMode.getMode().equals("Left") ? 0 : alignMode.getMode().equals("Center") ? 1 : 2;
            }

            lastWidth = width;
            lastHeight = height;

            float y = 0;
            float w = 0;
            float h = 0;

            Unity.instance.moduleManager.getModules().sort(new ModuleComparator(sort));

            ArrayList<String> mods = new ArrayList<>();

            for(Module module : Unity.instance.moduleManager.getModules()) {
                if (!module.isToggled() || module.getCategory() == Category.HIDDEN) continue;

                if (FontManager.instance.robotoRegularSmall.getStringWidth(module.getDisplayName()) > w)
                    w = FontManager.instance.robotoRegularSmall.getStringWidth(module.getDisplayName());

                h += FontManager.instance.robotoRegularSmall.getStringHeight(module.getDisplayName());

                mods.add(module.getDisplayName());
            }


            if(lastWidth > w * scale && align == 2) {
                x = x + lastWidth - w * scale;
            }else if(lastWidth / scale < w * scale && align == 2){
                x = x + lastWidth - w * scale;
            }

            if(lastHeight < h * scale && sort == 0) {
                this.y = this.y + lastHeight - h * scale;
            }else if(lastHeight > h * scale && sort == 0) {
                this.y = this.y + lastHeight - h * scale;
            }

            for(Module module : Unity.instance.moduleManager.getModules()) {
                if(!module.isToggled() || module.getCategory() == Category.HIDDEN) continue;

                float x = align == 0 ? this.x : align == 1 ? this.x + width / 2 - FontManager.instance.robotoRegularSmall.getStringWidth(module.getDisplayName()) / 2 : this.x + w - FontManager.instance.robotoRegularSmall.getStringWidth(module.getDisplayName());

                FontManager.instance.robotoRegularSmall.drawString(module.getDisplayName(), x, this.y + y, UIUtil.getOppositeFontColor().getRGB());

                y += FontManager.instance.robotoRegularSmall.getStringHeight(module.getDisplayName());
            }

            height = y * scale;
            width = w * scale;

            unscale();
        }
    }

    public static class ModuleComparator implements Comparator<Module> {
        public int sort;

        public ModuleComparator(int sort) {
            this.sort = sort;
        }

        @Override
        public int compare(Module o1, Module o2) {
            if(sort == 1) {
                if(FontManager.instance.robotoRegularSmall.getStringWidth(o1.getDisplayName()) < FontManager.instance.robotoRegularSmall.getStringWidth(o2.getDisplayName()))
                    return 1;

                if(FontManager.instance.robotoRegularSmall.getStringWidth(o1.getDisplayName()) > FontManager.instance.robotoRegularSmall.getStringWidth(o2.getDisplayName()))
                    return -1;
            }else if(sort == 0) {
                if(FontManager.instance.robotoRegularSmall.getStringWidth(o1.getDisplayName()) > FontManager.instance.robotoRegularSmall.getStringWidth(o2.getDisplayName()))
                    return 1;

                if(FontManager.instance.robotoRegularSmall.getStringWidth(o1.getDisplayName()) <  FontManager.instance.robotoRegularSmall.getStringWidth(o2.getDisplayName()))
                    return -1;
            }
            return 0;
        }
    }
}