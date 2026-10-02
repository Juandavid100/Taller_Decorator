@echo off
REM Compiles and runs Mopa-Mopa Studio without Maven (requires JDK 17+).
REM Usage: run.bat            -> web app at http://localhost:8080
REM        run.bat --console  -> console demo
cd /d "%~dp0"
if exist out rmdir /s /q out
mkdir out
dir /s /b src\main\java\*.java > sources.txt
javac -encoding UTF-8 -d out @sources.txt
if errorlevel 1 (
    del sources.txt
    pause
    exit /b 1
)
del sources.txt
xcopy /e /i /q /y src\main\resources out > nul
java -cp out com.mopamopa.studio.Main %*
