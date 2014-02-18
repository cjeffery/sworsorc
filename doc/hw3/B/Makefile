.PHONY: clean pdf forcepdf png
DIAGRAMS = pngs/Map.png pngs/PenguinMovement.png pngs/spell.png \
           pngs/TerrainClasses.png pngs/UnitClasses.png
pdf: classdiagrams_b.tex classdiagrams_b.pdf ${DIAGRAMS} combatClassesDict.tex
png: pngs/*.png

pngs/%.png: ClassDiagrams/%.pu
	java -jar plantuml.jar -o ../pngs/ $<

classdiagrams_b.pdf: classdiagrams_b.tex combatClassesDict.tex ${DIAGRAMS}
	pdflatex classdiagrams_b.tex
forcepdf: classdiagrams_b.tex combatClassesDict.tex ${DIAGRAMS}
	pdflatex classdiagrams_b.tex	
clean:
	rm -f classdiagrams_b.pdf
	rm -f pngs/*
	rm -f classdiagrams_b.aux
	rm -f classdiagrams_b.log

