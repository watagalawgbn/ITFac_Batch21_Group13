@echo off
REM Script to run All Tests

echo.
echo ============================================
echo   Running All Tests
echo ============================================
echo.

cd /d D:\ITFac_Batch21_Group13\ITQA

echo Building the project...
call mvn clean compile

echo.
echo Running All Tests...
call mvn test

echo.
echo ============================================
echo Test execution completed!
echo Reports available at:
echo - All Tests: target/cucumber-reports.html
echo - Admin Tests: target/cucumber-reports-admin.html
echo - User Tests: target/cucumber-reports-user.html
echo ============================================
pause
