/*
 * Copyright (c) 2016. Saiy™ Ltd. All Rights Reserved.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package ai.saiy.android.api;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import ai.saiy.android.api.language.nlu.NLULanguageAPIAI;
import ai.saiy.android.api.language.nlu.NLULanguageMicrosoft;
import ai.saiy.android.api.language.nlu.NLULanguageNuance;
import ai.saiy.android.api.language.nlu.NLULanguageWit;
import ai.saiy.android.api.language.tts.TTSLanguageLocal;
import ai.saiy.android.api.language.tts.TTSLanguageNuance;
import ai.saiy.android.api.language.vr.VRLanguageGoogle;
import ai.saiy.android.api.language.vr.VRLanguageIBM;
import ai.saiy.android.api.language.vr.VRLanguageMicrosoft;
import ai.saiy.android.api.language.vr.VRLanguageNative;
import ai.saiy.android.api.language.vr.VRLanguageNuance;
import ai.saiy.android.api.language.vr.VRLanguageRemote;
import ai.saiy.android.api.language.vr.VRLanguageWit;
import ai.saiy.android.api.utils.LocalUtils;

/**
 * Created by benrandall76@gmail.com on 24/02/2016.
 */
public class RequestParcel implements Parcelable {

    public static final String PARCEL_KEY = "remote_request_parcel";

    private Defaults.ACTION action;
    private Defaults.TTS providerTTS;
    private Defaults.VR providerVR;
    private Defaults.LanguageModel languageModel;
    private String utterance;
    private String requestId;
    private String GOOGLE_CHROMIUM_API_KEY;
    private String GOOGLE_CLOUD_ACCESS_TOKEN;
    private long GOOGLE_CLOUD_ACCESS_EXPIRY;
    private String NUANCE_APP_KEY;
    private String NUANCE_SERVER_URI;
    private String NUANCE_SERVER_URI_NLU;
    private String NUANCE_CONTEXT_TAG;
    private String API_AI_CLIENT_ACCESS_TOKEN;
    private String OXFORD_KEY_1;
    private String OXFORD_KEY_2;
    private String LUIS_APP_ID;
    private String LUIS_SUBSCRIPTION_ID;
    private String REMOTE_SERVER_URI;
    private String REMOTE_ACCESS_TOKEN;
    private String WIT_SERVER_ACCESS_TOKEN;
    private String IBM_SERVICE_USER_NAME;
    private String IBM_SERVICE_PASSWORD;
    private VRLanguageNuance vrLanguageNuance;
    private TTSLanguageNuance ttsLanguageNuance;
    private VRLanguageGoogle vrLanguageGoogle;
    private TTSLanguageLocal ttsLanguageLocal;
    private VRLanguageMicrosoft vrLanguageMicrosoft;
    private VRLanguageRemote vrLanguageRemote;
    private VRLanguageWit vrLanguageWit;
    private VRLanguageIBM vrLanguageIBM;
    private NLULanguageMicrosoft nluLanguageMicrosoft;
    private NLULanguageWit nluLanguageWit;
    private NLULanguageAPIAI nluLanguageAPIAI;
    private NLULanguageNuance nluLanguageNuance;
    private VRLanguageNative vrLanguageNative;

    public static final Creator<RequestParcel> CREATOR = new
            Creator<RequestParcel>() {
                public RequestParcel createFromParcel(@NonNull final Parcel in) {
                    return new RequestParcel(in);
                }

                public RequestParcel[] newArray(int size) {
                    return new RequestParcel[size];
                }
            };

    public RequestParcel() {
    }

    private RequestParcel(@NonNull final Parcel in) {
        readFromParcel(in);
    }

