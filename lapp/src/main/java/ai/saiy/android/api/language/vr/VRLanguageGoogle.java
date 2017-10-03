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
 * Created by benrandall76@gmail.com on 15/03/2016.
 */
public enum VRLanguageGoogle {

    AFRIKAANS_SOUTH_AFRICA("af-ZA"),
    INDONESIAN_INDONESIA("id-ID"),
    MALAY_MALAYSIA("ms-MY"),
    CATALAN_SPAIN("ca-ES"),
    CZECH_CZECH_REPUBLIC("cs-CZ"),
    DANISH_DENMARK("da-DK"),
    GERMAN_GERMANY("de-DE"),
    ENGLISH_AUSTRALIA("en-AU"),
    ENGLISH_CANADA("en-CA"),
    ENGLISH_UNITED_KINGDOM("en-GB"),
    ENGLISH_INDIA("en-IN"),
    ENGLISH_IRELAND("en-IE"),
    ENGLISH_NEW_ZEALAND("en-NZ"),
    ENGLISH_PHILIPPINES("en-PH"),
    ENGLISH_SOUTH_AFRICA("en-ZA"),
    ENGLISH_UNITED_STATES("en-US"),
    SPANISH_ARGENTINA("es-AR"),
    SPANISH_BOLIVIA("es-BO"),
    SPANISH_CHILE("es-CL"),
    SPANISH_COLOMBIA("es-CO"),
    SPANISH_COSTA_RICA("es-CR"),
    SPANISH_ECUADOR("es-EC"),
    SPANISH_EL_SALVADOR("es-SV"),
    SPANISH_SPAIN("es-ES"),
    Spanish_United_States("es-US"),
    SPANISH_GUATEMALA("es-GT"),
    SPANISH_HONDURAS("es-HN"),
    SPANISH_MEXICO("es-MX"),
    SPANISH_NICARAGUA("es-NI"),
    SPANISH_PANAMA("es-PA"),
    SPANISH_PARAGUAY("es-PY"),
    SPANISH_PERU("es-PE"),
    SPANISH_PUERTO_RICO("es-PR"),
    SPANISH_DOMINICAN_REPUBLIC("es-DO"),
    SPANISH_URUGUAY("es-UY"),
    SPANISH_VENEZUELA("es-VE"),
    BASQUE_SPAIN("eu-ES"),
    FILIPINO_PHILIPPINES("fil-PH"),
    FRENCH_FRANCE("fr-FR"),
    GALICIAN_SPAIN("gl-ES"),
    CROATIAN_CROATIA("hr-HR"),
    ZULU_SOUTH_AFRICA("zu-ZA"),
    ICELANDIC_ICELAND("is-IS"),
    ITALIAN_ITALY("it-IT"),
    LITHUANIAN_LITHUANIA("lt-LT"),
    HUNGARIAN_HUNGARY("hu-HU"),
    DUTCH_NETHERLANDS("nl-NL"),
    NORWEGIAN_BOKMAL_NORWAY("nb-NO"),
    POLISH_POLAND("pl-PL"),
    PORTUGUESE_BRAZIL("pt-BR"),
    PORTUGUESE_PORTUGAL("pt-PT"),
    ROMANIAN_ROMANIA("ro-RO"),
    SLOVAK_SLOVAKIA("sk-SK"),
    SLOVENIAN_SLOVENIA("sl-SI"),
    FINNISH_FINLAND("fi-FI"),
    SWEDISH_SWEDEN("sv-SE"),
    VIETNAMESE_VIETNAM("vi-VN"),
    TURKISH_TURKEY("tr-TR"),
    GREEK_GREECE("el-GR"),
    BULGARIAN_BULGARIA("bg-BG"),
    RUSSIAN_RUSSIA("ru-RU"),
    SERBIAN_SERBIA("sr-RS"),
    UKRAINIAN_UKRAINE("uk-UA"),
    HEBREW_ISRAEL("he-IL"),
    ARABIC_ISRAEL("ar-IL"),
    ARABIC_JORDAN("ar-JO"),
    ARABIC_UNITED_ARAB_EMIRATES("ar-AE"),
    ARABIC_BAHRAIN("ar-BH"),
    ARABIC_ALGERIA("ar-DZ"),
    ARABIC_SAUDI_ARABIA("ar-SA"),
    ARABIC_IRAQ("ar-IQ"),
    ARABIC_KUWAIT("ar-KW"),
    ARABIC_MOROCCO("ar-MA"),
    ARABIC_TUNISIA("ar-TN"),
    ARABIC_OMAN("ar-OM"),
    ARABIC_STATE_OF_PALESTINE("ar-PS"),
    ARABIC_QATAR("ar-QA"),
    ARABIC_LEBANON("ar-LB"),
    ARABIC_EGYPT("ar-EG"),
    PERSIAN_IRAN("fa-IR"),
    HINDI_INDIA("hi-IN"),
    THAI_THAILAND("th-TH"),
    KOREAN_SOUTH_KOREA("ko-KR"),
    CHINESE_MANDARIN_TRADITIONAL_TAIWAN("cmn-Hant-TW"),
    CHINESE_CANTONESE_TRADITIONAL_HONG_KONG("yue-Hant-HK"),
    JAPANESE_JAPAN("ja-JP"),
    Chinese_Mandarin_Simplified_Hong_Kong("cmn-Hans-HK"),
    CHINESE_MANDARIN_SIMPLIFIED_CHINA("cmn-Hans-CN");

