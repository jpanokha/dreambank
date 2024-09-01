package com.dreambank.cerdit.card.application.config;

import com.mongodb.ConnectionString;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

import com.bol.crypt.CryptVault;
import com.bol.secure.CachedEncryptionEventListener;
import com.bol.secure.ReflectionEncryptionEventListener;

import java.util.Base64;

@Configuration
@EnableReactiveMongoRepositories(basePackages = "com.dreambank.cerdit.card.application")
@Slf4j
public class MongoConfig extends AbstractReactiveMongoConfiguration {


    @Value("${spring.data.mongodb.host}")
    private String host;

    @Value("${spring.data.mongodb.port}")
    private String port;

    @Value("${spring.data.mongodb.database}")
    private String database;

    private static final byte[] secretKey = Base64.getDecoder().decode("hqHKBLV83LpCqzKpf8OvutbCs+O5wX5BPu3btWpEvXA=");
    private static final byte[] oldKey = Base64.getDecoder().decode("cUzurmCcL+K252XDJhhWI/A/+wxYXLgIm678bwsE2QM=");


    @Bean
    public CryptVault cryptVault() {
        return new CryptVault()
                .with256BitAesCbcPkcs5PaddingAnd128BitSaltKey(0, oldKey)
                .with256BitAesCbcPkcs5PaddingAnd128BitSaltKey(1, secretKey)
                // can be omitted if it's the highest version
                .withDefaultKeyVersion(1);
    }

    @Bean
    public CachedEncryptionEventListener encryptionEventListener(CryptVault cryptVault) {
        return new CachedEncryptionEventListener(cryptVault);
    }


    @Override
    public MongoClient reactiveMongoClient() {
        //return MongoClients.create("mongodb://mongo_db:27017/docker-db");

        String conn = "mongodb://" + host + ":" + port + "/" + getDatabaseName();
        log.info("Connection String :{}",conn);
        return MongoClients.create(conn);
    }

    @Override
    protected String getDatabaseName() {
        return database;
    }

    @Bean
    public ReactiveMongoTemplate reactiveMongoTemplate() {
        return new ReactiveMongoTemplate(reactiveMongoClient(), getDatabaseName());
    }



}