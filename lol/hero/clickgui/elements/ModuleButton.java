package lol.hero.clickgui.elements;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;

import com.nquantum.Asyncware;
import com.nquantum.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

import nig.hero.settings.Setting;
import org.lwjgl.input.Keyboard;

import lol.hero.clickgui.Panel;
import lol.hero.clickgui.elements.menu.ElementCheckBox;
import lol.hero.clickgui.elements.menu.ElementComboBox;
import lol.hero.clickgui.elements.menu.ElementSlider;
import lol.hero.clickgui.util.ColorUtil;

/**
 *  Made by HeroCode
 *  it's free to use
 *  but you have to credit me
 *
 *  @author HeroCode
 */
public class ModuleButton {
	public Module mod;
	public ArrayList<Element> menuelements;
	public Panel parent;
	public double x;
	public double y;
	public double width;
	public double height;
	public boolean extended = false;
	public boolean listening = false;

	/*
	 * Konstrukor
	 */
	public ModuleButton(Module imod, Panel pl) {
		mod = imod;
		height = Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT + 2;
		parent = pl;
		menuelements = new ArrayList<>();
		/*
		 * Settings wurden zuvor in eine ArrayList eingetragen
		 * dieses SettingSystem hat 3 Konstruktoren je nach
		 *  verwendetem Konstruktor ndert sich die Value
		 *  bei .isCheck() usw. so kann man ganz einfach ohne
		 *  irgendeinen Aufwand bestimmen welches Element
		 *  fr ein Setting bentigt wird :>
		 */
		if (Asyncware.instance.settingsManager.getSettingsByMod(imod) != null)
			for (Setting s : Asyncware.instance.settingsManager.getSettingsByMod(imod)) {
				if (s.isCheck()) {
					menuelements.add(new ElementCheckBox(this, s));
				} else if (s.isSlider()) {
					menuelements.add(new ElementSlider(this, s));
				} else if (s.isCombo()) {
					menuelements.add(new ElementComboBox(this, s));
				}
			}

	}

	/*
	 * Rendern des Elements 
	 */
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		Color temp = ColorUtil.getClickGUIColor();
		int color = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), 150).getRGB();
		
		/*
		 * Ist das Module an, wenn ja dann soll
		 *  #ein neues Rechteck in Gre des Buttons den Knopf als Toggled kennzeichnen
		 *  #sich der Text anders frben
		 */
		int textcolor = 0xffafafaf;
		if (mod.isToggled()) {
			Gui.drawRect(x - 2, y, x + width + 2, y + height + 1, color);
			textcolor = 0xffefefef;
		}
		
		/*
		 * Ist die Maus ber dem Element, wenn ja dann soll der Button sich anders frben
		 */
		if (isHovered(mouseX, mouseY)) {
			Gui.drawRect(x - 2, y, x + width + 2, y + height + 1, 0x55111111);
		}
		
		/*
		 * Den Namen des Modules in die Mitte (x und y) rendern
		 */

		Asyncware.renderer.drawTotalCenteredStringWithShadow(mod.getName(),  x + width / 2, y + 1 + height / 2, textcolor);
		//FontUtil.drawTotalCenteredStringWithShadow(mod.getName(), x + width / 2, y + 1 + height / 2, textcolor);
	}

	/*
	 * 'true' oder 'false' bedeutet hat der Nutzer damit interagiert und
	 * sollen alle anderen Versuche der Interaktion abgebrochen werden?
	 */
	public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
		if (!isHovered(mouseX, mouseY))
			return false;

		/*
		 * Rechtsklick, wenn ja dann Module togglen, 
		 */
		if (mouseButton == 0) {
			mod.toggle();
			
			if(Asyncware.instance.settingsManager.getSettingByName("Sound").getValBoolean())
			Minecraft.getMinecraft().thePlayer.playSound("random.click", 0.5f, 0.5f);
		} else if (mouseButton == 1) {
			/*
			 * Wenn ein Settingsmenu existiert dann sollen alle Settingsmenus 
			 * geschlossen werden und dieses geffnet/geschlossen werden
			 */
			if (menuelements != null && menuelements.size() > 0) {
				boolean b = !this.extended;
				Asyncware.instance.clickGui1.closeAllSettings();
				this.extended = b;
				
				if(Asyncware.instance.settingsManager.getSettingByName("Sound").getValBoolean())
				if(extended)Minecraft.getMinecraft().thePlayer.playSound("tile.piston.out", 1f, 1f);else Minecraft.getMinecraft().thePlayer.playSound("tile.piston.in", 1f, 1f);
			}
		} else if (mouseButton == 2) {
			/*
			 * MidClick => Set keybind (wait for next key)
			 */
			listening = true;
		}
		return true;
	}

	public boolean keyTyped(char typedChar, int keyCode) throws IOException {
		/*
		 * Wenn listening, dann soll der nchster Key (abgesehen 'ESCAPE') als Keybind fr mod
		 * danach soll nicht mehr gewartet werden!
		 */
		if (listening) {
			if (keyCode != Keyboard.KEY_ESCAPE) {
				//Client.sendChatMessage("Bound '" + mod.getName() + "'" + " to '" + Keyboard.getKeyName(keyCode) + "'");
				mod.setKey(keyCode);
			} else {
				//Client.sendChatMessage("Unbound '" + mod.getName() + "'");
				mod.setKey(Keyboard.KEY_NONE);
			}
			listening = false;
			return true;
		}
		return false;
	}

	public boolean isHovered(int mouseX, int mouseY) {
		return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
	}

}
