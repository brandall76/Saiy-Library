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

package ai.saiy.android.api.language.tts;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.StringTokenizer;

import ai.saiy.android.api.Defaults;
import ai.saiy.android.api.attributes.Gender;

/**
 * Created by benrandall76@gmail.com on 14/03/2016.
 */
public enum TTSLanguageNuance {

    ARABIC_LAILA("Laila", Gender.FEMALE, "ara-XWW"),
    ARABIC_MAGED("Maged", Gender.MALE, "ara-XWW"),
    ARABIC_TARIK("Tarik", Gender.MALE, "ara-XWW"),
    BAHASA_INDONESIA_DAMAYANTI("Damayanti", Gender.FEMALE, "ind-IDN"),
    BASQUE_MIREN("Miren", Gender.FEMALE, "baq-ESP"),
    CANTONESE_SIN_JI("Sin-Ji", Gender.FEMALE, "yue-CHN"),
    CATALAN_JORDI("Jordi", Gender.MALE, "cat-ESP"),
    CATALAN_MONTSERRAT("Montserrat", Gender.FEMALE, "cat-ESP"),
    CZECH_IVETA("Iveta", Gender.FEMALE, "ces-CZE"),
    CZECH_ZUZANA("Zuzana", Gender.FEMALE, "ces-CZE"),
    DANISH_IDA("Ida", Gender.FEMALE, "dan-DNK"),
    DANISH_MAGNUS("Magnus", Gender.MALE, "dan-DNK"),
    DUTCH_CLAIRE("Claire", Gender.FEMALE, "nld-NLD"),
    DUTCH_XANDER("Xander", Gender.MALE, "nld-NLD"),
    DUTCH_BELGIUM_ELLEN("Ellen", Gender.FEMALE, "nld-BEL"),
    ENGLISH_AUSTRALIA_KAREN("Karen", Gender.FEMALE, "eng-AUS"),
    ENGLISH_AUSTRALIA_LEE("Lee", Gender.MALE, "eng-AUS"),
    ENGLISH_GB_KATE("Kate", Gender.FEMALE, "eng-GBR"),
    ENGLISH_GB_SERENA("Serena", Gender.FEMALE, "eng-GBR"),
    ENGLISH_GB_DANIEL("Daniel", Gender.MALE, "eng-GBR"),
    ENGLISH_GB_OLIVER("Oliver", Gender.MALE, "eng-GBR"),
    ENGLISH_INDIA_VEENA("Veena", Gender.FEMALE, "eng-IND"),
    ENGLISH_IRELAND_MOIRA("Moira", Gender.FEMALE, "eng-IRL"),
    ENGLISH_SCOTLAND_FIONA("Fiona", Gender.FEMALE, "eng-SCT"),
    ENGLISH_SOUTH_AFRICA_TESSA("Tessa", Gender.FEMALE, "eng-ZAF"),
    ENGLISH_US_AVA("Ava", Gender.FEMALE, "eng-USA"),
    ENGLISH_US_ALLISON("Allison", Gender.FEMALE, "eng-USA"),
    ENGLISH_US_SAMANTHA("Samantha", Gender.FEMALE, "eng-USA"),
    ENGLISH_US_SUSAN("Susan", Gender.FEMALE, "eng-USA"),
    ENGLISH_US_ZOE("Zoe", Gender.FEMALE, "eng-USA"),
    ENGLISH_US_TOM("Tom", Gender.MALE, "eng-USA"),
    FINNISH_SATU("Satu", Gender.FEMALE, "fin-FIN"),
    FRENCH_AUDREY_ML("Audrey-ML", Gender.FEMALE, "fra-FRA"),
    FRENCH_THOMAS("Thomas", Gender.MALE, "fra-FRA"),
    FRENCH_AURELIE("Aurelie", Gender.FEMALE, "fra-FRA"),
    FRENCH_CANADA_AMELIE("Amelie", Gender.FEMALE, "fra-CAN"),
    FRENCH_CANADA_CHANTAL("Chantal", Gender.FEMALE, "fra-CAN"),
    FRENCH_CANADA_NICOLAS("Nicolas", Gender.MALE, "fra-CAN"),
    GALICIAN_CARMELA("Carmela", Gender.FEMALE, "glg-ESP"),
    GERMAN_ANNA_ML("Anna-ML", Gender.FEMALE, "deu-DEU"),
    GERMAN_PETRA_ML("Petra-ML", Gender.FEMALE, "deu-DEU"),
    GERMAN_MARKUS("Markus", Gender.MALE, "deu-DEU"),
    GERMAN_YANNICK("Yannick", Gender.MALE, "deu-DEU"),
    GREEK_MELINA("Melina", Gender.FEMALE, "ell-GRC"),
    GREEK_NIKOS("Nikos", Gender.MALE, "ell-GRC"),
    HEBREW_CARMIT("Carmit", Gender.FEMALE, "heb-ISR"),
    HINDI_LEKHA("Lekha", Gender.FEMALE, "hin-IND"),
    HUNGARIAN_MARISKA("Mariska", Gender.FEMALE, "hun-HUN"),
    ITALIAN_ALICE_ML("Alice-ML", Gender.FEMALE, "ita-ITA"),
    ITALIAN_FEDERICA("Federica", Gender.FEMALE, "ita-ITA"),
    ITALIAN_PAOLA("Paola", Gender.FEMALE, "ita-ITA"),
    ITALIAN_LUCA("Luca", Gender.MALE, "ita-ITA"),
    JAPANESE_KYOKO("Kyoko", Gender.FEMALE, "jpn-JPN"),
    JAPANESE_OTOYA("Otoya", Gender.MALE, "jpn-JPN"),
    KOREAN_SORA("Sora", Gender.FEMALE, "kor-KOR"),
    MANDARIN_CHINA_TIAN_TIAN("Tian-Tian", Gender.FEMALE, "cmn-CHN"),
    MANDARIN_TAIWAN_MEI_JIA("Mei-Jia", Gender.FEMALE, "cmn-TWN"),
    NORWEGIAN_NORA("Nora", Gender.FEMALE, "nor-NOR"),
    NORWEGIAN_HENRIK("Henrik", Gender.MALE, "nor-NOR"),
    POLISH_EWA("Ewa", Gender.FEMALE, "pol-POL"),
    POLISH_ZOSIA("Zosia", Gender.FEMALE, "pol-POL"),
    PORTUGUESE_BRAZIL_LUCIANA("Luciana", Gender.FEMALE, "por-BRA"),
    PORTUGUESE_BRAZIL_FELIPE("Felipe", Gender.MALE, "por-BRA"),
    PORTUGUESE_PORTUGAL_CATARINA("Catarina", Gender.FEMALE, "por-PRT"),
    PORTUGUESE_PORTUGAL_JOANA("Joana", Gender.FEMALE, "por-PRT"),
    ROMANIAN_IOANA("Ioana", Gender.FEMALE, "ron-ROU"),
    RUSSIAN_KATYA("Katya", Gender.FEMALE, "rus-RUS"),
    RUSSIAN_MILENA("Milena", Gender.FEMALE, "rus-RUS"),
    RUSSIAN_YURI("Yuri", Gender.MALE, "rus-RUS"),
    SLOVAK_LAURA("Laura", Gender.FEMALE, "slk-SVK"),
    SPANISH_CASTILIAN_MONICA("Monica", Gender.FEMALE, "spa-ESP"),
    SPANISH_CASTILIAN_JORGE("Jorge", Gender.MALE, "spa-ESP"),
    VALENCIAN_EMPAR("Empar", Gender.FEMALE, "spa-ESP"),
    SPANISH_COLUMBIA_SOLEDAD("Soledad", Gender.FEMALE, "spa-COL"),
    SPANISH_COLUMBIA_CARLOS("Carlos", Gender.MALE, "spa-COL"),
    SPANISH_MEXICO_ANGELICA("Angelica", Gender.FEMALE, "spa-MEX"),
    SPANISH_MEXICO_PAULINA("Paulina", Gender.FEMALE, "spa-MEX"),
    SPANISH_MEXICO_JUAN("Juan", Gender.MALE, "spa-MEX"),
    SWEDISH_ALVA("Alva", Gender.FEMALE, "swe-SWE"),
    SWEDISH_OSKAR("Oskar", Gender.MALE, "swe-SWE"),
    THAI_KANYA("Kanya", Gender.FEMALE, "tha-THA"),
    TURKISH_CEM("Cem", Gender.MALE, "tur-TUR"),
    TURKISH_YELDA("Yelda", Gender.FEMALE, "tur-TUR");

