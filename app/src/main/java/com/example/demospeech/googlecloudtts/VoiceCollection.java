package com.example.demospeech.googlecloudtts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Author: Changemyminds.
 * Date: 2018/6/24.
 * Description:
 * Reference:
 */
public class VoiceCollection {
    private HashMap<String, List<GoogleCloudVoice>> hashMap = new HashMap<>();

    public VoiceCollection() {
    }

    public void add(String language, GoogleCloudVoice gcpVoice) {
        if (hashMap.get(language) == null) {
            List<GoogleCloudVoice> list = new ArrayList<>();
            list.add(gcpVoice);
            hashMap.put(language, list);
            return;
        }

        List<GoogleCloudVoice> list = hashMap.get(language);
        list.add(gcpVoice);
    }


    public String[] getNames(String language) {
        if (language == null || language.length() == 0 || hashMap.get(language) == null) {
            return null;
        }

        List<String> names = new ArrayList<>();
        List<GoogleCloudVoice> gcpVoices = hashMap.get(language);
        for (GoogleCloudVoice gcpVoice : gcpVoices) {
            names.add(gcpVoice.getName());
        }

        return names.toArray(new String[names.size()]);
    }

    public List<GoogleCloudVoice> getGCPVoices(String language) {
        if (hashMap.get(language) == null) {
            return null;
        }

        return hashMap.get(language);
    }

    public GoogleCloudVoice getGCPVoice(String language, String name) {
        if (language == null || language.length() == 0 ||
                name == null || name.length() == 0 || hashMap.get(language) == null) {
            return null;
        }

        List<GoogleCloudVoice> gcpVoices = hashMap.get(language);
        for (GoogleCloudVoice gcpVoice : gcpVoices) {
            if (gcpVoice.getName().compareTo(name) == 0) {
                return gcpVoice;
            }
        }

        return null;
    }

    public void clear() {
        for (HashMap.Entry<String, List<GoogleCloudVoice>> entry : hashMap.entrySet()) {
            List<GoogleCloudVoice> gcpVoices = entry.getValue();
            gcpVoices.clear();
        }

        hashMap.clear();
    }

    public int size() {
        return hashMap.size();
    }


}
