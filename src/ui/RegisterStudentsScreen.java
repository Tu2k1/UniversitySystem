package ui;


import constants.Information;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Arrays;

public class RegisterStudentsScreen extends JFrame implements ActionListener {
    private JLabel idLabel, firstNameLabel, lastNameLabel, birthdayLabel, courseLabel;
    private JTextField idText, firstNameText, lastNameText, dayText, monthText, yearText;
    private JComboBox<String> courses;

    private JButton add, cancel;

    public RegisterStudentsScreen() {
        this.setVisible(true);
        this.setLocation(600, 200);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initialization();

        JPanel contentPane = (JPanel) this.getContentPane();
        contentPane.setLayout(new GridLayout(0, 1));

        JPanel idPanel = new JPanel();
        idPanel.add(idLabel);
        idPanel.add(idText);
        contentPane.add(idPanel);

        JPanel fnamePanel = new JPanel();
        fnamePanel.add(firstNameLabel);
        fnamePanel.add(firstNameText);
        contentPane.add(fnamePanel);

        JPanel lnamePanel = new JPanel();
        lnamePanel.add(lastNameLabel);
        lnamePanel.add(lastNameText);
        contentPane.add(lnamePanel);

        JPanel birthdayPanel = new JPanel();
        birthdayPanel.add(birthdayLabel);
        birthdayPanel.add(dayText);
        birthdayPanel.add(new JLabel("/"));
        birthdayPanel.add(monthText);
        birthdayPanel.add(new JLabel("/"));
        birthdayPanel.add(yearText);
        contentPane.add(birthdayPanel);

        JPanel coursePanel = new JPanel();
        coursePanel.add(courseLabel);
        coursePanel.add(courses);
        contentPane.add(coursePanel);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(add);
        buttonsPanel.add(cancel);
        contentPane.add(buttonsPanel);

        this.pack();
    }

    private void initialization() {
        idLabel = new JLabel("University ID");
        firstNameLabel = new JLabel("First name");
        lastNameLabel = new JLabel("Last name");
        birthdayLabel = new JLabel("Birthday");
        courseLabel = new JLabel("Course");
        idText = new JTextField(10);
        firstNameText = new JTextField(10);
        lastNameText = new JTextField(10);
        dayText = new JTextField(2);
        monthText = new JTextField(2);
        yearText = new JTextField(4);
        courses = new JComboBox<>(Information.COURSES_NAMES);
        add = new JButton("Add");
        cancel = new JButton("Cancel");

        add.addActionListener(this);
        cancel.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton b = (JButton) e.getSource();

        if (b == cancel) {
            resetFields();
            return;
        }
        try {
            int id = Integer.parseInt(idText.getText().trim());
            String firstName = firstNameText.getText().trim();
            String lastName = lastNameText.getText().trim();
            int day = Integer.parseInt(dayText.getText().trim());
            int month = Integer.parseInt(monthText.getText().trim());
            int year = Integer.parseInt(yearText.getText().trim());
            LocalDate birthDate = LocalDate.of(year, month, day);
            String chosenCourse = String.valueOf(courses.getSelectedItem());

            if (isAlreadyExists(id, chosenCourse)) {
                JOptionPane.showMessageDialog(
                        null,
                        "The given informations are already exists",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            BufferedWriter studentsFile = new BufferedWriter(new FileWriter("students.txt", true));
            String result = id + " " + firstName + " " + lastName + " " + birthDate + " " + chosenCourse;
            studentsFile.write(result);
            studentsFile.newLine();
            studentsFile.close();

            registerMessage(id, chosenCourse);
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (NumberFormatException ex3) {
            if (ex3.getMessage().equals("For input string: \"\"")) {
                JOptionPane.showMessageDialog(
                        null,
                        "There are some empty fields",
                        "Empty Field",
                        JOptionPane.WARNING_MESSAGE
                );
            } else
                JOptionPane.showMessageDialog(
                        null,
                        "fields cannot contains a letter",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
        } catch (DateTimeException ex4) {
            JOptionPane.showMessageDialog(
                    null,
                    "Invalid date",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    private boolean isAlreadyExists(int id, String chosenCourse) {
        boolean flag = false;
        try {
            BufferedReader reader = new BufferedReader(new FileReader("students.txt"));
            String s;
            while ((s = reader.readLine()) != null) {
                String[] result = s.split(" ");
                if (id == (Integer.parseInt(result[0])) && chosenCourse.equals(result[4])) {
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

    private void registerMessage(int id, String course) {
        JOptionPane.showMessageDialog(
                null,
                "Student with ID " + id + " have been registered to course " + course + " successfully",
                "Information",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void resetFields() {
        idText.setText("");
        firstNameText.setText("");
        lastNameText.setText("");
        dayText.setText("");
        monthText.setText("");
        yearText.setText("");
        courses.setSelectedIndex(0);
    }
}
