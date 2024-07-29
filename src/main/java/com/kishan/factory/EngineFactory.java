package com.kishan.factory;

import com.kishan.core.engines.Engine;
import com.kishan.core.engines.EngineType;
import com.kishan.core.engines.HashingEngine;
import com.kishan.core.engines.UniqueKeyGeneratorEngine;
import lombok.SneakyThrows;

import java.security.NoSuchAlgorithmException;

public class EngineFactory {

    @SneakyThrows
    public Engine getEngine(EngineType type)  {
        switch (type) {
            case HASHING -> {
                return new HashingEngine();
            }
            case KEY_GENERATOR -> {
                return new UniqueKeyGeneratorEngine();
            }
            default -> throw new NoSuchAlgorithmException();

        }
    }
}
