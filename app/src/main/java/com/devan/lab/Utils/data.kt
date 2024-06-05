//package com.devan.lab.Utils
//
//import com.devan.lab.Models.Concept
//import com.devan.lab.Models.Course
//import com.devan.lab.Models.Module
//import com.devan.lab.Models.QuizQuestion
//
//val courses = listOf(
//    Course(
//        id = 1,
//        title = "Introduction to Programming with Python",
//        icon = "ic_python",
//        category = "Developing",
//        modules = listOf(
//            Module(
//                id = 11,
//                title = "Getting Started with Python",
//                videoUrl = "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/_uQrJ0TkZlc?si=Fqnwmg5MujCxwwv9\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" referrerpolicy=\"strict-origin-when-cross-origin\" allowfullscreen></iframe>",
//                description = "Learn the basics of Python programming, including syntax, variables, and data types.",
//                concepts = listOf(
//                    Concept(id = 1, title = "Python Syntax", category = "Basics", description = "The structure and rules for writing Python code."),
//                    Concept(id = 2, title = "Variables", category = "Basics", description = "Storage locations in memory with names."),
//                    Concept(id = 3, title = "Data Types", category = "Basics", description = "Different types of data like integers, strings, and lists."),
//                    Concept(id = 4, title = "Operators", category = "Basics", description = "Symbols used to perform operations on variables and values."),
//                    Concept(id = 5, title = "Input/Output", category = "Basics", description = "Methods for receiving input from users and displaying output."),
//                    Concept(id = 6, title = "Comments", category = "Best Practices", description = "Annotations in code to make it easier to understand.")
//                ),
//                quiz = listOf(
//                    QuizQuestion(
//                        id = 101,
//                        question = "What is the correct way to assign a value to a variable in Python?",
//                        options = listOf("var a = 5", "a = 5", "a := 5", "a == 5"),
//                        correctAnswer = "a = 5"
//                    ),
//                    QuizQuestion(
//                        id = 102,
//                        question = "Which of the following is a valid string in Python?",
//                        options = listOf("'Hello'", "Hello", "Hello'", "'Hello"),
//                        correctAnswer = "'Hello'"
//                    ),
//                    QuizQuestion(
//                        id = 103,
//                        question = "How do you write comments in Python?",
//                        options = listOf("// This is a comment", "# This is a comment", "/* This is a comment */", "<!-- This is a comment -->"),
//                        correctAnswer = "# This is a comment"
//                    ),
//                    QuizQuestion(
//                        id = 104,
//                        question = "What is the output of 'print(3 + 4)' in Python?",
//                        options = listOf("34", "7", "3 + 4", "Error"),
//                        correctAnswer = "7"
//                    ),
//                    QuizQuestion(
//                        id = 105,
//                        question = "Which function is used to get input from the user in Python?",
//                        options = listOf("input()", "get()", "read()", "scan()"),
//                        correctAnswer = "input()"
//                    )
//                )
//            ),
//            Module(
//                id = 12,
//                title = "Control Flow in Python",
//                videoUrl = "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/Zp5MuPOtsSY?si=JMdsrj3WXChH8KIT\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" referrerpolicy=\"strict-origin-when-cross-origin\" allowfullscreen></iframe>",
//                description = "Understand control flow statements such as if-else, for loops, and while loops in Python.",
//                concepts = listOf(
//                    Concept(id = 1, title = "If Statements", category = "Control Flow", description = "Conditionally execute a block of code."),
//                    Concept(id = 2, title = "Else and Elif", category = "Control Flow", description = "Provide alternative blocks of code to execute."),
//                    Concept(id = 3, title = "For Loops", category = "Loops", description = "Iterate over a sequence of elements."),
//                    Concept(id = 4, title = "While Loops", category = "Loops", description = "Repeat a block of code as long as a condition is true."),
//                    Concept(id = 5, title = "Break Statement", category = "Control Flow", description = "Exit the nearest enclosing loop."),
//                    Concept(id = 6, title = "Continue Statement", category = "Control Flow", description = "Skip the rest of the code inside the loop for the current iteration only.")
//                ),
//                quiz = listOf(
//                    QuizQuestion(
//                        id = 201,
//                        question = "What is the correct syntax for an if statement in Python?",
//                        options = listOf("if condition:", "if (condition)", "if {condition}", "if: condition"),
//                        correctAnswer = "if condition:"
//                    ),
//                    QuizQuestion(
//                        id = 202,
//                        question = "Which keyword is used to exit a loop prematurely?",
//                        options = listOf("exit", "break", "stop", "end"),
//                        correctAnswer = "break"
//                    ),
//                    QuizQuestion(
//                        id = 203,
//                        question = "What is the output of 'for i in range(3): print(i)'?",
//                        options = listOf("0 1 2", "1 2 3", "0 1 2 3", "None"),
//                        correctAnswer = "0 1 2"
//                    ),
//                    QuizQuestion(
//                        id = 204,
//                        question = "Which statement is used to skip the current iteration in a loop?",
//                        options = listOf("skip", "continue", "break", "pass"),
//                        correctAnswer = "continue"
//                    ),
//                    QuizQuestion(
//                        id = 205,
//                        question = "What does the 'else' keyword in a loop signify?",
//                        options = listOf("Executes if the loop terminates normally", "Executes if the loop encounters a break", "Executes at the start of each iteration", "Executes only if the loop runs indefinitely"),
//                        correctAnswer = "Executes if the loop terminates normally"
//                    )
//                )
//            ),
//            Module(
//                id = 13,
//                title = "Functions and Modules",
//                videoUrl = "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/89cGQjB5R4M?si=uSxCdK7ofZna8nT2\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" referrerpolicy=\"strict-origin-when-cross-origin\" allowfullscreen></iframe>",
//                description = "Learn how to write reusable code using functions and modules in Python.",
//                concepts = listOf(
//                    Concept(id = 1, title = "Defining Functions", category = "Functions", description = "How to create a function using the def keyword."),
//                    Concept(id = 2, title = "Function Arguments", category = "Functions", description = "Passing parameters to functions."),
//                    Concept(id = 3, title = "Return Values", category = "Functions", description = "Returning values from functions."),
//                    Concept(id = 4, title = "Lambda Functions", category = "Functions", description = "Anonymous functions defined using the lambda keyword."),
//                    Concept(id = 5, title = "Importing Modules", category = "Modules", description = "How to use external modules in your Python code."),
//                    Concept(id = 6, title = "Creating Modules", category = "Modules", description = "How to create your own modules.")
//                ),
//                quiz = listOf(
//                    QuizQuestion(
//                        id = 301,
//                        question = "How do you define a function in Python?",
//                        options = listOf("function myFunc():", "def myFunc():", "define myFunc():", "fn myFunc():"),
//                        correctAnswer = "def myFunc():"
//                    ),
//                    QuizQuestion(
//                        id = 302,
//                        question = "What keyword is used to return a value from a function?",
//                        options = listOf("return", "yield", "output", "give"),
//                        correctAnswer = "return"
//                    ),
//                    QuizQuestion(
//                        id = 303,
//                        question = "What is a lambda function?",
//                        options = listOf("A small anonymous function", "A type of loop", "A module", "A built-in function"),
//                        correctAnswer = "A small anonymous function"
//                    ),
//                    QuizQuestion(
//                        id = 304,
//                        question = "How do you import a module in Python?",
//                        options = listOf("include module_name", "import module_name", "using module_name", "require module_name"),
//                        correctAnswer = "import module_name"
//                    ),
//                    QuizQuestion(
//                        id = 305,
//                        question = "What is the correct syntax to define a function with two parameters?",
//                        options = listOf("def myFunc(param1, param2):", "def myFunc(param1 param2):", "def myFunc(param1; param2):", "def myFunc(param1: param2):"),
//                        correctAnswer = "def myFunc(param1, param2):"
//                    )
//                )
//            )
//        )
//    ),
//    Course(
//        id = 2,
//        title = "Introduction to Java Programming",
//        icon = "ic_java",
//        category = "Developing",
//        modules = listOf(
//            Module(
//                id = 21,
//                title = "Getting Started with Java",
//                videoUrl = "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/grEKMHGYyns?si=WG_WybAlqwicj8Qn\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" referrerpolicy=\"strict-origin-when-cross-origin\" allowfullscreen></iframe>",
//                description = "Learn the basics of Java programming, including setup, syntax, and basic concepts.",
//                concepts = listOf(
//                    Concept(id = 1, title = "Java Syntax", category = "Basics", description = "The structure and rules for writing Java code."),
//                    Concept(id = 2, title = "Variables", category = "Basics", description = "Storage locations in memory with names."),
//                    Concept(id = 3, title = "Data Types", category = "Basics", description = "Different types of data like integers, strings, and arrays."),
//                    Concept(id = 4, title = "Operators", category = "Basics", description = "Symbols used to perform operations on variables and values."),
//                    Concept(id = 5, title = "Control Statements", category = "Control Flow", description = "Statements that control the flow of execution."),
//                    Concept(id = 6, title = "Comments", category = "Best Practices", description = "Annotations in code to make it easier to understand.")
//                ),
//                quiz = listOf(
//                    QuizQuestion(
//                        id = 401,
//                        question = "How do you declare a variable in Java?",
//                        options = listOf("int a = 5;", "var a = 5;", "a := 5;", "a == 5;"),
//                        correctAnswer = "int a = 5;"
//                    ),
//                    QuizQuestion(
//                        id = 402,
//                        question = "Which of the following is a valid string in Java?",
//                        options = listOf("\"Hello\"", "Hello", "Hello'", "'Hello'"),
//                        correctAnswer = "\"Hello\""
//                    ),
//                    QuizQuestion(
//                        id = 403,
//                        question = "How do you write comments in Java?",
//                        options = listOf("# This is a comment", "// This is a comment", "/* This is a comment */", "<!-- This is a comment -->"),
//                        correctAnswer = "// This is a comment"
//                    ),
//                    QuizQuestion(
//                        id = 404,
//                        question = "What is the output of 'System.out.println(3 + 4)' in Java?",
//                        options = listOf("34", "7", "3 + 4", "Error"),
//                        correctAnswer = "7"
//                    ),
//                    QuizQuestion(
//                        id = 405,
//                        question = "Which function is used to get input from the user in Java?",
//                        options = listOf("Scanner.next()", "input()", "get()", "read()"),
//                        correctAnswer = "Scanner.next()"
//                    )
//                )
//            ),
//            Module(
//                id = 22,
//                title = "Java Control Flow",
//                videoUrl = "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/Af3s3KsxStY?si=zvPXFMAOW4vi0Kta\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" referrerpolicy=\"strict-origin-when-cross-origin\" allowfullscreen></iframe>",
//                description = "Understand control flow statements such as if-else, for loops, and while loops in Java.",
//                concepts = listOf(
//                    Concept(id = 1, title = "If Statements", category = "Control Flow", description = "Conditionally execute a block of code."),
//                    Concept(id = 2, title = "Else and Else If", category = "Control Flow", description = "Provide alternative blocks of code to execute."),
//                    Concept(id = 3, title = "For Loops", category = "Loops", description = "Iterate over a sequence of elements."),
//                    Concept(id = 4, title = "While Loops", category = "Loops", description = "Repeat a block of code as long as a condition is true."),
//                    Concept(id = 5, title = "Break Statement", category = "Control Flow", description = "Exit the nearest enclosing loop."),
//                    Concept(id = 6, title = "Continue Statement", category = "Control Flow", description = "Skip the rest of the code inside the loop for the current iteration only.")
//                ),
//                quiz = listOf(
//                    QuizQuestion(
//                        id = 501,
//                        question = "What is the correct syntax for an if statement in Java?",
//                        options = listOf("if (condition) {", "if condition:", "if {condition}", "if: condition"),
//                        correctAnswer = "if (condition) {"
//                    ),
//                    QuizQuestion(
//                        id = 502,
//                        question = "Which keyword is used to exit a loop prematurely in Java?",
//                        options = listOf("exit", "break", "stop", "end"),
//                        correctAnswer = "break"
//                    ),
//                    QuizQuestion(
//                        id = 503,
//                        question = "What is the output of 'for (int i = 0; i < 3; i++) { System.out.println(i); }'?",
//                        options = listOf("0 1 2", "1 2 3", "0 1 2 3", "None"),
//                        correctAnswer = "0 1 2"
//                    ),
//                    QuizQuestion(
//                        id = 504,
//                        question = "Which statement is used to skip the current iteration in a loop in Java?",
//                        options = listOf("skip", "continue", "break", "pass"),
//                        correctAnswer = "continue"
//                    ),
//                    QuizQuestion(
//                        id = 505,
//                        question = "What does the 'else' keyword in a loop signify in Java?",
//                        options = listOf("Executes if the loop terminates normally", "Executes if the loop encounters a break", "Executes at the start of each iteration", "Executes only if the loop runs indefinitely"),
//                        correctAnswer = "Executes if the loop terminates normally"
//                    )
//                )
//            ),
//            Module(
//                id = 23,
//                title = "Object-Oriented Programming in Java",
//                videoUrl = "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/6T_HgnjoYwM?si=jckDzMWeNY2bZd-E\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" referrerpolicy=\"strict-origin-when-cross-origin\" allowfullscreen></iframe>",
//                description = "Learn the fundamentals of object-oriented programming (OOP) in Java.",
//                concepts = listOf(
//                    Concept(id = 1, title = "Classes and Objects", category = "OOP Concepts", description = "Blueprints for creating objects and instances of a class."),
//                    Concept(id = 2, title = "Inheritance", category = "OOP Concepts", description = "Mechanism for a new class to inherit properties and methods from an existing class."),
//                    Concept(id = 3, title = "Polymorphism", category = "OOP Concepts", description = "Ability of different classes to respond to the same method call."),
//                    Concept(id = 4, title = "Encapsulation", category = "OOP Concepts", description = "Bundling of data and methods that operate on that data within one unit."),
//                    Concept(id = 5, title = "Abstraction", category = "OOP Concepts", description = "Hiding complex implementation details and showing only the necessary features."),
//                    Concept(id = 6, title = "Interfaces", category = "Advanced OOP", description = "Abstract types that allow classes to implement multiple inheritances.")
//                ),
//                quiz = listOf(
//                    QuizQuestion(
//                        id = 601,
//                        question = "What is a class in Java?",
//                        options = listOf("A blueprint for creating objects", "A variable", "A method", "An operator"),
//                        correctAnswer = "A blueprint for creating objects"
//                    ),
//                    QuizQuestion(
//                        id = 602,
//                        question = "What is inheritance in Java?",
//                        options = listOf("Mechanism for a new class to inherit properties and methods from an existing class", "Ability of a class to have multiple methods", "Hiding complex implementation details", "Mechanism to handle runtime errors"),
//                        correctAnswer = "Mechanism for a new class to inherit properties and methods from an existing class"
//                    ),
//                    QuizQuestion(
//                        id = 603,
//                        question = "What is polymorphism in Java?",
//                        options = listOf("Ability of different classes to respond to the same method call", "Hiding complex implementation details", "Mechanism to handle runtime errors", "Process of creating objects"),
//                        correctAnswer = "Ability of different classes to respond to the same method call"
//                    ),
//                    QuizQuestion(
//                        id = 604,
//                        question = "What is encapsulation in Java?",
//                        options = listOf("Bundling of data and methods that operate on that data within one unit", "Mechanism to handle runtime errors", "Process of creating objects", "Mechanism for a new class to inherit properties and methods from an existing class"),
//                        correctAnswer = "Bundling of data and methods that operate on that data within one unit"
//                    ),
//                    QuizQuestion(
//                        id = 605,
//                        question = "What is an interface in Java?",
//                        options = listOf("Abstract type that allows classes to implement multiple inheritances", "Blueprint for creating objects", "Mechanism for handling exceptions", "Process of creating objects"),
//                        correctAnswer = "Abstract type that allows classes to implement multiple inheritances"
//                    )
//                )
//            )
//        )
//    ),
//    Course(
//        id = 3,
//        title = "Introduction to UI Design",
//        icon = "ic_ui_design",
//        category = "Designing",
//        modules = listOf(
//            Module(
//                id = 31,
//                title = "Basics of UI Design",
//                videoUrl = "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/uwNClNmekGU?si=1WVB_-OtByjVzXVg\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" referrerpolicy=\"strict-origin-when-cross-origin\" allowfullscreen></iframe>",
//                description = "Learn the basics of UI design, including principles and tools.",
//                concepts = listOf(
//                    Concept(id = 1, title = "Design Principles", category = "Basics", description = "Fundamental concepts like balance, contrast, and alignment."),
//                    Concept(id = 2, title = "Color Theory", category = "Basics", description = "Study of colors and their impact on design."),
//                    Concept(id = 3, title = "Typography", category = "Basics", description = "Art and technique of arranging type."),
//                    Concept(id = 4, title = "Grid Systems", category = "Layout", description = "Frameworks used to organize content."),
//                    Concept(id = 5, title = "UI Elements", category = "Components", description = "Basic components like buttons, icons, and input fields."),
//                    Concept(id = 6, title = "User Research", category = "Process", description = "Methods for understanding user needs and behaviors.")
//                ),
//                quiz = listOf(
//                    QuizQuestion(
//                        id = 701,
//                        question = "What is balance in design principles?",
//                        options = listOf("Distribution of visual weight", "Choice of colors", "Arrangement of type", "User research method"),
//                        correctAnswer = "Distribution of visual weight"
//                    ),
//                    QuizQuestion(
//                        id = 702,
//                        question = "What is color theory?",
//                        options = listOf("Study of colors and their impact on design", "Art of arranging type", "Framework for organizing content", "Method for understanding user needs"),
//                        correctAnswer = "Study of colors and their impact on design"
//                    ),
//                    QuizQuestion(
//                        id = 703,
//                        question = "What does typography refer to?",
//                        options = listOf("Arrangement of type", "Framework for organizing content", "Choice of colors", "Basic components like buttons and icons"),
//                        correctAnswer = "Arrangement of type"
//                    ),
//                    QuizQuestion(
//                        id = 704,
//                        question = "What is the purpose of a grid system in UI design?",
//                        options = listOf("Organize content", "Distribute visual weight", "Arrange type", "Understand user needs"),
//                        correctAnswer = "Organize content"
//                    ),
//                    QuizQuestion(
//                        id = 705,
//                        question = "Which UI element is typically used for user inputs?",
//                        options = listOf("Button", "Icon", "Input field", "Typography"),
//                        correctAnswer = "Input field"
//                    )
//                )
//            ),
//            Module(
//                id = 32,
//                title = "Designing with Figma",
//                videoUrl = "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/shuIfhrLIP0?si=RTFLW65dIYN0wLx3\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" referrerpolicy=\"strict-origin-when-cross-origin\" allowfullscreen></iframe>",
//                description = "Understand how to use Figma for UI design projects.",
//                concepts = listOf(
//                    Concept(id = 1, title = "Figma Interface", category = "Basics", description = "Overview of the Figma interface and tools."),
//                    Concept(id = 2, title = "Design Components", category = "Components", description = "Creating reusable design elements."),
//                    Concept(id = 3, title = "Prototyping", category = "Prototyping", description = "Creating interactive prototypes."),
//                    Concept(id = 4, title = "Collaboration", category = "Process", description = "Working with team members in Figma."),
//                    Concept(id = 5, title = "Auto Layout", category = "Advanced", description = "Using auto layout for responsive design."),
//                    Concept(id = 6, title = "Design Systems", category = "Advanced", description = "Creating and managing design systems.")
//                ),
//                quiz = listOf(
//                    QuizQuestion(
//                        id = 801,
//                        question = "What is the primary function of the Figma interface?",
//                        options = listOf("Create and manage design projects", "Understand user needs", "Arrange type", "Distribute visual weight"),
//                        correctAnswer = "Create and manage design projects"
//                    ),
//                    QuizQuestion(
//                        id = 802,
//                        question = "What are design components in Figma?",
//                        options = listOf("Reusable design elements", "User research methods", "Basic UI elements", "Grid systems"),
//                        correctAnswer = "Reusable design elements"
//                    ),
//                    QuizQuestion(
//                        id = 803,
//                        question = "What is prototyping in Figma?",
//                        options = listOf("Creating interactive prototypes", "Arranging type", "Understanding user needs", "Choosing colors"),
//                        correctAnswer = "Creating interactive prototypes"
//                    ),
//                    QuizQuestion(
//                        id = 804,
//                        question = "How does Figma support collaboration?",
//                        options = listOf("Allows multiple users to work on the same file", "Organizes content", "Distributes visual weight", "Arranges type"),
//                        correctAnswer = "Allows multiple users to work on the same file"
//                    ),
//                    QuizQuestion(
//                        id = 805,
//                        question = "What is the purpose of auto layout in Figma?",
//                        options = listOf("Create responsive designs", "Organize content", "Arrange type", "Understand user needs"),
//                        correctAnswer = "Create responsive designs"
//                    )
//                )
//            ),
//            Module(
//                id = 33,
//                title = "Advanced UI Design Techniques",
//                videoUrl = "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/9jp_1gHhieA?si=AoAS7OJFtBp1wO-f\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" referrerpolicy=\"strict-origin-when-cross-origin\" allowfullscreen></iframe>",
//                description = "Explore advanced techniques in UI design.",
//                concepts = listOf(
//                    Concept(id = 1, title = "Microinteractions", category = "Advanced", description = "Small animations that enhance user experience."),
//                    Concept(id = 2, title = "Responsive Design", category = "Advanced", description = "Designs that adapt to different screen sizes."),
//                    Concept(id = 3, title = "Accessibility", category = "Best Practices", description = "Making designs usable for people with disabilities."),
//                    Concept(id = 4, title = "User Testing", category = "Process", description = "Methods for evaluating usability with real users."),
//                    Concept(id = 5, title = "Advanced Prototyping", category = "Prototyping", description = "Creating high-fidelity prototypes."),
//                    Concept(id = 6, title = "Animation", category = "Advanced", description = "Adding motion to UI elements to improve user engagement.")
//                ),
//                quiz = listOf(
//                    QuizQuestion(
//                        id = 901,
//                        question = "What are microinteractions?",
//                        options = listOf("Small animations that enhance user experience", "Methods for user testing", "Frameworks for organizing content", "Basic UI components"),
//                        correctAnswer = "Small animations that enhance user experience"
//                    ),
//                    QuizQuestion(
//                        id = 902,
//                        question = "What is the purpose of responsive design?",
//                        options = listOf("Adapt designs to different screen sizes", "Organize content", "Arrange type", "Distribute visual weight"),
//                        correctAnswer = "Adapt designs to different screen sizes"
//                    ),
//                    QuizQuestion(
//                        id = 903,
//                        question = "What is accessibility in UI design?",
//                        options = listOf("Making designs usable for people with disabilities", "Creating high-fidelity prototypes", "Adding motion to UI elements", "User testing methods"),
//                        correctAnswer = "Making designs usable for people with disabilities"
//                    ),
//                    QuizQuestion(
//                        id = 904,
//                        question = "What is the purpose of user testing?",
//                        options = listOf("Evaluate usability with real users", "Distribute visual weight", "Choose colors", "Arrange type"),
//                        correctAnswer = "Evaluate usability with real users"
//                    ),
//                    QuizQuestion(
//                        id = 905,
//                        question = "What is the focus of advanced prototyping?",
//                        options = listOf("Creating high-fidelity prototypes", "Organizing content", "Distributing visual weight", "Arranging type"),
//                        correctAnswer = "Creating high-fidelity prototypes"
//                    )
//                )
//            )
//        )
//    ),
//    Course(
//        id = 4,
//        title = "Machine Learning Basics",
//        icon = "ic_ml",
//        category = "IA and ML",
//        modules = listOf(
//            Module(
//                id = 41,
//                title = "Introduction to Machine Learning",
//                videoUrl = "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/ukzFI9rgwfU?si=7iLbxCDARvxb884W\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" referrerpolicy=\"strict-origin-when-cross-origin\" allowfullscreen></iframe>",
//                description = "Learn the fundamentals of machine learning.",
//                concepts = listOf(
//                    Concept(id = 1, title = "Supervised Learning", category = "Basics", description = "Training a model on labeled data."),
//                    Concept(id = 2, title = "Unsupervised Learning", category = "Basics", description = "Finding patterns in unlabeled data."),
//                    Concept(id = 3, title = "Reinforcement Learning", category = "Advanced", description = "Training models based on feedback from the environment."),
//                    Concept(id = 4, title = "Regression", category = "Basics", description = "Predicting continuous values."),
//                    Concept(id = 5, title = "Classification", category = "Basics", description = "Predicting discrete labels."),
//                    Concept(id = 6, title = "Clustering", category = "Advanced", description = "Grouping similar data points together.")
//                ),
//                quiz = listOf(
//                    QuizQuestion(
//                        id = 111,
//                        question = "What is supervised learning?",
//                        options = listOf("Training a model on labeled data", "Finding patterns in unlabeled data", "Training models based on feedback from the environment", "Grouping similar data points together"),
//                        correctAnswer = "Training a model on labeled data"
//                    ),
//                    QuizQuestion(
//                        id = 112,
//                        question = "What is the goal of regression in machine learning?",
//                        options = listOf("Predicting continuous values", "Predicting discrete labels", "Grouping similar data points", "Finding patterns in unlabeled data"),
//                        correctAnswer = "Predicting continuous values"
//                    ),
//                    QuizQuestion(
//                        id = 113,
//                        question = "What is clustering?",
//                        options = listOf("Grouping similar data points together", "Training a model on labeled data", "Predicting discrete labels", "Finding patterns in unlabeled data"),
//                        correctAnswer = "Grouping similar data points together"
//                    ),
//                    QuizQuestion(
//                        id = 114,
//                        question = "What is reinforcement learning?",
//                        options = listOf("Training models based on feedback from the environment", "Training a model on labeled data", "Predicting continuous values", "Finding patterns in unlabeled data"),
//                        correctAnswer = "Training models based on feedback from the environment"
//                    ),
//                    QuizQuestion(
//                        id = 115,
//                        question = "What is the primary focus of classification?",
//                        options = listOf("Predicting discrete labels", "Predicting continuous values", "Grouping similar data points", "Finding patterns in unlabeled data"),
//                        correctAnswer = "Predicting discrete labels"
//                    )
//                )
//            ),
//            Module(
//                id = 42,
//                title = "Supervised Learning",
//                videoUrl = "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/4qVRBYAdLAo?si=U-ruaPrTFwcf47JI\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" referrerpolicy=\"strict-origin-when-cross-origin\" allowfullscreen></iframe>",
//                description = "Understand supervised learning algorithms and techniques.",
//                concepts = listOf(
//                    Concept(id = 1, title = "Linear Regression", category = "Regression", description = "Modeling the relationship between a dependent variable and one or more independent variables."),
//                    Concept(id = 2, title = "Logistic Regression", category = "Classification", description = "Predicting the probability of a binary outcome."),
//                    Concept(id = 3, title = "Decision Trees", category = "Classification", description = "Tree-like models used for decision making."),
//                    Concept(id = 4, title = "Support Vector Machines", category = "Classification", description = "Finding the hyperplane that best separates different classes."),
//                    Concept(id = 5, title = "K-Nearest Neighbors", category = "Classification", description = "Classifying data points based on the classes of their nearest neighbors."),
//                    Concept(id = 6, title = "Random Forests", category = "Ensemble Methods", description = "Ensemble of decision trees for improving predictive performance.")
//                ),
//                quiz = listOf(
//                    QuizQuestion(
//                        id = 121,
//                        question = "What is the primary goal of linear regression?",
//                        options = listOf("Modeling the relationship between a dependent variable and one or more independent variables", "Predicting the probability of a binary outcome", "Classifying data points based on the classes of their nearest neighbors", "Finding patterns in unlabeled data"),
//                        correctAnswer = "Modeling the relationship between a dependent variable and one or more independent variables"
//                    ),
//                    QuizQuestion(
//                        id = 122,
//                        question = "What is logistic regression used for?",
//                        options = listOf("Predicting the probability of a binary outcome", "Modeling the relationship between a dependent variable and one or more independent variables", "Grouping similar data points together", "Finding patterns in unlabeled data"),
//                        correctAnswer = "Predicting the probability of a binary outcome"
//                    ),
//                    QuizQuestion(
//                        id = 123,
//                        question = "What is the purpose of decision trees?",
//                        options = listOf("Tree-like models used for decision making", "Finding patterns in unlabeled data", "Predicting continuous values", "Classifying data points based on the classes of their nearest neighbors"),
//                        correctAnswer = "Tree-like models used for decision making"
//                    ),
//                    QuizQuestion(
//                        id = 124,
//                        question = "What do support vector machines aim to find?",
//                        options = listOf("Finding the hyperplane that best separates different classes", "Grouping similar data points together", "Modeling the relationship between a dependent variable and one or more independent variables", "Predicting continuous values"),
//                        correctAnswer = "Finding the hyperplane that best separates different classes"
//                    ),
//                    QuizQuestion(
//                        id = 125,
//                        question = "How does k-nearest neighbors classify data points?",
//                        options = listOf("Based on the classes of their nearest neighbors", "Finding patterns in unlabeled data", "Predicting the probability of a binary outcome", "Using ensemble of decision trees"),
//                        correctAnswer = "Based on the classes of their nearest neighbors"
//                    )
//                )
//            ),
//            Module(
//                id = 43,
//                title = "Unsupervised Learning",
//                videoUrl = "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/D6gtZrsYi6c?si=uvmQWNKyf2U79hdo\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" referrerpolicy=\"strict-origin-when-cross-origin\" allowfullscreen></iframe>",
//                description = "Explore unsupervised learning algorithms and techniques.",
//                concepts = listOf(
//                    Concept(id = 1, title = "K-Means Clustering", category = "Clustering", description = "Partitioning data into k distinct clusters."),
//                    Concept(id = 2, title = "Hierarchical Clustering", category = "Clustering", description = "Building a hierarchy of clusters."),
//                    Concept(id = 3, title = "Principal Component Analysis", category = "Dimensionality Reduction", description = "Reducing the dimensionality of data while preserving variance."),
//                    Concept(id = 4, title = "Anomaly Detection", category = "Anomaly Detection", description = "Identifying rare items, events, or observations."),
//                    Concept(id = 5, title = "Association Rules", category = "Association", description = "Discovering interesting relations between variables in large databases."),
//                    Concept(id = 6, title = "Autoencoders", category = "Neural Networks", description = "Neural networks used for unsupervised learning tasks.")
//                ),
//                quiz = listOf(
//                    QuizQuestion(
//                        id = 131,
//                        question = "What is k-means clustering?",
//                        options = listOf("Partitioning data into k distinct clusters", "Building a hierarchy of clusters", "Reducing the dimensionality of data", "Identifying rare items, events, or observations"),
//                        correctAnswer = "Partitioning data into k distinct clusters"
//                    ),
//                    QuizQuestion(
//                        id = 132,
//                        question = "What is the purpose of principal component analysis?",
//                        options = listOf("Reducing the dimensionality of data while preserving variance", "Partitioning data into k distinct clusters", "Building a hierarchy of clusters", "Discovering interesting relations between variables"),
//                        correctAnswer = "Reducing the dimensionality of data while preserving variance"
//                    ),
//                    QuizQuestion(
//                        id = 133,
//                        question = "What is anomaly detection used for?",
//                        options = listOf("Identifying rare items, events, or observations", "Partitioning data into k distinct clusters", "Building a hierarchy of clusters", "Reducing the dimensionality of data"),
//                        correctAnswer = "Identifying rare items, events, or observations"
//                    ),
//                    QuizQuestion(
//                        id = 134,
//                        question = "What are association rules in unsupervised learning?",
//                        options = listOf("Discovering interesting relations between variables in large databases", "Identifying rare items, events, or observations", "Partitioning data into k distinct clusters", "Reducing the dimensionality of data"),
//                        correctAnswer = "Discovering interesting relations between variables in large databases"
//                    ),
//                    QuizQuestion(
//                        id = 135,
//                        question = "What are autoencoders used for?",
//                        options = listOf("Unsupervised learning tasks", "Supervised learning tasks", "Predicting continuous values", "Finding patterns in labeled data"),
//                        correctAnswer = "Unsupervised learning tasks"
//                    )
//                )
//            )
//        )
//    )
//)
