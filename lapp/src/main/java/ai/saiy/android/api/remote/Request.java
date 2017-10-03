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

/**
 * Created by benrandall76@gmail.com on 27/02/2016.
 */
public class Request {

    /**
     * Key used to retrieve an {@code ArrayList<String>} from the {@link android.os.Bundle} passed to the
     * {@link SaiyListener#onSpeechResults(android.os.Bundle, String)} These strings are the possible
     * recognition results, where the first element is the most likely candidate.
     */
    public static final String RESULTS_RECOGNITION = "results_recognition";

    /**
     * Key used to retrieve a float array from the {@link android.os.Bundle} passed to the
     * {@link SaiyListener#onSpeechResults(android.os.Bundle, String)} The array should be
     * the same size as the ArrayList provided in {@link #RESULTS_RECOGNITION}, and should contain
     * values ranging from 0.0 to 1.0, or -1 to represent an unavailable confidence score.
     * <p>
     * Confidence values close to 1.0 indicate high confidence (the speech recognizer is confident
     * that the recognition result is correct), while values close to 0.0 indicate low confidence.
     * <p>
     * This value is optional and might not be provided.
     */
    public static final String CONFIDENCE_SCORES = "confidence_scores";

    /**
     * Key used to retrieve a string from the {@link android.os.Bundle} passed to the
     * {@link SaiyListener#onSpeechResults(android.os.Bundle, String)} This string is either a string
     * representation of the complete results, or the JSON structured NLU response.
     */
    public static final String RESULTS_NLU = "results_nlu";

    /**
     * Constant used to denote the parent JSONObject from a remote implementation
     */
    public static final String RESULTS = "results";

    /**
     * Constant used to denote the speech string from a remote implementation
     */
    public static final String SPEECH = "speech";

    /**
     * Constant used to denote the speech confidence float value from a remote implementation
     */
    public static final String CONFIDENCE = "confidence";

    /**
     * Network operation timed out.
     */
    public static final int ERROR_NETWORK_TIMEOUT = 1;
    /**
     * Other network related errors.
     */
    public static final int ERROR_NETWORK = 2;
    /**
     * Audio recording error.
     */
    public static final int ERROR_AUDIO = 3;
    /**
     * Server sends error status.
     */
    public static final int ERROR_SERVER = 4;
    /**
     * Other client side errors.
     */
    public static final int ERROR_CLIENT = 5;
    /**
     * No speech input
     */
    public static final int ERROR_SPEECH_TIMEOUT = 6;
    /**
     * No recognition result matched.
     */
    public static final int ERROR_NO_MATCH = 7;
    /**
     * RecognitionService busy.
     */
    public static final int ERROR_RECOGNIZER_BUSY = 8;
    /**
     * Insufficient permissions
     */
    public static final int ERROR_INSUFFICIENT_PERMISSIONS = 9;
}
