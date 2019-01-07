## README for your consideration

### Requirements
The table below lists some software requirements for testing the custom babysitter class.
Refer to the Instruction section for proper setup and steps to run the unit tests.
Using the package manager Chocolatey will simplify the process of installing the JDK and Gradle.

Software | Requirement | Version / Link
---|---|---
Chocolatey | Recommended | [Latest version](https://chocolatey.org/install)
Java Development Kit | Required | [JDK 8 update 191 or later](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
Gradle | Required | [Release 3.4.1 or later](https://gradle.org/releases/)
Windows OS | Recommended | If not Windows, refer to additional notes

### Instructions

1. Open a command prompt window (cmd.exe) with the "Run as administrator" option. 
2. Install Chocolatey by running the following command in the window.
`@"%SystemRoot%\System32\WindowsPowerShell\v1.0\powershell.exe" -NoProfile -InputFormat None -ExecutionPolicy Bypass -Command "iex ((New-Object System.Net.WebClient).DownloadString('https://chocolatey.org/install.ps1'))" && SET "PATH=%PATH%;%ALLUSERSPROFILE%\chocolatey\bin"`
3. Install the JDK by entering the following into the command prompt in sequence.\
`choco install jdk8`\
`refreshenv`
4. Install Gradle with the following command:\
`choco install gradle`
5. Download a copy of the BabysitterKata folder from this Github repository. Link
6. Navigate to the folder by entering `cd FILEPATH` in the command prompt where `FILEPATH` refers to the download directory (eg. `cd C:\Users\username\Downloads\BabysitterKata`) 
7. Enter `gradle test` in the command prompt and evaluate the output.
8. A web-friendly html output of the test results can be found in the BabysitterKata folder under `.\build\reports\tests\test\index.html`

### Additional Notes
* Refer to a helpful guide to setup Java unit testing with Gradle provided [here at Exercism.io](https://exercism.io/tracks/java/installation). This will provide instructions for non-Windows systems.
* To check the version of Gradle installed type `gradle -v` in the command prompt.
* To check the version of JDK either navigate to `C:\Program Files\Java\` or find it from a list of installed programs from the Control Panel.

***
### Original Readme below
***
# Babysitter Kata

## Background
This kata simulates a babysitter working and getting paid for one night.  The rules are pretty straight forward.

## Feature
*As a babysitter<br>
In order to get paid for 1 night of work<br>
I want to calculate my nightly charge<br>*

## Requirements
The babysitter:
- starts no earlier than 5:00PM
- leaves no later than 4:00AM
- only babysits for one family per night
- gets paid for full hours (no fractional hours)
- should be prevented from mistakes when entering times (e.g. end time before start time, or outside of allowable work hours)

The job:
- Pays different rates for each family (based on bedtimes, kids and pets, etc...)
- Family A pays $15 per hour before 11pm, and $20 per hour the rest of the night
- Family B pays $12 per hour before 10pm, $8 between 10 and 12, and $16 the rest of the night
- Family C pays $21 per hour before 9pm, then $15 the rest of the night
- The time ranges are the same as the babysitter (5pm through 4am)

Deliverable:
- Calculate total pay, based on babysitter start and end time, and a family.
