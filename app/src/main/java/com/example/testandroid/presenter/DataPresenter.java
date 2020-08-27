package com.example.testandroid.presenter;

import android.os.Handler;
import android.os.Looper;

import com.example.testandroid.model.DataModel;
import com.example.testandroid.view.IDataView;

public class DataPresenter implements IDataPresenter {

    IDataView mIDataView;
    Handler handler;
    DataModel mDataModel;

    public DataPresenter(IDataView mIDataView){
        this.mIDataView = mIDataView;
        mDataModel = new DataModel();
        handler = new Handler(Looper.getMainLooper());
    }


    @Override
    public void onClearText() {

    }

    @Override
    public void startLoading() {

        mIDataView.showLoading();

        Boolean res = null;
        
        try {
            res = mDataModel.receiveAllProducts();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Boolean finalRes = res;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mIDataView.onLoadingData(finalRes);
                mIDataView.hideLoading();
            }
        }, 3000);

    }
}
