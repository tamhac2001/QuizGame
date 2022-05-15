package com.B1906680.app.presentation;

import com.B1906680.app.model.Question;
import com.B1906680.app.presentation_model.GameResultPresentationModel;
import com.B1906680.app.utils.AppComponent;

import javax.inject.Inject;
import javax.swing.*;

public class GameResultForm extends JFrame {
    private JList questionJList;
    private JPanel mainPanel;


    private final GameResultPresentationModel presentationModel;
    private final AppComponent appComponent;

    public GameResultForm(GameResultPresentationModel presentationModel, AppComponent appComponent) {
        this.presentationModel = presentationModel;
        this.appComponent = appComponent;
    }

    public void initState() {
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setContentPane(mainPanel);
        setSize(500, 300);
        setTitle("Quiz Game");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        // frame appear middle of the screen
        setLocationRelativeTo(null);
        setVisible(true);


        final DefaultListModel<String> questionsListModel = new DefaultListModel<>();
        for (Question question : presentationModel.getQuestionList()) {
            System.out.println(question);
            questionsListModel.addElement((question.isUserDidAnswerCorrect() ? "✅ " : "❌ ") + question.getQuestionString());
        }
        questionJList.setModel(questionsListModel);
    }
}
