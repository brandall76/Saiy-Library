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

package ai.saiy.android.api.language.vr;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.StringTokenizer;

import ai.saiy.android.api.Defaults;

/**
 * Created by benrandall76@gmail.com on 01/08/2016.
 */

public enum VRLanguageIBM {

    ENGLISH_GB("eng-GBR", "en-UK_BroadbandModel"),
    ENGLISH_US("eng-USA", "en-US_BroadbandModel"),
    FRENCH_FRANCE("fra-FRA", "fr-FR_BroadbandModel"),
    PORTUGUESE_BRAZIL("por-BRA", "pt-BR_BroadbandModel"),
    JAPANESE("jpn-JPN", "ja-JP_BroadbandModel"),
    SPANISH_SPAIN("spa-ESP", "es-ES_BroadbandModel"),
    ARABIC_INTERNATIONAL("ara-XWW", "ar-AR_BroadbandModel"),
    MANDARIN_CHINA_SIMPLIFIED("zho-CHN", "zh-CN_BroadbandModel");

    private static final boolean DEBUG = Defaults.getLogging();
    private static final String CLS_NAME = VRLanguageIBM.class.getSimpleName();

    private final String localeString;
    private final String model;

    VRLanguageIBM(final String localeString, final String model) {
        this.localeString = localeString;
        this.model = model;
    }

    public String getLocaleString() {
        return localeString;
    }

    public String getModel() {
        return model;
    }

    public static VRLanguageIBM[] getLanguages() {
        return VRLanguageIBM.values();
    }

    public String getModel(@NonNull final String localeString) {

        for (final VRLanguageIBM language : getLanguages()) {
            if (DEBUG) {
                Log.i(CLS_NAME, "language: " + language.getLocaleString());
            }

            if (localeString.matches(language.getLocaleString())) {
                return language.getModel();
            }

        }

        return ENGLISH_US.getModel();
    }

    public static boolean isLanguageAvailable(@NonNull final Locale userLocale) {

        final String localeString = userLocale.toString();

        StringTokenizer tokens;
        Locale locale;

        try {

            if (DEBUG) {
                Log.i(CLS_NAME, "userLocale: " + localeString);
                Log.i(CLS_NAME, "userLocale.getISO3Language: " + userLocale.getISO3Language());
                Log.i(CLS_NAME, "userLocale.getISO3Country : " + userLocale.getISO3Country());
            }

            final Locale iso3Locale = new Locale(userLocale.getISO3Language(), userLocale.getISO3Country());

            for (final VRLanguageIBM language : getLanguages()) {
                if (DEBUG) {
                    Log.i(CLS_NAME, "language: " + language.getLocaleString());
                }

                tokens = new StringTokenizer(language.getLocaleString(), "-");
                locale = new Locale(tokens.nextToken(), tokens.nextToken());

                if (DEBUG) {
                    Log.i(CLS_NAME, "locale: " + locale.toString());
                }

                if (locale.equals(iso3Locale)) {
                    if (DEBUG) {
                        Log.i(CLS_NAME, "Locale full match");
                    }
                    return true;
                }
            }

        } catch (final MissingResourceException e) {
            if (DEBUG) {
                Log.e(CLS_NAME, "isLanguageAvailable: iso3 language/country error. checking language only");
                e.printStackTrace();
            }
        }

        try {

            final Locale iso3Locale = new Locale(userLocale.getISO3Language());

            for (final VRLanguageIBM language : getLanguages()) {
                if (DEBUG) {
                    Log.i(CLS_NAME, "language: " + language.getLocaleString());
                }

                tokens = new StringTokenizer(language.getLocaleString(), "-");
                locale = new Locale(tokens.nextToken(), tokens.nextToken());

                if (DEBUG) {
                    Log.i(CLS_NAME, "locale: " + locale.toString());
                }

                // Perhaps this should not return true for many cases.
                if (locale.getLanguage().matches(iso3Locale.getISO3Language())) {
                    if (DEBUG) {
                        Log.i(CLS_NAME, "Locale Language match");
                    }
                    return true;
                }
            }

        } catch (final MissingResourceException e) {
            if (DEBUG) {
                Log.e(CLS_NAME, "isLanguageAvailable: iso3 language error. returning false");
                e.printStackTrace();
            }
        }

        return false;
    }

    public static VRLanguageIBM getLanguage(@NonNull final Locale userLocale) {

        final String localeString = userLocale.toString();
        StringTokenizer tokens;
        Locale locale;


        try {

            if (DEBUG) {
                Log.i(CLS_NAME, "userLocale: " + localeString);
                Log.i(CLS_NAME, "userLocale.getISO3Language: " + userLocale.getISO3Language());
                Log.i(CLS_NAME, "userLocale.getISO3Country : " + userLocale.getISO3Country());
            }

            final Locale iso3Locale = new Locale(userLocale.getISO3Language(), userLocale.getISO3Country());

            for (final VRLanguageIBM language : getLanguages()) {
                if (DEBUG) {
                    Log.i(CLS_NAME, "language: " + language.getLocaleString());
                }

                tokens = new StringTokenizer(language.getLocaleString(), "-");
                locale = new Locale(tokens.nextToken(), tokens.nextToken());

                if (DEBUG) {
                    Log.i(CLS_NAME, "locale: " + locale.toString());
                }

                if (locale.equals(iso3Locale)) {
                    if (DEBUG) {
                        Log.i(CLS_NAME, "Locale full match");
                    }
                    return language;
                }
            }

        } catch (final MissingResourceException e) {
            if (DEBUG) {
                Log.e(CLS_NAME, "getLanguage: iso3 language/country error. checking language");
                e.printStackTrace();
            }
        }

        try {

            final Locale iso3Locale = new Locale(userLocale.getISO3Language());

            for (final VRLanguageIBM language : getLanguages()) {
                if (DEBUG) {
                    Log.i(CLS_NAME, "language: " + language.getLocaleString());
                }

                tokens = new StringTokenizer(language.getLocaleString(), "-");
                locale = new Locale(tokens.nextToken(), tokens.nextToken());

                if (DEBUG) {
                    Log.i(CLS_NAME, "locale: " + locale.toString());
                }

                // Perhaps this should not return true for many cases.
                if (locale.getLanguage().matches(iso3Locale.getISO3Language())) {
                    if (DEBUG) {
                        Log.i(CLS_NAME, "Locale Language match");
                    }
                    return language;
                }
            }

        } catch (final MissingResourceException e) {
            if (DEBUG) {
                Log.e(CLS_NAME, "getLanguage: iso3 language error. returning Default English");
                e.printStackTrace();
            }
        }

        return ENGLISH_US;
    }

    public static VRLanguageIBM getVRLanguageIBM(final String name) {
        if (DEBUG) {
            Log.i(CLS_NAME, "getVRLanguageIBM: " + name);
        }

        if (name != null) {

            try {
                return Enum.valueOf(VRLanguageIBM.class, name.trim().toUpperCase());
            } catch (final IllegalArgumentException e) {
                if (DEBUG) {
                    Log.w(CLS_NAME, "getVRLanguageIBM: IllegalArgumentException");
                    e.printStackTrace();
                }
                return VRLanguageIBM.ENGLISH_US;
            }

        } else {
            if (DEBUG) {
                Log.w(CLS_NAME, "getVRLanguageIBM: name null");
            }
            return VRLanguageIBM.ENGLISH_US;
        }
    }

}
