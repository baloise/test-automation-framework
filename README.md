# test-automation-framework (TAF)

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/8c8551f4acb54108b7cc372c84cb174a)](https://app.codacy.com/app/baloise/test-automation-framework?utm_source=github.com&utm_medium=referral&utm_content=baloise/test-automation-framework&utm_campaign=Badge_Grade_Dashboard)

[![gitpod-IDE](https://img.shields.io/badge/open--IDE-as--gitpod-blue.svg?style=flat&label=openIDE)](https://gitpod.io#https://github.com/baloise/test-automation-framework)

![CI](https://github.com/baloise/test-automation-framework/workflows/CI/badge.svg)

# TAF is a test automation framework based on JUnit and Selenium 
```
it can be used for Java Swing and Web Applications (HTML)
```

## goals
- improve efficiency in test automation
- minimize manual intervention 
- maximize robustness
- reusability of code

## benefits
- Tests can be executed with multiple data sets
- Multiple scenarios can be tested quickly by varying the data, thereby reducing the number of scripts needed
- Hard-coding data can be avoided so any changes to the test scripts do not affect the data being used and vice versa
- You’ll save time by executing more tests faster

## key ideas
- separation of test data from code - use same code or script for different combinations of test input data
- store test data in external data sources such as Excel 

Filling and checking via generic methods fill() and check(). Whether and what to be filled is controlled via the data pool 
- value (fills an input field with the value of a specific excel-cell)
- blank (empties the input field)
- {skip} skips the field and leaves it in the state it is.

The check-method works similar to the fill-method: 
- value
- {skip}

Within Excel, the formatting and formula-functionality can be used for defining test input or expected result-data. Dynamic data: an input date "=today()+30"

Certain convenience methods can be used

Object recognition (html) possible via
- id
- labels
- xpath

## built upon
Selenium
JUnit before after

interface to Jira -> reporting of test case status

Jenkins-jobs
