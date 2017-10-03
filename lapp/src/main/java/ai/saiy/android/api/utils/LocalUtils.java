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

package ai.saiy.android.api.utils;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import ai.saiy.android.api.Defaults;

/**
 * Created by benrandall76@gmail.com on 24/03/2016.
 */
public class LocalUtils {

    /**
     * Prevent instantiation
     */
    public LocalUtils() {
        throw new IllegalArgumentException(Resources.getSystem().getString(android.R.string.no));
    }

    private static final boolean DEBUG = Defaults.getLogging();
    private static final String CLS_NAME = LocalUtils.class.getSimpleName();

    public static final String LOCALE_DELIMITER_1 = "-";
    public static final String LOCALE_DELIMITER_2 = "_";

    /**
     * Utility method to convert a string to a {@link Locale}. If this process fails, the best
     * possible format will be returned.
     *
     * @param stringLocale the string {@link Locale} to convert
     * @return the converted {@link Locale}
     */
    public static Locale stringToLocale(@NonNull final String stringLocale) {

        try {
            StringTokenizer tokens;

            if (stringLocale.contains(LOCALE_DELIMITER_1)) {
                tokens = new StringTokenizer(stringLocale, LOCALE_DELIMITER_1);

                switch (tokens.countTokens()) {

                    case 1:
                        return new Locale(tokens.nextToken());
                    case 2:
                        return new Locale(tokens.nextToken(), tokens.nextToken());
                    case 3:
                        return new Locale(tokens.nextToken(), tokens.nextToken(), tokens.nextToken());
                    default:
                        return new Locale(stringLocale);
                }

            } else if (stringLocale.contains(LOCALE_DELIMITER_2)) {
                tokens = new StringTokenizer(stringLocale, LOCALE_DELIMITER_2);

                switch (tokens.countTokens()) {

                    case 1:
                        return new Locale(tokens.nextToken());
                    case 2:
                        return new Locale(tokens.nextToken(), tokens.nextToken());
                    case 3:
                        return new Locale(tokens.nextToken(), tokens.nextToken(), tokens.nextToken());
                    default:
                        return new Locale(stringLocale);
                }
            } else {
                return new Locale(stringLocale);
            }
        } catch (final NoSuchElementException e) {
            if (DEBUG) {
                Log.w(CLS_NAME, "stringToLocale: NoSuchElementException");
            }
        } catch (final MissingResourceException e) {
            if (DEBUG) {
                Log.w(CLS_NAME, "stringToLocale: MissingResourceException");
            }
        } catch (final NullPointerException e) {
            if (DEBUG) {
                Log.w(CLS_NAME, "stringToLocale: NullPointerException");
            }
        } catch (final Exception e) {
            if (DEBUG) {
                Log.w(CLS_NAME, "stringToLocale: Exception");
            }
        }

        return new Locale(stringLocale);
    }
}