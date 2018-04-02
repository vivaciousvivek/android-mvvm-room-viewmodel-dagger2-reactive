package com.techfirebase.android.mvvmroomviewmodeldagger2reactive.ui.word;

/**
 * Created by VIVEK KUMAR SINGH on 3/27/2018.
 * <p>
 * <p>This interface is used for Saparation of Concern of and screen navigation
 */
public interface WordNavigator {
    void handleError(Throwable throwable);

    void openMainActivity();
}
