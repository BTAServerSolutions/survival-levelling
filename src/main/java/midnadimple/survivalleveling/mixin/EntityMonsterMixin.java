package midnadimple.survivalleveling.mixin;

import midnadimple.survivalleveling.SurvivalLeveling;
import net.minecraft.client.Minecraft;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.monster.EntityMonster;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import midnadimple.survivalleveling.mixininterface.IEntityPlayerMixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(value = EntityMonster.class, remap = false)
public class EntityMonsterMixin extends EntityLiving {
	public EntityMonsterMixin(World world) {
		super(world);
	}

	@Unique
	@Final
	private static Minecraft mc = Minecraft.getMinecraft(Minecraft.class);

	@Override
	public boolean hurt(Entity attacker, int damage, DamageType type) {
		boolean hurtSuccess = super.hurt(attacker, damage, type);

		if (hurtSuccess && this.getHealth() <= 0 &&
			mc.theWorld.getGameRule(SurvivalLeveling.ALLOW_SURVIVAL_LEVELING) &&
			attacker instanceof EntityPlayer) {

			IEntityPlayerMixin player = (IEntityPlayerMixin) attacker;

			player.survival_leveling$gainExp(this.scoreValue / 100);
		}

		return hurtSuccess;
	}
}
