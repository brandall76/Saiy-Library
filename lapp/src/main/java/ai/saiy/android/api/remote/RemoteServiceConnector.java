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

package ai.saiy.android.api.remote;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import ai.saiy.android.api.Defaults;
import ai.saiy.android.api.R;
import ai.saiy.android.api.RequestParcel;
import ai.saiy.android.api.request.SaiyRequest;
import ai.saiy.android.api.request.SaiyRequestParams;
import ai.saiy.android.service.ISaiy;
import ai.saiy.android.service.ISaiyListener;


/**
 * All requests to the {SelfAware} service should be directed here.
 * <p>
 * It's a very short-lived helper Class that attempts to connect to Saiy to perform the requested task.
 * <p>
 * Created by benrandall76@gmail.com on 06/02/2016.
 */
public class RemoteServiceConnector {

    private final boolean DEBUG = Defaults.getLogging();
    private final String CLS_NAME = RemoteServiceConnector.class.getSimpleName();

    private boolean bound = false;

    private volatile ISaiy iSaiy = null;

    private long then;

    private final Context mContext;
    private volatile SaiyListener listener;
    private final SaiyRequest saiyRequest;
    private final Bundle bundle;

    /**
     * Constructor.
     *
     * @param mContext    the application context
     * @param saiyRequest the {@link SaiyRequest} parameters
     */
    public RemoteServiceConnector(@NonNull final Context mContext, @NonNull SaiyListener listener,
                                  @NonNull final SaiyRequest saiyRequest, @NonNull final Bundle bundle) {
        this.mContext = mContext.getApplicationContext();
        this.listener = listener;
        this.saiyRequest = saiyRequest;
        this.bundle = bundle;
    }

    public void createConnection() {
        if (DEBUG) {
            Log.i(CLS_NAME, "createConnection");
        }

        then = System.nanoTime();

        final Intent intent = saiyRequest.getRemoteIntent();
        doBindService(intent);

    }

    private void doBindService(final Intent intent) {
        if (DEBUG) {
            Log.i(CLS_NAME, "doBindService");
            Log.i(CLS_NAME, "doBindService: isMain thread: " + (Looper.myLooper() == Looper.getMainLooper()));
        }
        try {

            bound = mContext.bindService(intent, mConnection, Context.BIND_ABOVE_CLIENT
                    | Context.BIND_WAIVE_PRIORITY);

            if (DEBUG) {
                Log.d(CLS_NAME, "doBindService: bound: " + bound);
            }

            if (!bound) {
                listener.onError(Defaults.ERROR.ERROR_DENIED, extractRequestId(bundle));
            }

        } catch (final SecurityException e) {
            if (DEBUG) {
                Log.e(CLS_NAME, "doBindService: SecurityException");
                e.printStackTrace();
            }

            Log.e("Saiy", "SecurityException: Service requires the permission "
                    + SaiyRequest.CONTROL_SAIY);
        }
    }

    private ISaiyListener.Stub iSaiyListener = new ISaiyListener.Stub() {

        @SuppressWarnings("RedundantThrows")
        @Override
        public void onSpeechResults(final Bundle results, String requestId) throws RemoteException {
            if (DEBUG) {
                Log.d(CLS_NAME, "onSpeechResults: id: " + requestId);
            }

            requestId = validateRequestId(requestId);

            final ArrayList<String> voiceData = results.getStringArrayList(Request.RESULTS_RECOGNITION);
            final float[] confidenceScore = results.getFloatArray(Request.CONFIDENCE_SCORES);

            if (DEBUG) {

                if (confidenceScore != null && voiceData != null && confidenceScore.length == voiceData.size()) {

                    for (int i = 0; i < voiceData.size(); i++) {
                        Log.i(CLS_NAME, "onSpeechResults: " + voiceData.get(i) + " ~ " + confidenceScore[i]);
                    }
                } else if (voiceData != null) {

                    for (int i = 0; i < voiceData.size(); i++) {
                        Log.i(CLS_NAME, "onSpeechResults: " + voiceData.get(i));
                    }
                } else {
                    Log.w(CLS_NAME, "onSpeechResults: values error");
                }
            }

            listener.onSpeechResults(results, requestId);
        }

        @Override
        @SuppressWarnings("RedundantThrows")
        public void onError(final String error, String requestId) throws RemoteException {
            if (DEBUG) {
                Log.d(CLS_NAME, "onError: id: " + requestId);
                Log.d(CLS_NAME, "onError: " + error);
            }

            requestId = validateRequestId(requestId);
            listener.onError(Defaults.getERROR(error), requestId);
        }

        @Override
        @SuppressWarnings("RedundantThrows")
        public void onUtteranceCompleted(String requestId) throws RemoteException {
            if (DEBUG) {
                Log.d(CLS_NAME, "onUtteranceCompleted: " + requestId);
            }

            requestId = validateRequestId(requestId);
            listener.onUtteranceCompleted(requestId);
        }
    };

