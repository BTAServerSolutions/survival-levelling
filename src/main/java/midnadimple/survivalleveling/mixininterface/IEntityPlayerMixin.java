package midnadimple.survivalleveling.mixininterface;

public interface IEntityPlayerMixin {
	void survival_leveling$gainExp(int numExp);
	int survival_leveling$getExp();
	int survival_leveling$getNextLevelGate();
}
