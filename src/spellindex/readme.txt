Tyler Jaszkowiak
27 Feb 2014

The files in this directory are associated with the spell index draft.
It is implemented here as a method of a SpellBook class.
When passed a character, it returns an array of spells that character may attempt, or a list containing only the empty spell if the character is not a wizard.

In order to test this, I had to create skeletons of the Character and Spell classes. These are in no way an attempt to actually represent eitehr of these objects. They simply contain the information needed by the method that returns a character's spell options. 

The files in this directory inlude:
Character.java    -    the skeleton character class
Spell.java        -    the skeleton spell class
SpellBook.java    -    the spellbook class that contains my method
SpellIndex.java   -    the class containing a main function that I used to test my method.