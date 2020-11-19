package com.example.demospeech;

import android.util.Log;

import com.example.demospeech.googlecloudtts.AudioConfig;
import com.example.demospeech.googlecloudtts.EAudioEncoding;
import com.example.demospeech.googlecloudtts.GoogleCloudAPIConfig;
import com.example.demospeech.googlecloudtts.GoogleCloudVoice;
import com.example.demospeech.googlecloudtts.VoiceCollection;
import com.example.demospeech.googlecloudtts.VoiceList;
import com.example.demospeech.modol.AndroidTTSAdapter;
import com.example.demospeech.modol.GoogleCloudTTSAdapter;
import com.example.demospeech.modol.SpeechManager;
import com.example.demospeech.modol.android.AndroidVoice;

import java.util.Locale;
/**
 * Author: Changemyminds.
 * Date: 2019/6/23.
 * Description:
 * Reference:
 */
public class MainPresenter implements MainContract.IPresenter, VoiceList.IVoiceListener {
    private static final String TAG = "MainPresenter";

    private MainContract.IView mView;

    private GoogleCloudAPIConfig mApiConfig = new GoogleCloudAPIConfig("AIzaSyDri0QAoSoG-j8YBHMqAR-8rg4P3EuoU0I");

    private VoiceCollection mVoiceCollection;

    private SpeechManager mSpeechManager;
    private GoogleCloudTTSAdapter mGoogleCloudTTSAdapter;

    public MainPresenter(MainContract.IView view) {
        // ioc
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void onCreate() {
        mSpeechManager = new SpeechManager();

        mGoogleCloudTTSAdapter = new GoogleCloudTTSAdapter(mApiConfig);
        mGoogleCloudTTSAdapter.addVoiceListener(this);
        loadGoogleCloudTTS();

        // set ConcreteDecorator
        mSpeechManager.setSpeech(mGoogleCloudTTSAdapter);
    }

    @Override
    public void onDestroy() {
        disposeSpeak();
    }

    private void initGoogleCloudTTSVoice() {
        String languageCode = "vi-VN";
        String name = "vi-VN-Wavenet-D";
        float pitch = ((float) (mView.getProgressPitch() - 2000) / 100);
        float speakRate = ((float) (mView.getProgressSpeakRate() + 25) / 100);

        mGoogleCloudTTSAdapter.setGoogleCloudVoice(new GoogleCloudVoice(languageCode, name))
                .setAudioConfig(new AudioConfig.Builder()
                        .addAudioEncoding(EAudioEncoding.MP3)
                        .addSpeakingRate(speakRate)
                        .addPitch(pitch)
                        .build()
                );
    }

    @Override
    public void onResponse(String jsonText) {
        Log.d(TAG, "Load voice json string " + jsonText);
    }

    @Override
    public void onReceive(VoiceCollection collection) {
        mVoiceCollection = collection;
        mView.invoke(new Runnable() {
            @Override
            public void run() {
            }
        });
    }

    @Override
    public void onFailure(String error) {

    }


    public void startSpeak(String text) {
        mSpeechManager.stopSpeak();
        if (mVoiceCollection == null || mVoiceCollection.size() == 0) {
            return;
        }

        initGoogleCloudTTSVoice();
        mSpeechManager.startSpeak(text);
    }

    public void stopSpeak() {
        mSpeechManager.stopSpeak();
    }

    public void resumeSpeak() {
        mSpeechManager.resume();
    }

    public void pauseSpeak() {
        mSpeechManager.pause();
    }

    public void disposeSpeak() {
        mSpeechManager.dispose();
        mSpeechManager = null;
    }

    @Override
    public void loadGoogleCloudTTS() {
        mGoogleCloudTTSAdapter.loadVoiceList();
    }

    @Override
    public void initAndroidTTS() {
        AndroidTTSAdapter androidTTSAdapter = new AndroidTTSAdapter(mView.getContext());
        AndroidVoice androidVoice = new AndroidVoice.Builder()
                .addLanguage(Locale.ENGLISH)
                .addPitch(1.0f)
                .addSpeakingRate(1.0f)
                .build();
        androidTTSAdapter.setAndroidVoice(androidVoice);

        // set the next handler
        SpeechManager androidTTSManager = new SpeechManager();
        androidTTSManager.setSpeech(androidTTSAdapter);
        mSpeechManager.setSupervisor(androidTTSManager);
    }
}



