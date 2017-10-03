# Saiy Android Library

This library acts as a connector between the main Saiy application and external applications that may wish to integrate some features of Saiy - such as a tutorial for their application, or interaction with the user regarding settings and functionality.

The library is a required dependency of building the full Saiy application.

## Installation

The project is built using Java 7 - Android SDK (API 26)

Once tested by the community, this project will be available via mavern/gradle.

Until then, you'll need to import the application via Android Studio, either as a new project from version control or via the downloadable zip.

When the project is successfully imported, select Build and Rebuild Project from inside Android Studio. This will generate an aar file, which will be located in the following project directory:

```
/lapp/build/outputs/aar/lapp-debug.aar
```

From the full Saiy project, right hand click on the app folder and select new - module - Import jar/aar Package. Navigate to the above directory and select the aar file.

Right hand click on the app folder and select Open Module Settings. Click the green + at the top left and select the module. In the same settings, make sure the app module is highlighted and select the Dependencies tab. Press the green + at the top right and choose module dependency. Select lapp-debug. Rebuild the project.  
