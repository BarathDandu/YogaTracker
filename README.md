# YogaTracker
### University of South Florida

### COP4656.001S21.

### Software Development for Mobile Devices

### Sitting Yoga Tracker Final Report

### Date: 4/6/2021

### Members

### Dustin Cardoso (U18146522) dustin16@usf.edu

### Kailash Permaul (U25389363) permaul1@usf.edu

### Barath Narayan Reddy Dandu (U07625313) bdandu@usf.edu


## Main Index


- Introduction
- Description
- Motivation
- Report Structure
- Vision and Scope
   - Description of the app
   - Vision Statement
   - Major features
   - Feature comparison
   - Scope of initial release
   - Scope of Potential Future releases
   - Operating Environment
- Horizontal Prototype - Requirement Analysis
   - Functional Requirement
   - First-Release Scope
   - Development Challenges
   - First-Release Features
   - Future Features
- Horizontal Prototype - System Design
   - Activities
   - User Interfaces
   - External Services
- Program Design
- Feature
- Feature
   - Feature
- Testing
- Unit Testing
- Integration Testing
- System Testing
- Conclusions
- References
- Annex
- Feature Comparison Tables
- App Activities
   - Main Screen Figures
   - Exercise Selection
   - Stat Selection
   - Help
   - Activity Play
   - Statistics View
   - Goal Setting
   - Login Screen
   - Application Flowchart
   - Activity Play Flowchart


## Introduction

## Description

The purpose of this application is to help those who are unable to exercise regularly, due
to things such as old age, injury, or disability. It has the ability to sense the movements of
the phone to guide users to stability and correctness as well as set and track goals set by
the user.

## Motivation

While there are many exercise applications that exist, few or none are directed towards
those who may have little ability to exercise in the first place. This app is specifically
designed to allow those who are sitting to stretch and use their muscles, which is
beneficial to the health and longevity of those muscles by reducing possible atrophy.

## Report Structure

The following document contains a number of sections, including:
● Vision and Scope
○ This section provides a vision of what the app is meant to be as well as why
the app is needed in comparison to what is already available by providing
features of both initial and future releases
● Horizontal Prototype
○ The horizontal prototype provides a more refined understanding of what the
app will be and how it will look, including the activities it will use, how it will
store data, and its functional requirements
● Program Design and Testing
○ This involves a more in-depth analysis of how-to app runs by going over the
major algorithms, data structures, and libraries of the application and its
features


## Vision and Scope

### Description of the app

This app would be a health-oriented app that focuses on movements and
exercises one can do while sitting down. Elderly people can have a difficult time

getting exercise, as it is harder for them to hold their balance or complete swift or
dexterous motions. The app would provide different motions for the users to select
from, each with a graphical representation. Once selected, a timer will start and the
user will do their best to complete the motion or hold a pose, while the phone
sensor would use gyroscope and accelerometer readings to determine if the
motion/pose is done correctly. If not, the user is notified that they need to fix their

position or movements.

These exercise attempts would then be stored for the user to see how they did and
what could be improved (too slow/fast, too much movement, etc.). The app can
show progress over time based on changes in sensor readings and frequency of
exercise. The use of these sensors to track progress would make this app more
complex and interesting than other applications on the market already.

### Vision Statement

This application is meant to help the elderly or anyone who may have issues with
regular exercise methods by giving them new ways to do so. An athlete with a
broken ankle or an older man with a bad hip should still have the ability to be
physically active, as it is good for one’s health and wellness to do so. Over time the
application could extend beyond sitting to include more extensive exercises that

are still modest, in order to keep with the demographic of people who find it more
difficult to complete regular exercises that may require too much movement or
stress.

### Major features

```
● Multiple exercises to choose from depending on difficulty (using gyroscope
or accelerometer sensor)
● Timer for each exercise to provide challenge
● Progress tracking and data storage through Google Fit API or Firebase
```

### Feature comparison

```
Feature Sitting Exercises “Sitting Cardio”
```
```
Multiple exercises Yes Yes
```
```
Exercise Timer Yes Yes
```
```
Sensor readings Yes No
```
```
Progress tracking Yes Calendar Only
Table 1: App Feature Comparison
```
### Scope of initial release

The initial release of the app would include a few exercises to be completed as
well as full usage of the gyroscope/accelerometer readings. The readings would
then provide feedback and be stored through the Google Fit API or something
similar. Each exercise would be on a timer and provide some kind of physical

activity that can be tracked, and users would be able to look back at their usage
and see what can be improved.

### Scope of Potential Future releases

Future application releases would include a more robust library of exercises and

