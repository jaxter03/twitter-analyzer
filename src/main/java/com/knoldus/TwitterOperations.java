package com.knoldus;

import twitter4j.Status;
import twitter4j.TwitterException;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TwitterOperations {
    TwitterFetcher operation;

    TwitterOperations() {
        operation = new TwitterFetcher();
    }

    /**
     * This function gives List of Posts on basis of Keyword.
     * @param keyword User provided input to fetch tweets.
     * @param limit Number of Posts.
     * @return Latest Posts list.
     * @throws TwitterException Exception.
     */
    CompletableFuture<List<String>> getLatestPosts(String keyword, int limit) throws TwitterException {

        CompletableFuture<Stream<Status>> futureTweetsStream = operation.fetch(keyword);
        return futureTweetsStream.thenApply(tweetsStream -> {
            return tweetsStream.map(tweet -> tweet.getText())
                    .limit(limit)
                    .collect(Collectors.toList());
        });

    }

    /**
     *
     * @param keyword User provided input to fetch tweets.
     * @param limit Number of Posts.
     * @return Old Post List.
     * @throws TwitterException Exception.
     */
    CompletableFuture<List<String>> getOldPosts(String keyword, long limit) throws TwitterException {

        CompletableFuture<Stream<Status>> futureTweetsStream = operation.fetch(keyword);
        CompletableFuture<List<String>> futureListTweets = futureTweetsStream.thenApply(tweetsStream -> {
            return tweetsStream.map(tweet -> tweet.getText())
                    .sorted(Comparator.reverseOrder())
                    .limit(limit)
                    .collect(Collectors.toList());

        });

        return futureListTweets;


    }

    /**
     * It finds number of retweets of given posts.
     * @param keyword User provided input to fetch tweets.
     * @return List of Retweets.
     * @throws TwitterException Exception.
     */
    CompletableFuture<List<Integer>> listOfNoOfRetweetsFinder(String keyword) throws TwitterException {

        CompletableFuture<Stream<Status>> futureTweetsStream = operation.fetch(keyword);
        CompletableFuture<List<Integer>> futureListNoOfRetweets = futureTweetsStream.thenApply(tweetsStream -> {
            return tweetsStream.map(tweet -> tweet.getRetweetCount())
                    .sorted(Comparator.reverseOrder())
                    .collect(Collectors.toList());
                }
        );

        return futureListNoOfRetweets;

    }

    /**
     * It finds List of total number of favorite on each tweet.
     * @param keyword User provided input to fetch tweets.
     * @return List of Retweets.
     * @throws TwitterException Exception.
     */
    CompletableFuture<List<Integer>> listOfNoOfLikesFinder(String keyword) throws TwitterException {

        CompletableFuture<Stream<Status>> futureTweetsStream = operation.fetch(keyword);

        return futureTweetsStream.thenApply(tweetsStream -> {
            return tweetsStream.map(tweet -> tweet.getFavoriteCount())
                    .sorted(Comparator.reverseOrder())
                    .collect(Collectors.toList());
                }
        );


    }

    /**
     *  It finds list of posts of given date
     * @param keyword User provided input to fetch tweets.
     * @param givenDate Date provided by user.
     * @return List of Posts.
     * @throws TwitterException Exception
     */
    CompletableFuture<List<Integer>> listOfPostsOfDateFinder(String keyword, Date givenDate) throws TwitterException {

        CompletableFuture<Stream<Status>> futureTweetsStream = operation.fetch(keyword);

        return futureTweetsStream.thenApply(tweetsStream -> {
            return tweetsStream.filter(tweet -> tweet.getCreatedAt() == givenDate)
                    .map(Status::getRetweetCount).collect(Collectors.toList());
                }

        );

    }

    /**
     * This function send sum of total Likes.
     * @param keyword User provided input to fetch tweets.
     * @return Sum of favorite clicks on provided list of posts.
     * @throws TwitterException Exception
     */
    CompletableFuture<Integer> totalNoOfLikesFinder(String keyword) throws TwitterException {
        CompletableFuture<Stream<Status>> futureTweetsStream = operation.fetch(keyword);

        return futureTweetsStream.thenApply(tweetsStream -> {
            return tweetsStream.map(tweet -> tweet.getFavoriteCount()).mapToInt(Integer::intValue).sum();
                }
        );


    }

}