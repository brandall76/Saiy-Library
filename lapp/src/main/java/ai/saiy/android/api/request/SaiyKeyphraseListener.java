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

package ai.saiy.android.api.request;

/**
 * Created by benrandall76@gmail.com on 26/04/2016.
 */
public interface SaiyKeyphraseListener {

    /**
     * Asynchronous response from sending a {@link SaiyKeyphrase} request. After sending
     * {@link SaiyKeyphrase#sendRequest(int)} this Interface will return the result.
     *
     * @param result    one of {@link android.app.Activity#RESULT_OK} or {@link android.app.Activity#RESULT_CANCELED}
     * @param requestId the id of the {@link SaiyKeyphrase#sendRequest(int)}
     */
    void onEnrollKeyphrase(final int result, final int requestId);
}
