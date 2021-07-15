package lol.hero.clickgui.util;

import java.awt.Color;

import com.nquantum.Asyncware;

/**
 *  Made by HeroCode
 *  it's free to use
 *  but you have to credit me
 *
 *  @author HeroCode
 */
public class ColorUtil {
	
	public static Color getClickGUIColor(){
		//return new Color(30, 30, 30, 255);
		return new Color((int) Asyncware.instance.settingsManager.getSettingByName("GuiRed").getValDouble(), (int) Asyncware.instance.settingsManager.getSettingByName("GuiGreen").getValDouble(), (int) Asyncware.instance.settingsManager.getSettingByName("GuiBlue").getValDouble());
	}
}
