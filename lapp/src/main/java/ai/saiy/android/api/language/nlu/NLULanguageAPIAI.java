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

package ai.saiy.android.api.language.nlu;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.StringTokenizer;

import ai.saiy.android.api.Defaults;

/**
 * Created by benrandall76@gmail.com on 29/09/2016.
 */

public enum NLULanguageAPIAI {

    PORTUGUESE_PORTUGAL("PT"),
    PORTUGUESE_BRAZIL("PT-BR"),
    CHINESE_CANTONESE("ZH-HK"),
    CHINESE_SIMPLIFIED("ZH-CN"),
    CHINESE_TRADITIONAL("ZH-TW"),
    ENGLISH("EN"),
    DUTCH("NL"),
    FRENCH("FR"),
    GERMAN("DE"),
    ITALIAN("IT"),
    JAPANESE("JA"),
    KOREAN("KO"),
    RUSSIAN("RU"),
    SPANISH("ES"),
    UKRAINIAN("UK");

    private static final boolean DEBUG = Defaults.getLogging();
    private static final String CLS_NAME = NLULanguageAPIAI.class.getSimpleName();

    private final String localeString;

    NLULanguageAPIAI(final String localeString) {
        this.localeString = localeString;
    }

    public String getLocaleString() {
        return localeString;
    }

    public static NLULanguageAPIAI[] getLanguages() {
        return NLULanguageAPIAI.values();
    }

    public static boolean isLanguageAvailable(@NonNull final Locale userLocale) {

        final String localeString = userLocale.toString();

        StringTokenizer tokens;
        Locale locale = null;

        try {

            if (DEBUG) {
                Log.i(CLS_NAME, "userLocale: " + localeString);
                Log.i(CLS_NAME, "userLocale.getLanguage: " + userLocale.getLanguage());
                Log.i(CLS_NAME, "userLocale.getCountry : " + userLocale.getCountry());
            }

            final Locale formattedLocale = new Locale(userLocale.getLanguage(), userLocale.getCountry());

            for (final NLULanguageAPIAI language : getLanguages()) {
                if (DEBUG) {
                    Log.i(CLS_NAME, "language: " + language.getLocaleString());
                }

                tokens = new StringTokenizer(language.getLocaleString(), "-");

                switch (tokens.countTokens()) {

                    case 1:
                        locale = new Locale(tokens.nextToken());
                        break;
                    case 2:
                        locale = new Locale(tokens.nextToken(), tokens.nextToken());
                        break;
                    default:
                        break;
                }

                if (locale != null) {

                    if (DEBUG) {
                        Log.i(CLS_NAME, "locale: " + locale.toString());
                    }

                    if (locale.equals(formattedLocale)) {
                        if (DEBUG) {
                            Log.i(CLS_NAME, "Locale full match");
                        }
                        return true;
                    }
                } else {
                    if (DEBUG) {
                        Log.w(CLS_NAME, "Locale null");
                    }
                }
            }

        } catch (final MissingResourceException e) {
            if (DEBUG) {
                Log.e(CLS_NAME, "isLanguageAvailable: language/country error. checking language only");
                e.printStackTrace();
            }
        }

        try {

            final Locale formattedLocale = new Locale(userLocale.getLanguage());

            for (final NLULanguageAPIAI language : getLanguages()) {
                if (DEBUG) {
                    Log.i(CLS_NAME, "language: " + language.getLocaleString());
                }

                tokens = new StringTokenizer(language.getLocaleString(), "-");

                switch (tokens.countTokens()) {

                    case 1:
                        locale = new Locale(tokens.nextToken());
                        break;
                    case 2:
                        locale = new Locale(tokens.nextToken(), tokens.nextToken());
                        break;
                    default:
                        break;
                }

                if (locale != null) {
                    if (DEBUG) {
                        Log.i(CLS_NAME, "locale: " + locale.toString());
                    }

                    // Perhaps this should not return true for many cases.
                    if (locale.getLanguage().matches(formattedLocale.getLanguage())) {
                        if (DEBUG) {
                            Log.i(CLS_NAME, "Locale Language match");
                        }
                        return true;
                    }
                } else {
                    if (DEBUG) {
                        Log.w(CLS_NAME, "Locale language only null");
                    }
                }
            }

        } catch (final MissingResourceException e) {
            if (DEBUG) {
                Log.e(CLS_NAME, "isLanguageAvailable: language error. returning false");
                e.printStackTrace();
            }
        }

        return false;
    }

