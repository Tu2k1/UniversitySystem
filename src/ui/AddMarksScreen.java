package ui;

import constants.Information;
import customexceptions.EmptyInputException;
import customexceptions.InvalidMarkException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;


public class AddMarksScreen extends JFrame implements ActionListener {

    private JLabel courseLabel, studentLabel, markLabel;
    private JTextField markText;
    private JComboBox<String> courses, students;
    private JButton add, cancel;

    public AddMarksScreen() {
        this.setVisible(true);
        this.setLocation(600, 200);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initialization();

        JPanel contentPane = (JPanel) this.getContentPane();
        contentPane.setLayout(new GridLayout(0, 1));

        JPanel coursePanel = new JPanel();
        coursePanel.add(courseLabel);
        coursePanel.add(courses);
        contentPane.add(coursePanel);

        JPanel studentPanel = new JPanel();
        studentPanel.add(studentLabel);
        studentPanel.add(students);
        contentPane.add(studentPanel);

        JPanel markPanel = new JPanel();
        markPanel.add(markLabel);
        markPanel.add(markText);
        markPanel.add(new JLabel("/100"));
        contentPane.add(markPanel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(add);
        buttonPanel.add(cancel);
        contentPane.add(buttonPanel);

        this.pack();

        add.addActionListener(this);
        cancel.addActionListener(this);
        courses.addActionListener(this);
    }

    private void initialization() {
        courseLabel = new JLabel("Course");
        studentLabel = new JLabel("Student");
        markLabel = new JLabel("Mark");
        markText = new JTextField(2);
        courses = new JComboBox<>(Information.COURSES_NAMES);
        courses.setSelectedIndex(-1);
        students = new JComboBox<>();
        students.setSelectedIndex(-1);
        add = new JButton("Add");
        cancel = new JButton("Cancel");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (courses == e.getSource()) {
            setStudentComboBox();
            return;
        }
        if (e.getSource() == cancel) {
            this.setVisible(false);
            return;
        }
        try {
            checkIsEmpty();
            int mark = Integer.parseInt(markText.getText().trim());
            checkIsValid(mark);
            String studentId = String.valueOf(students.getSelectedItem());
            String course = String.valueOf(courses.getSelectedItem());
            if(isAlreadyExists(mark,studentId,course)){
                JOptionPane.showMessageDialog(
                        null,
                        "The given informations are already exists",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }
            BufferedWriter markWriter = new BufferedWriter(new FileWriter("marks.txt", true));
            String r = studentId + " " + course + " " + mark;
            markWriter.write(r);
            markWriter.newLine();
            markWriter.close();

            addMarkMessage(mark, studentId, course);
        } catch (EmptyInputException ex) {
            JOptionPane.showMessageDialog(
                    null,
                    "Inputs fields cannot be empty",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE
            );
        } catch (InvalidMarkException ex2) {
            JOptionPane.showMessageDialog(
                    null,
                    ex2.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        } catch (NumberFormatException ex3) {
            JOptionPane.showMessageDialog(
                    null,
                    "mark field cannot contains a letter",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void addMarkMessage(int mark, String studentId, String course) {
        JOptionPane.showMessageDialog(
                null,
                mark + " have been added to student ID " + studentId + " to course " + course,
                "Information",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void checkIsValid(int mark) throws InvalidMarkException {
        if (mark < 0 || mark > 100) {
            throw new InvalidMarkException("mark cannot be negative or above 100");
        }
    }

    private void checkIsEmpty() throws EmptyInputException {
        if (markText.getText().isEmpty() ||
                students.getSelectedItem() == null ||
                courses.getSelectedItem() == null) {
            throw new EmptyInputException();
        }
    }

    private void setStudentComboBox() {
        String course = String.valueOf(courses.getSelectedItem());
        try {
            File studentsFile = new File("students.txt");
            BufferedReader reader = new BufferedReader(new FileReader(studentsFile));
            String s;
            students.removeAllItems();
            while ((s = reader.readLine()) != null) {
                String[] result = s.split(" ");
                if (course.equals(result[4])) {
                    students.addItem(result[0]);
                }
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("File does not exists");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private boolean isAlreadyExists(int mark, String id, String course) {
        boolean flag = false;
        try {
            BufferedReader reader = new BufferedReader(new FileReader("marks.txt"));
            String s;
            while ((s = reader.readLine()) != null) {
                String[] result = s.split(" ");
                if (mark == Integer.parseInt(result[2]) && id.equals(result[0]) && course.equals(result[1])){
                    flag = true;
                }
            }
            reader.close();
        } catch (FileNotFoundException ex) {
            System.out.println("File does not exists");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }
}
