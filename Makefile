all:
	find ./src -name "*.java" > sources.txt
	javac -nowarn -Xlint:none -d build -cp "lib/*" @sources.txt
	java -cp build:lib/* livre.Main

windows:
	javac -Xlint:none -d build -cp lib/* @sources.txt
	java -cp build;lib/* livre.Main

test-linux:
	find ./src -name "*.java" > sources.txt
	javac -nowarn -Xlint:none -d build -cp "lib/*" @sources.txt
	java -cp build:lib/* livre.unittest.TestLauncher

test-windows:
	javac -Xlint:none -d build -cp lib/* @sources.txt
	java -cp build;lib/* livre.unittest.TestLauncher