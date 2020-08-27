package com.example.testandroid.view;

public interface IDataView extends LoadingView {
    void onLoadingData(Boolean resultLoadingData);
    void showError();
}
