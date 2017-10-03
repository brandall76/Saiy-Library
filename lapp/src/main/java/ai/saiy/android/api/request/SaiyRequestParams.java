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

package ai.saiy.android.api.request;

import android.net.Uri;
import android.support.annotation.NonNull;

import java.util.Locale;
import java.util.UUID;

import ai.saiy.android.api.Defaults;
import ai.saiy.android.api.attributes.Gender;
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

/**
 * Class to fine tune your Text to Speech and Voice Recognition request. Please be aware, it is not
 * necessary to do this every time you make a request and instead you can set your preferred defaults
 * in the {@link Defaults} class and use the alternative Constructor in the {@link SaiyRequest}
 * class, which will automagically read them.
 * <p>
 * Created by benrandall76@gmail.com on 24/02/2016.
 */
public class SaiyRequestParams {

    public static final String SILENCE = "silence";
    public static final String ID_UNKNOWN = "id_unknown";

    private String utterance = SILENCE;
    private String requestId = UUID.randomUUID().toString().replaceAll("-", "");

    private Defaults.ACTION action = Defaults.ACTION.SPEAK_ONLY;
    private Defaults.TTS providerTTS = Defaults.TTS.LOCAL;
    private Defaults.VR providerVR = Defaults.VR.NATIVE;
    private Defaults.LanguageModel languageModel = Defaults.LanguageModel.LOCAL;

    private TTSLanguageNuance ttsLanguageNuance = TTSLanguageNuance.FRENCH_AUDREY_ML;
    private VRLanguageNuance vrLanguageNuance = VRLanguageNuance.ENGLISH_US;
    private VRLanguageGoogle vrLanguageGoogle = VRLanguageGoogle.ENGLISH_UNITED_STATES;
    private NLULanguageNuance nluLanguageNuance = NLULanguageNuance.English_US;
    private TTSLanguageLocal ttsLanguageLocal = new TTSLanguageLocal(Locale.getDefault());
    private VRLanguageMicrosoft vrLanguageMicrosoft = VRLanguageMicrosoft.ENGLISH_US;
    private VRLanguageRemote vrLanguageRemote = new VRLanguageRemote(Locale.getDefault());
    private VRLanguageIBM vrLanguageIBM = VRLanguageIBM.ENGLISH_US;
    private VRLanguageWit vrLanguageWit = VRLanguageWit.ENGLISH;
    private NLULanguageWit nluLanguageWit = NLULanguageWit.ENGLISH;
    private NLULanguageAPIAI nluLanguageAPIAI = NLULanguageAPIAI.ENGLISH;
    private NLULanguageMicrosoft nluLanguageMicrosoft = NLULanguageMicrosoft.ENGLISH_US;
    private VRLanguageNative vrLanguageNative = new VRLanguageNative((Locale.getDefault()));

    private String GOOGLE_CHROMIUM_API_KEY = "_your_api_key_here";
    private String GOOGLE_CLOUD_ACCESS_TOKEN = "_your_access_token_here";
    private long GOOGLE_CLOUD_ACCESS_EXPIRY = -1L;

    private String NUANCE_APP_KEY = "_your_api_key_here";
    private String NUANCE_CONTEXT_TAG = "_your_context_tag_here";
    private Uri NUANCE_SERVER_URI = Uri.parse("_your_server_uri_here");
    private Uri NUANCE_SERVER_URI_NLU = Uri.parse("_your_server_uri_here");

    private String API_AI_ACCESS_TOKEN = "_your_access_token_here";

    private String OXFORD_KEY_1 = "_your_api_key_here";
    private String OXFORD_KEY_2 = "_your_api_key_here";
    private String LUIS_APP_ID = "_your_app_id_here";
    private String LUIS_SUBSCRIPTION_ID = "_your_subscription_id_here";

    private Uri REMOTE_SERVER_URI = Uri.parse("_your_server_uri_here");
    private String REMOTE_ACCESS_TOKEN = "_your_remote_access_token_here";

    private String IBM_SERVICE_USER_NAME = "_your_user_name_here";
    private String IBM_SERVICE_PASSWORD = "_your_password_here";

