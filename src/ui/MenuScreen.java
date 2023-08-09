package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuScreen extends JFrame implements ActionListener {
    private JButton registerStudent;
    private JButton addTeacher;
    private JButton addMarks;
    private JButton statistics;

    public MenuScreen() {
        this.setVisible(true);
        this.setLocation(600, 200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initialization();

        JPanel contentPane = (JPanel) this.getContentPane();
        contentPane.setLayout(new GridLayout(0, 1, 0, 8));
        contentPane.add(registerStudent);
        contentPane.add(addTeacher);
        contentPane.add(addMarks);
        contentPane.add(statistics);

        this.pack();
        registerStudent.addActionListener(this);
        addTeacher.addActionListener(this);
        addMarks.addActionListener(this);
        statistics.addActionListener(this);
    }

    private void initialization() {
        registerStudent = new JButton("Register students");
        addTeacher = new JButton("Add teachers");
        addMarks = new JButton("Add marks");
        statistics = new JButton("Statistics");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton b = (JButton) e.getSource();
        if (b == registerStudent) {
            new RegisterStudentsScreen();
        } else if (b == addMarks) {
            new AddMarksScreen();
        } else if (b == addTeacher) {
            new AddTeacherScreen();
        } else {
            new StatisticsScreen();
        }
    }
}
