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

import android.content.Context;
import android.support.annotation.NonNull;

import ai.saiy.android.api.R;

/**
 * Helper class to handle specific library errors
 * <p>
 * Created by benrandall76@gmail.com on 24/02/2016.
 */
public class RemoteError extends RuntimeException {

    public RemoteError() {
        super();
    }

    public RemoteError(@NonNull final String detailMessage) {
        super(detailMessage);
    }

    public RemoteError(@NonNull final String detailMessage, final Throwable throwable) {
        super(detailMessage, throwable);
    }

    public RemoteError(@NonNull final Throwable throwable) {
        super(throwable);
    }

    public static RemoteError throwUnknownAction(@NonNull final Context ctx) {
        return new RemoteError(ctx.getString(R.string.error_action));
    }

    public static RemoteError throwUnknownTTSProvider(@NonNull final Context ctx) {
        return new RemoteError(ctx.getString(R.string.error_provider_tts));
    }

    public static RemoteError throwUnknownVRProvider(@NonNull final Context ctx) {
        return new RemoteError(ctx.getString(R.string.error_provider_vr));
    }

    public static RemoteError throwUnknownAPIKeyGoogle(@NonNull final Context ctx) {
        return new RemoteError(ctx.getString(R.string.error_api_google_chromium));
    }

    public static RemoteError throwUnknownGoogleCloudConfig(@NonNull final Context ctx) {
        return new RemoteError(ctx.getString(R.string.error_api_google_cloud));
    }

    public static RemoteError throwUnknownAPIKeyAPIAI(@NonNull final Context ctx) {
        return new RemoteError(ctx.getString(R.string.error_api_api_ai));
    }

    public static RemoteError throwUnknownAPIKeyMicrosoft(@NonNull final Context ctx) {
        return new RemoteError(ctx.getString(R.string.error_api_microsoft));
    }

    public static RemoteError throwUnknownAPIKeyWit(@NonNull final Context ctx) {
        return new RemoteError(ctx.getString(R.string.error_api_wit));
    }

    public static RemoteError throwUnknownAPIKeyIBM(@NonNull final Context ctx) {
        return new RemoteError(ctx.getString(R.string.error_api_ibm));
    }

    public static RemoteError throwUnknownAPIKeyNuance(@NonNull final Context ctx) {
        return new RemoteError(ctx.getString(R.string.error_api_nuance));
    }

    public static RemoteError throwNuanceNLUConfig(@NonNull final Context ctx) {
        return new RemoteError(ctx.getString(R.string.error_api_nuance_nlu));
    }
}