    private String WIT_SERVER_ACCESS_TOKEN = "_your_access_token_here";


    public SaiyRequestParams() {
    }

    /**
     * Set the request id to identify the callback. This isn't a requirement, but useful if you are
     * sending multiple types of requests that you may wish to handle differently, at different times.
     *
     * @param requestId the id that will uniquely identify the request
     */
    public void setRequestId(@NonNull final String requestId) {
        this.requestId = requestId;
    }

    /**
     * Get the current request id
     *
     * @return the requestId
     */
    public String getRequestId() {
        return requestId;
    }

    /**
     * Set the remote action
     *
     * @param action the required {@link Defaults.ACTION}
     */
    public void setAction(@NonNull final Defaults.ACTION action) {
        this.action = action;
    }

    /**
     * Get the remote action
     *
     * @return the remote {@link Defaults.ACTION}
     */
    public Defaults.ACTION getAction() {
        return action;
    }

    /**
     * Get the Google Chromium API key
     *
     * @return the GOOGLE_CHROMIUM_API_KEY
     */
    public String getGOOGLE_CHROMIUM_API_KEY() {
        return GOOGLE_CHROMIUM_API_KEY;
    }

    /**
     * Set your Google Chromium API key
     *
     * @param GOOGLE_CHROMIUM_API_KEY the Google Chromium API key
     */
    public void setGOOGLE_CHROMIUM_API_KEY(@NonNull final String GOOGLE_CHROMIUM_API_KEY) {
        this.GOOGLE_CHROMIUM_API_KEY = GOOGLE_CHROMIUM_API_KEY;
    }

    /**
     * Get the Google Cloud access token
     *
     * @return the GOOGLE_CLOUD_ACCESS_TOKEN
     */
    public String getGOOGLE_CLOUD_ACCESS_TOKEN() {
        return GOOGLE_CLOUD_ACCESS_TOKEN;
    }

    /**
     * Set your Google Cloud access token
     *
     * @param GOOGLE_CLOUD_ACCESS_TOKEN the Google Cloud access token
     */
    public void setGOOGLE_CLOUD_ACCESS_TOKEN(@NonNull final String GOOGLE_CLOUD_ACCESS_TOKEN) {
        this.GOOGLE_CLOUD_ACCESS_TOKEN = GOOGLE_CLOUD_ACCESS_TOKEN;
    }

    /**
     * Get the Google Cloud access token expiry
     *
     * @return the GOOGLE_CLOUD_ACCESS_EXPIRY
     */
    public long getGOOGLE_CLOUD_ACCESS_EXPIRY() {
        return GOOGLE_CLOUD_ACCESS_EXPIRY;
    }

    /**
     * Set your Google Cloud access token expiry
     *
     * @param GOOGLE_CLOUD_ACCESS_EXPIRY the Google Cloud access token expiry
     */
    public void setGOOGLE_CLOUD_ACCESS_EXPIRY(final long GOOGLE_CLOUD_ACCESS_EXPIRY) {
        this.GOOGLE_CLOUD_ACCESS_EXPIRY = GOOGLE_CLOUD_ACCESS_EXPIRY;
    }

    /**
     * Get the Nuance APP key
     *
     * @return the NUANCE_APP_KEY
     */
    public String getNUANCE_APP_KEY() {
        return NUANCE_APP_KEY;
    }

    /**
     * Set your Nuance APP key
     *
     * @param NUANCE_APP_KEY the Nuance APP key
     */
    public void setNUANCE_APP_KEY(@NonNull final String NUANCE_APP_KEY) {
        this.NUANCE_APP_KEY = NUANCE_APP_KEY;
    }

    /**
     * Get the Nuance Context Tag
     *
     * @return the NUANCE_CONTEXT_TAG
     */
    public String getNUANCE_CONTEXT_TAG() {
        return NUANCE_CONTEXT_TAG;
    }

    /**
     * Set your Nuance Context Tag
     *
     * @param NUANCE_CONTEXT_TAG the Nuance Context Tag
     */
    public void setNUANCE_CONTEXT_TAG(@NonNull final String NUANCE_CONTEXT_TAG) {
        this.NUANCE_CONTEXT_TAG = NUANCE_CONTEXT_TAG;
    }

