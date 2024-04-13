package midnadimple.survivallevelling.mixin;

import com.mojang.nbt.CompoundTag;
import midnadimple.survivallevelling.SurvivalLevelling;
import midnadimple.survivallevelling.mixininterface.IEntityPlayerMPMixin;
import midnadimple.survivallevelling.mixininterface.IEntityPlayerMixin;
import midnadimple.survivallevelling.network.PacketAddExp;
import midnadimple.survivallevelling.network.PacketSendExp;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.player.gamemode.Gamemode;
import net.minecraft.core.world.World;
import net.minecraft.server.entity.player.EntityPlayerMP;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static java.lang.Math.max;
import static java.lang.Math.min;

@Mixin(value = EntityPlayer.class, remap = false)
public abstract class EntityPlayerMixin extends EntityLiving implements IEntityPlayerMixin {
	@Shadow
	public Gamemode gamemode;

	@Unique
	private EntityPlayer thisAs = (EntityPlayer)(Object)this;
	@Unique
	public int level = 1;
	@Unique
	public int exp = 0;
	@Unique
	private int prevExp = exp;
	@Unique
	private int prevLevel = level;
	@Unique
	private static final int[] levelGates = {6, 9, 12, 15, 18, 21, 27, 33, 39, 45, 51, 57, 63, 69, 75, 81};
	@Unique
	private static final int LEVEL_MAX = levelGates.length;
	@Unique
	private static final int baseHealth = 8;
	@Unique
	private static final int incrementHealth = 2;

	public EntityPlayerMixin(World world) {
		super(world);
	}

	@Override
	public int survival_levelling$getExp() {
		return exp;
	}

	@Override
	public int survival_levelling$getNextLevelGate() {
		int index = Math.max(level - 1, 0);
		return levelGates[index];
	}

	@Override
	public int survival_levelling$getLevel() {
		return level;
	}

	@Override
	public int survival_levelling$getPrevLevel() {
		return prevLevel;
	}

	@Override
	public int survival_levelling$getLevelMax() {
		return LEVEL_MAX;
	}

	@Override
	public int survival_levelling$getPrevExp() {
		return prevExp;
	}

	@Override
	public void survival_levelling$setExp(int numExp) {
		prevExp = exp;
		exp = max(numExp, 0);
		while (exp >= survival_levelling$getNextLevelGate()) {
			exp -= survival_levelling$getNextLevelGate();
			survival_levelling$setLevel(level + 1);
			System.out.println("Player leveled up, now level " + level + ", max health " + this.getMaxHealth() + ", health " + this.getHealth());
		}
	}

	@Override
	public void survival_levelling$setLevel(int numLevel) {
		prevLevel = level;
		level = numLevel;

		int newHealth = min(this.getHealth() + (level-prevLevel) * incrementHealth, 40);
		newHealth = max(newHealth, baseHealth);
		this.setHealthRaw(newHealth);

		if (!world.isClientSide && thisAs instanceof EntityPlayerMP) {
			PacketSendExp packet = new PacketSendExp(exp, level);
			((IEntityPlayerMPMixin)this).survival_leveling$serverPacketExp(packet);
		}
	}

	@Inject(method = "getMaxHealth", at = @At("HEAD"), cancellable = true)
	private void getMaxHealth(CallbackInfoReturnable<Integer> cir) {
		if (world.getGameRule(SurvivalLevelling.ALLOW_survival_levelling) && gamemode == Gamemode.survival) {
			cir.setReturnValue(min(baseHealth + (level-1) * incrementHealth, 40));
			cir.cancel();
		}
	}

	@Override
	public void survival_levelling$gainExp(int numExp) {
		System.out.println("Player gained " + numExp + " exp");
		prevExp = exp;
		survival_levelling$setExp(exp + numExp);

		if (!world.isClientSide && thisAs instanceof EntityPlayerMP) {
			PacketAddExp packet = new PacketAddExp(exp);
			((IEntityPlayerMPMixin)this).survival_leveling$serverPacketExp(packet);
		}
	}

	@Inject(method = "onDeath", at = @At("TAIL"))
	private void onDeath(Entity entity, CallbackInfo ci) {
		level = 1;
		exp = 0;
		System.out.println("Player died, now level " + level + ", max health " + this.getMaxHealth() + ", health " + this.getHealth());
	}

	@Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
	private void addAdditionalSaveData(CompoundTag tag, CallbackInfo ci)
	{
		tag.putInt("Level", level);
		tag.putInt("Exp", exp);
	}

	@Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
	private void readAdditionalSaveData(CompoundTag tag, CallbackInfo ci)
	{
		level = tag.getIntegerOrDefault("Level", 1);
		exp = tag.getIntegerOrDefault("Exp", 0);
	}
}
