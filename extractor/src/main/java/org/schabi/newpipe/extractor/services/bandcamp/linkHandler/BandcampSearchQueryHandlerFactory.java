// Created by Fynn Godau 2019, licensed GNU GPL version 3 or later

package org.schabi.newpipe.extractor.services.bandcamp.linkHandler;

import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.linkhandler.SearchQueryHandlerFactory;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.schabi.newpipe.extractor.services.bandcamp.extractors.BandcampExtractorHelper.BASE_URL;
import static org.schabi.newpipe.extractor.utils.Utils.buildSearchParameters;

public class BandcampSearchQueryHandlerFactory extends SearchQueryHandlerFactory {


    @Override
    public String getUrl(final String query, final List<String> contentFilter, final String sortFilter)
            throws ParsingException {
        try {
            final Map<String, String> params = new HashMap<>();
            params.put("q", query);
            params.put("page", "1");
            return BASE_URL + "/search?" + buildSearchParameters(params);
        } catch (final UnsupportedEncodingException e) {
            throw new ParsingException("query \"" + query + "\" could not be encoded", e);
        }
    }
}
