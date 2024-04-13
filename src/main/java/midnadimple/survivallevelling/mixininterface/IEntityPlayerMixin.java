package midnadimple.survivallevelling.mixininterface;

public interface IEntityPlayerMixin {
	void survival_levelling$gainExp(int numExp);
	int survival_levelling$getExp();
	int survival_levelling$getNextLevelGate();
	int survival_levelling$getLevel();
	int survival_levelling$getLevelMax();

	int survival_levelling$getPrevExp();
	int survival_levelling$getPrevLevel();
	void survival_levelling$setExp(int numExp);
	void survival_levelling$setLevel(int numLevel);
}
