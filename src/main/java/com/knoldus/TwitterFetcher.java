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
        String consumerKey=System.getenv("CONSUMER_KEY");
        String CONSUMER_KEY = System.getenv("CONSUMER_KEY");
        String CONSUMER_SECRET = System.getenv("CONSUMER_SECRET");
        String TOKEN_KEY = System.getenv("TOKEN_KEY");
        String TOKEN_SECRET = System.getenv("TOKEN_SECRET");

        twitter.setOAuthConsumer(CONSUMER_KEY,
                CONSUMER_SECRET);
        twitter.setOAuthAccessToken(new AccessToken(
                TOKEN_KEY,
                TOKEN_SECRET));

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
        query.setCount(100);
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