    private static final boolean DEBUG = Defaults.getLogging();
    private static final String CLS_NAME = VRLanguageGoogle.class.getSimpleName();

    private final String localeString;

    VRLanguageGoogle(final String localeString) {
        this.localeString = localeString;
    }

    public String getLocaleString() {
        return localeString;
    }

    public static VRLanguageGoogle[] getLanguages() {
        return VRLanguageGoogle.values();
    }

    public static boolean isLanguageAvailable(@NonNull final Locale userLocale) {

        final String localeString = userLocale.toString();

        StringTokenizer tokens;
        Locale locale;

        try {

            if (DEBUG) {
                Log.i(CLS_NAME, "userLocale: " + localeString);
                Log.i(CLS_NAME, "userLocale.getLanguage: " + userLocale.getLanguage());
                Log.i(CLS_NAME, "userLocale.getCountry : " + userLocale.getCountry());
            }

            final Locale formattedLocale = new Locale(userLocale.getLanguage(), userLocale.getCountry());

            for (final VRLanguageGoogle language : getLanguages()) {
                if (DEBUG) {
                    Log.i(CLS_NAME, "language: " + language.getLocaleString());
                }

                tokens = new StringTokenizer(language.getLocaleString(), "-");
                locale = new Locale(tokens.nextToken(), tokens.nextToken());

                if (DEBUG) {
                    Log.i(CLS_NAME, "locale: " + locale.toString());
                }

                if (locale.equals(formattedLocale)) {
                    if (DEBUG) {
                        Log.i(CLS_NAME, "Locale full match");
                    }
                    return true;
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

            for (final VRLanguageGoogle language : getLanguages()) {
                if (DEBUG) {
                    Log.i(CLS_NAME, "language: " + language.getLocaleString());
                }

                tokens = new StringTokenizer(language.getLocaleString(), "-");
                locale = new Locale(tokens.nextToken(), tokens.nextToken());

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
            }

        } catch (final MissingResourceException e) {
            if (DEBUG) {
                Log.e(CLS_NAME, "isLanguageAvailable: language error. returning false");
                e.printStackTrace();
            }
        }

        return false;
    }

    public static VRLanguageGoogle getLanguage(@NonNull final Locale userLocale) {

        final String localeString = userLocale.toString();
        StringTokenizer tokens;
        Locale locale;


        try {

            if (DEBUG) {
                Log.i(CLS_NAME, "userLocale: " + localeString);
                Log.i(CLS_NAME, "userLocale.getLanguage: " + userLocale.getLanguage());
                Log.i(CLS_NAME, "userLocale.getCountry : " + userLocale.getCountry());
            }

            final Locale formattedLocale = new Locale(userLocale.getLanguage(), userLocale.getCountry());

            for (final VRLanguageGoogle language : getLanguages()) {
                if (DEBUG) {
                    Log.i(CLS_NAME, "language: " + language.getLocaleString());
                }

                tokens = new StringTokenizer(language.getLocaleString(), "-");
                locale = new Locale(tokens.nextToken(), tokens.nextToken());

                if (DEBUG) {
                    Log.i(CLS_NAME, "locale: " + locale.toString());
                }

                if (locale.equals(formattedLocale)) {
                    if (DEBUG) {
                        Log.i(CLS_NAME, "Locale full match");
                    }
                    return language;
                }
            }

        } catch (final MissingResourceException e) {
            if (DEBUG) {
                Log.e(CLS_NAME, "getLanguage: language/country error. checking language");
                e.printStackTrace();
            }
        }

        try {

            final Locale formattedLocale = new Locale(userLocale.getLanguage());

            for (final VRLanguageGoogle language : getLanguages()) {
                if (DEBUG) {
                    Log.i(CLS_NAME, "language: " + language.getLocaleString());
                }

                tokens = new StringTokenizer(language.getLocaleString(), "-");
                locale = new Locale(tokens.nextToken(), tokens.nextToken());

                if (DEBUG) {
                    Log.i(CLS_NAME, "locale: " + locale.toString());
                }

                // Perhaps this should not return true for many cases.
                if (locale.getLanguage().matches(formattedLocale.getLanguage())) {
                    if (DEBUG) {
                        Log.i(CLS_NAME, "Locale Language match");
                    }
                    return language;
                }
            }

        } catch (final MissingResourceException e) {
            if (DEBUG) {
                Log.e(CLS_NAME, "getLanguage: language error. returning Default English");
                e.printStackTrace();
            }
        }

        return ENGLISH_UNITED_STATES;
    }

    public static VRLanguageGoogle getVRLanguageGoogle(final String name) {
        if (DEBUG) {
            Log.i(CLS_NAME, "getVRLanguageGoogle: " + name);
        }

        if (name != null) {

            try {
                return Enum.valueOf(VRLanguageGoogle.class, name.trim().toUpperCase());
            } catch (final IllegalArgumentException e) {
                if (DEBUG) {
                    Log.w(CLS_NAME, "getVRLanguageGoogle: IllegalArgumentException");
                    e.printStackTrace();
                }
                return VRLanguageGoogle.ENGLISH_UNITED_STATES;
            }

        } else {
            if (DEBUG) {
                Log.w(CLS_NAME, "getVRLanguageGoogle: name null");
            }
            return VRLanguageGoogle.ENGLISH_UNITED_STATES;
        }
    }
}
