package com.kishan.core;

import com.google.common.base.Strings;
import com.kishan.UrlShortenerConfiguration;
import com.kishan.core.engines.EngineType;
import com.kishan.db.DB;
import com.kishan.factory.EngineFactory;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Singleton
@AllArgsConstructor(onConstructor = @__(@Inject))
public class UrlShortener {
    private final EngineFactory factory;
    private final UrlShortenerConfiguration urlShortenerConfiguration;

    /**
     * Takes longUrl and alias (readable short url) and returns the shortened URL
     *
     * @param longUrl (the original long url that needs to be shortened)
     * @param alias   (human readable short link_
     * @return shortUrl (string)
     */
    public String shorten(String longUrl, String alias, EngineType type) {
        // this needs to be thread safe
        if (!Strings.isNullOrEmpty(alias) && DB.cache.containsKey(alias)) {
            // this alias is already taken throw error
            throw new IllegalArgumentException("Alias already exists");
        }
        // if the user has provided an alias, we should honor that and should not invoke key generator
        String shortKey;
        if (Strings.isNullOrEmpty(alias)) {
            shortKey = factory.getEngine(type).getUniqueKey(longUrl);
        } else {
            shortKey = alias;
        }
        // fetch the unique key from the KGS
        // update the DB/Cache (here we are storing everything in memory)
        DB.cache.put(shortKey, longUrl);
        return String.format("%s/%s", urlShortenerConfiguration.getBaseUrl(), shortKey);
    }

    /**
     * This finds whether there exists shortUrl, if exists it finds the corresponding long url and redirect it
     *
     * @param shortUrl: the url provided by the service in the exchange of long url
     * @return long url
     */
    public String resolver(String shortUrl) {
        try {
            String longUrl = DB.cache.get(shortUrl);
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
