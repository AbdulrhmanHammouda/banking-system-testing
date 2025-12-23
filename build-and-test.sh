#!/bin/bash
# Banking System Testing Project - Build and Test Script
# Reference: term_project_fall_2026

echo "============================================"
echo "Banking System Testing Project"
echo "Build and Test Script"
echo "============================================"

echo ""
echo "Step 1: Clean and compile..."
mvn clean compile -q
if [ $? -ne 0 ]; then
    echo "ERROR: Compilation failed!"
    exit 1
fi

echo ""
echo "Step 2: Running all tests..."
mvn test
if [ $? -ne 0 ]; then
    echo "WARNING: Some tests may have failed."
fi

echo ""
echo "Step 3: Copying test reports to acceptance-delivery..."
mkdir -p acceptance-delivery/surefire-reports
if [ -d "target/surefire-reports" ]; then
    cp -r target/surefire-reports/* acceptance-delivery/surefire-reports/
    echo "Surefire reports copied successfully."
else
    echo "No surefire reports found."
fi

echo ""
echo "Step 4: Generating test summary..."
cat > acceptance-delivery/test-summary.txt << EOF
Test Run Summary
================
Date: $(date)

EOF
ls -la target/surefire-reports/*.txt 2>/dev/null >> acceptance-delivery/test-summary.txt

echo ""
echo "============================================"
echo "Build and Test Complete!"
echo "============================================"
echo ""
echo "Results saved to: acceptance-delivery/"
echo "- surefire-reports/  (test XML reports)"
echo "- test-summary.txt   (summary)"
echo ""
echo "Review docs/ folder for documentation."
echo ""
