package com.fiap.util;

import java.util.List;
import java.util.stream.Collectors;

import twitter4j.Status;

public class TwitterCollection {

	public static long getNumOfRetweets(List<Status> tweets){
		return tweets.stream().filter(tweet -> tweet.isRetweet()).count();		
	}
	
	public static long getNumOfTweets(List<Status> tweets){
		return tweets.stream().filter(tweet -> !tweet.isRetweet()).count();
	}
	
	public static long getNumOfFavorites(List<Status> tweets){
		return tweets.stream().filter(tweet -> tweet.getFavoriteCount() > 0).count();
	}
	
	public static List<Status> getTweetsSortedByAuthor(List<Status> tweets){
	
		return tweets.stream().sorted((twitt1, twitt2) -> {
			return twitt1.getUser().getName().compareToIgnoreCase(twitt2.getUser().getName());
		}).collect(Collectors.toList());

	}
	
	public static List<Status> getTweetsSortedByDate(List<Status> tweets){
		
		return tweets.stream().sorted((tweet1, tweet2) -> {
			if(tweet1.getCreatedAt().after(tweet2.getCreatedAt())){
				return 1;
			}else if(tweet1.getCreatedAt().equals(tweet2.getCreatedAt())){
				return 0;
			} else {
				return -1;
			}
		}).collect(Collectors.toList());
	}

}
