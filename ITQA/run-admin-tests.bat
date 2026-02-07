@echo off
REM Script to run Admin Tests

echo.
echo ============================================
echo   Running Admin Tests
echo ============================================
echo.

cd /d D:\ITFac_Batch21_Group13\ITQA

echo Building the project...
call mvn clean compile

echo.
echo Running Admin Tests...
call mvn test -Dtest=AdminTestRunner

echo.
echo ============================================
echo Test execution completed!
echo Report available at: target/cucumber-reports-admin.html
echo ============================================
pause
