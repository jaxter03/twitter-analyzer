package com.knoldus;

import twitter4j.*;
import twitter4j.auth.AccessToken;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

/**
 * This class fetch tweets from Twitter Api.
 */
public class TwitterFetcher {

    Twitter twitter;

    TwitterFetcher() {
        twitter = new TwitterFactory().getInstance();
        twitter.setOAuthConsumer("e6uS4phTxImI68qlA6h4V3zwR",
                "M8b4Q3sudgU9mNZgJx1onUlqQYi5h5YCK1GVacjAc8yHDAohFc");
        twitter.setOAuthAccessToken(new AccessToken(
                "160922224-AKOoOasbqi3huqT7uyq4Og0Oqlucn8rKeD9IcUvU",
                "7HgIJUmjOX2AZThvVp7RPWsZwOrW1ffpvkEpjeBSQynnH"));

    }

    /**
     * This function fetch tweets on basis of provided keyword.
     *
     * @param keyword User provided input to fetch tweets.
     * @return Stream of List of tweets.
     * @throws TwitterException TwitterException.
     */
    CompletableFuture<Stream<Status>> fetch(String keyword) throws TwitterException {

        Query query = new Query(keyword);

        CompletableFuture<Stream<Status>> futureTweetsStream = CompletableFuture.supplyAsync(() -> {
            Stream<Status> tweetsStream = null;
            try {
                tweetsStream = twitter.search(query).getTweets().stream();
            } catch (TwitterException e) {
                e.printStackTrace();
            }
            return tweetsStream;
        });

        return futureTweetsStream;


    }


}
