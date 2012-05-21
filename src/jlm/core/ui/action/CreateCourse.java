package jlm.core.ui.action;

import jlm.core.model.Course;
import jlm.core.model.Game;
import jlm.core.ui.CreateCourseDialog;
import jlm.core.ui.TeacherConsoleDialog;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class CreateCourse extends AbstractGameAction {

    private TeacherConsoleDialog teacherConsoleDialog;

    public CreateCourse(Game game, String text, TeacherConsoleDialog teacherConsoleDialog) {
        super(game, text);
        this.teacherConsoleDialog = teacherConsoleDialog;
    }

    public CreateCourse(Game game, String text, ImageIcon icon) {
        super(game, text, icon);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        new CreateCourseDialog().show();
        teacherConsoleDialog.refresh();
    }
}
