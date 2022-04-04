package com.B1906680.app.presentation;

import com.B1906680.app.model.Question;
import com.B1906680.app.presentation_model.GamePresentationModel;
import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class GameForm extends JFrame {
    private JPanel mainPanel;
    private JButton answer2;
    private JButton answer1;
    private JButton answer4;
    private JButton answer3;
    private final JButton[] buttons = {answer1, answer2, answer3, answer4};
    private JLabel questionLabel;
    private JLabel currentQuestionOnTotalQuestion;
    private JProgressBar timeProgressBar;

    private final GamePresentationModel presentationModel = new GamePresentationModel(Question.questionList());

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public JButton getAnswer2() {
        return answer2;
    }

    public JButton getAnswer1() {
        return answer1;
    }

    public JButton getAnswer4() {
        return answer4;
    }

    public JButton getAnswer3() {
        return answer3;
    }

    public JLabel getQuestion() {
        return questionLabel;
    }

    public JProgressBar getTimeProgressBar() {
        return timeProgressBar;
    }

    public JLabel getCurrentQuestionOnTotalQuestion() {
        return currentQuestionOnTotalQuestion;
    }

    private final Timer timer = new Timer(1000, null);


    public GameForm() throws IOException {
    }

    public void initState() {
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setContentPane(mainPanel);
        setSize(960, 540);
        setTitle("Quiz Game");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        questionLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        timeProgressBar.setBorder(BorderFactory.createEmptyBorder(0, 10, 20, 0));
        timeProgressBar.setMinimum(0);
        timeProgressBar.setMaximum(10);

        currentQuestionOnTotalQuestion.setSize(30, 30);
//        questionLabel.add(currentQuestionOnTotalQuestion);
        updateState();
        timer.addActionListener(new ActionListener() {
            int counter = 10;

            @Override
            public void actionPerformed(ActionEvent e) {
                counter--;
                if (counter == 0) {
                    counter = 10;
                    presentationModel.toNextQuestion();
                    if (presentationModel.getCurrentQuestionIndex() < presentationModel.getQuestionList().size()) {
                        updateState();
                    } else {
                        timer.stop();
                    }
                }
            }
        });

    }

    public void updateState() {
        this.questionLabel.setText(htmlToText(presentationModel.question()));
        this.currentQuestionOnTotalQuestion.setText(presentationModel.getCurrentQuestionIndex() + 1 + "/" + 10);
        // reset the button value
        for (JButton button : buttons) {
            button.setText("");
        }
        switch (this.presentationModel.correctAnswerNumber()) {
            case 1: {
                this.answer1.setText(htmlToText(presentationModel.correctAnswer()));
                break;
            }
            case 2: {
                this.answer2.setText(htmlToText(presentationModel.correctAnswer()));

                break;
            }
            case 3: {
                this.answer3.setText(htmlToText(presentationModel.correctAnswer()));
                break;
            }
            default: {
                this.answer4.setText(htmlToText(presentationModel.correctAnswer()));
                break;
            }
        }
        for (String wrongAnswer : presentationModel.incorrectAnswer()) {
//            System.out.println(wrongAnswer);
            for (JButton button : buttons) {
                if (button.getText().isEmpty()) {
                    button.setText(htmlToText(wrongAnswer));
                    break;
                }
            }
        }


    }


    private @NotNull String htmlToText(String html) {
        return Jsoup.parse(html).text();
    }
}
