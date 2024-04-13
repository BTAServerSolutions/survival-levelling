package midnadimple.survivallevelling.gui;

import midnadimple.survivallevelling.SurvivalLevelling;
import midnadimple.survivallevelling.mixininterface.IEntityPlayerMixin;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.hud.Layout;
import net.minecraft.client.gui.hud.MovableHudComponent;
import net.minecraft.core.player.gamemode.Gamemode;
import org.lwjgl.opengl.GL11;

import java.awt.*;

// Stolen from jamdoggie's stamina bar
public class ExpBarComponent extends MovableHudComponent {
	public ExpBarComponent(String key, int xSize, int ySize, Layout layout) {
		super(key, xSize, ySize, layout);
	}

	@Override
	public boolean isVisible(Minecraft minecraft) {
		return true;
	}

	private void renderExpBar(Minecraft mc, Gui gui, int x, int y, float exp_percent, int level, int textColor)
	{
		GL11.glDisable(GL11.GL_BLEND);

		String texture = "/assets/survivallevelling/textures/gui/exp_bar.png";
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, mc.renderEngine.getTexture(texture));

		int width = 182;
		int height = 5;
		gui.drawTexturedModalRect(x, y, 0, 0, width, height);
		gui.drawTexturedModalRect(x, y, 0, height, (int) (exp_percent * width), height);

		mc.fontRenderer.drawCenteredString(Integer.toString(level), x + (width / 2), y - 2, textColor);
	}

	@Override
	public void render(Minecraft mc, GuiIngame gui, int xSizeScreen, int ySizeScreen, float partialTick)
	{
		int x = this.getLayout().getComponentX(mc, this, xSizeScreen);
		int y = this.getLayout().getComponentY(mc, this, ySizeScreen);

		if (mc.theWorld.getGameRule(SurvivalLevelling.ALLOW_survival_levelling) && mc.thePlayer.getGamemode() == Gamemode.survival) {
			IEntityPlayerMixin player = (IEntityPlayerMixin) mc.thePlayer;

			if (player.survival_levelling$getLevel() == player.survival_levelling$getLevelMax()) {
				setColor(new Color(255, 255, 85));
				renderExpBar(mc, gui, x, y, 1.0f, player.survival_levelling$getLevel(), 0x119B84);
			} else {
				setColor(new Color(17, 155, 132));
				renderExpBar(mc, gui, x, y, player.survival_levelling$getExp() / (float) player.survival_levelling$getNextLevelGate(),
					player.survival_levelling$getLevel(), 0xFFFF55);
			}
		}
	}

	@Override
	public void renderPreview(Minecraft mc, Gui gui, Layout layout, int xSizeScreen, int ySizeScreen)
	{
		int x = layout.getComponentX(mc, this, xSizeScreen);
		int y = layout.getComponentY(mc, this, ySizeScreen);

		setColor(new Color(17, 155, 132));
		renderExpBar(mc, gui, x, y, 1.0f, 1, 0xFFFF55);
	}

	private void setColor(Color color)
	{
		GL11.glColor4f(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getAlpha() / 255f);
	}
}
