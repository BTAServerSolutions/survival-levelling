package midnadimple.survivalleveling.mixin;

import midnadimple.survivalleveling.SurvivalLeveling;
import midnadimple.survivalleveling.mixininterface.IEntityPlayerMixin;
import net.minecraft.client.Minecraft;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static java.lang.Math.min;

@Mixin(value = EntityPlayer.class, remap = false)
public abstract class EntityPlayerMixin extends EntityLiving implements IEntityPlayerMixin {
	@Unique
	public int level = 1;
	@Unique
	private int exp = 0;
	@Unique
	private static int[] levelGates = {6, 9, 12, 15, 18, 21, 27, 33, 39, 45, 51, 57, 63, 69, 75, 81};
	@Unique
	private static final int baseHealth = 8;
	@Unique
	private static final int incrementHealth = 2;

	@Unique
	private static Minecraft mc = Minecraft.getMinecraft(Minecraft.class);

	public EntityPlayerMixin(World world) {
		super(world);
	}

	@Override
	public int survival_leveling$getExp() {
		return exp;
	}

	@Override
	public int survival_leveling$getNextLevelGate() {
		return levelGates[level - 1];
	}

	@Inject(method = "getMaxHealth", at = @At("HEAD"), cancellable = true)
	private void getMaxHealth(CallbackInfoReturnable<Integer> cir) {
		if (mc.theWorld.getGameRule(SurvivalLeveling.ALLOW_SURVIVAL_LEVELING)) {
			cir.setReturnValue(min(baseHealth + (level-1) * incrementHealth, 40));
			cir.cancel();
		}
	}

	@Override
	public void survival_leveling$gainExp(int numExp) {
		exp += numExp;
		System.out.println("Player gained " + numExp + " exp, now has " + exp + " exp");
		if (exp >= survival_leveling$getNextLevelGate()) {
			exp = 0;
			level += 1;
			this.setHealthRaw(this.getHealth() + 1);
			System.out.println("Player leveled up, now level " + level + ", max health " + this.getMaxHealth() + ", health " + this.getHealth());
		}
	}

	@Inject(method = "onDeath", at = @At("TAIL"))
	private void onDeath(Entity entity, CallbackInfo ci) {
		level = 1;
		System.out.println("Player died, now level " + level + ", max health " + this.getMaxHealth() + ", health " + this.getHealth());
	}
}