    private ServiceConnection mConnection = new ServiceConnection() {

        public void onServiceConnected(final ComponentName className, final IBinder iBinder) {
            if (DEBUG) {
                Log.i(CLS_NAME, "onServiceConnected");
                Log.i(CLS_NAME, "onServiceConnected: CLS: " + iBinder.getClass().getSimpleName());
                Log.v(CLS_NAME, "onServiceConnected: binder alive: " + iBinder.isBinderAlive());
            }

            try {

                iSaiy = ISaiy.Stub.asInterface(iBinder);

                if (iSaiy.isReal()) {
                    if (DEBUG) {
                        Log.i(CLS_NAME, "isReal: true");
                    }

                    final RequestParcel parcel = bundle.getParcelable(RequestParcel.PARCEL_KEY);

                    if (parcel != null) {

                        switch (parcel.getAction()) {

                            case SPEAK_ONLY:
                                if (DEBUG) {
                                    Log.i(CLS_NAME, "onServiceConnected: ACTION_SPEAK_ONLY");
                                }
                                bundle.putInt("extra_speech_priority", 10);
                                iSaiy.speakOnly(iSaiyListener, bundle);
                                break;
                            case SPEAK_LISTEN:
                                if (DEBUG) {
                                    Log.i(CLS_NAME, "onServiceConnected: ACTION_SPEAK_LISTEN");
                                }
                                bundle.putInt("extra_speech_priority", 10);
                                iSaiy.speakListen(iSaiyListener, bundle);
                                break;
                            default:
                                if (DEBUG) {
                                    Log.i(CLS_NAME, "onServiceConnected: ACTION_UNKNOWN");
                                }

                                Log.e(CLS_NAME, mContext.getString(R.string.error_action));
                                listener.onError(Defaults.ERROR.ERROR_DEVELOPER, extractRequestId(bundle));
                                break;
                        }
                    } else {
                        Log.e(CLS_NAME, "onServiceConnected: RequestParcel null. " +
                                "This should never happen?");
                        listener.onError(Defaults.ERROR.ERROR_DEVELOPER, SaiyRequestParams.ID_UNKNOWN);
                    }
                } else {
                    Log.e(CLS_NAME, "Saiy declined the request for the remote service binding");
                    listener.onError(Defaults.ERROR.ERROR_DENIED, extractRequestId(bundle));
                }
            } catch (final RemoteException e) {
                if (DEBUG) {
                    Log.e(CLS_NAME, "onServiceConnected: RemoteException");
                    e.printStackTrace();
                }
                listener.onError(Defaults.ERROR.ERROR_DENIED, extractRequestId(bundle));
            } catch (final NullPointerException e) {
                if (DEBUG) {
                    Log.e(CLS_NAME, "onServiceConnected: NullPointerException");
                    e.printStackTrace();
                }
                listener.onError(Defaults.ERROR.ERROR_DEVELOPER, extractRequestId(bundle));
            } catch (final Exception e) {
                if (DEBUG) {
                    Log.e(CLS_NAME, "onServiceConnected: Exception");
                    e.printStackTrace();
                }
                listener.onError(Defaults.ERROR.ERROR_DEVELOPER, extractRequestId(bundle));
            }

            doUnbindService();
        }

        public void onServiceDisconnected(final ComponentName className) {
            if (DEBUG) {
                Log.i(CLS_NAME, "onServiceDisconnected");
            }

            iSaiy = null;
        }
    };

    private void doUnbindService() {
        if (DEBUG) {
            Log.i(CLS_NAME, "doUnbindService");
        }

        if (bound) {

            if (iSaiy != null && mConnection != null && mContext != null) {
                mContext.unbindService(mConnection);
            } else {
                if (DEBUG) {
                    Log.w(CLS_NAME, "doUnbindService: iSaiy|mConnection|mContext: null");
                }
            }

            bound = false;

        } else {
            if (DEBUG) {
                Log.w(CLS_NAME, "doUnbindService: bound: false");
            }
        }

        if (DEBUG) {
            final long now = System.nanoTime();
            final long elapsed = now - then;

            Log.d(CLS_NAME, ": elapsed: "
                    + TimeUnit.MILLISECONDS.convert(elapsed, TimeUnit.NANOSECONDS));
        }
    }

    /**
     * Check the request id to make sure it is not null and doesn't match the default ID_UNKNOWN in
     * the {@link SaiyRequestParams} class.
     * <p>
     * If the request id is invalid it therefore cannot be used to trace the original request.
     * This would only happen if the original request was misconfigured.
     *
     * @param requestId the id of the request
     * @return the valid request id or {@link SaiyRequestParams#ID_UNKNOWN} otherwise
     */
    private String validateRequestId(final String requestId) {

        if (requestId == null || requestId.matches(SaiyRequestParams.ID_UNKNOWN)) {
            if (DEBUG) {
                Log.w(CLS_NAME, "onError: Saiy was unable to extract the request id");
            }

            return SaiyRequestParams.ID_UNKNOWN;
        }

        return requestId;
    }

    /**
     * Extract the request id to use when returning a response to the interface that Saiy has not
     * handled directly. This may be due to a configuration error.
     *
     * @param bundle containing the {@link RequestParcel}
     * @return the valid request id or {@link SaiyRequestParams#ID_UNKNOWN} otherwise
     */
    private String extractRequestId(final Bundle bundle) {

        if (bundle != null) {
            final RequestParcel parcel = bundle.getParcelable(RequestParcel.PARCEL_KEY);

            if (parcel != null) {
                final String requestId = parcel.getRequestId();

                if (requestId != null) {
                    return requestId;
                }
            }
        }

        return SaiyRequestParams.ID_UNKNOWN;
    }
}

