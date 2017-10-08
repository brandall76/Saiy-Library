# Saiy® Android Library

This library acts as a connector between the main Saiy application and external applications that may wish to integrate some features of Saiy - such as a tutorial for their application, or interaction with the user regarding settings and functionality.

The library is a required dependency of building the [full Saiy application](https://github.com/brandall76/Saiy-PS).

## License & Copyright

The project is licensed under the GNU Affero General Public License V3. This is a copyleft license. See [LICENSE](https://github.com/brandall76/Saiy-PS/blob/master/LICENSE) 

I have selected this license over any other, in order to ensure that any adaptations or improvements to the code base, require to be published under the same license. This will protect any hard work from being adapted into closed sourced projects, without giving back.

The license grant is not for Saiy's trademarks, which include the logo designs. Saiy reserves all trademark and copyright rights in and to all [Saiy trademarks](https://trademarks.ipo.gov.uk/ipo-tmcase/page/Results/1/UK00003168669?legacySearch=False).

Copyright © 2017 Saiy® Ltd.

## Contributor License Agreements

I need to clarify the most appropriate for the GNU Affero General Public License - will revisit very soon. Any suggestions welcome.

## Installation

The project is built using Java 7 - Android SDK (API 26)

Once tested by the community, this project will be available via mavern/gradle.

Until then, you'll need to import the application via Android Studio, either as a new project from version control or via the downloadable zip.

When the project is successfully imported, select Build and Rebuild Project from inside Android Studio. This will generate an aar file, which will be located in the following project directory:

```
/lapp/build/outputs/aar/lapp-debug.aar
```

[Stack Overflow instructions](https://stackoverflow.com/q/29826717/1256219) for the following procedure:

From the full Saiy project, right hand click on the app folder and select new - module - Import jar/aar Package. Navigate to the above directory and select the aar file.

Right hand click on the app folder and select Open Module Settings. Click the green + at the top left and select the module. In the same settings, make sure the app module is highlighted and select the Dependencies tab. Press the green + at the top right and choose module dependency. Select lapp-debug. Rebuild the project.  

## Troubleshooting

Please use the [Stack Overflow tag](https://stackoverflow.com/tags/saiy/info) for compiling related questions and errors.

For code issues and crashes, please open an issue.