    /**
     * Get the Nuance Server Uri
     *
     * @return the NUANCE_SERVER_URI
     */
    public Uri getNUANCE_SERVER_URI() {
        return NUANCE_SERVER_URI;
    }

    /**
     * Set your Nuance Server Uri
     *
     * @param NUANCE_SERVER_URI the Nuance Context Tag
     */
    public void setNUANCE_SERVER_URI(@NonNull final Uri NUANCE_SERVER_URI) {
        this.NUANCE_SERVER_URI = NUANCE_SERVER_URI;
    }

    /**
     * Get the Nuance Server NLU Uri
     *
     * @return the NUANCE_SERVER_URI_NLU
     */
    public Uri getNUANCE_SERVER_URI_NLU() {
        return NUANCE_SERVER_URI_NLU;
    }

    /**
     * Set your Nuance Server NLU Uri
     *
     * @param NUANCE_SERVER_URI_NLU the Nuance Context Tag
     */
    public void setNUANCE_SERVER_URI_NLU(@NonNull final Uri NUANCE_SERVER_URI_NLU) {
        this.NUANCE_SERVER_URI_NLU = NUANCE_SERVER_URI_NLU;
    }

    /**
     * Get the IBM service password
     *
     * @return the IBM_SERVICE_PASSWORD
     */
    public String getIBM_SERVICE_PASSWORD() {
        return IBM_SERVICE_PASSWORD;
    }

    /**
     * Set your IBM service password
     *
     * @param IBM_SERVICE_PASSWORD the server password
     */
    public void setIBM_SERVICE_PASSWORD(@NonNull final String IBM_SERVICE_PASSWORD) {
        this.IBM_SERVICE_PASSWORD = IBM_SERVICE_PASSWORD;
    }

    /**
     * Get the IBM service user name
     *
     * @return the IBM_SERVICE_USER_NAME
     */
    public String getIBM_SERVICE_USER_NAME() {
        return IBM_SERVICE_USER_NAME;
    }

    /**
     * Set your IBM service user name
     *
     * @param IBM_SERVICE_USER_NAME the server user name
     */
    public void setIBM_SERVICE_USER_NAME(@NonNull final String IBM_SERVICE_USER_NAME) {
        this.IBM_SERVICE_USER_NAME = IBM_SERVICE_USER_NAME;
    }

    /**
     * Get the Wit server access token
     *
     * @return the WIT_SERVER_ACCESS_TOKEN
     */
    public String getWIT_SERVER_ACCESS_TOKEN() {
        return WIT_SERVER_ACCESS_TOKEN;
    }

    /**
     * Set your Wit server access token
     *
     * @param WIT_SERVER_ACCESS_TOKEN the server access token
     */
    public void setWIT_SERVER_ACCESS_TOKEN(@NonNull final String WIT_SERVER_ACCESS_TOKEN) {
        this.WIT_SERVER_ACCESS_TOKEN = WIT_SERVER_ACCESS_TOKEN;
    }

    /**
     * Set the utterance to speak
     *
     * @param words the utterance to speak
     */
    public void setUtterance(@NonNull final String words) {
        this.utterance = words;
    }

    /**
     * Get the utterance to speak
     *
     * @return the utterance String
     */
    public String getUtterance() {
        return utterance;
    }

    /**
     * Set the remote Text to Speech provider
     *
     * @param provider the required {@link Defaults.TTS}
     */
    public void setTTSProvider(@NonNull final Defaults.TTS provider) {
        this.providerTTS = provider;
    }

    /**
     * Get the remote Text to Speech provider
     *
     * @return the remote {@link Defaults.TTS}
     */
    public Defaults.TTS getTTSProvider() {
        return providerTTS;
    }

    /**
     * Set the remote Voice Recognition provider
     *
     * @param provider the required {@link Defaults.VR}
     */
    public void setVRProvider(@NonNull final Defaults.VR provider) {
        this.providerVR = provider;
    }

    /**
     * Get the remote Voice Recognition provider
     *
     * @return the remote {@link Defaults.VR}
     */
    public Defaults.VR getVRProvider() {
        return providerVR;
    }

