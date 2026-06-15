@echo off
title SkyWave Airlines Management System - Week 4
echo Compiling SkyWave Airlines Management System Week 4...

if not exist out mkdir out
javac -d out src\airlinemanagementsystem\*.java

if errorlevel 1 (
    echo.
    echo Compilation failed. Please check Java installation or code errors.
    pause
    exit /b
)

echo.
echo Starting application...
java -cp out airlinemanagementsystem.AirlineManagementSystem
pause
