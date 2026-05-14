@echo off
set JAVA_PATH="C:\Users\joseph\.antigravity\extensions\redhat.java-1.54.0-win32-x64\jre\21.0.10-win32-x86_64\bin\java.exe"
%JAVA_PATH% -cp "out/production/geodash;lib\*" Main
pause