    public RequestParcel(@NonNull final Defaults.ACTION action,
                         @NonNull final String utterance,
                         @NonNull final String requestId,
                         @NonNull final Defaults.TTS providerTTS,
                         @NonNull final Defaults.VR providerVR,
                         @NonNull final Defaults.LanguageModel languageModel,
                         @NonNull final String GOOGLE_CHROMIUM_API_KEY,
                         @NonNull final String GOOGLE_CLOUD_ACCESS_TOKEN,
                         final long GOOGLE_CLOUD_ACCESS_EXPIRY,
                         @NonNull final String NUANCE_APP_KEY,
                         @NonNull final Uri NUANCE_SERVER_URI,
                         @NonNull final Uri NUANCE_SERVER_URI_NLU,
                         @NonNull final String NUANCE_CONTEXT_TAG,
                         @NonNull final String API_AI_CLIENT_ACCESS_TOKEN,
                         @NonNull final String OXFORD_KEY_1,
                         @NonNull final String OXFORD_KEY_2,
                         @NonNull final String LUIS_APP_ID,
                         @NonNull final String LUIS_SUBSCRIPTION_ID,
                         @NonNull final Uri REMOTE_SERVER_URI,
                         @NonNull final String REMOTE_ACCESS_TOKEN,
                         @NonNull final String WIT_SERVER_ACCESS_TOKEN,
                         @NonNull final String IBM_SERVICE_USER_NAME,
                         @NonNull final String IBM_SERVICE_PASSWORD,
                         @NonNull final VRLanguageNuance vrLanguageNuance,
                         @NonNull final TTSLanguageNuance ttsLanguageNuance,
                         @NonNull final VRLanguageGoogle vrLanguageGoogle,
                         @NonNull final TTSLanguageLocal ttsLanguageLocal,
                         @NonNull final VRLanguageMicrosoft vrLanguageMicrosoft,
                         @NonNull final VRLanguageRemote vrLanguageRemote,
                         @NonNull final VRLanguageWit vrLanguageWit,
                         @NonNull final VRLanguageIBM vrLanguageIBM,
                         @NonNull final NLULanguageMicrosoft nluLanguageMicrosoft,
                         @NonNull final NLULanguageWit nluLanguageWit,
                         @NonNull final NLULanguageAPIAI nluLanguageAPIAI,
                         @NonNull final NLULanguageNuance nluLanguageNuance,
                         @NonNull final VRLanguageNative vrLanguageNative) {
        this.action = action;
        this.providerTTS = providerTTS;
        this.providerVR = providerVR;
        this.languageModel = languageModel;
        this.utterance = utterance;
        this.requestId = requestId;
        this.GOOGLE_CHROMIUM_API_KEY = GOOGLE_CHROMIUM_API_KEY;
        this.GOOGLE_CLOUD_ACCESS_TOKEN = GOOGLE_CLOUD_ACCESS_TOKEN;
        this.GOOGLE_CLOUD_ACCESS_EXPIRY = GOOGLE_CLOUD_ACCESS_EXPIRY;
        this.NUANCE_APP_KEY = NUANCE_APP_KEY;
        this.NUANCE_SERVER_URI = NUANCE_SERVER_URI.toString();
        this.NUANCE_SERVER_URI_NLU = NUANCE_SERVER_URI_NLU.toString();
        this.NUANCE_CONTEXT_TAG = NUANCE_CONTEXT_TAG;
        this.API_AI_CLIENT_ACCESS_TOKEN = API_AI_CLIENT_ACCESS_TOKEN;
        this.OXFORD_KEY_1 = OXFORD_KEY_1;
        this.OXFORD_KEY_2 = OXFORD_KEY_2;
        this.LUIS_APP_ID = LUIS_APP_ID;
        this.LUIS_SUBSCRIPTION_ID = LUIS_SUBSCRIPTION_ID;
        this.REMOTE_SERVER_URI = REMOTE_SERVER_URI.toString();
        this.REMOTE_ACCESS_TOKEN = REMOTE_ACCESS_TOKEN;
        this.WIT_SERVER_ACCESS_TOKEN = WIT_SERVER_ACCESS_TOKEN;
        this.IBM_SERVICE_USER_NAME = IBM_SERVICE_USER_NAME;
        this.IBM_SERVICE_PASSWORD = IBM_SERVICE_PASSWORD;
        this.vrLanguageNuance = vrLanguageNuance;
        this.ttsLanguageNuance = ttsLanguageNuance;
        this.vrLanguageGoogle = vrLanguageGoogle;
        this.ttsLanguageLocal = ttsLanguageLocal;
        this.vrLanguageMicrosoft = vrLanguageMicrosoft;
        this.vrLanguageRemote = vrLanguageRemote;
        this.vrLanguageWit = vrLanguageWit;
        this.vrLanguageIBM = vrLanguageIBM;
        this.nluLanguageMicrosoft = nluLanguageMicrosoft;
        this.nluLanguageWit = nluLanguageWit;
        this.nluLanguageAPIAI = nluLanguageAPIAI;
        this.nluLanguageNuance = nluLanguageNuance;
        this.vrLanguageNative = vrLanguageNative;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull final Parcel out, int flags) {
        out.writeSerializable(this.action);
        out.writeSerializable(this.providerTTS);
        out.writeSerializable(this.providerVR);
        out.writeSerializable(this.languageModel);
        out.writeString(this.utterance);
        out.writeString(this.requestId);
        out.writeString(this.GOOGLE_CHROMIUM_API_KEY);
        out.writeString(this.GOOGLE_CLOUD_ACCESS_TOKEN);
        out.writeLong(this.GOOGLE_CLOUD_ACCESS_EXPIRY);
        out.writeString(this.NUANCE_APP_KEY);
        out.writeString(this.NUANCE_SERVER_URI);
        out.writeString(this.NUANCE_SERVER_URI_NLU);
        out.writeString(this.NUANCE_CONTEXT_TAG);
        out.writeString(this.API_AI_CLIENT_ACCESS_TOKEN);
        out.writeString(this.OXFORD_KEY_1);
        out.writeString(this.OXFORD_KEY_2);
        out.writeString(this.LUIS_APP_ID);
        out.writeString(this.LUIS_SUBSCRIPTION_ID);
        out.writeString(this.REMOTE_SERVER_URI);
        out.writeString(this.REMOTE_ACCESS_TOKEN);
        out.writeString(this.WIT_SERVER_ACCESS_TOKEN);
        out.writeString(this.IBM_SERVICE_USER_NAME);
        out.writeString(this.IBM_SERVICE_PASSWORD);
        out.writeSerializable(this.vrLanguageNuance);
        out.writeSerializable(this.ttsLanguageNuance);
        out.writeSerializable(this.vrLanguageGoogle);
        out.writeString(this.ttsLanguageLocal.getLocale().toString());
        out.writeSerializable(this.vrLanguageMicrosoft);
        out.writeString(this.vrLanguageRemote.getLocale().toString());
        out.writeSerializable(this.vrLanguageWit);
        out.writeSerializable(this.vrLanguageIBM);
        out.writeSerializable(this.nluLanguageMicrosoft);
        out.writeSerializable(this.nluLanguageWit);
        out.writeSerializable(this.nluLanguageAPIAI);
        out.writeSerializable(this.nluLanguageNuance);
        out.writeString(this.vrLanguageNative.getLocale().toString());
    }

