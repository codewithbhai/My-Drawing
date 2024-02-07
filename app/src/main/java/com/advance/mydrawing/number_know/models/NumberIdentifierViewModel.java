package com.advance.mydrawing.number_know.models;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * Created by zulfikar on 28 Dec 2023 at 11:31.
 */
public class NumberIdentifierViewModel extends ViewModel {
    private MutableLiveData<String> textLiveData = new MutableLiveData<>();

    public MutableLiveData<String> getTextLiveData() {
        return textLiveData;
    }

    public void updateText(String newText) {
        textLiveData.setValue(newText);
    }
}
