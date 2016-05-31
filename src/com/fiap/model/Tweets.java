package com.fiap.model;

import java.time.LocalDate;
import java.util.List;

import com.fiap.util.TwitterCollection;

import twitter4j.Status;

public class Tweets {

	private LocalDate date;
	private List<Status> tweets;
	

	public Tweets(LocalDate date, List<Status> tweets) {
		this.date = date;
		this.tweets = tweets;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public List<Status> getTweets() {
		return tweets;
	}

	public void setTweets(List<Status> tweets) {
		this.tweets = tweets;
	}

	public long getRetweetsNumber() {
		return TwitterCollection.getNumOfRetweets(tweets);
	}


	public long getTweetsNumber() {
		return TwitterCollection.getNumOfTweets(tweets);
	}

	public long getFavoriteNumber() {
		return TwitterCollection.getNumOfFavorites(tweets);
	}

	public boolean isEmpty(){
		return tweets.isEmpty();
	}
}