    public void readFromParcel(@NonNull final Parcel in) {
        this.action = (Defaults.ACTION) in.readSerializable();
        this.providerTTS = (Defaults.TTS) in.readSerializable();
        this.providerVR = (Defaults.VR) in.readSerializable();
        this.languageModel = (Defaults.LanguageModel) in.readSerializable();
        this.utterance = in.readString();
        this.requestId = in.readString();
        this.GOOGLE_CHROMIUM_API_KEY = in.readString();
        this.GOOGLE_CLOUD_ACCESS_TOKEN = in.readString();
        this.GOOGLE_CLOUD_ACCESS_EXPIRY = in.readLong();
        this.NUANCE_APP_KEY = in.readString();
        this.NUANCE_SERVER_URI = in.readString();
        this.NUANCE_SERVER_URI_NLU = in.readString();
        this.NUANCE_CONTEXT_TAG = in.readString();
        this.API_AI_CLIENT_ACCESS_TOKEN = in.readString();
        this.OXFORD_KEY_1 = in.readString();
        this.OXFORD_KEY_2 = in.readString();
        this.LUIS_APP_ID = in.readString();
        this.LUIS_SUBSCRIPTION_ID = in.readString();
        this.REMOTE_SERVER_URI = in.readString();
        this.REMOTE_ACCESS_TOKEN = in.readString();
        this.WIT_SERVER_ACCESS_TOKEN = in.readString();
        this.IBM_SERVICE_USER_NAME = in.readString();
        this.IBM_SERVICE_PASSWORD = in.readString();
        this.vrLanguageNuance = (VRLanguageNuance) in.readSerializable();
        this.ttsLanguageNuance = (TTSLanguageNuance) in.readSerializable();
        this.vrLanguageGoogle = (VRLanguageGoogle) in.readSerializable();
        this.ttsLanguageLocal = new TTSLanguageLocal(LocalUtils.stringToLocale(in.readString()));
        this.vrLanguageMicrosoft = (VRLanguageMicrosoft) in.readSerializable();
        this.vrLanguageRemote = new VRLanguageRemote(LocalUtils.stringToLocale(in.readString()));
        this.vrLanguageWit = (VRLanguageWit) in.readSerializable();
        this.vrLanguageIBM = (VRLanguageIBM) in.readSerializable();
        this.nluLanguageMicrosoft = (NLULanguageMicrosoft) in.readSerializable();
        this.nluLanguageWit = (NLULanguageWit) in.readSerializable();
        this.nluLanguageAPIAI = (NLULanguageAPIAI) in.readSerializable();
        this.nluLanguageNuance = (NLULanguageNuance) in.readSerializable();
        this.vrLanguageNative = new VRLanguageNative(LocalUtils.stringToLocale(in.readString()));
    }


