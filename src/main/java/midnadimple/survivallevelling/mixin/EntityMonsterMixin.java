package midnadimple.survivallevelling.mixin;

import midnadimple.survivallevelling.SurvivalLevelling;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.monster.EntityMonster;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import midnadimple.survivallevelling.mixininterface.IEntityPlayerMixin;

@Mixin(value = EntityMonster.class, remap = false)
public class EntityMonsterMixin extends EntityLiving {
	public EntityMonsterMixin(World world) {
		super(world);
	}

	@Override
	public boolean hurt(Entity attacker, int damage, DamageType type) {
		boolean hurtSuccess = super.hurt(attacker, damage, type);

		if (hurtSuccess && this.getHealth() <= 0 &&
			world.getGameRule(SurvivalLevelling.ALLOW_survival_levelling) &&
			attacker instanceof EntityPlayer) {

			IEntityPlayerMixin player = (IEntityPlayerMixin) attacker;
			player.survival_levelling$gainExp(this.scoreValue / 100);
		}

		return hurtSuccess;
	}
}