    /**
     * Set the Nuance voice recognition language
     * <p>
     * You should call {@link SaiyRequest#isVRLanguageNuanceAvailable(Locale)} before using this
     *
     * @param languageNuance the {@link VRLanguageNuance}
     */
    public void setVRLanguageNuance(@NonNull final VRLanguageNuance languageNuance) {
        this.vrLanguageNuance = languageNuance;
    }

    /**
     * Set the Nuance voice recognition language
     * <p>
     * You should call {@link SaiyRequest#isVRLanguageNuanceAvailable(Locale)} before using this
     *
     * @param userLocale the {@link Locale} required.
     */
    public void setVRLanguageNuance(@NonNull final Locale userLocale) {
        this.vrLanguageNuance = VRLanguageNuance.getLanguage(userLocale);
    }

    /**
     * Set the Nuance NLU recognition language
     * <p>
     * You should call {@link SaiyRequest#isNLULanguageNuanceAvailable(Locale)} before using this
     *
     * @param userLocale the {@link Locale} required.
     */
    public void setNLULanguageNuance(@NonNull final Locale userLocale) {
        this.nluLanguageNuance = NLULanguageNuance.getLanguage(userLocale);
    }

    /**
     * Set the Nuance NLU recognition language
     * <p>
     * You should call {@link SaiyRequest#isNLULanguageNuanceAvailable(Locale)} before using this
     *
     * @param nluLanguageNuance the {@link NLULanguageNuance} required.
     */
    public void setNLULanguageNuance(@NonNull final NLULanguageNuance nluLanguageNuance) {
        this.nluLanguageNuance = nluLanguageNuance;
    }

    /**
     * Get the Nuance NLU recognition language
     *
     * @return the {@link NLULanguageNuance}
     */
    public NLULanguageNuance getNLULanguageNuance() {
        return nluLanguageNuance;
    }

    /**
     * Get the Nuance voice recognition language
     *
     * @return the {@link VRLanguageNuance}
     */
    public VRLanguageNuance getVRLanguageNuance() {
        return vrLanguageNuance;
    }

    /**
     * Set the Nuance TTS Language
     * <p>
     * You should call {@link SaiyRequest#getTTSVoiceNuance(Locale, Gender)} before using this
     *
     * @param ttsLanguageNuance the {@link TTSLanguageNuance}
     */
    public void setTTSLanguageNuance(@NonNull final TTSLanguageNuance ttsLanguageNuance) {
        this.ttsLanguageNuance = ttsLanguageNuance;
    }

    /**
     * Set the Nuance TTS Language
     * <p>
     * You should call {@link SaiyRequest#isTTSLanguageNuanceAvailable(Locale)} before using this
     *
     * @param userLocale      the {@link Locale} required.
     * @param preferredGender the preferred voice {@link Gender}
     */
    public void setTTSLanguageNuance(@NonNull final Locale userLocale,
                                     @NonNull final Gender preferredGender) {
        this.ttsLanguageNuance = TTSLanguageNuance.getVoice(userLocale, preferredGender);
    }

    /**
     * Get the Nuance TTS Language
     *
     * @return the {@link TTSLanguageNuance}
     */
    public TTSLanguageNuance getTTSLanguageNuance() {
        return ttsLanguageNuance;
    }

    /**
     * Set the Google voice recognition language
     *
     * @param vrLanguageGoogle the {@link VRLanguageGoogle}
     */
    public void setVRLanguageGoogle(@NonNull final VRLanguageGoogle vrLanguageGoogle) {
        this.vrLanguageGoogle = vrLanguageGoogle;
    }

    /**
     * Set the Google voice recognition language
     *
     * @param userLocale the {@link Locale} required
     */
    public void setVRLanguageGoogle(@NonNull final Locale userLocale) {
        this.vrLanguageGoogle = VRLanguageGoogle.getLanguage(userLocale);
    }

    /**
     * Get the Google voice recognition language
     *
     * @return the {@link VRLanguageGoogle}
     */
    public VRLanguageGoogle getVRLanguageGoogle() {
        return vrLanguageGoogle;
    }