    private static final boolean DEBUG = Defaults.getLogging();
    private static final String CLS_NAME = TTSLanguageNuance.class.getSimpleName();

    private final String name;
    private final Gender gender;
    private final String localeString;

    TTSLanguageNuance(final String name, final Gender gender, final String localeString) {
        this.name = name;
        this.gender = gender;
        this.localeString = localeString;
    }

    public Gender getGender() {
        return gender;
    }

    public String getLocaleString() {
        return localeString;
    }

    public String getName() {
        return name;
    }

    public static TTSLanguageNuance[] getVoices() {
        return TTSLanguageNuance.values();
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
            Locale voiceLocale;

            for (final TTSLanguageNuance voice : getVoices()) {
                if (DEBUG) {
                    Log.i(CLS_NAME, "voice: " + voice.getName() + " ~ " + voice.getLocaleString() + " ~ " + voice.getGender().toString());
                }

                tokens = new StringTokenizer(voice.getLocaleString(), "-");
                voiceLocale = new Locale(tokens.nextToken(), tokens.nextToken());

                if (DEBUG) {
                    Log.i(CLS_NAME, "voiceLocale: " + voiceLocale.toString());
                }

                if (voiceLocale.equals(iso3Locale)) {
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

            if (DEBUG) {
                Log.i(CLS_NAME, "userLocale: " + localeString);
                Log.i(CLS_NAME, "userLocale.getISO3Language: " + userLocale.getISO3Language());
            }

            final Locale iso3Locale = new Locale(userLocale.getISO3Language());

            StringTokenizer tokens;
            Locale voiceLocale;

            for (final TTSLanguageNuance voice : getVoices()) {
                if (DEBUG) {
                    Log.i(CLS_NAME, "voice: " + voice.getName() + " ~ " + voice.getLocaleString() + " ~ " + voice.getGender().toString());
                }

                tokens = new StringTokenizer(voice.getLocaleString(), "-");
                voiceLocale = new Locale(tokens.nextToken(), tokens.nextToken());

                if (DEBUG) {
                    Log.i(CLS_NAME, "voiceLocale: " + voiceLocale.toString());
                }

                // Perhaps this should not return true for many cases.
                if (voiceLocale.getLanguage().matches(iso3Locale.getISO3Language())) {
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

    public static TTSLanguageNuance getVoice(@NonNull final Locale userLocale, @NonNull final Gender preferredGender) {

        final String localeString = userLocale.toString();
        final ArrayList<TTSLanguageNuance> voiceLocaleArray = new ArrayList<>();

        StringTokenizer tokens;
        Locale voiceLocale;

        try {

            if (DEBUG) {
                Log.i(CLS_NAME, "userLocale: " + localeString);
                Log.i(CLS_NAME, "userLocale.getISO3Language: " + userLocale.getISO3Language());
                Log.i(CLS_NAME, "userLocale.getISO3Country : " + userLocale.getISO3Country());
            }

            final Locale iso3Locale = new Locale(userLocale.getISO3Language(), userLocale.getISO3Country());

            for (final TTSLanguageNuance voice : getVoices()) {
                if (DEBUG) {
                    Log.i(CLS_NAME, "voice: " + voice.getName() + " ~ " + voice.getLocaleString() + " ~ " + voice.getGender().toString());
                }

                tokens = new StringTokenizer(voice.getLocaleString(), "-");
                voiceLocale = new Locale(tokens.nextToken(), tokens.nextToken());

                if (DEBUG) {
                    Log.i(CLS_NAME, "voiceLocale: " + voiceLocale.toString());
                }

                if (voiceLocale.equals(iso3Locale)) {
                    if (DEBUG) {
                        Log.i(CLS_NAME, "Locale full match");
                    }
                    voiceLocaleArray.add(voice);
                }
            }

        } catch (final MissingResourceException e) {
            if (DEBUG) {
                Log.e(CLS_NAME, "getVoice: iso3 language/country error. Checking language only");
                e.printStackTrace();
            }
        }

        try {

            if (voiceLocaleArray.isEmpty()) {
                if (DEBUG) {
                    Log.i(CLS_NAME, "No full Locale match - checking language only");
                }

                final Locale iso3Locale = new Locale(userLocale.getISO3Language());

                for (final TTSLanguageNuance voice : getVoices()) {
                    if (DEBUG) {
                        Log.i(CLS_NAME, "voice: " + voice.getName() + " ~ " + voice.getLocaleString() + " ~ " + voice.getGender().toString());
                    }

                    tokens = new StringTokenizer(voice.getLocaleString(), "-");
                    voiceLocale = new Locale(tokens.nextToken(), tokens.nextToken());

                    if (DEBUG) {
                        Log.i(CLS_NAME, "voiceLocale: " + voiceLocale.toString());
                    }

                    // Perhaps this should not return true for many cases.
                    if (voiceLocale.getLanguage().matches(iso3Locale.getISO3Language())) {
                        if (DEBUG) {
                            Log.i(CLS_NAME, "Locale Language match");
                        }
                        voiceLocaleArray.add(voice);
                    }
                }
            }

        } catch (final MissingResourceException e) {
            if (DEBUG) {
                Log.e(CLS_NAME, "getVoice: iso3 language error");
                e.printStackTrace();
            }
        }

        if (!voiceLocaleArray.isEmpty()) {

            for (final TTSLanguageNuance voice : voiceLocaleArray) {
                if (DEBUG) {
                    Log.i(CLS_NAME, "voice: " + voice.getName() + " ~ " + voice.getLocaleString() + " ~ " + voice.getGender().toString());
                }

                if (voice.getGender().equals(preferredGender)) {
                    if (DEBUG) {
                        Log.i(CLS_NAME, "Gender match");
                    }
                    return voice;
                }
            }

            return voiceLocaleArray.get(0);
        }

        return FRENCH_AUDREY_ML;
    }

    public static TTSLanguageNuance getTTSLanguageNuance(final String name) {
        if (DEBUG) {
            Log.i(CLS_NAME, "getTTSLanguageNuance: " + name);
        }

        if (name != null) {

            try {
                return Enum.valueOf(TTSLanguageNuance.class, name.trim().toUpperCase());
            } catch (final IllegalArgumentException e) {
                if (DEBUG) {
                    Log.w(CLS_NAME, "getTTSLanguageNuance: IllegalArgumentException");
                    e.printStackTrace();
                }
                return TTSLanguageNuance.FRENCH_AUDREY_ML;
            }

        } else {
            if (DEBUG) {
                Log.w(CLS_NAME, "getTTSLanguageNuance: name null");
            }
            return TTSLanguageNuance.FRENCH_AUDREY_ML;
        }
    }
}
