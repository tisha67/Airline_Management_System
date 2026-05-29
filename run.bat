@echo off
title Airline Management System - Week 2
cd /d "%~dp0"

if not exist out mkdir out

javac -d out src\airlinemanagementsystem\*.java
if errorlevel 1 (
    echo.
    echo Compilation failed! Please check your Java installation or code.
    pause
    exit /b
)

java -cp out airlinemanagementsystem.AirlineManagementSystem
pause
