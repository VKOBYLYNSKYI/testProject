package com.example.testandroid.fragments;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.testandroid.MainActivity;
import com.example.testandroid.R;
import com.example.testandroid.presenter.DataPresenter;
import com.example.testandroid.presenter.IDataPresenter;
import com.example.testandroid.view.IDataView;
import com.google.android.material.snackbar.Snackbar;

import java.lang.annotation.Documented;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;


/**
* This is the main fragment, shows the user a WebView or Game, it depends on the server side.
 *
 */
public class MainFragment extends Fragment implements IDataView {

    @BindView(R.id.web)     WebView web;
    @BindView(R.id.progress)    ProgressBar progress;
    IDataPresenter  mIDataPresenter;
    SharedPreferences   sPref;

    String  SAVED_RESULT = "saved_result";

    @SuppressLint("LongLogTag")
    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.w("MainFragment/onCreateView", "Success");

        return inflater.inflate(R.layout.fragment_main, container, false);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("LongLogTag")
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.w("MainFragment/onViewCreated", "Success");

        mIDataPresenter = new DataPresenter(this);
        ButterKnife.bind(this, view);

        if(MainActivity.res == null) {
            mIDataPresenter.startLoading();
        } else {
            setWebSetting(web);
        }

    }

    @SuppressLint("SetJavaScriptEnabled")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setWebSetting(WebView web){
        CookieManager.getInstance().setAcceptThirdPartyCookies(web, true);
        web.requestFocus();
        WebSettings webSettings = web.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSettings.setAppCacheEnabled(true);
        webSettings.setLightTouchEnabled(true);
        web.loadUrl("http://www.nvtc.ee/e-oppe/Ija/index.html");
        web.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) { }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        Log.w("MainFragment/onStart", "Success");
    }

    void saveData(boolean result) {
        sPref = getActivity().getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putBoolean(SAVED_RESULT, result);
        ed.commit();
    }

    Boolean loadData() {
        sPref = getActivity().getPreferences(MODE_PRIVATE);
        Boolean res = sPref.getBoolean(SAVED_RESULT, false);
        return res;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("LongLogTag")
    @Override
    public void onLoadingData(Boolean resultLoadingData) {
        Log.w("MainFragment/onLoadingData", "Success");
        Snackbar.make(getView(), "OK", Snackbar.LENGTH_LONG).show();

        //Save information if no errors appear
        if(resultLoadingData != null) {
            saveData(resultLoadingData);
        }

        if (resultLoadingData == null) {
            Boolean res = loadData();
            if(res){
                setWebSetting(web);
            } else {
                Navigation.findNavController(getView()).navigate(R.id.SecondFragment);
            }
        } else if(resultLoadingData) {
            setWebSetting(web);
        } else {
            Navigation.findNavController(getView()).navigate(R.id.SecondFragment);
        }

    }

    @Override
    public void showError() {
        Log.w("MainFragment/showError", "Success");
        Snackbar.make(getView(), "An error has occurred", Snackbar.LENGTH_LONG).show();
    }

    @SuppressLint("LongLogTag")
    @Override
    public void showLoading() {
        progress.setVisibility(View.VISIBLE);
        Log.w("MainFragment/showLoading", "Success");
    }

    @SuppressLint("LongLogTag")
    @Override
    public void hideLoading() {
        progress.setVisibility(View.GONE);
        Log.w("MainFragment/hideLoading", "Success");
    }

}