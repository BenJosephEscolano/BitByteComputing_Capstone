@echo off
set JAVAC_PATH="C:\Users\joseph\.antigravity\extensions\redhat.java-1.54.0-win32-x64\jre\21.0.10-win32-x86_64\bin\javac.exe"
if not exist out\production\geodash mkdir out\production\geodash
dir /s /b src\*.java > sources.txt
%JAVAC_PATH% -d out\production\geodash -cp "lib\*" -sourcepath src @sources.txt
del sources.txt
echo Compilation complete!
pause
