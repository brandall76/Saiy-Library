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

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Process;
import android.speech.RecognitionService;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.Pair;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Set;

import ai.saiy.android.api.Defaults;
import ai.saiy.android.api.RequestParcel;
import ai.saiy.android.api.attributes.Gender;
import ai.saiy.android.api.language.nlu.NLULanguageAPIAI;
import ai.saiy.android.api.language.nlu.NLULanguageNuance;
import ai.saiy.android.api.language.tts.TTSLanguageNuance;
import ai.saiy.android.api.language.vr.VRLanguageGoogle;
import ai.saiy.android.api.language.vr.VRLanguageIBM;
import ai.saiy.android.api.language.vr.VRLanguageMicrosoft;
import ai.saiy.android.api.language.vr.VRLanguageNuance;
import ai.saiy.android.api.language.vr.VRLanguageWit;
import ai.saiy.android.api.remote.RemoteError;
import ai.saiy.android.api.remote.RemoteServiceConnector;
import ai.saiy.android.api.remote.SaiyListener;
import ai.saiy.android.api.utils.LocalUtils;


/**
 * Helper Class to prepare our request to the {@link RemoteServiceConnector}
 * <p>
 * A weak reference is held to the Context used. Although this should not be an Activity Context and
 * Saiy should handle the garbage collection sufficiently well, let's not take any chances?
 * <p>
 * Created by benrandall76@gmail.com on 08/02/2016.
 */
public class SaiyRequest {

    private static final boolean DEBUG = Defaults.getLogging();
    private static final String CLS_NAME = SaiyRequest.class.getSimpleName();

    public static final String CONTROL_SAIY = "ai.saiy.android.permission.CONTROL_SAIY";
    public static final int SAIY_VR_REQUEST_CODE = 69;
    public static final String REMOTE_CLS_NAME = "ai.saiy.android.service.SelfAware";
    public static final String REMOTE_PKG_NAME = "ai.saiy.android";
    public static final String NUANCE_NLU_HOST = "nmsps.dev.nuance.com";
    private static final String GOOGLE_RECOGNIZER_PACKAGE = "com.google.android.googlequicksearchbox";

    private static final String _YOUR_ = "_your_";

    private final ArrayList<Locale> ttsLocales = new ArrayList<>();
    private final ArrayList<Locale> vrLocales = new ArrayList<>();

    private volatile TextToSpeech tts;

    private SaiyRequestParams params;

    private final RemoteServiceConnector sc;
    private final WeakReference<Context> wContext;
    private final Bundle bundle = new Bundle();

    /**
     * Constructor.
     * <p>
     * The Constructor to use to initialise Saiy. This Constructor can be used on a background thread
     * or outside of an {@link Activity}
     * <p>
     * If you set @param getLanguages to true, it will allow a short-lived {@link TextToSpeech}
     * object to be instantiated, in order to query the {@link TextToSpeech}
     * {@link Locale} that are installed on the user's device. This shouldn't be too much of
     * a performance issue, as the {@link TextToSpeech} object is initialised on a background thread.
     * <p>
     * If you decide to set the @param getLanguages to false, then no {@link TextToSpeech} object
     * will be initialised and further calls to {@link #isTTSLanguageLocalAvailable(Locale)}
     * will always return false, as Saiy would have been unable to verify the case either way.
     *
     * @param mContext     the application context is held as a {@link WeakReference}
     * @param listener     the {@link SaiyListener} interface that will receive callbacks
     * @param params       the {@link SaiyRequestParams}
     * @param getLanguages true if you want to be able to query native TTS languages, false otherwise,
     *                     which will prevent a short-lived Text to Speech object being initialised
     */
    public SaiyRequest(@NonNull final Context mContext, @NonNull final SaiyListener listener,
                       @NonNull final SaiyRequestParams params, final boolean getLanguages) {
        this.wContext = new WeakReference<>(mContext.getApplicationContext());
        this.params = params;

        sc = new RemoteServiceConnector(this.wContext.get(), listener, this, bundle);
        sendBroadcast();

        if (getLanguages) {
            if (DEBUG) {
                Log.i(CLS_NAME, "Instantiating TTS Engine");
            }
            initTTS();
        }
    }

    /**
     * Constants for checking the availability of the Saiy API
     */
    public enum SaiyAvailability {

        ALL_GOOD,
        NOT_INSTALLED,
        NO_VR_PROVIDER,
        NO_TTS_ENGINE,
        MISSING_PERMISSION
    }

    /**
     * Initialise a short-lived Text to Speech object, in order to query the available languages.
     */
    private void initTTS() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                android.os.Process.setThreadPriority(Process.THREAD_PRIORITY_LESS_FAVORABLE);