    /**
     * Set the local TTS Language
     *
     * @param userLocale the {@link Locale} required.
     */
    public void setTTSLanguageLocal(@NonNull final Locale userLocale) {
        this.ttsLanguageLocal = new TTSLanguageLocal(userLocale);
    }

    /**
     * Get the local TTS Language
     *
     * @return the {@link TTSLanguageLocal}
     */
    public TTSLanguageLocal getTTSLanguageLocale() {
        return ttsLanguageLocal;
    }

    /**
     * Get the default Language Model
     *
     * @return the {@link Defaults.LanguageModel}
     */
    public Defaults.LanguageModel getLanguageModel() {
        return languageModel;
    }

    /**
     * Set the default Language Model
     *
     * @param languageModel the {@link Defaults.LanguageModel}
     */
    public void setLanguageModel(@NonNull final Defaults.LanguageModel languageModel) {
        this.languageModel = languageModel;
    }

    /**
     * Get the API AI access token
     *
     * @return the access token
     */
    public String getAPI_AI_ACCESS_TOKEN() {
        return API_AI_ACCESS_TOKEN;
    }

    /**
     * Set the API AI access token
     *
     * @param API_AI_ACCESS_TOKEN the access token
     */
    public void setAPI_AI_ACCESS_TOKEN(@NonNull final String API_AI_ACCESS_TOKEN) {
        this.API_AI_ACCESS_TOKEN = API_AI_ACCESS_TOKEN;
    }

    /**
     * Get the LUIS app id
     *
     * @return the LUIS app id
     */
    public String getLUIS_APP_ID() {
        return LUIS_APP_ID;
    }

    /**
     * Set the LUIS app id
     *
     * @param LUIS_APP_ID the app id
     */
    public void setLUIS_APP_ID(@NonNull final String LUIS_APP_ID) {
        this.LUIS_APP_ID = LUIS_APP_ID;
    }

    /**
     * Get the LUIS subscription id
     *
     * @return the LUIS subscription id
     */
    public String getLUIS_SUBSCRIPTION_ID() {
        return LUIS_SUBSCRIPTION_ID;
    }

    /**
     * Set the LUIS subscription id
     *
     * @param LUIS_SUBSCRIPTION_ID the LUIS subscription id
     */
    public void setLUIS_SUBSCRIPTION_ID(@NonNull final String LUIS_SUBSCRIPTION_ID) {
        this.LUIS_SUBSCRIPTION_ID = LUIS_SUBSCRIPTION_ID;
    }

    /**
     * Get the Oxford key 1
     *
     * @return the Oxford key 1
     */
    public String getOXFORD_KEY_1() {
        return OXFORD_KEY_1;
    }

    /**
     * Set the Oxford key 1
     *
     * @param OXFORD_KEY_1 the key 1
     */
    public void setOXFORD_KEY_1(@NonNull final String OXFORD_KEY_1) {
        this.OXFORD_KEY_1 = OXFORD_KEY_1;
    }

    /**
     * Get the Oxford key 2
     *
     * @return the Oxford key 2
     */
    public String getOXFORD_KEY_2() {
        return OXFORD_KEY_2;
    }

    /**
     * Set the Oxford key 2
     *
     * @param OXFORD_KEY_2 the key 2
     */
    public void setOXFORD_KEY_2(@NonNull final String OXFORD_KEY_2) {
        this.OXFORD_KEY_2 = OXFORD_KEY_2;
    }

    /**
     * Get the remote access token
     *
     * @return the remote access token
     */
    public String getREMOTE_ACCESS_TOKEN() {
        return REMOTE_ACCESS_TOKEN;
    }

    /**
     * Set the remote access token
     *
     * @param REMOTE_ACCESS_TOKEN the remote access token
     */
    public void setREMOTE_ACCESS_TOKEN(@NonNull final String REMOTE_ACCESS_TOKEN) {
        this.REMOTE_ACCESS_TOKEN = REMOTE_ACCESS_TOKEN;
    }

    /**
     * Get the remote service Uri
     *
     * @return the REMOTE_SERVER_URI
     */
    public Uri getREMOTE_SERVER_URI() {
        return REMOTE_SERVER_URI;
    }