could branch out into easy exercises that don’t need to be done sitting down in
order to accommodate those with arm injuries or the like. Releases could also
include communication functions, such as the ability to share progress through
social media or the app itself.

### Operating Environment

The current operating environment is Android, with the oldest device that can run
the app being Jellybean 4.1. In the future, development of an iOS version could
also be made.

## Program Design

### Feature 1: Login/User Authentication

Overview:
The application opens to a login screen that is authenticated by Google Firebase,
allowing the user to register via email or google account as well as utilize a “Forgot
Password” feature. Registration requires an email address and password, and upon
receiving this information Firebase creates a unique string key (UID) that attaches to the
user. The “Forgot Password” feature lets the user enter their email address to be sent a
link to reset their password.

Data Structures:
The login feature utilizes a “User” class that creates a user using a constructor
containing strings for the email and password. Registration requires the use of multiple
firebase objects in order for the specific user to attach itself to the database and be tied to
a UID. Logging in requires an “idToken” string to be successfully received from Firebase
and for it to also match the UID tied to the user before login can be successful.

### Feature 2: ActivityPlay/Exercise Activity

Overview:
ActivityPlay is one of the main aspects of the application as the user completes
exercises here. The user can press play to start an exercise or pause to exit and return to
the exercise selection screen. Once play is pressed, the activity checks the id of the
exercise image, so it knows which one is being played, and begins listening to the
phone’s sensor readings of the accelerometer and magnetometer. The rest of the
exercise is mostly done inside a function that is called every time the sensor readings
change, which is usually 2-3 times a second. If exercise one or two is being played, the
program will not immediately begin orientation boundaries, but will instead wait for the
phone to hit a certain acceleration threshold to indicate arm movement. This is done by
using a high-pass filter to exclude the force of gravity from the acceleration’s components
to give a linear acceleration in each direction, which is then used as a threshold.
The orientation boundaries vary by exercise with different pitch and roll values to
determine the orientation the phone needs to stay in for the duration of the exercise. If the
phone leaves that orientation, the user is notified with a red screen until the orientation is
fixed or the exercise ends. Good orientation readings add to a counter which is later
divided by the total number of sensor readings to provide a percentage of phone
stability/accuracy during the exercise, which can later be viewed in the statistics viewing
activity. The end of the exercise is set by a timer which counts to either a default value of


10 seconds or the goal time set by the user in the goals activity. Once the timer ends, the
stability of the phone, the amount of time the exercise was done, and the date it was done
is all stored in an external database.

Data Structures:
The local data of the activity are mostly global float and integer variables that
handle counting and timing of the exercises. This is supplemented by the sensor objects
for sensor management, acceleration, and magnetometer, as well as arrays to hold the
sensor readings of the objects in each direction. External data is handled using several
array lists and hashmaps to push and pull data related to the goals and statistics of each
exercise, as well as a database reference that acts as a query.

### Feature 3: Goal setting and statistics view

### Goal Setting:

Overview:
This activity allows users to create goals for themselves. The goal set is a time in
seconds that will be used for exercise length, as well as to display data in stat view.

To display the calendar for user selection the android java class CalendarView is utilized.
This Java class creates a widget that the user can use within the UI to select a specific
date. The specific time for the exercise is chosen by pressing the plus or minus button to
add and subtract values. This is an onclick that calls a function to convert the text view
from a string, add to it and then re-update the text view. Once the user is ready to set the
goal they can click add, at which time the activity will upload the data to the database and
exit the setGoals activity.

Data Structures:
This activity uses a limited number of data structures most of which are defined
globally. Int and strings are used to update information both on the display as well as the
database, and objects that represent UI elements are necessary to get information from
the user. A CalendarView object is used specifically in order to get the date set by the
user. A hashmap is used to store data into the database after user input.


### Statistics View:

Overview:
Statistics View is a breakdown of previous exercises the user has done. Currently
the activity shows the accuracy of sensor readings, a breakdown of exercise completion
in comparison to other exercises, as well as sensor readings over time.

The first section of the stat view shows accuracy and the current goal. The accuracy of
sensor readings represents how well a user did in a given exercise, as the sensor reading
accuracy is the percentage of overall exercise time the user managed to stay in the
correct pose. This information is given for both the most recent exercise as well as the
average of accuracy over time. The displayed goal is the current goal, meaning, it is the
goal set at a date closest to the current date. If a goal was set for the day the user is
currently accessing the stat view it will be displayed in this section.

