// Created by Fynn Godau 2019, licensed GNU GPL version 3 or later

package org.schabi.newpipe.extractor.services.bandcamp.extractors;

import com.grack.nanojson.JsonArray;
import com.grack.nanojson.JsonObject;
import com.grack.nanojson.JsonParser;
import com.grack.nanojson.JsonParserException;
import org.schabi.newpipe.extractor.NewPipe;
import org.schabi.newpipe.extractor.StreamingService;
import org.schabi.newpipe.extractor.downloader.Downloader;
import org.schabi.newpipe.extractor.exceptions.ExtractionException;
import org.schabi.newpipe.extractor.suggestion.SuggestionExtractor;

import java.io.IOException;
import java.util.*;

import static org.schabi.newpipe.extractor.services.bandcamp.extractors.BandcampExtractorHelper.BASE_API_URL;
import static org.schabi.newpipe.extractor.utils.Utils.buildSearchParameters;

public class BandcampSuggestionExtractor extends SuggestionExtractor {

    private static final String AUTOCOMPLETE_URL = BASE_API_URL + "/fuzzysearch/1/autocomplete?";

    public BandcampSuggestionExtractor(final StreamingService service) {
        super(service);
    }

    @Override
    public List<String> suggestionList(final String query) throws IOException, ExtractionException {
        final Downloader downloader = NewPipe.getDownloader();

        try {
            final Map<String, String> params = new HashMap<>();
            params.put("q", query);
            final JsonObject fuzzyResults = JsonParser.object().from(
                    downloader.get(AUTOCOMPLETE_URL + buildSearchParameters(params)).responseBody()
            );

            final JsonArray jsonArray = fuzzyResults.getObject("auto")
                    .getArray("results");

            final List<String> suggestions = new ArrayList<>();

            for (final Object fuzzyResult : jsonArray) {
                final String res = ((JsonObject) fuzzyResult).getString("name");

                if (!suggestions.contains(res)) suggestions.add(res);
            }

            return suggestions;
        } catch (final JsonParserException e) {
            return Collections.emptyList();
        }

    }
}
