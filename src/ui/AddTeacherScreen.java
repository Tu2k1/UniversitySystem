package ui;

import constants.Information;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class AddTeacherScreen extends JFrame implements ActionListener {
    private JLabel courseLabel, teacherLabel;
    private JComboBox<String> courses, teachers;
    private JButton add, cancel;

    public AddTeacherScreen() {
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
        this.setLocation(600, 200);

        initialization();

        JPanel contentPane = (JPanel) this.getContentPane();
        JPanel coursePanel = new JPanel();
        JPanel teacherPanel = new JPanel();
        JPanel buttonPanel = new JPanel();

        coursePanel.add(courseLabel);
        coursePanel.add(courses);
        contentPane.add(coursePanel, BorderLayout.NORTH);

        teacherPanel.add(teacherLabel);
        teacherPanel.add(teachers);
        contentPane.add(teacherPanel, BorderLayout.CENTER);

        buttonPanel.add(add);
        buttonPanel.add(cancel);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);

        this.pack();

        add.addActionListener(this);
        cancel.addActionListener(this);
    }

    private void initialization() {
        courseLabel = new JLabel("Course");
        teacherLabel = new JLabel("Teacher");
        courses = new JComboBox<>(Information.COURSES_NAMES);
        teachers = new JComboBox<>(Information.TEACHERS_NAMES);
        add = new JButton("Add");
        cancel = new JButton("Cancel");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        if (button == cancel) {
            this.setVisible(false);
            return;
        }
        try {
            String chosenCourse = String.valueOf(courses.getSelectedItem());
            String chosenTeacher = String.valueOf(teachers.getSelectedItem());

            if (isAlreadyExists(chosenCourse, chosenTeacher)) {
                JOptionPane.showMessageDialog(
                        null,
                        "The given informations are already exists",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }
            BufferedWriter teacherFile = new BufferedWriter(new FileWriter("teachers.txt", true));
            String result = chosenTeacher + " " + chosenCourse;
            teacherFile.write(result);
            teacherFile.newLine();
            teacherFile.close();

            teacherAdded(chosenCourse, chosenTeacher);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private boolean isAlreadyExists(String chosenCourse, String chosenTeacher) {
        boolean isDuplicates = false;
        try {
            BufferedReader reader = new BufferedReader(new FileReader("teachers.txt"));
            String s;
            while ((s = reader.readLine()) != null) {
                String[] result = s.split(" ");
                String fullName = result[0] + " " + result[1];
                if (chosenTeacher.equals(fullName) && chosenCourse.equals(result[2])) {
                    isDuplicates = true;
                    break;
                }
            }
            reader.close();
        } catch (FileNotFoundException ex) {
            System.out.println("File does not exists");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return isDuplicates;
    }

    private void teacherAdded(String chosenCourse, String chosenTeacher) {
        JOptionPane.showMessageDialog(
                null,
                "Teacher " + chosenTeacher + " have been set to the course " + chosenCourse + " successfully",
                "Information",
                JOptionPane.INFORMATION_MESSAGE
        );
        resetFields();
    }

    private void resetFields() {
        courses.setSelectedIndex(0);
        teachers.setSelectedIndex(0);
    }
}
