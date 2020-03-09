package com.knoldus;

import twitter4j.TwitterException;

import java.time.Instant;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class TwitterOpearationExample {

    public static void main(String[] args) throws TwitterException, ExecutionException, InterruptedException {


        TwitterOperations operation = new TwitterOperations();

        //1. Latest Post (Newer to Older) with limit

        System.out.println(operation.getLatestPosts("ElonMusk", 6, 2).get());
        // operation.getLatestPosts("ElonMusk",6).thenAccept(System.out::println);

        // 2.Older to Newer with limit and offset values

        System.out.println(operation.getOldPosts("ElonMusk", 6, 2).get());
        // operation.getLatestPosts("ElonMusk",6).thenAccept(System.out::println);

        //3.Number of Retweets (Higher to Lower)

        System.out.println(operation.listOfNoOfRetweetsFinder("Happy").get());

        // Number of Likes (Higher to Lower)

        System.out.println(operation.listOfNoOfLikesFinder("Happy").get());

        // Get the List and number of tweets for an entered date.

        System.out.println(operation.listOfPostsOfDateFinder("Happy", Date.from(Instant.now())).get());

        //Get the number of likes on a particular keyword in a time interval of 15 mins.

        System.out.println(operation.totalNoOfLikesFinder("ElonMusk").get());

       


    }
}