The middle section displays the percentage the selected exercise has been done in
relation to total exercise completions, as well as the total times the exercise has been
completed. The pie chart on the right shows a breakdown of all exercises in relation to
each other in terms of competition. The purpose of this statistical breakdown is to allow
the user to make sure they are doing each exercise an equivalent number of times.

The last section in this activity is a line graph that displays the sensor accuracy over time.
The purpose of this is to allow users to see their progress overtime.

Data Structures:
The data is retrieved from firebase using arraylist, objects are utilized in order to
display the data with the hellocharts api, specifically for LineChartView and PieChartView.
Arraylist and integer values are additionally needed in order to interact with both textviews
and the hellochart api.


### Main Application Flowchart:

```
Figure 9: Main application flowchart
```

### Activity Play Flowchart:

Figure 10: Activity Play Flowchart


## Testing

## Unit Testing

This testing is done for testing the individual modules or units of a software
product. In the case of unit testing, it was done for the activity java files and the xml
files for the layout of the app.

```
● The activity java files contain many functions that the developers have created and
the individual functions are tested while they are being implemented by the
developers.
● Individual modules were tested by log functions to log variables and track the
data’s movements in the debugging terminal
● After testing each individual function, the xml files and the java files compatibility
was tested by repeatedly giving various inputs so that the code functions properly.
● Input testing was done with multiple boundaries, both positive and negative,
to ensure code robustness and reliability on a small scale.
```
For example, the activityPlay function needed to be tested for the correct pitch and roll
Values according to each exercise. Once an initial range was gotten on the pitch (such
as between -1.5 and -0.1) this range was shortened after multiple iterations to
determine what range would be feasible for the while also not being overly flexible.

## Integration Testing

Integration testing is the testing of the interface between two or more software
units. Its focus is on determining the correctness of the interface.

```
● There are a lot of methods for integration testing but the method that was used
for this application was the big-bang integration testing, where modules are put
together at once to determine flaws and errors to be then fixed.
● This type of testing is done for smaller projects which do not contain many
modules or units
● While testing the application using this method, the main flaws were:
○ Integration between statistics and firebase pulling
○ Setting goal times to transfer over to the play activity
○ The external database storing data correctly
```
One instance of this is the goal time variable in goalSet would not properly integrate to
change the timer time in the play activity. This required the testing of the database
referencing loops to figure out how the variable is being manipulated and where it can be
changed without affecting the default time set in the play activity upon creation.


## System Testing

System testing is the software testing of the entire project as a whole.
This is the final form of testing where the testers test for the final acceptance of
the application.

```
● The system testing for this app was done by testing for performance and stress.
● After the whole application was developed the developers tested the application
by repeatedly giving it improper inputs and stress testing it. The application was
also given to random people to get their inputs and stress test it.
● After some testing the developers found some errors and flaws with the log
messages that they implemented in individual modules and made corrections to it.
● The developers also made note that the firebase authentication is inconsistent on
the speed of user login. The firebase authentication login is inconsistent due to the
server speed, but that is something that is not in the hands of the developers. This
was mostly bypassed by moving data transfer to less frequently called functions.
```
To test the correct statistical values in statView it was pertinent to check that the correct
data displayed in the correct activity. This was necessary as while data was present and
stored correctly in firebase it may not have been displayed correctly or processed
correctly within the actual stat view activity. This was done by comparing the database
information to the data displayed within the activity, by immediate reference as well by
performing simple mathematical operations.

## Conclusions

This application is meant to act as a tool to help those who may have trouble
exercising in a normal fashion. Being given a guideline and goals can help motivate
someone to do things they would not normally do. While the app in its current state does
not have many exercises, it has a full functionality that would be helpful to those who use
it.


## References

“CalendarView : Android Developers.” Android Developers,
developer.android.com/reference/android/widget/CalendarView.

“Codelabs for Android Developer Fundamentals : Training Courses.” Android
Developers, developer.android.com/courses/fundamentals-training/toc-v2.

“Installation & Setup on Android | Firebase Realtime Database.” Google, Google,
firebase.google.com/docs/database/android/start.

“Seniors Yoga Sequence: Chair Yoga Sequence for Seniors.” Tummee.com,
[http://www.tummee.com/yoga-sequences/yoga-sequence-for-seniors-restorative-and-chair-](http://www.tummee.com/yoga-sequences/yoga-sequence-for-seniors-restorative-and-chair-)
poses.

Wach, Leszek. “HelloCharts.” Hellocharts-Android, 2014, github.com/lecho/hellocharts-
android.

## Annex

Android Studio - IDE for app development and sensor management

Google Firebase - Realtime database for external storage

HelloChart API - Charting library for Android applications


