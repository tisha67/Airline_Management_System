@echo off
title SkyWave Airlines Week 3

echo Compiling SkyWave Airlines Week 3...
if not exist out mkdir out
javac -d out src\airlinemanagementsystem\*.java

if errorlevel 1 (
    echo.
    echo Compilation failed. Please check Java installation or source code.
    pause
    exit /b
)

echo Running project...
java -cp out airlinemanagementsystem.AirlineManagementSystem
pause
