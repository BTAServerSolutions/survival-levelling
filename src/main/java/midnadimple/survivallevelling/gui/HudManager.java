package midnadimple.survivallevelling.gui;

import net.minecraft.client.gui.hud.ComponentAnchor;
import net.minecraft.client.gui.hud.Layout;
import net.minecraft.client.gui.hud.HudComponents;
import net.minecraft.client.gui.hud.SnapLayout;

public class HudManager {
	public void init() {
		ExpBarComponent expBar = new ExpBarComponent("exp", 182, 7,
			new SnapLayout(HudComponents.HOTBAR, ComponentAnchor.TOP_LEFT, ComponentAnchor.BOTTOM_LEFT));

		HudComponents.register(expBar);

		Layout healthLayout = HudComponents.HEALTH_BAR.getLayout();
		if (healthLayout instanceof SnapLayout)
			((SnapLayout) healthLayout).setParent(expBar);

		Layout armourLayout = HudComponents.ARMOR_BAR.getLayout();
		if (armourLayout instanceof SnapLayout)
			((SnapLayout) armourLayout).setParent(expBar);
	}
}
