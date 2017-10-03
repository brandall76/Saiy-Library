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

package ai.saiy.android.service;

import android.os.Bundle;

interface ISaiyListener {

    /**
    * The Text to Speech utterance has completed
    *
    * @param requestId the request id sent with the original Saiy request
    */
    oneway void onUtteranceCompleted(in String requestId);

    /**
    * Called when recognition results are ready.
    *
    * @param results a Bundle containing the most likely results (N-best list) and exras
    * @param requestId the request id sent with the original Saiy request
    */
    oneway void onSpeechResults(in Bundle results, in String requestId);

    /**
    * A Text to Speech or voice recognition error occurred.
    *
    * @param error code is defined in {@link Defaults.ERROR}
    * @param requestId the request id sent with the original Saiy request
    */
    oneway void onError(String error, in String requestId);

}
