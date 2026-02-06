# Script to run Normal User Tests
# This script runs only the user_plants.feature tests

Write-Host "Building the project..." -ForegroundColor Green
mvn clean compile

Write-Host "`nRunning Normal User Tests..." -ForegroundColor Green
mvn test -Dtest=UserTestRunner

Write-Host "`nTest execution completed!" -ForegroundColor Green
Write-Host "Check the report at: target/cucumber-reports-user.html" -ForegroundColor Yellow
