package ui;

import constants.Information;
import customexceptions.EmptyFileException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class StatisticsScreen extends JFrame implements ActionListener {
    private JLabel courseLabel, passLabel, failLabel;
    private JComboBox<String> courses;
    private JLabel passNumber, failNumber;

    public StatisticsScreen() {
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocation(600, 200);

        initialization();

        JPanel contentPane = (JPanel) this.getContentPane();
        JPanel coursePanel = new JPanel();
        JPanel passPanel = new JPanel();
        JPanel failPanel = new JPanel();

        coursePanel.add(courseLabel);
        coursePanel.add(courses);
        contentPane.add(coursePanel, BorderLayout.NORTH);

        passPanel.add(passLabel);
        passPanel.add(passNumber);
        contentPane.add(passPanel, BorderLayout.CENTER);

        failPanel.add(failLabel);
        failPanel.add(failNumber);
        contentPane.add(failPanel, BorderLayout.SOUTH);
        this.pack();

        courses.addActionListener(this);
    }

    private void initialization() {
        courseLabel = new JLabel("Course");
        passLabel = new JLabel("Pass");
        failLabel = new JLabel("Fail");
        courses = new JComboBox<>(Information.COURSES_NAMES);
        courses.setSelectedIndex(-1);
        passNumber = new JLabel("0");
        failNumber = new JLabel("0");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            int passCount = 0;
            int failCount = 0;
            File marks = new File("marks.txt");
            checkIsEmpty(marks);
            BufferedReader markReader = new BufferedReader(new FileReader(marks));
            String s;
            while ((s = markReader.readLine()) != null) {
                String[] result = s.split(" ");
                String course = result[1];
                int mark = Integer.parseInt(result[2]);
                if (mark >= 60 && course.equals(courses.getSelectedItem()))
                    passCount++;
                else if (mark < 60 && course.equals(courses.getSelectedItem()))
                    failCount++;
            }
            markReader.close();

            passNumber.setText(String.valueOf(passCount));
            failNumber.setText(String.valueOf(failCount));

        } catch (FileNotFoundException ex) {
            System.out.println("File does not exists");
        } catch (IOException ex2) {
            ex2.printStackTrace();
        } catch (EmptyFileException ex3) {
            JOptionPane.showMessageDialog(
                    null,
                    "There is no data to read.",
                    "Empty File",
                    JOptionPane.WARNING_MESSAGE
            );
            this.setVisible(false);
        }
    }

    private void checkIsEmpty(File marks) throws EmptyFileException {
        if (marks.length() == 0) {
            throw new EmptyFileException();
        }
    }
}
