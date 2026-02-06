@echo off
REM Script to run Normal User Tests

echo.
echo ============================================
echo   Running Normal User Tests
echo ============================================
echo.

cd /d D:\ITFac_Batch21_Group13\ITQA

echo Building the project...
call mvn clean compile

echo.
echo Running Normal User Tests...
call mvn test -Dtest=UserTestRunner

echo.
echo ============================================
echo Test execution completed!
echo Report available at: target/cucumber-reports-user.html
echo ============================================
pause
