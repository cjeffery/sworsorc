cd ..
call ant -f build.xml -q -Dconfig=Server jar
copy /Y dist\sworsorc.jar temp\server.jar
call ant -f build.xml -q -Dconfig=HUD jar
copy /Y dist\sworsorc.jar temp\client.jar
start java -jar temp\client.jar left
start java -jar temp\client.jar right
start java -jar temp\server.jar
cd utilities
