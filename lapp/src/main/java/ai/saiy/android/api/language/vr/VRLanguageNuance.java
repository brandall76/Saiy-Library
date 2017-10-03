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
 * Created by benrandall76@gmail.com on 14/03/2016.
 */
public enum VRLanguageNuance {

    ARABIC_EGYPT("ara-EGY"),
    ARABIC_SAUDI_ARABIA("ara-SAU"),
    ARABIC_INTERNATIONAL("ara-XWW"),
    BAHASA_INDONESIA("ind-IDN"),
    CANTONESE_SIMPLIFIED("yue-CHN"),
    CATALAN("cat-ESP"),
    CROATIAN("hrv-HRV"),
    CZECH("ces-CZE"),
    DANISH("dan-DNK"),
    DUTCH("nld-NLD"),
    ENGLISH_AUSTRALIA("eng-AUS"),
    ENGLISH_GB("eng-GBR"),
    ENGLISH_US("eng-USA"),
    ENGLISH_INDIA("eng-IND"),
    FINNISH("fin-FIN"),
    FRENCH_CANADA("fra-CAN"),
    FRENCH_FRANCE("fra-FRA"),
    GERMAN("deu-DEU"),
    GREEK("ell-GRC"),
    HEBREW("heb-ISR"),
    HINDI("hin-IND"),
    HUNGARIAN("hun-HUN"),
    ITALIAN("ita-ITA"),
    JAPANESE("jpn-JPN"),
    KOREAN("kor-KOR"),
    MALAY("zlm-MYS"),
    MANDARIN_CHINA_SIMPLIFIED("cmn-CHN"),
    MANDARIN_TAIWAN_TRADITIONAL("cmn-TWN"),
    NORWEGIAN("nor-NOR"),
    POLISH("pol-POL"),
    PORTUGUESE_BRAZIL("por-BRA"),
    PORTUGUESE_PORTUGAL("por-PRT"),
    ROMANIAN("ron-ROU"),
    RUSSIAN("rus-RUS"),
    SLOVAK("slk-SVK"),
    SPANISH_SPAIN("spa-ESP"),
    SPANISH_LATAM("spa-XLA"),
    SWEDISH("swe-SWE"),
    THAI("tha-THA"),
    TURKISH("tur-TUR"),
    UKRAINIAN("ukr-UKR"),
    VIETNAMESE("vie-VNM");

    private static final boolean DEBUG = Defaults.getLogging();
    private static final String CLS_NAME = VRLanguageNuance.class.getSimpleName();

    private final String localeString;

    VRLanguageNuance(final String localeString) {
        this.localeString = localeString;
    }

    public String getLocaleString() {
        return localeString;
    }

    public static VRLanguageNuance[] getLanguages() {
        return VRLanguageNuance.values();
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

            for (final VRLanguageNuance language : getLanguages()) {
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

            for (final VRLanguageNuance language : getLanguages()) {
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

    public static VRLanguageNuance getLanguage(@NonNull final Locale userLocale) {

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

            for (final VRLanguageNuance language : getLanguages()) {
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

            for (final VRLanguageNuance language : getLanguages()) {
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

    public static VRLanguageNuance getVRLanguageNuance(final String name) {
        if (DEBUG) {
            Log.i(CLS_NAME, "getVRLanguageNuance: " + name);
        }

        if (name != null) {

            try {
                return Enum.valueOf(VRLanguageNuance.class, name.trim().toUpperCase());
            } catch (final IllegalArgumentException e) {
                if (DEBUG) {
                    Log.w(CLS_NAME, "getVRLanguageNuance: IllegalArgumentException");
                    e.printStackTrace();
                }
                return VRLanguageNuance.ENGLISH_US;
            }

        } else {
            if (DEBUG) {
                Log.w(CLS_NAME, "getVRLanguageNuance: name null");
            }
            return VRLanguageNuance.ENGLISH_US;
        }
    }
}