    public Defaults.ACTION getAction() {
        return action;
    }

    public void setAction(@NonNull final Defaults.ACTION action) {
        this.action = action;
    }

    public Defaults.TTS getProviderTTS() {
        return providerTTS;
    }

    public void setProviderTTS(@NonNull final Defaults.TTS providerTTS) {
        this.providerTTS = providerTTS;
    }

    public Defaults.VR getProviderVR() {
        return providerVR;
    }

    public void setProviderVR(@NonNull final Defaults.VR providerVR) {
        this.providerVR = providerVR;
    }

    public Defaults.LanguageModel getLanguageModel() {
        return languageModel;
    }

    public void setLanguageModel(@NonNull final Defaults.LanguageModel languageModel) {
        this.languageModel = languageModel;
    }

    public String getUtterance() {
        return utterance;
    }

    public void setUtterance(@NonNull final String utterance) {
        this.utterance = utterance;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(@NonNull final String requestId) {
        this.requestId = requestId;
    }

    public String getGOOGLE_CHROMIUM_API_KEY() {
        return GOOGLE_CHROMIUM_API_KEY;
    }

    public void setGOOGLE_CHROMIUM_API_KEY(@NonNull final String GOOGLE_CHROMIUM_API_KEY) {
        this.GOOGLE_CHROMIUM_API_KEY = GOOGLE_CHROMIUM_API_KEY;
    }

    public String getGOOGLE_CLOUD_ACCESS_TOKEN() {
        return GOOGLE_CLOUD_ACCESS_TOKEN;
    }

    public void setGOOGLE_CLOUD_ACCESS_TOKEN(@NonNull final String GOOGLE_CLOUD_ACCESS_TOKEN) {
        this.GOOGLE_CLOUD_ACCESS_TOKEN = GOOGLE_CLOUD_ACCESS_TOKEN;
    }

    public long getGOOGLE_CLOUD_ACCESS_EXPIRY() {
        return GOOGLE_CLOUD_ACCESS_EXPIRY;
    }

    public void setGOOGLE_CLOUD_ACCESS_EXPIRY(final long GOOGLE_CLOUD_ACCESS_EXPIRY) {
        this.GOOGLE_CLOUD_ACCESS_EXPIRY = GOOGLE_CLOUD_ACCESS_EXPIRY;
    }

    public String getNUANCE_APP_KEY() {
        return NUANCE_APP_KEY;
    }

    public void setNUANCE_APP_KEY(@NonNull final String NUANCE_APP_KEY) {
        this.NUANCE_APP_KEY = NUANCE_APP_KEY;
    }

    public Uri getNUANCE_SERVER_URI() {
        return Uri.parse(NUANCE_SERVER_URI);
    }

    public void setNUANCE_SERVER_URI(@NonNull final String NUANCE_SERVER_URI) {
        this.NUANCE_SERVER_URI = NUANCE_SERVER_URI;
    }

    public Uri getNUANCE_SERVER_URI_NLU() {
        return Uri.parse(NUANCE_SERVER_URI_NLU);
    }

    public void setNUANCE_SERVER_URI_NLU(final String NUANCE_SERVER_URI_NLU) {
        this.NUANCE_SERVER_URI_NLU = NUANCE_SERVER_URI_NLU;
    }

    public String getNUANCE_CONTEXT_TAG() {
        return NUANCE_CONTEXT_TAG;
    }

    public void setNUANCE_CONTEXT_TAG(@NonNull final String NUANCE_CONTEXT_TAG) {
        this.NUANCE_CONTEXT_TAG = NUANCE_CONTEXT_TAG;
    }

    public String getAPI_AI_CLIENT_ACCESS_TOKEN() {
        return API_AI_CLIENT_ACCESS_TOKEN;
    }

    public void setAPI_AI_CLIENT_ACCESS_TOKEN(@NonNull final String API_AI_CLIENT_ACCESS_TOKEN) {
        this.API_AI_CLIENT_ACCESS_TOKEN = API_AI_CLIENT_ACCESS_TOKEN;
    }

    public String getLUIS_APP_ID() {
        return LUIS_APP_ID;
    }

    public void setLUIS_APP_ID(@NonNull final String LUIS_APP_ID) {
        this.LUIS_APP_ID = LUIS_APP_ID;
    }

    public String getLUIS_SUBSCRIPTION_ID() {
        return LUIS_SUBSCRIPTION_ID;
    }

    public void setLUIS_SUBSCRIPTION_ID(@NonNull final String LUIS_SUBSCRIPTION_ID) {
        this.LUIS_SUBSCRIPTION_ID = LUIS_SUBSCRIPTION_ID;
    }

    public String getOXFORD_KEY_1() {
        return OXFORD_KEY_1;
    }

    public void setOXFORD_KEY_1(@NonNull final String OXFORD_KEY_1) {
        this.OXFORD_KEY_1 = OXFORD_KEY_1;
    }

    public String getOXFORD_KEY_2() {
        return OXFORD_KEY_2;
    }

    public void setOXFORD_KEY_2(@NonNull final String OXFORD_KEY_2) {
        this.OXFORD_KEY_2 = OXFORD_KEY_2;
    }

    public String getREMOTE_ACCESS_TOKEN() {
        return REMOTE_ACCESS_TOKEN;
    }

    public void setREMOTE_ACCESS_TOKEN(@NonNull final String REMOTE_ACCESS_TOKEN) {
        this.REMOTE_ACCESS_TOKEN = REMOTE_ACCESS_TOKEN;
    }

    public Uri getREMOTE_SERVER_URI() {
        return Uri.parse(REMOTE_SERVER_URI);
    }

    public void setREMOTE_SERVER_URI(@NonNull final String REMOTE_SERVER_URI) {
        this.REMOTE_SERVER_URI = REMOTE_SERVER_URI;
    }

    public String getIBM_SERVICE_PASSWORD() {
        return IBM_SERVICE_PASSWORD;
    }

    public void setIBM_SERVICE_PASSWORD(final String IBM_SERVICE_PASSWORD) {
        this.IBM_SERVICE_PASSWORD = IBM_SERVICE_PASSWORD;
    }

    public String getIBM_SERVICE_USER_NAME() {
        return IBM_SERVICE_USER_NAME;
    }

    public void setIBM_SERVICE_USER_NAME(final String IBM_SERVICE_USER_NAME) {
        this.IBM_SERVICE_USER_NAME = IBM_SERVICE_USER_NAME;
    }

    public String getWIT_SERVER_ACCESS_TOKEN() {
        return WIT_SERVER_ACCESS_TOKEN;
    }

    public void setWIT_SERVER_ACCESS_TOKEN(final String WIT_SERVER_ACCESS_TOKEN) {
        this.WIT_SERVER_ACCESS_TOKEN = WIT_SERVER_ACCESS_TOKEN;
    }

    public TTSLanguageNuance getTTSLanguageNuance() {
        return ttsLanguageNuance;
    }

    public void setTTSLanguageNuance(@NonNull final TTSLanguageNuance ttsLanguageNuance) {
        this.ttsLanguageNuance = ttsLanguageNuance;
    }

    public VRLanguageGoogle getVRLanguageGoogle() {
        return vrLanguageGoogle;
    }

    public void setVRLanguageGoogle(final VRLanguageGoogle vrLanguageGoogle) {
        this.vrLanguageGoogle = vrLanguageGoogle;
    }

    public VRLanguageNuance getVRLanguageNuance() {
        return vrLanguageNuance;
    }

    public void setVRLanguageNuance(final VRLanguageNuance vrLanguageNuance) {
        this.vrLanguageNuance = vrLanguageNuance;
    }

    public TTSLanguageLocal getTTSLanguageLocal() {
        return ttsLanguageLocal;
    }

    public void setTTSLanguageLocal(@NonNull final TTSLanguageLocal ttsLanguageLocal) {
        this.ttsLanguageLocal = ttsLanguageLocal;
    }

    public VRLanguageRemote getVRLanguageRemote() {
        return vrLanguageRemote;
    }

    public void setVRLanguageRemote(@NonNull final VRLanguageRemote vrLanguageRemote) {
        this.vrLanguageRemote = vrLanguageRemote;
    }

    public VRLanguageMicrosoft getVRLanguageMicrosoft() {
        return vrLanguageMicrosoft;
    }

    public void setVRLanguageMicrosoft(@NonNull final VRLanguageMicrosoft vrLanguageMicrosoft) {
        this.vrLanguageMicrosoft = vrLanguageMicrosoft;
    }

    public VRLanguageWit getVRLanguageWit() {
        return vrLanguageWit;
    }

    public void setVRLanguageWit(@NonNull final VRLanguageWit vrLanguageWit) {
        this.vrLanguageWit = vrLanguageWit;
    }

    public VRLanguageIBM getVRLanguageIBM() {
        return vrLanguageIBM;
    }

    public void setVRLanguageIBM(@NonNull final VRLanguageIBM vrLanguageIBM) {
        this.vrLanguageIBM = vrLanguageIBM;
    }

    public NLULanguageWit getNLULanguageWit() {
        return nluLanguageWit;
    }

    public void setNLULanguageWit(@NonNull final NLULanguageWit nluLanguageWit) {
        this.nluLanguageWit = nluLanguageWit;
    }

    public NLULanguageNuance getNLULanguageNuance() {
        return nluLanguageNuance;
    }

    public void setNLULanguageNuance(@NonNull final NLULanguageNuance nluLanguageNuance) {
        this.nluLanguageNuance = nluLanguageNuance;
    }

    public NLULanguageAPIAI getNLULanguageAPIAI() {
        return nluLanguageAPIAI;
    }

    public void setNLULanguageAPIAI(@NonNull final NLULanguageAPIAI nluLanguageAPIAI) {
        this.nluLanguageAPIAI = nluLanguageAPIAI;
    }

    public NLULanguageMicrosoft getNLULanguageMicrosoft() {
        return nluLanguageMicrosoft;
    }

    public void setNLULanguageMicrosoft(@NonNull final NLULanguageMicrosoft nluLanguageMicrosoft) {
        this.nluLanguageMicrosoft = nluLanguageMicrosoft;
    }

    public VRLanguageNative getVRLanguageNative() {
        return vrLanguageNative;
    }

    public void setVRLanguageNative(final VRLanguageNative vrLanguageNative) {
        this.vrLanguageNative = vrLanguageNative;
    }
}
