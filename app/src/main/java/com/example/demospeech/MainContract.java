package com.example.demospeech;

import android.content.Context;
/**
 * Author: Changemyminds.
 * Date: 2020/6/21.
 * Description:
 * Reference:
 */
public interface MainContract {
    interface IView {

        int getProgressPitch();

        int getProgressSpeakRate();
        void invoke(Runnable runnable);
        void setPresenter(IPresenter presenter);

        Context getContext();
    }

    interface IPresenter {
        void onCreate();

        void onDestroy();

        void startSpeak(String text);

        void stopSpeak();

        void pauseSpeak();

        void resumeSpeak();

        void disposeSpeak();

        void initAndroidTTS();

        void loadGoogleCloudTTS();
    }
}
