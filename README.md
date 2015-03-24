# CS4440-ObiGrocery
A grocery list app that keeps track of your purchases and makes decisions like it's reading your mind!

# App Functionality
* Create new grocery lists
* Load existing grocery lists
* Input purchased items on a given day
* Generate reports based on category and timeframe
* Determine regular vs. impulse purchases

## For the Ambitious
* Tutorial for app: https://github.com/amlcurran/ShowcaseView
* OCR reader: https://github.com/GautamGupta/Simple-Android-OCR

# Setting Up Android Environment
Please read the following before starting.

## Installing All the Things
- Set up **JDK 8** on your computer.
- Set up **Android SDK 21** on your computer.
- Set up **Eclipse (Java EE IDE)** on your computer. If you already have Eclipse set up, check for updates.
- Install **Git plugin** to Eclipse. *(Optional, but helpful to notify you if changes in the repo have been made.)*
- **Note:** you can also download and install the ADT Bundle. I don't know how well it'll set itself up because I integrated everything manually. However, you need the above four things in order to run the app.
- Learn Android SDK From Scratch: http://code.tutsplus.com/series/learn-android-sdk-from-scratch--mobile-21677

## Importing Project to Workspace
- **Clone this Git repo** into your workspace. I assume you already know the basics of version control for Git. (clone, push, pull, status, commit)
- When you get Eclipse installed, open Eclipse and import the Git repo as a project. (Go to "File > Import...", then click on "Existing Projects into Workspace", then find the directory of your Git repo.)
- Your project should load in correctly.

## Setting Up Android Emulator
- Open Eclipse, go to "Window > Open Android Virtual Device Manager"
- Click on "Create..."
  - Name your emulator anything.
  - **Device**: Nexus 5 (4.95", 1080 x 1920: xxhdpi)
  - **Target**: Android 5.0.1 - API Level 21
  - **CPU/ABI**: Intel Atom (x86_64)
  - **Keyboard**: Hardware keyboard present
  - **Skin**: Skin with dynamic hardware controls
  - **Cameras**: none
  - **Memory options**: RAM = 1024, VM Heap = 64
  - **Internal Storage**: 200 MiB
  - **SD Card**: don't need to put stuff
  - **Emulation Options**: Check "Use Host GPU"

## Setting Up Emulator for App Launch
- Be sure you're currently selecting the project.
- Go to "Run > Run configurations..."
- The Run Configurations window should open to "Android Application > ObiGrocery-Android-Config" with "Android" tab open. Select "Launch Default Activity" if it hasn't been selected already.
- Switch to "Target" tab. Select "Automatically pick compatible device..." and select your emulator.
- Hit "Run".

## How to Run App on Emulator
- Select the project, right-click, go to "Run as... > Android Application"
- **Note**: If you don't have the emulator open, it will take a few minutes for the emulator to appear on your screen, then a few minutes for the app itself to load.
- **Note**: Every time you want to test the app, do not close the emulator. Close the app, run the app via Eclipse, and the app should update on the emulator.

## Tips Before Working on Project
- Always, always, ALWAYS check the status of the Git repo.
- Before starting your work on the project, always save the work you want to save and pull from the repo.
- Do not push changes that will break the repo.