                tts = new TextToSpeech(wContext.get(), new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(final int status) {

                        switch (status) {
                            case TextToSpeech.SUCCESS:
                                if (DEBUG) {
                                    Log.i(CLS_NAME, "TextToSpeech.SUCCESS");
                                }

                                try {

                                    if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                                        final Set<Locale> setLoc = tts.getAvailableLanguages();

                                        synchronized (ttsLocales) {
                                            ttsLocales.clear();
                                            ttsLocales.addAll(setLoc);

                                            if (DEBUG) {
                                                for (Locale loc : ttsLocales) {
                                                    Log.i(CLS_NAME, "ttsLocales: " + loc.toString());
                                                }
                                            }
                                        }
                                    } else {
                                        synchronized (ttsLocales) {
                                            ttsLocales.clear();

                                            final Locale[] locales = Locale.getAvailableLocales();
                                            for (final Locale loc : locales) {

                                                try {

                                                    switch (tts.isLanguageAvailable(loc)) {
                                                        case TextToSpeech.LANG_AVAILABLE:
                                                        case TextToSpeech.LANG_COUNTRY_AVAILABLE:
                                                        case TextToSpeech.LANG_COUNTRY_VAR_AVAILABLE:
                                                            ttsLocales.add(loc);
                                                            break;
                                                    }
                                                } catch (final IllegalArgumentException e) {
                                                    if (DEBUG) {
                                                        Log.e(CLS_NAME, "isLanguageAvailable: IllegalArgumentException: "
                                                                + loc.toString());
                                                    }
                                                }
                                            }

                                            if (DEBUG) {
                                                for (Locale loc : ttsLocales) {
                                                    Log.i(CLS_NAME, "ttsLocales: " + loc.toString());
                                                }
                                            }
                                        }
                                    }
                                } catch (final NullPointerException e) {
                                    if (DEBUG) {
                                        Log.e(CLS_NAME, "ttsLoc NullPointerException");
                                        e.printStackTrace();
                                    }
                                } catch (final Exception e) {
                                    if (DEBUG) {
                                        Log.e(CLS_NAME, "ttsLoc Exception");
                                        e.printStackTrace();
                                    }
                                } finally {
                                    releaseTTS();
                                }
                                break;
                            case TextToSpeech.ERROR:
                                if (DEBUG) {
                                    Log.w(CLS_NAME, "TextToSpeech.ERROR");
                                }
                                releaseTTS();
                                break;
                        }

                    }
                });
            }
        }).start();
    }

    /**
     * Release the Text to Speech object, handling try/catch for misbehaving engines
     */
    private void releaseTTS() {
        if (DEBUG) {
            Log.i(CLS_NAME, "releaseTTS");
        }

        try {
            if (tts != null) {
                tts.shutdown();
                tts = null;
            }
        } catch (final NullPointerException e) {
            if (DEBUG) {
                Log.e(CLS_NAME, "releaseTTS NullPointerException");
                e.printStackTrace();
            }
        } catch (final Exception e) {
            if (DEBUG) {
                Log.e(CLS_NAME, "releaseTTS Exception");
                e.printStackTrace();
            }
        }
    }

    /**
     * Send an {@link Context#sendOrderedBroadcast(Intent, String, BroadcastReceiver, Handler, int, String, Bundle)}
     * to get the available Voice Recognition languages from the default provider, if there is one.
     */
    private void sendBroadcast() {

        final Intent recognitionIntent = new Intent();
        recognitionIntent.setAction(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        final PackageManager pm = wContext.get().getPackageManager();
        final List<ResolveInfo> list = pm.queryIntentActivities(recognitionIntent, PackageManager.GET_META_DATA);

        final int size = list.size();

        if (size > 0) {

            final Intent vrIntent = new Intent(RecognizerIntent.ACTION_GET_LANGUAGE_DETAILS);
            vrIntent.setPackage(GOOGLE_RECOGNIZER_PACKAGE);
            wContext.get().sendOrderedBroadcast(vrIntent, null, new BroadcastReceiver() {
                @Override
                public void onReceive(final Context context, final Intent intent) {
                    if (DEBUG) {
                        Log.i(CLS_NAME, "onReceive");
                    }

                    if (getResultCode() == SAIY_VR_REQUEST_CODE) {

                        if (intent != null) {
                            final Bundle bundle = getResultExtras(true);

                            if (bundle != null) {
                                if (bundle.containsKey(RecognizerIntent.EXTRA_SUPPORTED_LANGUAGES)) {
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            android.os.Process.setThreadPriority(Process.THREAD_PRIORITY_LESS_FAVORABLE);
                                            setVRLocales(bundle.getStringArrayList(RecognizerIntent.EXTRA_SUPPORTED_LANGUAGES));
                                        }
                                    }).start();
                                } else {
                                    if (DEBUG) {
                                        Log.w(CLS_NAME, "onReceive: EXTRA_SUPPORTED_LANGUAGES missing");
                                    }
                                }
                            } else {
                                if (DEBUG) {
                                    Log.w(CLS_NAME, "onReceive: Bundle null");
                                }
                            }
                        } else {
                            if (DEBUG) {
                                Log.w(CLS_NAME, "onReceive: Intent null");
                            }
                        }
                    } else {
                        if (DEBUG) {
                            Log.w(CLS_NAME, "onReceive: getResultCode() != SAIY_VR_REQUEST_CODE");
                        }
                    }
                }
            }, null, SAIY_VR_REQUEST_CODE, null, null);

        } else {
            if (DEBUG) {
                Log.w(CLS_NAME, "The user has no Voice Recognition Service available");
            }
        }
    }

    /**
     * Start the service connection
     */
    public void execute() {
        if (DEBUG) {
            Log.i(CLS_NAME, "execute");
        }

        if (prepareBundle()) {
            if (DEBUG) {
                Log.i(CLS_NAME, "execute: passed");
            }

            final RequestParcel parcel = new RequestParcel(
                    params.getAction(),
                    params.getUtterance(),
                    params.getRequestId(),
                    params.getTTSProvider(),
                    params.getVRProvider(),
                    params.getLanguageModel(),
                    params.getGOOGLE_CHROMIUM_API_KEY(),
                    params.getGOOGLE_CLOUD_ACCESS_TOKEN(),
                    params.getGOOGLE_CLOUD_ACCESS_EXPIRY(),
                    params.getNUANCE_APP_KEY(),
                    params.getNUANCE_SERVER_URI(),
                    params.getNUANCE_SERVER_URI_NLU(),
                    params.getNUANCE_CONTEXT_TAG(),
                    params.getAPI_AI_ACCESS_TOKEN(),
                    params.getOXFORD_KEY_1(),
                    params.getOXFORD_KEY_2(),
                    params.getLUIS_APP_ID(),
                    params.getLUIS_SUBSCRIPTION_ID(),
                    params.getREMOTE_SERVER_URI(),
                    params.getREMOTE_ACCESS_TOKEN(),
                    params.getWIT_SERVER_ACCESS_TOKEN(),
                    params.getIBM_SERVICE_USER_NAME(),
                    params.getIBM_SERVICE_PASSWORD(),
                    params.getVRLanguageNuance(),
                    params.getTTSLanguageNuance(),
                    params.getVRLanguageGoogle(),
                    params.getTTSLanguageLocale(),
                    params.getVRLanguageMicrosoft(),
                    params.getVRLanguageRemote(),
                    params.getVRLanguageWit(),
                    params.getVRLanguageIBM(),
                    params.getNLULanguageMicrosoft(),
                    params.getNLULanguageWit(),
                    params.getNLULanguageAPIAI(),
                    params.getNLULanguageNuance(),
                    params.getVRLanguageNative());

            bundle.putParcelable(RequestParcel.PARCEL_KEY, parcel);
            sc.createConnection();

        } else {
            if (DEBUG) {
                Log.e(CLS_NAME, "execute: failed verbose configuration checks");
            }
        }
    }

    /**
     * Set the {@link SaiyRequestParams} to be used by Saiy in the request.
     *
     * @param params the {@link SaiyRequestParams}
     */
    public void setParams(@NonNull final SaiyRequestParams params) {
        this.params = params;
    }

    private boolean prepareBundle() {
        if (DEBUG) {
            Log.i(CLS_NAME, "prepareBundle");
        }

        return checkAction();
    }

    /**
     * Get the required service intent
     *
     * @return the prepared intent
     */
    public Intent getRemoteIntent() {
        final Intent intent = new Intent();
        intent.setComponent(new ComponentName(SaiyRequest.REMOTE_PKG_NAME, SaiyRequest.REMOTE_CLS_NAME));
        intent.setAction(wContext.get().getPackageName());
        wContext.get().startService(intent);
        return intent;
    }

    private boolean checkAction() {
        if (DEBUG) {
            Log.i(CLS_NAME, "checkAction");
        }

        switch (params.getAction()) {

            case SPEAK_ONLY:
                if (DEBUG) {
                    Log.i(CLS_NAME, "checkAction: ACTION.SPEAK_ONLY");
                }
                break;
            case SPEAK_LISTEN:
                if (DEBUG) {
                    Log.i(CLS_NAME, "checkAction: ACTION.SPEAK_LISTEN");
                }
                break;
            default:
                if (DEBUG) {
                    Log.e(CLS_NAME, "checkAction: ACTION.UNKNOWN");
                    throw RemoteError.throwUnknownAction(wContext.get());
                } else {
                    return false;
                }
        }

        return checkProviderTTS();
    }

    private boolean checkProviderTTS() {
        if (DEBUG) {
            Log.i(CLS_NAME, "checkProviderTTS");
        }

        switch (params.getTTSProvider()) {

            case LOCAL:
                if (DEBUG) {
                    Log.i(CLS_NAME, "checkProviderTTS: Defaults.TTS.LOCAL");
                }
                break;
            case NETWORK_NUANCE:
                if (DEBUG) {
                    Log.i(CLS_NAME, "checkProviderTTS: Defaults.TTS.NUANCE");
                }
                break;
            default:
                if (DEBUG) {
                    Log.e(CLS_NAME, "checkProviderTTS: Defaults.TTS.UNKNOWN");
                    throw RemoteError.throwUnknownTTSProvider(wContext.get());
                } else {
                    return false;
                }
        }

        return checkVRProvider();
    }

    private boolean checkVRProvider() {
        if (DEBUG) {
            Log.i(CLS_NAME, "checkVRProvider");
        }

        switch (params.getLanguageModel()) {

            case NUANCE:
                if (DEBUG) {
                    Log.i(CLS_NAME, "checkVRProvider: " + Defaults.LanguageModel.NUANCE.name());
                }

                params.setVRProvider(Defaults.VR.NUANCE);
                break;
            case MICROSOFT:
                if (DEBUG) {
                    Log.i(CLS_NAME, "checkVRProvider: " + Defaults.LanguageModel.MICROSOFT.name());
                }

                params.setVRProvider(Defaults.VR.MICROSOFT);
                break;
            case API_AI:
                if (DEBUG) {
                    Log.i(CLS_NAME, "checkVRProvider: " + Defaults.LanguageModel.API_AI.name());
                }

                switch (params.getVRProvider()) {

                    case NATIVE:
                        if (DEBUG) {
                            Log.i(CLS_NAME, "checkVRProvider: " + Defaults.VR.NATIVE.name());
                        }

                        params.setVRProvider(Defaults.VR.NATIVE);
                        break;
                    case GOOGLE_CLOUD:
                        if (DEBUG) {
                            Log.i(CLS_NAME, "checkVRProvider: " + Defaults.VR.GOOGLE_CLOUD.name());
                        }

                        params.setVRProvider(Defaults.VR.GOOGLE_CLOUD);
                        break;
                    case GOOGLE_CHROMIUM:
                        if (DEBUG) {
                            Log.i(CLS_NAME, "checkVRProvider: " + Defaults.VR.GOOGLE_CHROMIUM.name());
                        }

                        params.setVRProvider(Defaults.VR.GOOGLE_CHROMIUM);
                        break;
                    case NUANCE:
                        if (DEBUG) {
                            Log.i(CLS_NAME, "checkVRProvider: " + Defaults.VR.NUANCE.name());
                        }

                        params.setVRProvider(Defaults.VR.NUANCE);
                        break;
                    case MICROSOFT:
                        if (DEBUG) {
                            Log.i(CLS_NAME, "checkVRProvider: " + Defaults.VR.MICROSOFT.name());
                        }

                        params.setVRProvider(Defaults.VR.MICROSOFT);
                        break;
                    case WIT:
                        if (DEBUG) {
                            Log.i(CLS_NAME, "checkVRProvider: " + Defaults.VR.WIT.name());
                        }

                        params.setVRProvider(Defaults.VR.WIT);
                        break;
                    case IBM:
                        if (DEBUG) {
                            Log.i(CLS_NAME, "checkVRProvider: " + Defaults.VR.IBM.name());
                        }
                        break;
                    default:
                        if (DEBUG) {
                            Log.e(CLS_NAME, "checkVRProvider: PROVIDER_VR.UNKNOWN");
                            throw RemoteError.throwUnknownVRProvider(wContext.get());
                        } else {
                            return false;
                        }
                }

                break;
            case WIT:
                if (DEBUG) {
                    Log.i(CLS_NAME, "checkVRProvider: " + Defaults.LanguageModel.WIT.name());
                }

                params.setVRProvider(Defaults.VR.WIT);
                break;
            case LOCAL:
                if (DEBUG) {
                    Log.i(CLS_NAME, "checkVRProvider: " + Defaults.LanguageModel.LOCAL.name());
                }

                switch (params.getVRProvider()) {

                    case NATIVE:
                        if (DEBUG) {
                            Log.i(CLS_NAME, "checkVRProvider: " + Defaults.VR.NATIVE.name());
                        }
                        break;
                    case GOOGLE_CLOUD:
                        if (DEBUG) {
                            Log.i(CLS_NAME, "checkVRProvider: " + Defaults.VR.GOOGLE_CLOUD.name());
                        }
                        break;
                    case GOOGLE_CHROMIUM:
                        if (DEBUG) {
                            Log.i(CLS_NAME, "checkVRProvider: " + Defaults.VR.GOOGLE_CHROMIUM.name());
                        }
                        break;
                    case NUANCE:
                        if (DEBUG) {
                            Log.i(CLS_NAME, "checkVRProvider: " + Defaults.VR.NUANCE.name());
                        }
                        break;
                    case MICROSOFT:
                        if (DEBUG) {
                            Log.i(CLS_NAME, "checkVRProvider: " + Defaults.VR.MICROSOFT.name());
                        }
                        break;
                    case WIT:
                        if (DEBUG) {
                            Log.i(CLS_NAME, "checkVRProvider: " + Defaults.VR.WIT.name());
                        }
                        break;
                    case IBM:
                        if (DEBUG) {
                            Log.i(CLS_NAME, "checkVRProvider: " + Defaults.VR.IBM.name());
                        }
                        break;
                    default:
                        if (DEBUG) {
                            Log.e(CLS_NAME, "checkVRProvider: PROVIDER_VR.UNKNOWN");
                            throw RemoteError.throwUnknownVRProvider(wContext.get());
                        } else {
                            return false;
                        }
                }
        }

        return checkAPIKey();
    }

    private boolean checkAPIKey() {
        if (DEBUG) {
            Log.i(CLS_NAME, "checkAPIKey");
        }

        switch (params.getTTSProvider()) {

            case LOCAL:
            case NETWORK_NUANCE:
                if (DEBUG) {
                    Log.i(CLS_NAME, "checkAPIKey: Defaults.TTS.NUANCE");
                }

                if (checkNuanceConfig()) {
                    if (DEBUG) {
                        Log.i(CLS_NAME, "checkAPIKey: Defaults.TTS.NUANCE: API key present");
                    }
                    break;
                } else {
                    if (DEBUG) {
                        Log.e(CLS_NAME, "checkAPIKey: Defaults.TTS.NUANCE: API key missing");
                        throw RemoteError.throwUnknownAPIKeyNuance(wContext.get());
                    } else {
                        return false;
                    }
                }
        }

        switch (params.getLanguageModel()) {

            case NUANCE:
                if (DEBUG) {
                    Log.i(CLS_NAME, "checkAPIKey: " + Defaults.LanguageModel.NUANCE);
                }

                if (checkNuanceConfig()) {
                    if (DEBUG) {
                        Log.i(CLS_NAME, "checkAPIKey: API key present");
                    }
                    break;
                } else {
                    if (DEBUG) {
                        Log.e(CLS_NAME, "checkAPIKey: API data error");
                        throw RemoteError.throwNuanceNLUConfig(wContext.get());
                    } else {
                        return false;
                    }
                }

            case API_AI:
                if (DEBUG) {
                    Log.i(CLS_NAME, "checkAPIKey: " + Defaults.LanguageModel.API_AI);
                }

                if (checkAPIAIConfig()) {
                    if (DEBUG) {
                        Log.i(CLS_NAME, "checkAPIKey: API key present");
                    }
                    break;
                } else {
                    if (DEBUG) {
                        Log.e(CLS_NAME, "checkAPIKey: API data error");
                        throw RemoteError.throwUnknownAPIKeyAPIAI(wContext.get());
                    } else {
                        return false;
                    }
                }

            case MICROSOFT:
                if (DEBUG) {
                    Log.i(CLS_NAME, "checkAPIKey: " + Defaults.LanguageModel.MICROSOFT);
                }

                if (checkMicrosoftConfig()) {
                    if (DEBUG) {
                        Log.i(CLS_NAME, "checkAPIKey: API key present");
                    }
                    break;
                } else {
                    if (DEBUG) {
                        Log.e(CLS_NAME, "checkAPIKey: API data error");
                        throw RemoteError.throwUnknownAPIKeyMicrosoft(wContext.get());
                    } else {
                        return false;
                    }
                }

            case WIT:
                if (DEBUG) {
                    Log.i(CLS_NAME, "checkAPIKey: " + Defaults.LanguageModel.WIT);
                }

                if (checkWitConfig()) {
                    if (DEBUG) {
                        Log.i(CLS_NAME, "checkAPIKey: API key present");
                    }
                    break;
                } else {
                    if (DEBUG) {
                        Log.e(CLS_NAME, "checkAPIKey: API data error");
                        throw RemoteError.throwUnknownAPIKeyWit(wContext.get());
                    } else {
                        return false;
                    }
                }

            default:

                switch (params.getVRProvider()) {

                    case GOOGLE_CLOUD:
                        if (DEBUG) {
                            Log.i(CLS_NAME, "checkAPIKey: " + Defaults.VR.GOOGLE_CLOUD);
                        }

                        if (checkGoogleCloudConfig()) {
                            if (DEBUG) {
                                Log.i(CLS_NAME, "checkAPIKey: API key present");
                            }
                            break;
                        } else {
                            if (DEBUG) {
                                Log.e(CLS_NAME, "checkAPIKey: API key missing");
                                throw RemoteError.throwUnknownGoogleCloudConfig(wContext.get());
                            } else {
                                return false;
                            }
                        }
                    case GOOGLE_CHROMIUM:
                        if (DEBUG) {
                            Log.i(CLS_NAME, "checkAPIKey: " + Defaults.VR.GOOGLE_CHROMIUM);
                        }

                        if (checkGoogleChromiumConfig()) {
                            if (DEBUG) {
                                Log.i(CLS_NAME, "checkAPIKey: API key present");
                            }
                            break;
                        } else {
                            if (DEBUG) {
                                Log.e(CLS_NAME, "checkAPIKey: API key missing");
                                throw RemoteError.throwUnknownAPIKeyGoogle(wContext.get());
                            } else {
                                return false;
                            }
                        }
                    case NUANCE:
                        if (DEBUG) {
                            Log.i(CLS_NAME, "checkAPIKey: " + Defaults.VR.NUANCE);
                        }

                        if (checkNuanceConfig()) {
                            if (DEBUG) {
                                Log.i(CLS_NAME, "checkAPIKey: API key present");
                            }
                            break;
                        } else {
                            if (DEBUG) {
                                Log.e(CLS_NAME, "checkAPIKey: API data error");
                                throw RemoteError.throwUnknownAPIKeyNuance(wContext.get());
                            } else {
                                return false;
                            }
                        }

                    case MICROSOFT:
                        if (DEBUG) {
                            Log.i(CLS_NAME, "checkAPIKey: " + Defaults.VR.MICROSOFT);
                        }

                        if (checkMicrosoftConfig()) {
                            if (DEBUG) {
                                Log.i(CLS_NAME, "checkAPIKey: API key present");
                            }
                            break;
                        } else {
                            if (DEBUG) {
                                Log.e(CLS_NAME, "checkAPIKey: API data error");
                                throw RemoteError.throwUnknownAPIKeyMicrosoft(wContext.get());
                            } else {
                                return false;
                            }
                        }

                    case WIT:
                        if (DEBUG) {
                            Log.i(CLS_NAME, "checkAPIKey: " + Defaults.VR.WIT);
                        }

                        if (checkWitConfig()) {
                            if (DEBUG) {
                                Log.i(CLS_NAME, "checkAPIKey: API key present");
                            }
                            break;
                        } else {
                            if (DEBUG) {
                                Log.e(CLS_NAME, "checkAPIKey: API data error");
                                throw RemoteError.throwUnknownAPIKeyWit(wContext.get());
                            } else {
                                return false;
                            }
                        }

                    case IBM:
                        if (DEBUG) {
                            Log.i(CLS_NAME, "checkAPIKey: " + Defaults.VR.IBM);
                        }

                        if (checkIBMConfig()) {
                            if (DEBUG) {
                                Log.i(CLS_NAME, "checkAPIKey: API key present");
                            }
                            break;
                        } else {
                            if (DEBUG) {
                                Log.e(CLS_NAME, "checkAPIKey: API data error");
                                throw RemoteError.throwUnknownAPIKeyIBM(wContext.get());
                            } else {
                                return false;
                            }
                        }
                }

                break;
        }

        return true;
    }

    private boolean checkAPIAIConfig() {
        if (DEBUG) {
            Log.i(CLS_NAME, "checkAPIAIConfig");
        }

        return !params.getAPI_AI_ACCESS_TOKEN().startsWith(_YOUR_);
    }

    private boolean checkWitConfig() {
        if (DEBUG) {
            Log.i(CLS_NAME, "checkWitConfig");
        }

        return !params.getWIT_SERVER_ACCESS_TOKEN().startsWith(_YOUR_);
    }

    private boolean checkIBMConfig() {
        if (DEBUG) {
            Log.i(CLS_NAME, "checkIBMConfig");
        }

        return !params.getIBM_SERVICE_USER_NAME().startsWith(_YOUR_)
                && !params.getIBM_SERVICE_PASSWORD().startsWith(_YOUR_);
    }

    private boolean checkMicrosoftConfig() {
        if (DEBUG) {
            Log.i(CLS_NAME, "checkMicrosoftConfig");
        }

        switch (params.getLanguageModel()) {

            case MICROSOFT:
                if (DEBUG) {
                    Log.i(CLS_NAME, "checkMicrosoftConfig: LanguageModel.MICROSOFT");
                }

                return !params.getOXFORD_KEY_1().startsWith(_YOUR_)
                        && !params.getOXFORD_KEY_2().startsWith(_YOUR_)
                        && !params.getLUIS_APP_ID().startsWith(_YOUR_)
                        && !params.getLUIS_SUBSCRIPTION_ID().startsWith(_YOUR_);
            default:
                if (DEBUG) {
                    Log.i(CLS_NAME, "checkMicrosoftConfig: LanguageModel.DEFAULT");
                }
                return !params.getOXFORD_KEY_1().startsWith(_YOUR_)
                        && !params.getOXFORD_KEY_2().startsWith(_YOUR_);
        }
    }

    private boolean checkGoogleChromiumConfig() {
        if (DEBUG) {
            Log.i(CLS_NAME, "checkGoogleChromiumConfig");
        }
        return !params.getGOOGLE_CHROMIUM_API_KEY().startsWith(_YOUR_);
    }

    private boolean checkGoogleCloudConfig() {
        if (DEBUG) {
            Log.i(CLS_NAME, "checkGoogleCloudConfig");
        }
        return !params.getGOOGLE_CLOUD_ACCESS_TOKEN().startsWith(_YOUR_)
                && params.getGOOGLE_CLOUD_ACCESS_EXPIRY() > 0;
    }

    private boolean checkNuanceConfig() {
        if (DEBUG) {
            Log.i(CLS_NAME, "checkNuanceConfig");
        }

        switch (params.getTTSProvider()) {

            case NETWORK_NUANCE:
                if (DEBUG) {
                    Log.i(CLS_NAME, "checkAPIKey: Defaults.TTS.NUANCE");
                }

                if (params.getNUANCE_APP_KEY().startsWith(_YOUR_)) {
                    if (DEBUG) {
                        Log.e(CLS_NAME, "checkNuanceConfig: Defaults.TTS.NUANCE: API key missing");
                    }
                    return false;
                } else if (params.getNUANCE_SERVER_URI().toString().startsWith(_YOUR_)) {
                    if (DEBUG) {
                        Log.e(CLS_NAME, "checkNuanceConfig: Defaults.TTS.NUANCE: URI missing");
                    }
                    return false;
                } else {
                    if (DEBUG) {
                        Log.i(CLS_NAME, "checkNuanceConfig: Defaults.TTS.NUANCE: Credentials present");
                    }
                    return true;
                }
        }

        switch (params.getLanguageModel()) {

            case NUANCE:
                if (DEBUG) {
                    Log.i(CLS_NAME, "checkNuanceConfig: LanguageModel.NUANCE");
                }

                if (params.getNUANCE_CONTEXT_TAG().startsWith(_YOUR_)) {
                    if (DEBUG) {
                        Log.e(CLS_NAME, "checkNuanceConfig: LanguageModel.NUANCE_NLU: CONTEXT TAG missing");
                    }
                    return false;
                } else if (!params.getNUANCE_SERVER_URI_NLU().toString().contains(NUANCE_NLU_HOST)) {
                    if (DEBUG) {
                        Log.e(CLS_NAME, "checkNuanceConfig: LanguageModel.NUANCE_NLU HOST requires " + NUANCE_NLU_HOST);
                    }
                    return false;
                } else {
                    if (DEBUG) {
                        Log.i(CLS_NAME, "checkNuanceConfig: LanguageModel.NUANCE_NLU: Credentials present");
                    }
                    return true;
                }

            default:

                switch (params.getVRProvider()) {

                    case NUANCE:
                        if (DEBUG) {
                            Log.i(CLS_NAME, "checkNuanceConfig: PROVIDER_VR.NUANCE");
                        }

                        if (params.getNUANCE_APP_KEY().startsWith(_YOUR_)) {
                            if (DEBUG) {
                                Log.e(CLS_NAME, "checkNuanceConfig: PROVIDER_VR.NUANCE: API key missing");
                            }
                            return false;
                        } else if (params.getNUANCE_SERVER_URI().toString().startsWith(_YOUR_)) {
                            if (DEBUG) {
                                Log.e(CLS_NAME, "checkNuanceConfig: PROVIDER_VR.NUANCE: URI missing");
                            }
                            return false;
                        } else {
                            if (DEBUG) {
                                Log.i(CLS_NAME, "checkNuanceConfig: PROVIDER_VR.NUANCE: Credentials present");
                            }
                            return true;
                        }
                }
        }

        return true;
    }


    /**
     * Check to see if the user has Saiy installed on their device and if it will function
     * by simply checking the availability of the required tts and vr services.
     *
     * @param ctx the application context
     * @return a {@link Pair} with the first parameter a boolean, denoting success and the second
     * a {@link SaiyAvailability} enum, defining a failure constant.
     */
    public static Pair<Boolean, SaiyAvailability> isSaiyAvailable(@NonNull final Context ctx) {

        boolean saiyInstalled;

        try {
            ctx.getApplicationContext().getPackageManager().getApplicationInfo(REMOTE_PKG_NAME, 0);
            saiyInstalled = true;
        } catch (final PackageManager.NameNotFoundException e) {
            saiyInstalled = false;
        }

        final boolean vrProviders = !ctx.getPackageManager().queryIntentServices(
                new Intent(RecognitionService.SERVICE_INTERFACE), 0).isEmpty()
                && SpeechRecognizer.isRecognitionAvailable(ctx);

        final boolean ttsProviders = !ctx.getPackageManager().queryIntentActivities(
                new Intent(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA), 0).isEmpty();

        boolean permissions = true;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            permissions = !(ctx.checkSelfPermission(android.Manifest.permission.RECORD_AUDIO)
                    != PackageManager.PERMISSION_GRANTED
                    || ctx.checkCallingOrSelfPermission(CONTROL_SAIY)
                    != PackageManager.PERMISSION_GRANTED);
        }

        if (DEBUG) {
            Log.d(CLS_NAME, "permissions granted for " + ctx.getPackageName() + " " + permissions);
        }

        if (saiyInstalled && vrProviders && ttsProviders && permissions) {
            return new Pair<>(true, SaiyAvailability.ALL_GOOD);
        } else {

            if (!saiyInstalled) {
                return new Pair<>(false, SaiyAvailability.NOT_INSTALLED);
            } else if (!vrProviders) {
                return new Pair<>(false, SaiyAvailability.NO_VR_PROVIDER);
            } else if (!permissions) {
                return new Pair<>(false, SaiyAvailability.MISSING_PERMISSION);
            } else {
                return new Pair<>(false, SaiyAvailability.NO_TTS_ENGINE);
            }
        }
    }

    /**
     * Utility method to coerce Voice Recognition {@link Locale} language Strings into an
     * ISO3 {@link Locale} format
     *
     * @param availableVoices returned from the {@link Intent} from {@link #sendBroadcast()}
     */
    private void setVRLocales(final ArrayList<String> availableVoices) {
        if (DEBUG) {
            Log.i(CLS_NAME, "setVRLocales");
        }

        if (availableVoices != null && !availableVoices.isEmpty()) {
            synchronized (vrLocales) {
                vrLocales.clear();

                for (final String loc : availableVoices) {
                    if (DEBUG) {
                        Log.d(CLS_NAME, "loc: " + loc);
                    }

                    vrLocales.add(LocalUtils.stringToLocale(loc));
                }
            }
        } else {
            if (DEBUG) {
                Log.w(CLS_NAME, "setVRLocales: availableVoices null or empty");
            }
        }
    }

    /**
     * Check if the Local TTS Engine supports the given Locale.
     *
     * @param userLocale the user's {@link Locale}
     * @return true if the default TTS Engine supports the given Locale or false if the
     * locale is not supported or the languages were not available to query
     */

    public boolean isTTSLanguageLocalAvailable(@NonNull final Locale userLocale) {

        synchronized (ttsLocales) {

            if (!ttsLocales.isEmpty()) {

                for (final Locale loc : ttsLocales) {
                    if (DEBUG) {
                        Log.i(CLS_NAME, "ttsLoc: comparing: " + userLocale.toString() + " ~ " + loc.toString());
                    }

                    if (loc.equals(userLocale)) {
                        if (DEBUG) {
                            Log.i(CLS_NAME, "Locale full match");
                        }
                        return true;
                    }
                }


                for (final Locale loc : ttsLocales) {
                    if (DEBUG) {
                        Log.i(CLS_NAME, "ttsLoc: comparing: " + userLocale.toString() + " ~ " + loc.toString());
                    }

                    try {

                        if (DEBUG) {
                            Log.i(CLS_NAME, "ttsLoc: comparingISO: "
                                    + loc.getISO3Language() + " ~ " + userLocale.getISO3Language());
                        }

                        if (loc.getISO3Language().matches(userLocale.getISO3Language())) {
                            if (DEBUG) {
                                Log.i(CLS_NAME, "Locale Language match");
                            }
                            return true;
                        }

                    } catch (final MissingResourceException e) {
                        if (DEBUG) {
                            Log.e(CLS_NAME, "Locale Language error. Continuing");
                            e.printStackTrace();
                        }
                    }
                }
            } else {
                if (DEBUG) {
                    Log.w(CLS_NAME, "ttsLocales empty");
                }
            }

            if (DEBUG) {
                Log.w(CLS_NAME, "Locale no match");
            }

            return false;
        }
    }

    /**
     * Check if the Native Recognition Service supports the given Locale.
     *
     * @param userLocale the user's {@link Locale}
     * @return true if the {@link Locale} is supported or false if the locale is not supported
     * or the languages were not available to query.
     */
    public boolean isVRLanguageNativeAvailable(@NonNull final Locale userLocale) {

        synchronized (vrLocales) {

            if (!vrLocales.isEmpty()) {

                for (final Locale loc : vrLocales) {
                    if (DEBUG) {
                        Log.i(CLS_NAME, "vrLoc: comparing: " + userLocale.toString() + " ~ " + loc.toString());
                    }

                    if (loc.equals(userLocale)) {
                        if (DEBUG) {
                            Log.i(CLS_NAME, "Locale full match");
                        }
                        return true;
                    }
                }


                for (final Locale loc : vrLocales) {
                    if (DEBUG) {
                        Log.i(CLS_NAME, "vrLoc: comparing: " + userLocale.toString() + " ~ " + loc.toString());
                    }

                    try {

                        if (DEBUG) {
                            Log.i(CLS_NAME, "vrLoc: comparing: "
                                    + loc.getISO3Language() + " ~ " + userLocale.getISO3Language());
                        }

                        if (loc.getISO3Language().matches(userLocale.getISO3Language())) {
                            if (DEBUG) {
                                Log.i(CLS_NAME, "Locale Language match");
                            }
                            return true;
                        }

                    } catch (final MissingResourceException e) {
                        if (DEBUG) {
                            Log.e(CLS_NAME, "Locale Language error. Continuing");
                            e.printStackTrace();
                        }
                    }
                }
            } else {
                if (DEBUG) {
                    Log.w(CLS_NAME, "vrLocales empty");
                }
            }

            if (DEBUG) {
                Log.w(CLS_NAME, "Locale no match");
            }

            return false;
        }
    }


    /**
     * Check if the Nuance TTS Engine supports the given {@link Locale}
     *
     * @param userLocale the requested {@link Locale}
     * @return true if the {@link Locale} is supported
     */
    public boolean isTTSLanguageNuanceAvailable(@NonNull final Locale userLocale) {
        return TTSLanguageNuance.isLanguageAvailable(userLocale);
    }

    /**
     * Get the {@link TTSLanguageNuance}
     * <p>
     * You should call {@link SaiyRequest#isTTSLanguageNuanceAvailable(Locale)} before this.
     *
     * @param userLocale      the requested {@link Locale}
     * @param preferredGender the preferred voice {@link Gender}
     * @return the {@link TTSLanguageNuance} or a multi-lingual default engine
     */
    public TTSLanguageNuance getTTSVoiceNuance(@NonNull final Locale userLocale,
                                               @NonNull final Gender preferredGender) {
        return TTSLanguageNuance.getVoice(userLocale, preferredGender);
    }

    /**
     * Check if Nuance Voice Recognition supports the given {@link Locale}
     *
     * @return true if the {@link Locale} is supported
     */
    public boolean isVRLanguageNuanceAvailable(@NonNull final Locale userLocale) {
        return VRLanguageNuance.isLanguageAvailable(userLocale);
    }

    /**
     * Check if Nuance NLU supports the given {@link Locale}
     *
     * @return true if the {@link Locale} is supported
     */
    public boolean isNLULanguageNuanceAvailable(@NonNull final Locale userLocale) {
        return NLULanguageNuance.isLanguageAvailable(userLocale);
    }

    /**
     * Check if Microsoft Voice Recognition supports the given {@link Locale}
     *
     * @return true if the {@link Locale} is supported
     */
    public boolean isVRLanguageMicrosoftAvailable(@NonNull final Locale userLocale) {
        return VRLanguageMicrosoft.isLanguageAvailable(userLocale);
    }

    /**
     * Check if Microsoft NLU supports the given {@link Locale}. Currently the same as the supported
     * voice recognition languages
     *
     * @return true if the {@link Locale} is supported
     */
    public boolean isNLULanguageMicrosoftAvailable(@NonNull final Locale userLocale) {
        return VRLanguageMicrosoft.isLanguageAvailable(userLocale);
    }

    /**
     * Check if Wit Voice Recognition supports the given {@link Locale}
     *
     * @return true if the {@link Locale} is supported
     */
    public boolean isVRLanguageWitAvailable(@NonNull final Locale userLocale) {
        return VRLanguageWit.isLanguageAvailable(userLocale);
    }

    /**
     * Check if IBM Voice Recognition supports the given {@link Locale}
     *
     * @return true if the {@link Locale} is supported
     */
    public boolean isVRLanguageIBMAvailable(@NonNull final Locale userLocale) {
        return VRLanguageIBM.isLanguageAvailable(userLocale);
    }

    /**
     * Check if API AI NLU supports the given {@link Locale}. Currently the same as the supported
     * voice recognition languages
     *
     * @return true if the {@link Locale} is supported
     */
    public boolean isNLULanguageAPIAIAvailable(@NonNull final Locale userLocale) {
        return NLULanguageAPIAI.isLanguageAvailable(userLocale);
    }

    /**
     * Check if Wit NLU supports the given {@link Locale}. Currently the same as the supported
     * voice recognition languages
     *
     * @return true if the {@link Locale} is supported
     */
    public boolean isNLULanguageWitAvailable(@NonNull final Locale userLocale) {
        return VRLanguageWit.isLanguageAvailable(userLocale);
    }

    /**
     * Check if Google Voice Recognition supports the given {@link Locale}
     *
     * @return true if the {@link Locale} is supported
     */
    public boolean isVRLanguageGoogleAvailable(@NonNull final Locale userLocale) {
        return VRLanguageGoogle.isLanguageAvailable(userLocale);
    }
}
