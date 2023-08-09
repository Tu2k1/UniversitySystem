The following is a simple university administration system. It allows users to register students, add teachers, add marks, and view statistics.

To run the program, first open the terminal and navigate to the directory where the code is located. Then, type the following command:

```
javac *.java
java MenuScreen
```

This will compile the code and run the program. The program will display a login screen. Enter the username "admin" and the password "admin" to login.

Once you are logged in, you will see the main menu. The main menu has four options:

* Register students
* Add teachers
* Add marks
* View statistics

To register a student, select the "Register students" option from the main menu. You will be prompted to enter the student's ID, first name, last name, date of birth, and course.

To add a teacher, select the "Add teachers" option from the main menu. You will be prompted to enter the teacher's name, surname, and course.

To add marks, select the "Add marks" option from the main menu. You will be prompted to enter the student's ID, course, and mark.

To view statistics, select the "View statistics" option from the main menu. You will be able to view the number of students registered in each course, the average mark for each course, and the highest and lowest marks for each course.

The program uses three text files to store data:

* students.txt
* teachers.txt
* marks.txt

The students.txt file stores the data of all registered students. Each line in the file contains the student's ID, first name, last name, date of birth, and course.

The teachers.txt file stores the data of all teachers. Each line in the file contains the teacher's name, surname, and course.

The marks.txt file stores the marks of all students. Each line in the file contains the student's ID, course, and mark.
