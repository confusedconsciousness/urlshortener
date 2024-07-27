package com.kishan.core;

import com.google.common.base.Strings;
import com.kishan.UrlShortenerConfiguration;
import jakarta.inject.Inject;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@AllArgsConstructor(onConstructor = @__(@Inject))
public class UrlShortenerEngine {

    private final KGS keyGeneratorService;
    private UrlShortenerConfiguration urlShortenerConfiguration;
    private final Map<String, String> cache = new HashMap<>();

    public String shorten(String longUrl, String alias) {
        if (cache.containsKey(alias)) {
            // this alias is already taken throw error
            throw new IllegalArgumentException("Alias already exists");
        }
        // fetch the unique key from the KGS
        String shortKey = keyGeneratorService.getUniqueKey();
        // update the DB/Cache (here we are storing everything in memory)
        cache.put(shortKey, longUrl);
        return String.format("%s/%s", urlShortenerConfiguration.getBaseUrl(), shortKey);
    }


    public String resolver(String shortUrl) {
        try {
            String shortKey = shortUrl.split("/")[1];
            String longUrl = cache.get(shortKey);
            if (Strings.isNullOrEmpty(longUrl)) {
                log.error("No such entry present corresponding to {} URL", shortUrl);
                throw new IllegalArgumentException("No such URL exists");
            }
            return longUrl;
        } catch (Exception e) {
            throw new IllegalArgumentException("Malformed URL");
        }
    }


}
