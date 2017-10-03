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

package ai.saiy.android.api.remote;


import android.os.Bundle;

import ai.saiy.android.api.Defaults;
import ai.saiy.android.api.request.SaiyRequestParams;

/**
 * Client facing interface to connect to the Saiy request service
 * <p>
 * The parameters returned are guaranteed not to be null. However, if Saiy had previously
 * detected such a possible outcome, it would change the results to their default settings:
 * <p>
 * The request id will default to {@link SaiyRequestParams} ID_UNKNOWN
 * The error will default to {@link Defaults.ERROR} ERROR_UNKNOWN
 * The providerTTS will default to {@link Defaults.TTS}TTS.LOCAL
 * The providerVR currently defaults to {@link Defaults.VR}VR.GOOGLE_CHROMIUM
 * <p>
 * Created by benrandall76@gmail.com on 27/02/2016.
 */
public interface SaiyListener {

    /**
     * The Text to Speech utterance has completed
     *
     * @param requestId the request id sent with the original Saiy request
     */
    void onUtteranceCompleted(final String requestId);

    /**
     * Called when recognition results are ready.
     *
     * @param results   the Bundle containing the most likely results (N-best list) and extras.
     * @param requestId the request id sent with the original Saiy request
     */
    void onSpeechResults(final Bundle results, final String requestId);

    /**
     * A Text to Speech or voice recognition error occurred.
     *
     * @param error     code is defined in {@link Defaults.ERROR}
     * @param requestId the request id sent with the original Saiy request
     */
    void onError(final Defaults.ERROR error, final String requestId);
}
