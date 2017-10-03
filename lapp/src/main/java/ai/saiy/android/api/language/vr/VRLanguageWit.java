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

public enum VRLanguageWit {

    ALBANIAN("sqi-ALB"),
    ARABIC("ara-XWW"),
    AZERBAIJANI("aze-AZE"),
    BENGALI("ben-BDG"),
    BOSNIAN("bos-BIH"),
    BULGARIAN("bul-BUR"),
    BURMESE("mya-MMR"),
    CATALAN("cat-ESP"),
    CHINESE("zho-CHN"),
    CROATIAN("hrv-HRV"),
    CZECH("ces-CZE"),
    DANISH("dan-DNK"),
    DUTCH("nld-NLD"),
    ENGLISH("eng-GBR"),
    ESTONIAN("est-EST"),
    FINNISH("fin-FIN"),
    FRENCH("fra-FRA"),
    GEORGIAN("kat-GEO"),
    GERMAN("deu-DEU"),
    GREEK("ell-GRC"),
    HEBREW("heb-ISR"),
    HINDI("hin-IND"),
    HUNGARIAN("hun-HUN"),
    ICELANDIC("isl-ISL"),
    INDONESIAN("ind-IDN"),
    ITALIAN("ita-ITA"),
    JAPANESE("jpn-JPN"),
    KOREAN("kor-KOR"),
    LATIN("aze-AZE"),
    LITHUANIAN("lit-LTU"),
    MACEDONIAN("mkd-MKD"),
    MALAY("zlm-MYS"),
    NORWEGIAN("nor-NOR"),
    PERSIAN("fas-IRN"),
    POLISH("pol-POL"),
    PORTUGUESE("por-PRT"),
    ROMANIAN("ron-ROU"),
    RUSSIAN("rus-RUS"),
    SERBIAN("srp-SRB"),
    SLOVAK("slk-SVK"),
    SLOVENIAN("slv-SVN"),
    SPANISH("spa-ESP"),
    SWAHILI("swa-KEN"),
    SWEDISH("swe-SWE"),
    TAGALOG("tgl-PHL"),
    TAMIL("tam-IND"),
    THAI("tha-THA"),
    TURKISH("tur-TUR"),
    UKRAINIAN("ukr-UKR"),
    VIETNAMESE("vie-VNM");

    private static final boolean DEBUG = Defaults.getLogging();
    private static final String CLS_NAME = VRLanguageWit.class.getSimpleName();

    private final String localeString;

    VRLanguageWit(final String localeString) {
        this.localeString = localeString;
    }

    public String getLocaleString() {
        return localeString;
    }

    public static VRLanguageWit[] getLanguages() {
        return VRLanguageWit.values();
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

            for (final VRLanguageWit language : getLanguages()) {
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

            for (final VRLanguageWit language : getLanguages()) {
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

    public static VRLanguageWit getLanguage(@NonNull final Locale userLocale) {

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

            for (final VRLanguageWit language : getLanguages()) {
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

            for (final VRLanguageWit language : getLanguages()) {
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

        return ENGLISH;
    }

    public static VRLanguageWit getVRLanguageWit(final String name) {
        if (DEBUG) {
            Log.i(CLS_NAME, "getVRLanguageWit: " + name);
        }

        if (name != null) {

            try {
                return Enum.valueOf(VRLanguageWit.class, name.trim().toUpperCase());
            } catch (final IllegalArgumentException e) {
                if (DEBUG) {
                    Log.w(CLS_NAME, "getVRLanguageWit: IllegalArgumentException");
                    e.printStackTrace();
                }
                return VRLanguageWit.ENGLISH;
            }

        } else {
            if (DEBUG) {
                Log.w(CLS_NAME, "getVRLanguageWit: name null");
            }
            return VRLanguageWit.ENGLISH;
        }
    }

}
