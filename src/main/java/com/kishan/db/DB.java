package com.kishan.db;

import com.google.inject.Singleton;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class DB {
    public static Map<String, String> cache = new ConcurrentHashMap<>();

}
