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

import android.util.Log;

/**
 * Created by benrandall76@gmail.com on 24/02/2016.
 */
public class Defaults {

    /**
     * MUST NOT BE SET TO TRUE IN PRODUCTION!
     */
    private static boolean DEBUG = false;
    private static final String CLS_NAME = Defaults.class.getSimpleName();

    /**
     * Turn on the log output. MUST NOT BE SET TO TRUE IN PRODUCTION!
     *
     * @param enabled true to show log output, false otherwise
     */
    public static void setLogging(final boolean enabled) {
        DEBUG = enabled;
    }

    /**
     * Check if logging is enabled.
     *
     * @return true if enabled, false otherwise
     */
    public static boolean getLogging() {
        return DEBUG;
    }

    public enum LanguageModel {
        LOCAL,
        NUANCE,
        MICROSOFT,
        API_AI,
        WIT,
        REMOTE
    }

    public enum TTS {
        LOCAL,
        NETWORK_NUANCE
    }

    public enum VR {
        NATIVE,
        GOOGLE_CLOUD,
        GOOGLE_CHROMIUM,
        NUANCE,
        MICROSOFT,
        WIT,
        IBM,
        REMOTE
    }

    public enum ACTION {
        SPEAK_ONLY,
        SPEAK_LISTEN
    }

    public enum ERROR {
        ERROR_UNKNOWN,
        ERROR_DEVELOPER,
        ERROR_DENIED,
        ERROR_SAIY,
        ERROR_NO_MATCH,
        ERROR_BUSY,
        ERROR_NETWORK,
        ERROR_INTERRUPTED
    }

    public static Defaults.ACTION getACTION(final String name) {
        if (DEBUG) {
            Log.i(CLS_NAME, "getACTION: " + name);
        }

        if (name != null) {

            try {
                return Enum.valueOf(Defaults.ACTION.class, name.trim().toUpperCase());
            } catch (final IllegalArgumentException e) {
                if (DEBUG) {
                    Log.w(CLS_NAME, "getACTION: IllegalArgumentException");
                    e.printStackTrace();
                }
                return Defaults.ACTION.SPEAK_ONLY;
            }

        } else {
            if (DEBUG) {
                Log.w(CLS_NAME, "getACTION: name null");
            }

            return Defaults.ACTION.SPEAK_ONLY;
        }
    }

    public static Defaults.TTS getProviderTTS(final String name) {
        if (DEBUG) {
            Log.i(CLS_NAME, "getProviderTTS: " + name);
        }

        if (name != null) {

            try {
                return Enum.valueOf(Defaults.TTS.class, name.trim().toUpperCase());
            } catch (final IllegalArgumentException e) {
                if (DEBUG) {
                    Log.w(CLS_NAME, "getProviderTTS: IllegalArgumentException");
                    e.printStackTrace();
                }
                return Defaults.TTS.LOCAL;
            }

        } else {
            if (DEBUG) {
                Log.w(CLS_NAME, "getProviderTTS: name null");
            }
            return Defaults.TTS.LOCAL;
        }
    }

    public static Defaults.VR getProviderVR(final String name) {
        if (DEBUG) {
            Log.i(CLS_NAME, "getProviderVR: " + name);
        }

        if (name != null) {

            try {
                return Enum.valueOf(Defaults.VR.class, name.trim().toUpperCase());
            } catch (final IllegalArgumentException e) {
                if (DEBUG) {
                    Log.w(CLS_NAME, "getProviderVR: IllegalArgumentException");
                    e.printStackTrace();
                }
                return Defaults.VR.NATIVE;
            }

        } else {
            if (DEBUG) {
                Log.w(CLS_NAME, "getProviderVR: name null");
            }
            return Defaults.VR.NATIVE;
        }
    }

    public static Defaults.LanguageModel getLanguageModel(final String name) {
        if (DEBUG) {
            Log.i(CLS_NAME, "getLanguageModel: " + name);
        }

        if (name != null) {

            try {
                return Enum.valueOf(Defaults.LanguageModel.class, name.trim().toUpperCase());
            } catch (final IllegalArgumentException e) {
                if (DEBUG) {
                    Log.w(CLS_NAME, "getLanguageModel: IllegalArgumentException");
                    e.printStackTrace();
                }
                return Defaults.LanguageModel.LOCAL;
            }

        } else {
            if (DEBUG) {
                Log.w(CLS_NAME, "getLanguageModel: name null");
            }
            return Defaults.LanguageModel.LOCAL;
        }
    }


    public static ERROR getERROR(final String errorCode) {
        if (DEBUG) {
            Log.i(CLS_NAME, "getERROR: " + errorCode);
        }

        if (errorCode != null) {

            try {
                return Enum.valueOf(Defaults.ERROR.class, errorCode.trim().toUpperCase());
            } catch (final IllegalArgumentException e) {
                if (DEBUG) {
                    Log.w(CLS_NAME, "getERROR: IllegalArgumentException");
                    e.printStackTrace();
                }
                return Defaults.ERROR.ERROR_UNKNOWN;
            }

        } else {
            if (DEBUG) {
                Log.w(CLS_NAME, "getERROR: name null");
            }
            return Defaults.ERROR.ERROR_UNKNOWN;
        }
    }
}
