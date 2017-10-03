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
 * Created by benrandall76@gmail.com on 14/03/2016.
 */
public enum NLULanguageNuance {

    English_US("eng-USA");

    private static final boolean DEBUG = Defaults.getLogging();
    private static final String CLS_NAME = NLULanguageNuance.class.getSimpleName();

    private final String localeString;

    NLULanguageNuance(final String localeString) {
        this.localeString = localeString;
    }

    public String getLocaleString() {
        return localeString;
    }

    public static NLULanguageNuance[] getLanguages() {
        return NLULanguageNuance.values();
    }

    public static boolean isLanguageAvailable(@NonNull final Locale userLocale) {

        final String localeString = userLocale.toString();

        try {

            if (DEBUG) {
                Log.i(CLS_NAME, "userLocale: " + localeString);
                Log.i(CLS_NAME, "userLocale.getISO3Language: " + userLocale.getISO3Language());
                Log.i(CLS_NAME, "userLocale.getISO3Country : " + userLocale.getISO3Country());
            }

            final Locale iso3Locale = new Locale(userLocale.getISO3Language(), userLocale.getISO3Country());

            StringTokenizer tokens;
            Locale locale;

            for (final NLULanguageNuance language : getLanguages()) {
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

            if (DEBUG) {
                Log.i(CLS_NAME, "No full Locale match - checking language only");
            }

            for (final NLULanguageNuance language : getLanguages()) {
                if (DEBUG) {
                    Log.i(CLS_NAME, "language: " + language.getLocaleString());
                }

                tokens = new StringTokenizer(language.getLocaleString(), "-");
                locale = new Locale(tokens.nextToken(), tokens.nextToken());

                if (DEBUG) {
                    Log.i(CLS_NAME, "locale: " + locale.toString());
                }

                // Perhaps this should not return true for many cases.
                if (locale.getISO3Language().matches(iso3Locale.getISO3Language())) {
                    if (DEBUG) {
                        Log.i(CLS_NAME, "Locale Language match");
                    }
                    return true;
                }
            }

        } catch (final MissingResourceException e) {
            if (DEBUG) {
                Log.e(CLS_NAME, "isLanguageAvailable: iso3 error. returning false");
                e.printStackTrace();
            }
        }

        return false;
    }

    public static NLULanguageNuance getLanguage(@NonNull final Locale userLocale) {

        final String localeString = userLocale.toString();

        try {

            if (DEBUG) {
                Log.i(CLS_NAME, "userLocale: " + localeString);
                Log.i(CLS_NAME, "userLocale.getISO3Language: " + userLocale.getISO3Language());
                Log.i(CLS_NAME, "userLocale.getISO3Country : " + userLocale.getISO3Country());
            }

            final Locale iso3Locale = new Locale(userLocale.getISO3Language(), userLocale.getISO3Country());

            StringTokenizer tokens;
            Locale locale;

            for (final NLULanguageNuance language : getLanguages()) {
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

            if (DEBUG) {
                Log.i(CLS_NAME, "No full Locale match - checking language only");
            }

            for (final NLULanguageNuance language : getLanguages()) {
                if (DEBUG) {
                    Log.i(CLS_NAME, "language: " + language.getLocaleString());
                }

                tokens = new StringTokenizer(language.getLocaleString(), "-");
                locale = new Locale(tokens.nextToken(), tokens.nextToken());

                if (DEBUG) {
                    Log.i(CLS_NAME, "locale: " + locale.toString());
                }

                // Perhaps this should not return true for many cases.
                if (locale.getISO3Language().matches(iso3Locale.getISO3Language())) {
                    if (DEBUG) {
                        Log.i(CLS_NAME, "Locale Language match");
                    }
                    return language;
                }
            }

        } catch (final MissingResourceException e) {
            if (DEBUG) {
                Log.e(CLS_NAME, "getLanguage: iso3 error. returning Default English");
                e.printStackTrace();
            }
        }

        return English_US;
    }
}
