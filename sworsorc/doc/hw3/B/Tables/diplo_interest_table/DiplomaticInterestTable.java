public class DiplomaticInterestTable {
	static int get(Character emissary, Neutral neutral) {
		Race[] races = new Race[2];
		races[0] = neutral.race;
		races[1] = emissary.race;

		if(emissary.isSorceror)
			races[1] = Race.SORCEROR;

		int[] raceCategory = new int[2];
		for(int i = 0; i < 2; i++)
		switch(races[i]) {
			case HUMAN:    raceCategory[i] = 0; break;
			case ELF:      raceCategory[i] = 1; break;
			case DWARROW: 
			case GOBLIN:   raceCategory[i] = 2; break;
			case ANCIENT:  raceCategory[i] = 3; break;
			case CULTIST:
			case CRONK:
			case SORCEROR: raceCategory[i] = 4; break;
			case ORC:      raceCategory[i] = 5; break;
		}
		//                         H   E   G   A   C   O
		int[][] modifierTable = {{ 1,  1,  0, -1, -2, -2}, //human
		                         { 1,  3, -2, -1, -1, -1}, //elf
		                         { 0, -2,  2, -1, -1, -3}, //goblin, dwarrows
		                         {-2, -1,  0, -2,  2,  1}, //ancient
		                         {-2,  0, -1,  1,  1,  2}, //cultist, cronk, sor
		                         {-1, -1, -2,  1,  2,  3}};//orc
		return modifierTable[raceCategory[0]][raceCategory[1]];
	}

	public static void main(String[] args) {
		for(Race a : Race.values())
		for(Race b : Race.values()) {
			Character emissary = new Character();
			emissary.race = a;
			Neutral neutral = new Neutral();
			neutral.race = b;
			System.out.printf("%s has %d with %s\n", a.toString(),
			                                         get(emissary, neutral),
			                                         b.toString());
		}
		Character emissary = new Character();
		emissary.race = Race.HUMAN;
		emissary.isSorceror = true;
		Neutral neutral = new Neutral();
		neutral.race = Race.HUMAN;
		System.out.printf("Sorceror has %d with Human\n",
		                  get(emissary, neutral));
	}
}
		
