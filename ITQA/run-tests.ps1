# Test Automation Execution Script
# TC-UI-LOGIN-01 Test Runner

param(
    [string]$browser = "chrome",
    [string]$tag = "@UI",
    [string]$baseUrl = "http://localhost:8081"
)

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  Login Page UI Test Automation" -ForegroundColor Cyan
Write-Host "  TC-UI-LOGIN-01" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

Write-Host "Configuration:" -ForegroundColor Yellow
Write-Host "  Browser: $browser" -ForegroundColor White
Write-Host "  Tag: $tag" -ForegroundColor White
Write-Host "  Base URL: $baseUrl" -ForegroundColor White
Write-Host ""

# Check if Maven is installed
Write-Host "Checking Maven installation..." -ForegroundColor Yellow
$mavenVersion = mvn -version 2>&1
if ($LASTEXITCODE -ne 0) {
    Write-Host "ERROR: Maven is not installed or not in PATH" -ForegroundColor Red
    Write-Host "Please install Maven and add it to your PATH" -ForegroundColor Red
    exit 1
}
Write-Host "Maven found!" -ForegroundColor Green
Write-Host ""

# Navigate to project directory
$scriptPath = Split-Path -Parent $MyInvocation.MyCommand.Path
Set-Location $scriptPath

# Clean previous test results
Write-Host "Cleaning previous test results..." -ForegroundColor Yellow
mvn clean | Out-Null
Write-Host "Clean completed!" -ForegroundColor Green
Write-Host ""

# Run tests
Write-Host "Starting test execution..." -ForegroundColor Yellow
Write-Host ""

$testCommand = "mvn test -Dbrowser=$browser -Dcucumber.filter.tags=`"$tag`" -Dbase.url=$baseUrl"
Write-Host "Executing: $testCommand" -ForegroundColor Cyan
Write-Host ""

Invoke-Expression $testCommand

Write-Host ""
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  Test Execution Completed" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# Check if report was generated
$reportPath = Join-Path $scriptPath "target\cucumber-reports.html"
if (Test-Path $reportPath) {
    Write-Host "Test Report Generated: $reportPath" -ForegroundColor Green
    Write-Host ""
    $openReport = Read-Host "Would you like to open the test report? (Y/N)"
    if ($openReport -eq "Y" -or $openReport -eq "y") {
        Start-Process $reportPath
    }
} else {
    Write-Host "No report generated. Check console output for errors." -ForegroundColor Yellow
}

Write-Host ""
Write-Host "Thank you for using the test automation framework!" -ForegroundColor Cyan