    /**
     * Set your remote service Uri
     *
     * @param REMOTE_SERVER_URI the remote server Uri
     */
    public void setREMOTE_SERVER_URI(@NonNull final Uri REMOTE_SERVER_URI) {
        this.REMOTE_SERVER_URI = REMOTE_SERVER_URI;
    }

    public VRLanguageRemote getVRLanguageRemote() {
        return vrLanguageRemote;
    }

    /**
     * Set the remote voice recognition language
     *
     * @param vrLanguageRemote the {@link VRLanguageRemote}
     */
    public void setVRLanguageRemote(@NonNull final VRLanguageRemote vrLanguageRemote) {
        this.vrLanguageRemote = vrLanguageRemote;
    }

    /**
     * Set the remote voice recognition language
     *
     * @param userLocale the {@link Locale} required
     */
    public void setVRLanguageRemote(@NonNull final Locale userLocale) {
        this.vrLanguageRemote = new VRLanguageRemote(userLocale);
    }

    /**
     * Get the Microsoft voice recognition language
     *
     * @return the {@link VRLanguageMicrosoft}
     */
    public VRLanguageMicrosoft getVRLanguageMicrosoft() {
        return vrLanguageMicrosoft;
    }

    /**
     * Set the Microsoft voice recognition language
     * <p>
     * You should call {@link SaiyRequest#isVRLanguageMicrosoftAvailable(Locale)} before using this
     *
     * @param vrLanguageMicrosoft the {@link VRLanguageMicrosoft}
     */
    public void setVRLanguageMicrosoft(@NonNull final VRLanguageMicrosoft vrLanguageMicrosoft) {
        this.vrLanguageMicrosoft = vrLanguageMicrosoft;
    }

    /**
     * Set the Microsoft voice recognition language
     * <p>
     * You should call {@link SaiyRequest#isVRLanguageMicrosoftAvailable(Locale)} before using this
     *
     * @param userLocale the {@link Locale} required.
     */
    public void setVRLanguageMicrosoft(@NonNull final Locale userLocale) {
        this.vrLanguageMicrosoft = VRLanguageMicrosoft.getLanguage(userLocale);
    }

    /**
     * Get the Wit voice recognition language
     *
     * @return the {@link VRLanguageWit}
     */
    public VRLanguageWit getVRLanguageWit() {
        return vrLanguageWit;
    }

    /**
     * Set the Wit voice recognition language
     * <p>
     * You should call {@link SaiyRequest#isVRLanguageWitAvailable(Locale)} before using this
     *
     * @param vrLanguageWit the {@link VRLanguageWit}
     */
    public void setVRLanguageWit(@NonNull final VRLanguageWit vrLanguageWit) {
        this.vrLanguageWit = vrLanguageWit;
    }

    /**
     * Set the Wit voice recognition language
     * <p>
     * You should call {@link SaiyRequest#isVRLanguageWitAvailable(Locale)} before using this
     *
     * @param userLocale the {@link Locale} required.
     */
    public void setVRLanguageWit(@NonNull final Locale userLocale) {
        this.vrLanguageWit = VRLanguageWit.getLanguage(userLocale);
    }

    /**
     * Get the IBM voice recognition language
     *
     * @return the {@link VRLanguageIBM}
     */
    public VRLanguageIBM getVRLanguageIBM() {
        return vrLanguageIBM;
    }

    /**
     * Set the IBM voice recognition language
     * <p>
     * You should call {@link SaiyRequest#isVRLanguageIBMAvailable(Locale)} before using this
     *
     * @param vrLanguageIBM the {@link VRLanguageWit}
     */
    public void setVRLanguageIBM(@NonNull final VRLanguageIBM vrLanguageIBM) {
        this.vrLanguageIBM = vrLanguageIBM;
    }

    /**
     * Set the IBM voice recognition language
     * <p>
     * You should call {@link SaiyRequest#isVRLanguageIBMAvailable(Locale)} before using this
     *
     * @param userLocale the {@link Locale} required.
     */
    public void setVRLanguageIBM(@NonNull final Locale userLocale) {
        this.vrLanguageIBM = VRLanguageIBM.getLanguage(userLocale);
    }


