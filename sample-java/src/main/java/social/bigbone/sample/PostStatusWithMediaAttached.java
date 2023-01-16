package social.bigbone.sample;

import social.bigbone.MastodonClient;
import social.bigbone.api.entity.Attachment;
import social.bigbone.api.entity.Status.Visibility;
import social.bigbone.api.exception.BigBoneRequestException;
import social.bigbone.api.method.Media;
import social.bigbone.api.method.Statuses;

import java.io.File;
import java.util.Collections;
import java.util.List;

public class PostStatusWithMediaAttached {
    public static void main(final String[] args) throws BigBoneRequestException {
        final String instance = args[0];
        final String accessToken = args[1];

        // Instantiate client
        final MastodonClient client = new MastodonClient.Builder(instance)
            .accessToken(accessToken)
            .build();

        // Read file from resources folder
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        final File uploadFile = new File(classLoader.getResource("castle.jpg").getFile());

        // Upload image to Mastodon
        final Media media = new Media(client);
        final Attachment uploadedFile = media.postMedia(uploadFile, "image/jpg").execute();
        final String mediaId = uploadedFile.getId();

        // Post status with media attached
        final Statuses statuses = new Statuses(client);
        final String statusText = "Status posting test";
        final String inReplyToId = null;
        final List<String> mediaIds = Collections.singletonList(mediaId);
        final boolean sensitive = false;
        final String spoilerText = "A castle";
        final Visibility visibility = Visibility.Private;
        statuses.postStatus(statusText, inReplyToId, mediaIds, sensitive, spoilerText, visibility).execute();
    }
}
