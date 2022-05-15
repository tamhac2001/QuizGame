package com.B1906680.app.presentation;

import com.B1906680.app.presentation_model.LoginPresentationModel;
import com.B1906680.app.utils.AppComponent;
import com.B1906680.app.utils.DaggerAppComponent;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.swing.*;


public class LoginForm extends JFrame {
    private JPanel mainPanel;
    private JTextField email;
    private JPasswordField password;
    private JButton dangNhapButton;
    private JButton dangKyButton;
    private final LoginPresentationModel presentationModel;
    private final AppComponent appComponent;

    public LoginForm(LoginPresentationModel presentationModel, AppComponent appComponent) {
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
        dangKyButton.addActionListener(e -> dangKyButtonClicked());
        dangNhapButton.addActionListener(e -> dangNhapButtonClicked());
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
            GameForm gameForm = new GameForm(appComponent.buildGamePresentationModel(),appComponent);
            gameForm.initState();
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Wrong email or password", "Sign in", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