    /**
     * Set the Wit NLU recognition language
     * <p>
     * You should call {@link SaiyRequest#isNLULanguageWitAvailable(Locale)} before using this
     *
     * @param nluLanguageWit the {@link NLULanguageWit} required.
     */
    public void setNLULanguageWit(@NonNull final NLULanguageWit nluLanguageWit) {
        this.nluLanguageWit = nluLanguageWit;
    }

    /**
     * Set the Wit NLU recognition language
     * <p>
     * You should call {@link SaiyRequest#isNLULanguageWitAvailable(Locale)} before using this
     *
     * @param userLocale the {@link Locale} required.
     */
    public void setNLULanguageWit(@NonNull final Locale userLocale) {
        this.nluLanguageWit = NLULanguageWit.getLanguage(userLocale);
    }

    /**
     * Get the Wit NLU recognition language
     *
     * @return the {@link NLULanguageWit}
     */
    public NLULanguageWit getNLULanguageWit() {
        return nluLanguageWit;
    }


    /**
     * Set the API.ai NLU recognition language
     * <p>
     * You should call {@link SaiyRequest#isNLULanguageAPIAIAvailable(Locale)} before using this
     *
     * @param nluLanguageAPIAI the {@link NLULanguageAPIAI} required.
     */
    public void setNLULanguageAPIAI(@NonNull final NLULanguageAPIAI nluLanguageAPIAI) {
        this.nluLanguageAPIAI = nluLanguageAPIAI;
    }

    /**
     * Set the API.ai NLU recognition language
     * <p>
     * You should call {@link SaiyRequest#isNLULanguageAPIAIAvailable(Locale)} before using this
     *
     * @param userLocale the {@link Locale} required.
     */
    public void setNLULanguageAPIAI(@NonNull final Locale userLocale) {
        this.nluLanguageAPIAI = NLULanguageAPIAI.getLanguage(userLocale);
    }

    /**
     * Get the API.ai NLU recognition language
     *
     * @return the {@link NLULanguageAPIAI}
     */
    public NLULanguageAPIAI getNLULanguageAPIAI() {
        return nluLanguageAPIAI;
    }


    /**
     * Set the Microsoft NLU recognition language
     * <p>
     * You should call {@link SaiyRequest#isNLULanguageMicrosoftAvailable(Locale)} before using this
     *
     * @param nluLanguageMicrosoft the {@link NLULanguageMicrosoft} required.
     */
    public void setNLULanguageMicrosoft(@NonNull final NLULanguageMicrosoft nluLanguageMicrosoft) {
        this.nluLanguageMicrosoft = nluLanguageMicrosoft;
    }

    /**
     * Set the Microsoft NLU recognition language
     * <p>
     * You should call {@link SaiyRequest#isNLULanguageMicrosoftAvailable(Locale)} before using this
     *
     * @param userLocale the {@link Locale} required.
     */
    public void setNLULanguageMicrosoft(@NonNull final Locale userLocale) {
        this.nluLanguageMicrosoft = NLULanguageMicrosoft.getLanguage(userLocale);
    }

    /**
     * Get the Microsoft NLU recognition language
     *
     * @return the {@link NLULanguageMicrosoft}
     */
    public NLULanguageMicrosoft getNLULanguageMicrosoft() {
        return nluLanguageMicrosoft;
    }

    /**
     * Set the Native voice recognition language
     *
     * @param vrLanguageNative the {@link VRLanguageNative}
     */
    public void setVRLanguageNative(@NonNull final VRLanguageNative vrLanguageNative) {
        this.vrLanguageNative = vrLanguageNative;
    }

    /**
     * Set the Native voice recognition language
     *
     * @param userLocale the {@link Locale} required
     */
    public void setVRLanguageNative(@NonNull final Locale userLocale) {
        this.vrLanguageNative = new VRLanguageNative(userLocale);
    }

    /**
     * Get the Native voice recognition language
     *
     * @return the {@link VRLanguageNative}
     */
    public VRLanguageNative getVRLanguageNative() {
        return vrLanguageNative;
    }
}
