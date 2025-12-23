@echo off
REM Banking System Testing Project - Build and Test Script
REM Reference: term_project_fall_2026

echo ============================================
echo Banking System Testing Project
echo Build and Test Script
echo ============================================

REM Set Maven path (using IntelliJ bundled Maven)
set MAVEN_CMD="C:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2025.2.4\plugins\maven\lib\maven3\bin\mvn.cmd"

REM Check if Maven is available
if not exist %MAVEN_CMD% (
    echo Maven not found at expected location.
    echo Trying system Maven...
    set MAVEN_CMD=mvn
)

echo.
echo Step 1: Clean and compile...
call %MAVEN_CMD% clean compile -q
if errorlevel 1 (
    echo ERROR: Compilation failed!
    exit /b 1
)

echo.
echo Step 2: Running all tests...
call %MAVEN_CMD% test
if errorlevel 1 (
    echo WARNING: Some tests may have failed.
)

echo.
echo Step 3: Copying test reports to acceptance-delivery...
if not exist "acceptance-delivery" mkdir "acceptance-delivery"
if exist "target\surefire-reports" (
    xcopy /Y /E "target\surefire-reports\*" "acceptance-delivery\surefire-reports\" >nul 2>&1
    echo Surefire reports copied successfully.
) else (
    echo No surefire reports found.
)

echo.
echo Step 4: Generating test summary...
echo Test Run Summary > acceptance-delivery\test-summary.txt
echo ================ >> acceptance-delivery\test-summary.txt
echo Date: %date% %time% >> acceptance-delivery\test-summary.txt
echo. >> acceptance-delivery\test-summary.txt
dir /b target\surefire-reports\*.txt >> acceptance-delivery\test-summary.txt 2>nul

echo.
echo ============================================
echo Build and Test Complete!
echo ============================================
echo.
echo Results saved to: acceptance-delivery\
echo - surefire-reports\  (test XML reports)
echo - test-summary.txt   (summary)
echo.
echo Review docs\ folder for documentation.
echo.
