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

import ai.saiy.android.service.ISaiyListener;

import android.os.Bundle;

interface ISaiy {

   /**
    * Check to see if Saiy has returned a spoof binder object to decline the connection
    */
    boolean isReal();

   /**
    * Make a request to speak and then immediately start the voice recognition
    *
    * @param utterance the utterance to be spoken
    */
    oneway void speakListen(in ISaiyListener listener, in Bundle params);

   /**
    * Make a request to speak
    *
    * @param utterance the utterance to be spoken
    */
    oneway void speakOnly(in ISaiyListener listener, in Bundle params);
}
