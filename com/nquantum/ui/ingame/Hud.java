package com.nquantum.ui.ingame;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

import cf.nquan.font.UnicodeFontRenderer;
import cf.nquan.ttf.FontData;
import cf.nquan.ttf.FontRenderer;
import cf.nquan.util.*;
import com.nquantum.Asyncware;
import com.nquantum.font.Fonts;
import com.nquantum.font.MCFontRenderer;
import com.nquantum.module.Module;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.ARBSync;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiInventory;
import sun.java2d.pipe.hw.AccelSurface;

public class Hud extends GuiIngame {
	private static Minecraft mc = Minecraft.getMinecraft();
	private net.minecraft.client.gui.FontRenderer font = mc.fontRendererObj;


	//public static ScaledResolution sr = new ScaledResolution(mc);
	/*
	public static FontUtil font3 = new FontUtil("assets/minecraft/blazing/regular.ttf", Font.PLAIN, 28);
	public static FontUtil font2 = new FontUtil("assets/minecraft/blazing/regular.ttf", Font.PLAIN, 20);
	public static FontRenderer f = new FontRenderer(new ResourceLocation("blazing/regular.ttf"), 20.0f);

	 */

	public Hud(Minecraft mcIn) {
		super(mcIn);
	}

	@Override
	public void renderGameOverlay(float partialTicks) {
		super.renderGameOverlay(partialTicks);


		if(!mc.gameSettings.showDebugInfo) {
			renderInfo();


		//	renderArrayList();

		}
	}

