public enum Race {
	HUMAN, ELF, DWARROW, GOBLIN, ANCIENT, CULTIST, CRONK, SORCEROR, ORC;

	/* simple toString method, just for example code */
	public String toString() {
		switch(this) {
			case HUMAN:    return "Human";
			case ELF:      return "Elf";
			case DWARROW:  return "Dwarrow";
			case GOBLIN:   return "Goblin";
			case ANCIENT:  return "Ancient";
			case CULTIST:  return "Cultist";
			case CRONK:    return "Cronk";
			case SORCEROR: return "Sorceror";
			case ORC:      return "Orc";
		}
		assert false;
		return "ERROR";
	}
}