    public static NLULanguageAPIAI getLanguage(@NonNull final Locale userLocale) {

        final String localeString = userLocale.toString();
        StringTokenizer tokens;
        Locale locale = null;


        try {

            if (DEBUG) {
                Log.i(CLS_NAME, "userLocale: " + localeString);
                Log.i(CLS_NAME, "userLocale.getLanguage: " + userLocale.getLanguage());
                Log.i(CLS_NAME, "userLocale.getCountry : " + userLocale.getCountry());
            }

            final Locale formattedLocale = new Locale(userLocale.getLanguage(), userLocale.getCountry());

            for (final NLULanguageAPIAI language : getLanguages()) {
                if (DEBUG) {
                    Log.i(CLS_NAME, "language: " + language.getLocaleString());
                }

                tokens = new StringTokenizer(language.getLocaleString(), "-");

                switch (tokens.countTokens()) {

                    case 1:
                        locale = new Locale(tokens.nextToken());
                        break;
                    case 2:
                        locale = new Locale(tokens.nextToken(), tokens.nextToken());
                        break;
                    default:
                        break;
                }

                if (locale != null) {

                    if (DEBUG) {
                        Log.i(CLS_NAME, "locale: " + locale.toString());
                    }

                    if (locale.equals(formattedLocale)) {
                        if (DEBUG) {
                            Log.i(CLS_NAME, "Locale full match");
                        }
                        return language;
                    }
                } else {
                    if (DEBUG) {
                        Log.w(CLS_NAME, "Locale null");
                    }
                }
            }

        } catch (final MissingResourceException e) {
            if (DEBUG) {
                Log.e(CLS_NAME, "getLanguage: language/country error. checking language");
                e.printStackTrace();
            }
        }

        try {

            final Locale iso3Locale = new Locale(userLocale.getLanguage());

            for (final NLULanguageAPIAI language : getLanguages()) {
                if (DEBUG) {
                    Log.i(CLS_NAME, "language: " + language.getLocaleString());
                }

                tokens = new StringTokenizer(language.getLocaleString(), "-");

                switch (tokens.countTokens()) {

                    case 1:
                        locale = new Locale(tokens.nextToken());
                        break;
                    case 2:
                        locale = new Locale(tokens.nextToken(), tokens.nextToken());
                        break;
                    default:
                        break;
                }

                if (locale != null) {

                    if (DEBUG) {
                        Log.i(CLS_NAME, "locale: " + locale.toString());
                    }

                    // Perhaps this should not return true for many cases.
                    if (locale.getLanguage().matches(iso3Locale.getLanguage())) {
                        if (DEBUG) {
                            Log.i(CLS_NAME, "Locale Language match");
                        }
                        return language;
                    }
                } else {
                    if (DEBUG) {
                        Log.w(CLS_NAME, "Locale language only null");
                    }
                }
            }

        } catch (final MissingResourceException e) {
            if (DEBUG) {
                Log.e(CLS_NAME, "getLanguage: language error. returning Default English");
                e.printStackTrace();
            }
        }

        return ENGLISH;
    }

    public static NLULanguageAPIAI getNLULanguageAPIAI(final String name) {
        if (DEBUG) {
            Log.i(CLS_NAME, "getNLULanguageAPIAI: " + name);
        }

        if (name != null) {

            try {
                return Enum.valueOf(NLULanguageAPIAI.class, name.trim().toUpperCase());
            } catch (final IllegalArgumentException e) {
                if (DEBUG) {
                    Log.w(CLS_NAME, "getNLULanguageAPIAI: IllegalArgumentException");
                    e.printStackTrace();
                }
                return NLULanguageAPIAI.ENGLISH;
            }

        } else {
            if (DEBUG) {
                Log.w(CLS_NAME, "getNLULanguageAPIAI: name null");
            }
            return NLULanguageAPIAI.ENGLISH;
        }
    }
}