	private void renderInfo() {
		ScaledResolution sr = new ScaledResolution(mc);

		String text = "Credits to gabrik <3";
		int textWidth = mc.fontRendererObj.getStringWidth(text);

		float color = ((System.currentTimeMillis())%2000) / 2000f;
		int drawColor = Color.HSBtoRGB(color, 0.8f, 1f);

		int x = sr.getScaledWidth()/2 - textWidth/2;

	//	mc.fontRendererObj.drawStringWithShadow("Credits to gabrik <3", x, 10, drawColor);

		GL11.glPushMatrix();
		GL11.glScalef(1.0F, 1.0F, 1.0F);

		Character firstLetter = Asyncware.instance.name.charAt(1);
		String sub = Asyncware.instance.name.substring(1);

		float bps = Math.round(mc.thePlayer.getDistance(mc.thePlayer.lastTickPosX, mc.thePlayer.posY, mc.thePlayer.lastTickPosZ) * 200) / 10f;
	//	FontUtil fontheader = new FontUtil("blazing/regular.ttf", Font.PLAIN, 16);


		/*
		GlStateManager.pushMatrix();
		GlStateManager.translate(4, 4, 0);
		GlStateManager.scale(1.5f, 1.5f, 1);
		GlStateManager.translate(-4, -4, 0);

		GlStateManager.popMatrix();



*/

		Gui gui = new Gui();

		String csgoMode = Asyncware.instance.settingsManager.getSettingByName("CSGO Mode").getValString();

		boolean isCsgo = Asyncware.instance.settingsManager.getSettingByName("CSGO").getValBoolean();
		boolean isXd = Asyncware.instance.settingsManager.getSettingByName("Big").getValBoolean();

		boolean neverlose = csgoMode.equalsIgnoreCase("Neverlose");
		boolean skeet = csgoMode.equalsIgnoreCase("Gamesense");

		if (isCsgo) {

			if (neverlose) {

				GlStateManager.pushMatrix();
				GlStateManager.translate(-14f, 4f, 0);
				Gui.drawRect(Asyncware.instance.csgoRenderer.getStringWidth("Asyncware | " + mc.thePlayer.getName() + " | " + Time.getTime(System.currentTimeMillis(), "HH:mm:ss")) + 30D, 17D, 20D, 4D, new Color(9, 19, 34, 167).getRGB());
				GlStateManager.translate(0f, 23f, 0);
				Gui.drawRect(Asyncware.instance.csgoRenderer.getStringWidth("Asyncware | " + mc.thePlayer.getName() + " | " + Time.getTime(System.currentTimeMillis(), "HH:mm:ss")) + 30D, -20D, 20D, -19D, new Color(57, 44, 162,255).getRGB());
				GlStateManager.popMatrix();


				Asyncware.csgoRenderer.drawString("A", 10, 10, Colors.fadeBetween(0x5642F3, 0x392CA2, ((System.currentTimeMillis() + (100)) % 1000 / (1000 / 2.0f))), false);
				Asyncware.csgoRenderer.drawString("syncware | " + mc.thePlayer.getName() + " | " + Time.getTime(System.currentTimeMillis(), "HH:mm:ss"), 14.7f, 10, -1, false);

			} else {
				GlStateManager.pushMatrix();

				float strw = Asyncware.csgoRenderer.getStringWidth("async" + "ware | " + mc.thePlayer.getName() + " | " + Math.round(mc.timer.ticksPerSecond) + "tps");
				GL11.glTranslatef(5, 5, 0);
				GL11.glScalef(0.8f, 0.8f, 0.8f);


				Gui.drawRect(162.2f, 22.2f, -1.2f, -1.2f, new Color(82, 82, 82, 255).getRGB());
				Gui.drawRect(161.4f, 21.4f, -0.4f, -0.4f, new Color(47, 47, 47, 255).getRGB());
				Gui.drawRect(160.7f, 20.7f, 0.7f, 0.7f, new Color(82, 82, 82, 255).getRGB());
				Gui.drawRect(160, 20, 1, 1, new Color(28, 28, 28, 255).getRGB());
			//	gui.drawGradientRect(160.7f, 2.2f, 0.7f, 2.1f, Colors.RGBX(2, 1.0f, 1.0f, 20), Colors.RGBX(2, 1.0f, 1.0f, 530));
				GL11.glScalef(1.4f, 1.4f, 1.4f);

				Asyncware.csgoRenderer.drawString("", 2, 2, -1, false);
				mc.getTextureManager().bindTexture(new ResourceLocation("asyncware/skeet.png"));


				Gui.drawModalRectWithCustomSizedTexture(1.1f, 1, 0f, 0.0f, 113, 1, 113, 1);


				Asyncware.csgoRenderer.drawString("async", 7.5f, 3, -1, false);
				Asyncware.csgoRenderer.drawString("ware", 26.0f, 3,0x6cc312, false);
				Asyncware.csgoRenderer.drawString(" | " + mc.getDebugFps() + "fps" + " | " + Math.round(mc.timer.ticksPerSecond ) + "tps", 43.0f, 3,-1, false);


				//fontheader.drawString("asyncware", 1.5f, 3, -1);
				GlStateManager.popMatrix();



			}

	}
		else{
			if(Asyncware.instance.moduleManager.getModuleByName("Jello").isToggled()){
				Asyncware.renderer2.drawString("BlazingPack", 2, -2, new Color(255, 255, 255, 160).getRGB(), false);
				Asyncware.renderer.drawString("Edition", 4, 34, new Color(255, 255, 255, 160).getRGB(), false);
			} else {
				if (!Asyncware.instance.settingsManager.getSettingByName("Array Design").getValString().equalsIgnoreCase("Weird") && !Asyncware.instance.moduleManager.getModuleByName("HvH Engine").isToggled()) {

					int cur = new Color(100, 20, 255, 255).getRGB();
					Asyncware.renderer.drawString("A", 0, 0,Colors.fadeBetween(cur, Colors.darker(cur, 0.28f), ((System.currentTimeMillis() + (100)) % 1000 / (1000 / 2.0f))), true);
					Asyncware.renderer.drawString("syncware", 6, 0, -1, true);


				//Asyncware.csgoRenderer.drawScaledString("Zarzel", 100, 100f, new Color(0, 0, 0, 255).getRGB(), false, 1.102f);

				//Asyncware.csgoRenderer.drawScaledString("Zarzel", 100.2f, 99.9f, -1, false, 1.0f);

					//	Asyncware.renderer.drawString("K", 2, 2, Colors.RGB(), true);
					//	Asyncware.renderer.drawString("okscraftware - UHC Edition ", 8, 2, -1, true);
				}

				if(Asyncware.instance.moduleManager.getModuleByName("HvH Engine").isToggled()) {
					mc.fontRendererObj.drawStringWithShadow("H", 2, 2, Colors.RGB());
					mc.fontRendererObj.drawStringWithShadow("vH Engine", 8, 2, -1);


					GuiUtil.drawScaledString("Engine: ON", 2, 11, true, 0.5f);

				}
			}



		}

		if(isXd){
			Asyncware.renderer1.drawString("Asyncware", 3, 3, Colors.RGB(), true);


		}

		//GuiUtil.drawChromaStringModern("cool text :sunglasses:", 320, 40, false);



		// RenderUtil.renderRoundedQuad(new Vec3d(30,50,0), new Vec3d(80,100,0), 10,  new Color(0, 0, 0, 70));

		GlStateManager.pushMatrix();
		GlStateManager.translate(4, 4, 0);
		GlStateManager.scale(0.86f, 0.86f, 1);
		GlStateManager.translate(-4, -4, 0);
		/*
		Asyncware.renderer.drawString("V", 2, 16, Colors.Astolfo(100, 1.0f, 0.5f), true);
		Asyncware.renderer.drawString("inegar Sauce Development Edition", 8, 16, -1, true);

		 */
		GlStateManager.popMatrix();

		//
		//font.drawString("A", 2, 2, Colors.Astolfo(100, 1.0f, 0.5f));
		//font.drawString("syncware", 8, 2, -1);
		//Asyncware.fontxd.drawString("test", 5, 5, -1);

		//font.drawString(Asyncware.instance.name, 2, 2, 0xff1C4BE0);

		GL11.glPopMatrix();


	}



