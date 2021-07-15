package com.nquantum.ui.clickgui.elem;

import cf.nquan.util.Colors;
import cf.nquan.util.GuiUtil;
import cf.nquan.util.RenderUtil;
import com.nquantum.Asyncware;
import com.nquantum.module.Module;
import com.nquantum.ui.clickgui.Frame;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import org.lwjgl.opencl.AMDOfflineDevices;

public class Button
{
    int x, y, width, height;

    Module module;

    Frame parent;


    Minecraft mc = Minecraft.getMinecraft();
    public Button(Module module, int x, int y, Frame parent){
        this.module = module;
        this.x = x;
        this.y = y;
        this.parent = parent;
        this.height = 15;
    }

    public void draw(int MouseX, int MouseY){
        int color = -1;
        Asyncware.renderer.drawTotalCenteredString(module.getName(), x + 2, y, color );

        if(module.isToggled()){
            color = Colors.RGB();
        }
    }

    public void onClick(int mouseX, int mouseY, int button){
        if(mouseX >= this.x && mouseX <= this.x + this.width && mouseY >= this.y && this.y <= this.y + this.height){
            module.toggle();
        }

    }

}
