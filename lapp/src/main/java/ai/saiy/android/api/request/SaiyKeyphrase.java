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
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import ai.saiy.android.api.Defaults;

import static ai.saiy.android.api.request.SaiyRequest.REMOTE_PKG_NAME;

/**
 * Created by benrandall76@gmail.com on 26/04/2016.
 */
public class SaiyKeyphrase {

    private final boolean DEBUG = Defaults.getLogging();
    private final String CLS_NAME = SaiyKeyphrase.class.getSimpleName();

    public static final String SAIY_KEYPHRASE = "keyphrase";
    private static final String SAIY_HOT_WORD = "hot_word";
    public static final String SAIY_ACTION = "ai.saiy.android.SAIY_ACTION";
    public static final String SAIY_REQUEST_RECEIVER = "ai.saiy.android.SAIY_REQUEST_RECEIVER";
    public static final String REQUESTING_PACKAGE = "requesting_package";
    public static final String KEYPHRASE_REGEX = "keyphrase_regex";
    public static final String REGEX_CONTENT = "regex_content";

    public static final String REQUEST_TYPE = "request_type";
    public static final int REQUEST_KEYPHRASE = 1;
    private static final int REQUEST_HOT_WORD = 2;
    public static final String SAIY_KEYPHRASE_ID = "keyphrase_id";

    private final Context mContext;
    private final SaiyKeyphraseListener listener;

    private String packageName;
    private String keyphrase;
    private String hotWord;
    private int action;
    private Bundle bundle;
    private Regex regex;
    private String regularExpression;

    /**
     * Constructor
     *
     * @param mContext the application context
     */
    public SaiyKeyphrase(@NonNull final Context mContext, @NonNull final SaiyKeyphraseListener listener) {
        this.mContext = mContext;
        this.listener = listener;
    }

    public void setRegex(@NonNull final Regex regex) {
        this.regex = regex;
    }

    public void setRegularExpression(@NonNull final String regularExpression) {
        this.regularExpression = regularExpression;
    }

    /**
     * Set the action for your Intent Service to handle.
     *
     * @param action the custom action you will react to.
     */
    public void setDefinedAction(final int action) {
        this.action = action;
    }

    /**
     * An option extra bundle that can contain any extras of your choice, other than Serializable or
     * parcelable extras, which will be removed should Saiy detect them.
     *
     * @param bundle the optional bundle
     */
    public void setExtraBundle(@NonNull final Bundle bundle) {
        this.bundle = bundle;
    }

    /**
     * The keyphrase (spoken word(s)) that will trigger your custom request.
     *
     * @param keyphrase the keyphrase
     */
    public void setSaiyKeyphrase(@NonNull final String keyphrase) {
        this.keyphrase = keyphrase;
    }

    /**
     * Send the request to Saiy to attempt to register the Keyphrase.
     *
     * @param requestId the unique request id to identify the request
     * @return true if the request was configured correctly. False otherwise. Note, regardless of
     * whether or not this method returns true, the request will still be scrutinised by Saiy and
     * may be subsequently rejected. This final response will be received
     * in {@link SaiyKeyphraseListener#onEnrollKeyphrase(int, int)}
     */
    public boolean sendRequest(final int requestId) {

        if (validateRequest()) {
            final Intent saiyIntent = new Intent(SAIY_REQUEST_RECEIVER);
            saiyIntent.setPackage(REMOTE_PKG_NAME);
            saiyIntent.putExtra(REQUEST_TYPE, REQUEST_KEYPHRASE);
            saiyIntent.putExtra(REQUESTING_PACKAGE, packageName);
            saiyIntent.putExtra(SAIY_KEYPHRASE, keyphrase);
            saiyIntent.putExtra(SAIY_ACTION, action);
            saiyIntent.putExtra(SAIY_KEYPHRASE_ID, requestId);
            saiyIntent.putExtra(KEYPHRASE_REGEX, regex == null ? Regex.MATCHES : regex);
            saiyIntent.putExtra(REGEX_CONTENT, regularExpression);

            if (bundle != null && !bundle.isEmpty()) {
                saiyIntent.putExtras(bundle);
            }

            mContext.sendOrderedBroadcast(saiyIntent, null, new BroadcastReceiver() {
                @Override
                public void onReceive(final Context context, final Intent intent) {
                    if (DEBUG) {
                        Log.v(CLS_NAME, "BroadcastReceiver: sendRequest result: " + getResultCode());
                    }

                    if (listener != null) {

                        final Bundle bundle = intent.getExtras();
                        int responseId = 0;

                        if (bundle != null && !bundle.isEmpty()) {
                            responseId = bundle.getInt(SAIY_KEYPHRASE_ID, 0);
                        }

                        listener.onEnrollKeyphrase(getResultCode(), responseId);
                    } else {
                        Log.e(CLS_NAME, "The listener was null. You must include a non-null keyphrase " +
                                "listener in your constructor");
                    }

                }
            }, null, Activity.RESULT_CANCELED, null, null);

            return true;
        } else {
            return false;
        }
    }

    private boolean validateRequest() {

        if (mContext == null) {
            Log.e(CLS_NAME, "The context you supply cannot be null.");
            return false;
        } else {
            packageName = mContext.getPackageName();
        }

        if (hotWord != null) {
            Log.e(CLS_NAME, "Hot word registration is not yet available.");
            return false;
        }

        if (packageName == null || packageName.replaceAll("\\s", "").isEmpty()) {
            Log.e(CLS_NAME, "Saiy needs your package name in order to direct the request back to your app");
            return false;
        }

        if (keyphrase == null) {
            Log.e(CLS_NAME, "Keyphrase must not be null");
            return false;
        } else {

            try {
                //noinspection ResultOfMethodCallIgnored
                Pattern.compile(keyphrase);

                if (keyphrase.replaceAll("\\s", "").isEmpty()) {
                    Log.e(CLS_NAME, "Keyphrase must not be an empty string");
                    return false;
                }

            } catch (final PatternSyntaxException e) {
                Log.e(CLS_NAME, "Keyphrase must not contain regular expression");
                e.printStackTrace();
                return false;
            } catch (final Exception e) {
                Log.e(CLS_NAME, "Keyphrase must not contain regular expression");
                e.printStackTrace();
                return false;
            }
        }

        if (action == 0) {
            Log.e(CLS_NAME, "You must supply an action for your intent service. It must not be equal to zero");
            return false;
        }

        if (bundle == null) {
            if (DEBUG) {
                Log.v(CLS_NAME, "Sending request with null bundle. Assume intended developer behaviour");
            }
        }

        if (regex != null) {

            switch (regex) {

                case MATCHES:
                    break;
                case STARTS_WITH:
                    break;
                case ENDS_WITH:
                    break;
                case CONTAINS:
                    Log.e(CLS_NAME, "Regex.CONTAINS is not currently supported. Please contact the developer" +
                            "if you believe you have a valid use case");
                    return false;
                case CUSTOM:
                    Log.e(CLS_NAME, "Regex.CUSTOM is not currently supported. Please contact the developer" +
                            "if you believe you have a valid use case");
                    return false;
            }
        }

        if (regularExpression != null) {

            try {
                //noinspection ResultOfMethodCallIgnored
                Pattern.compile(regularExpression);
            } catch (final PatternSyntaxException e) {
                Log.e(CLS_NAME, "The regular expression was malformed");
                e.printStackTrace();
                return false;
            } catch (final Exception e) {
                Log.e(CLS_NAME, "The regular expression was malformed");
                e.printStackTrace();
                return false;
            }
        }

        return true;
    }
}