	private void renderKeyStrokes() {
		ScaledResolution sr = new ScaledResolution(mc);

		int wAlpha = (Keyboard.isKeyDown(mc.gameSettings.keyBindForward.getKeyCode()) ? 125 : 50);
		int aAlpha = (Keyboard.isKeyDown(mc.gameSettings.keyBindLeft.getKeyCode()) ? 125 : 50);
		int sAlpha = (Keyboard.isKeyDown(mc.gameSettings.keyBindBack.getKeyCode()) ? 125 : 50);
		int dAlpha = (Keyboard.isKeyDown(mc.gameSettings.keyBindRight.getKeyCode()) ? 125 : 50);

		Gui.drawRect(sr.getScaledWidth() - 29 - 29, sr.getScaledHeight() - 4 - 25 - 29, sr.getScaledWidth() - 4 - 29, sr.getScaledHeight() - 4 - 29, new Color(0, 0, 0, wAlpha).getRGB());
		Gui.drawRect(sr.getScaledWidth() - 29 - 29 - 29, sr.getScaledHeight() - 4 - 25, sr.getScaledWidth() - 4 - 29 - 29, sr.getScaledHeight() - 4, new Color(0, 0, 0, aAlpha).getRGB());
		Gui.drawRect(sr.getScaledWidth() - 29 - 29, sr.getScaledHeight() - 4 - 25, sr.getScaledWidth() - 4 - 29, sr.getScaledHeight() - 4, new Color(0, 0, 0, sAlpha).getRGB());
		Gui.drawRect(sr.getScaledWidth() - 29, sr.getScaledHeight() - 4 - 25, sr.getScaledWidth() - 4, sr.getScaledHeight() - 4, new Color(0, 0, 0, dAlpha).getRGB());

		font.drawString("W", sr.getScaledWidth() - 48, sr.getScaledHeight() - 49, 0xffffffff);
		font.drawString("A", sr.getScaledWidth() - 77, sr.getScaledHeight() - 20, 0xffffffff);
		font.drawString("S", sr.getScaledWidth() - 48.5, sr.getScaledHeight() - 20, 0xffffffff);
		font.drawString("D", sr.getScaledWidth() - 19, sr.getScaledHeight() - 20, 0xffffffff);
	}
	// this.buttonList.add(new GuiButton(500, this.width / 2 - 100, this.height / 4 + 48 + 24 * 2, "Login"));
	private void renderPlayer() {
		GuiInventory.drawEntityOnScreen(80, 55, 25, 0, 0, mc.thePlayer);
	}
}
/*
if(arrStyle.equalsIgnoreCase("Weird")){
		// MINI RECT
		//Gui.drawRect(sr.getScaledWidth() - Asyncware.renderer.getStringWidth(m.getDisplayName()) + -10, y - 1, sr.getScaledWidth(),  4 +  Asyncware.renderer.getFontHeight() + y + 2 , Colors.Astolfo(count * 100, 1.0f, 0.5f));

		// BIG RECT
		//Gui.drawRect(sr.getScaledWidth() - Asyncware.renderer.getStringWidth(m.getDisplayName()) - 10, y - 1, sr.getScaledWidth(),  4 +  Asyncware.renderer.getFontHeight() + y , new Color(20, 20, 20, 255).getRGB());


		}



					 if(arrStyle.equalsIgnoreCase("Classic")) {
						// Gui.drawRect(sr.getScaledWidth() - Asyncware.renderer.getStringWidth(m.getDisplayName()) + 1, y - 4, sr.getScaledWidth(),  4 +  Asyncware.renderer.getFontHeight() + y + 2 , Colors.Astolfo(count * 100, 1.0f, 0.5f));

						// BIG RECT
						//Gui.drawRect(sr.getScaledWidth() - Asyncware.renderer.getStringWidth(m.getDisplayName()) - 10, y - 1, sr.getScaledWidth(),  4 +  Asyncware.renderer.getFontHeight()+ y , new Color(20, 20, 20, 255).getRGB());
					}
					private void renderArrayList() {
		ScaledResolution sr = new ScaledResolution(mc);

		ArrayList<Module> enabledMods = new ArrayList<Module>();
		for(Module m : Asyncware.instance.moduleManager.getModules())
			if(m.isToggled())
				enabledMods.add(m);

		enabledMods.sort((m1, m2) -> Asyncware.renderer.getStringWidth(m2.getDisplayName()) - Asyncware.renderer.getStringWidth(m1.getDisplayName()));

		int count = 0;
		int y = 2;


		double X = Asyncware.instance.settingsManager.getSettingByName("X").getValDouble();
		double Y = Asyncware.instance.settingsManager.getSettingByName("Y").getValDouble();


		GL11.glPushMatrix();
		GL11.glTranslated(X, Y, 0);
			for(Module m : enabledMods) {

				double offsety = Asyncware.instance.settingsManager.getSettingByName("YOffset").getValDouble();
				boolean isFont = Asyncware.instance.settingsManager.getSettingByName("Custom Font").getValBoolean();
				boolean isRect = Asyncware.instance.settingsManager.getSettingByName("Rect").getValBoolean();
				String arrStyle = Asyncware.instance.settingsManager.getSettingByName("Array Design").getValString();

				if(isRect){

					// MINI RECT
				//	Gui.drawRect(sr.getScaledWidth() - Asyncware.renderer.getStringWidth(m.getDisplayName()) + -12, y - 4, sr.getScaledWidth(),  4 +  Asyncware.renderer.getFontHeight() + y + 2 , Colors.Astolfo(count * 100, 1.0f, 0.5f));
					if(arrStyle.equalsIgnoreCase("Gamesense")){
						// BIG RECT
						GlStateManager.pushMatrix();
						//Gui.drawRect(sr.getScaledWidth() - Asyncware.renderer.getStringWidth(m.getDisplayName()) - 10, y - 1, sr.getScaledWidth(),  0 +  Asyncware.renderer.getFontHeight() + y , new Color(20, 20, 20, 120).getRGB());
						Gui.drawRect(sr.getScaledWidth() - Asyncware.renderer.getStringWidth(m.getDisplayName()) - 10, y - 1, sr.getScaledWidth(), 2 + Asyncware.renderer.getFontHeight() + y, new Color(12, 12, 12, 230).getRGB());
						//Gui.drawRect(sr.getScaledWidth() - mc.fontRendererObj.getStringWidth(m.getDisplayName())  - 8, count * (this.mc.fontRendererObj.FONT_HEIGHT + 6), sr.getScaledWidth(), 6 + this.mc.fontRendererObj.FONT_HEIGHT + count * (this.mc.fontRendererObj.FONT_HEIGHT + 6), new Color(24, 24, 24,255).getRGB());

						Gui.drawRect(958, count * (mc.fontRendererObj.FONT_HEIGHT + 6), 960, 4 + this.mc.fontRendererObj.FONT_HEIGHT + count * (this.mc.fontRendererObj.FONT_HEIGHT + 7), Colors.RGBX(2, 1.0f, 0.5f, count * 120));

						//mc.fontRendererObj.drawStringWithShadow(m.getDisplayName(), sr.getScaledWidth() - this.mc.fontRendererObj.getStringWidth(m.getDisplayName()) - 4, 4 + count * (this.mc.fontRendererObj.FONT_HEIGHT + 6), Colors.Astolfo(count * 100, 1.0f, 0.5f));
						GlStateManager.popMatrix();
					} else {
						// BIG RECT


						//Gui.drawRect(sr.getScaledWidth() - Asyncware.renderer.getStringWidth(m.getDisplayName()) - 10, y - 1, sr.getScaledWidth(),  0 +  Asyncware.renderer.getFontHeight() + y , new Color(20, 20, 20, 120).getRGB());
						GlStateManager.pushMatrix();
						Gui.drawRect(sr.getScaledWidth() - Asyncware.renderer.getStringWidth(m.getDisplayName()) - 8, y + 1, sr.getScaledWidth(), 1.1f + Asyncware.renderer.getFontHeight() + y, new Color(24, 24, 24, 196).getRGB());
						//Gui.drawRect(sr.getScaledWidth() - 2, 2, sr.getScaledWidth(), 2 + Asyncware.renderer.getFontHeight() + y, Colors.RGBX(2, 1.0f, 0.5f, count * 120));
					//	Gui.drawRect(958, count * (mc.fontRendererObj.FONT_HEIGHT + 6), 960, 4 + this.mc.fontRendererObj.FONT_HEIGHT + count * (this.mc.fontRendererObj.FONT_HEIGHT + 7), Colors.RGBX(2, 1.0f, 0.5f, count * 120));





						//Gui.drawRect(sr.getScaledWidth() - mc.fontRendererObj.getStringWidth(m.getDisplayName())  - 8, count * (this.mc.fontRendererObj.FONT_HEIGHT + 6), sr.getScaledWidth(), 6 + this.mc.fontRendererObj.FONT_HEIGHT + count * (this.mc.fontRendererObj.FONT_HEIGHT + 6), new Color(24, 24, 24,255).getRGB());

						//Gui.drawRect(958, count * (mc.fontRendererObj.FONT_HEIGHT + 6), 960, -6 + this.mc.fontRendererObj.FONT_HEIGHT + count * (this.mc.fontRendererObj.FONT_HEIGHT + 7), Colors.RGBX(2, 1.0f, 0.5f, count * 120));
						//Gui.drawRect(958, count * (Asyncware.renderer.getFontHeight() + 1), 960, -20 +Asyncware.renderer.getFontHeight() + count * (this.mc.fontRendererObj.FONT_HEIGHT + 6), Colors.RGBX(2, 1.0f, 0.5f, count * 120));

						//mc.fontRendererObj.drawStringWithShadow(m.getDisplayName(), sr.getScaledWidth() - this.mc.fontRendererObj.getStringWidth(m.getDisplayName()) - 4, 4 + count * (this.mc.fontRendererObj.FONT_HEIGHT + 6), Colors.Astolfo(count * 100, 1.0f, 0.5f));
						GlStateManager.popMatrix();
					}
				} else{
					Gui.drawRect(sr.getScaledWidth() -  Asyncware.renderer.getStringWidth(m.getDisplayName()) - 10, y - 1, sr.getScaledWidth(),  2 + Asyncware.renderer.getFontHeight() + y , new Color(20, 20, 20, 0).getRGB());

				}

				if(isFont){
					//Asyncware.renderer.drawString(m.getDisplayName(), sr.getScaledWidth() - Asyncware.renderer.getStringWidth(m.getDisplayName()) - 7, y + 1, Colors.fadeBetween(0x484AA8, 0x2E86EF, ((System.currentTimeMillis() + (count * 100)) % 1000 / (1000 / 2.0f))), true);
					//Asyncware.renderer.drawString(m.getDisplayName(), sr.getScaledWidth() - Asyncware.renderer.getStringWidth(m.getDisplayName()) - 7, y + 1, Colors.Astolfo(count * 100, 1.0f, 0.5f), true);
					Asyncware.renderer.drawString(m.getDisplayName(), sr.getScaledWidth() - Asyncware.renderer.getStringWidth(m.getDisplayName()) - 5, y + 1, Colors.fadeBetween(0x5699FF, 0x496EF3, ((System.currentTimeMillis() + (count * 100)) % 1000 / (1000 / 2.0f))), true);
					//Asyncware.renderer.drawString(m.getMode(), sr.getScaledWidth() - Asyncware.renderer.getStringWidth(m.getDisplayName() + m.getMode()) + 5, y + 1, new Color(24, 24, 24, 255).getRGB(), false);


				} else{
					font.drawStringWithShadow(m.getDisplayName(), sr.getScaledWidth() - font.getStringWidth(m.getDisplayName()) - 2, y, Colors.Astolfo(count * 100, 1.0f, 0.5f));
				}


					// Gui.drawRect(sr.getScaledWidth() - mc.fontRendererObj.getStringWidth(m.getDisplayName()) - 7, y - 1, sr.getScaledWidth(),  4 + mc.fontRendererObj.FONT_HEIGHT + y , new Color(0, 0, 0, 80).getRGB());
					//



					y += (int) offsety;
					count++;

			}


		GL11.glPopMatrix();

	}
 */
