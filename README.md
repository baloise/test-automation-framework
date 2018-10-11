# test-automation-framework (TAF)

## travis [![Travis Status](https://travis-ci.org/baloise/test-automation-framework.svg?branch=master)](https://travis-ci.org/baloise/test-automation-framework) 

## circle ci [![CircleCI Status](https://circleci.com/gh/baloise/test-automation-framework.svg?style=svg)](https://circleci.com/gh/baloise/test-automation-framework)

# TAF is a test automation framework based on JUnit and Selenium. 
```
for...
 Java
    Swing
    HTML
```

## goals
- improve efficiency in test automation
- minimize manual intervention 
- maximize robustness
- reusability of code

## benefits
- Tests can be executed with multiple data sets.
- Multiple scenarios can be tested quickly by varying the data, thereby reducing the number of scripts needed.
- Hard-coding data can be avoided so any changes to the test scripts do not affect the data being used and vice versa.
- You’ll save time by executing more tests faster.

## key ideas
separation of test data from the code - use same code or script for different combinations of test input data
Tests can be executed with multiple data sets. 
store test data in external data sources such as Excel etc. (do we cover additional sources yet?)

Filling and checking via the fill() and check() method. Whether and what to be filled is controlled via the data pool 
- value (fills in the value)
- blank (empties the field)
- {skip} skips the field and leaves it in the state it is.

Within Excel, you can use formulas for test data definition. Dynamically: an input date =today()+30

for the checking as well: 
- value
- {skip}

process itself also controlled via an overall datapool
telling which form to be processed with which data

convenience methods
for.... 
filling
checking

Object recognition (html) possible via
id
labels
xpath

## build upon
Selenium
JUnit before after

interface to Jira -> reporting of test case status

Jenkins
