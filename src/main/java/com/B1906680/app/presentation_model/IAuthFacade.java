package com.B1906680.app.presentation_model;

public interface IAuthFacade {
    public String signUpWithEmailAndPassword(String email,String password);

    public String singInWithEmailAndPassword(String email,String password);
}
