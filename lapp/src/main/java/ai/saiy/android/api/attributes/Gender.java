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

package ai.saiy.android.api.attributes;

import android.util.Log;

import ai.saiy.android.api.Defaults;

/**
 * Created by benrandall76@gmail.com on 14/03/2016.
 */
public enum Gender {
    MALE,
    FEMALE,
    UNDEFINED;

    private static final boolean DEBUG = Defaults.getLogging();
    private static final String CLS_NAME = Gender.class.getSimpleName();

    public static Gender getGender(final int gender) {

        final Gender[] genders = Gender.values();

        if (genders.length > gender) {
            if (DEBUG) {
                Log.i(CLS_NAME, "getGender: " + genders[gender].toString());
            }
            return genders[gender];
        } else {
            if (DEBUG) {
                Log.w(CLS_NAME, "getGender: out of bounds. Returning UNDEFINED");
            }
            return Gender.UNDEFINED;
        }
    }
}
