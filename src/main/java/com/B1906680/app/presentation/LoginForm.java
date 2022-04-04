package com.B1906680.app.presentation;

import com.B1906680.app.presentation_model.LoginPresentationModel;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.inject.Inject;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


public class LoginForm extends JFrame {
    private JPanel mainPanel;
    private JTextField email;
    private JPasswordField password;
    private JButton dangNhapButton;
    private JButton dangKyButton;
    private final LoginPresentationModel presentationModel;

    //    private final LoginPresentationModel presentationModel= new LoginPresentationModel();
    @Inject
    public LoginForm(LoginPresentationModel presentationModel) {
        this.presentationModel = presentationModel;

    }

    public void initState() {
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setContentPane(mainPanel);
        setSize(600, 400);
        setTitle("Quiz Game");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        // frame appear middle of the screen
        setLocationRelativeTo(null);
        setVisible(true);
        dangKyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dangKyButtonClicked();
            }
        });
        dangNhapButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dangNhapButtonClicked();
            }
        });
    }

    private void dangKyButtonClicked() {
        String successOrError = this.presentationModel.signUpWithEmailAndPassword(this.email.getText(), String.valueOf(this.password.getPassword()));
        if (successOrError.equals("success")) {
            JOptionPane.showMessageDialog(this, "Sign up successfully", "Sign up", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JsonObject jsonObject = JsonParser.parseString(successOrError).getAsJsonObject();
            JOptionPane.showMessageDialog(this, jsonObject.get("error_code"), "Sign up", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void dangNhapButtonClicked() {
        String successOrError = this.presentationModel.singInWithEmailAndPassword(this.email.getText(), String.valueOf(this.password.getPassword()));
        if (successOrError.equals("success")) {
            try {
                new GameForm().initState();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Wrong email or password", "Sign in", JOptionPane.INFORMATION_MESSAGE);
        }
        dispose();
    }
}
