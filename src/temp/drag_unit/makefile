all: MouseMoveUnit.class Unit.class
MouseMoveUnit.class: MouseMoveUnit.java
	javac -J-Xmx100m MouseMoveUnit.java
Unit.class: Unit.java
	javac -J-Xmx100m Unit.java
run: MouseMoveUnit.class
	java -Xmx100m MouseMoveUnit
