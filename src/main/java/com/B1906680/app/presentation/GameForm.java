package com.B1906680.app.presentation;

import com.B1906680.app.model.Question;
import com.B1906680.app.presentation_model.GamePresentationModel;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class GameForm extends JFrame {
    private JPanel mainPanel;
    private JToggleButton answer2;
    private JToggleButton answer1;
    private JToggleButton answer4;
    private JToggleButton answer3;
    private ButtonGroup buttonGroup;
    private final JToggleButton[] buttons = {answer1, answer2, answer3, answer4};
    private JLabel questionLabel;
    private JLabel currentQuestionOnTotalQuestion;
    private JProgressBar timeProgressBar;
    private JLabel pointLabel;

    private final GamePresentationModel presentationModel = new GamePresentationModel(Question.questionList());

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public JToggleButton getAnswer2() {
        return answer2;
    }

    public JToggleButton getAnswer1() {
        return answer1;
    }

    public JToggleButton getAnswer4() {
        return answer4;
    }

    public JToggleButton getAnswer3() {
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
        buttonGroup = new ButtonGroup();
        buttonGroup.add(answer1);
        buttonGroup.add(answer2);
        buttonGroup.add(answer3);
        buttonGroup.add(answer4);
    }

    public void initState() {
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setContentPane(mainPanel);
        setSize(900, 500);
        setTitle("Quiz Game");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        questionLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        timeProgressBar.setBorder(BorderFactory.createEmptyBorder(0, 10, 20, 0));
        timeProgressBar.setMinimum(0);
        timeProgressBar.setMaximum(10);

        currentQuestionOnTotalQuestion.setSize(30, 30);

        int buttonIndex = 1;
        for (JToggleButton button : buttons) {
            int finalButtonIndex = buttonIndex;
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    presentationModel.setUserAnswerIndex(finalButtonIndex);
                    System.out.println("User select:" + finalButtonIndex);
                }
            });
            buttonIndex++;
        }

        updateState();
        timer.addActionListener(new ActionListener() {
            int counter = 10;

            @SneakyThrows
            @Override
            public void actionPerformed(ActionEvent e) {
                counter--;
                timeProgressBar.setValue(counter);
                if (counter == -1) {
                    updateStateWhenOutOfTime();
                    final boolean isUserAnswerCorrect = presentationModel.checkUserAnswer();
                    if (isUserAnswerCorrect) {
                        presentationModel.increaseCorrectAnswerCounter();
                    }
                }
                if (counter == -4) {
                    counter = 10;
                    presentationModel.toNextQuestion();
                    if (presentationModel.getCurrentQuestionIndex() < presentationModel.getQuestionList().size()) {
                        resetState();
                        updateState();
                    } else {
                        timer.stop();
                        // TODO: to next screen
                    }
                }
            }
        });
        timer.start();

    }

    public void updateState() {
        this.questionLabel.setText(htmlToText(presentationModel.question()));
        this.pointLabel.setText(String.valueOf(presentationModel.getCorrectAnswerCounter() * 100) + " diem");
        this.currentQuestionOnTotalQuestion.setText(presentationModel.getCurrentQuestionIndex() + 1 + "/" + 10);

        // get random correct answer
        presentationModel.updateCorrectAnswerIndex();
        System.out.println("Correct answer index :" + presentationModel.getCorrectAnswerIndex());
        switch (this.presentationModel.getCorrectAnswerIndex()) {
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
            case 4: {
                this.answer4.setText(htmlToText(presentationModel.correctAnswer()));
                break;
            }
        }
        for (String wrongAnswer : presentationModel.incorrectAnswer()) {
            for (JToggleButton button : buttons) {
                if (button.getText().isEmpty()) {
                    button.setText(htmlToText(wrongAnswer));
                    break;
                }
            }
        }
    }

    public void updateStateWhenOutOfTime() {
//        for (JToggleButton button : buttons) {
//            button.setSelected(false);
//        }
        buttonGroup.clearSelection();

        if (presentationModel.getUserAnswerIndex() != presentationModel.getCorrectAnswerIndex()) {
            switch (this.presentationModel.getUserAnswerIndex()) {
                case 1: {
                    this.answer1.setBackground(Color.red);
                    break;
                }
                case 2: {
                    this.answer2.setBackground(Color.red);
                    break;
                }
                case 3: {
                    this.answer3.setBackground(Color.red);
                    break;
                }
                case 4: {
                    this.answer4.setBackground(Color.red);
                    break;
                }

            }
        }
        switch (this.presentationModel.getCorrectAnswerIndex()) {
            case 1: {
                this.answer1.setBackground(Color.green);
                break;
            }
            case 2: {
                this.answer2.setBackground(Color.green);
                break;
            }
            case 3: {
                this.answer3.setBackground(Color.green);
                break;
            }
            default: {
                this.answer4.setBackground(Color.green);
                break;
            }
        }
    }

    public void resetState() {
        // reset userAnswer to 0;
        this.presentationModel.setUserAnswerIndex(0);
        // reset to initial button
        buttonGroup.clearSelection();
        for (JToggleButton button : buttons) {
            button.setText("");
            button.setBackground(Color.WHITE);
        }
    }

    private @NotNull String htmlToText(String html) {
        return Jsoup.parse(html).text();
    }
}
