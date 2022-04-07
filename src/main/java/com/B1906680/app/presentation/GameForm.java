package com.B1906680.app.presentation;

import com.B1906680.app.model.Question;
import com.B1906680.app.presentation_model.GamePresentationModel;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import sun.applet.Main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
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

        int counter = 1;
        for (JToggleButton button : buttons) {
            int finalCounter = counter;
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    presentationModel.setUserAnswerIndex(finalCounter);
                }
            });
            counter++;
        }


//        questionLabel.add(currentQuestionOnTotalQuestion);
        updateState();
        timer.addActionListener(new ActionListener() {
            int counter = 10;

            @SneakyThrows
            @Override
            public void actionPerformed(ActionEvent e) {
                counter--;
                timeProgressBar.setValue(counter);
                if (counter == 0) {
                    counter = 10;
                    final boolean isUserAnswerCorrect = presentationModel.checkUserAnswer();
                    if (isUserAnswerCorrect) {
                        presentationModel.increaseCorrectAnswerCounter();
                    } else {

                    }
//                    Thread.sleep(100);
                    presentationModel.toNextQuestion();
                    if (presentationModel.getCurrentQuestionIndex() < presentationModel.getQuestionList().size()) {
                        updateState();
                    } else {
                        timer.stop();
                    }
                }
            }
        });
        timer.start();

    }

    public void updateState() {
        this.questionLabel.setText(htmlToText(presentationModel.question()));
        this.pointLabel.setText(String.valueOf(presentationModel.getCorrectAnswerCounter()*100) + " diem");
        this.currentQuestionOnTotalQuestion.setText(presentationModel.getCurrentQuestionIndex() + 1 + "/" + 10);
        // reset userAnswer to 0;
        this.presentationModel.setUserAnswerIndex(0);
        // reset the button value to empty
        for (JToggleButton button : buttons) {
            button.setText("");
            button.setSelected(false);
        }
        // random correct answer
        presentationModel.updateCorrectAnswerIndex();
        switch (this.presentationModel.getCurrentQuestionIndex()) {
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
            for (JToggleButton button : buttons) {
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
