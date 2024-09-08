# Smart Tasks App

**Smart Tasks** is an Android demo application designed to help employees manage their daily tasks. 
The app allows users to view their task list for today and other days, sort tasks by priority, 
mark tasks as done or unresolved, and leave comments for their managers.

## Features

- **View tasks for the day**: Users can see the list of tasks for today and switch between tasks for other days.
- **Sort tasks by priority**: Tasks are automatically sorted by importance, so users know which tasks to tackle first.
- **Task status updates**: Users can mark tasks as "Done" or "Can't resolve".
- **Leave comments**: Users can leave comments on each task to provide feedback or explain issues.
- **Task status icons**: The task status is visually represented by an icon in the task list.

## Technologies

The app is built using a modern Android technology stack, including:

- **MVVM (Model-View-ViewModel)**: For separation of concerns and improved testability.
- **DataBinding**: Automatically updates the UI when data in the ViewModel changes.
- **Room**: Local database for storing task details and status, ensuring offline functionality.
- **RecyclerView**: Displays the task list with optimized and flexible list management.
- **Lifecycle**: Ensures proper management of resources such as network calls and database access throughout the app lifecycle.

## Architecture

The app follows the **MVVM architecture**:

- **Model**: Uses Room to manage data storage.
- **ViewModel**: Manages data and handles interactions between the model and view.
- **View (Activity/Fragment)**: Presents data and handles user interaction.

## Installation

1. Clone the repository:

   ```bash
   git clone https://github.com/vyasenenko/smart-tasks-app.git
   ```

2. Open the project in Android Studio.

3. Build and run the app on an Android device or emulator.

## Requirements

- **Minimum SDK version**: 21
- **Target SDK version**: 34

## Usage

1. Upon launching the app, the user will see the tasks for the current day.
2. The user can tap on any task to view its details.
3. The user can update the task's status and add a comment if necessary.
4. Task statuses are indicated by icons within the task list.

## License

This project is licensed under the MIT License.

MIT License

Copyright (c) 2024 Vitalii Yasenenko

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

---

Feel free to customize the content as per your project's needs!