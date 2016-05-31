package com.fiap.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fiap.api.TweetSearch;
import com.fiap.api.TwitterService;
import com.fiap.io.TweetWriter;
import com.fiap.model.Tweets;
import com.fiap.util.TwitterCollection;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class TweetController {

	private List<Status> tweets = new ArrayList<>();
	private Twitter twitter;
	private List<Tweets> tweetByDay;
	 
	/*MY TWiTTER
	 * Consumer Key (API Key)	AqXyVyDaSn3VwtiP77XHB1Iuz
	 * Consumer Secret (API Secret)	gtux10lpCtsaP3G1DInn1lKBCY6RUTeKaiMYIKLsEsCmrVZ6mN
	 * 
	 * Access Token	36113252-AlmbatgQcSURzD8yOaH7KcARukORmhJgLZlD5snR1
	 * Access Token Secret	x9mRi8huoeNoz3aYQ0FxKnCH4wzx1RxoY1BxUK72E1OEh
	 */
	
	/*FIAP
	 * Consumer Key:	3QRfILThjzQslxSRLps8u3tDv
	 * Consumer Secret:	exr4sElb7yLxXomoqNyo9aDLc29FoTnnAfia8c34Nzv1V4p5ED
	 * 
	 * Access Token:	729051384688066560-mR8PP2fslrG0fRCen6QYWhQ1gpTFcV2
	 * Access Token Secret:	iv8Nl75gAw0smCSMdVmlwGXViMMpD6VeEgNeAn7btFqIs
	 */
	public void searchBy(String word){
		TwitterService twitterService = TwitterService.getConnection(
				"3QRfILThjzQslxSRLps8u3tDv", "exr4sElb7yLxXomoqNyo9aDLc29FoTnnAfia8c34Nzv1V4p5ED");
		try {
			twitter = twitterService.userAuthentication(
					"729051384688066560-mR8PP2fslrG0fRCen6QYWhQ1gpTFcV2", "iv8Nl75gAw0smCSMdVmlwGXViMMpD6VeEgNeAn7btFqIs");
		
			TweetSearch tweetSearch = new TweetSearch(twitter);
			tweets = tweetSearch.getTweetsFromTheLast7days(word);
			TweetWriter.save(tweets,null);
			
			tweetByDay = tweetSearch.splitIntoDays();
			for(Tweets tweet : tweetByDay){
				TweetWriter.save(tweet.getTweets(), new File("Tweets/tweet_by_day_"+tweet.getDate()+".txt"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public long getTotalNumOfTweets(){
		return TwitterCollection.getNumOfTweets(tweets);
	}

	public long getTotalNumOfRetweets(){
		return TwitterCollection.getNumOfRetweets(tweets);
	}

	public long getTotalNumOfFavorites(){
		return TwitterCollection.getNumOfFavorites(tweets);

	}
	
	public List<Tweets> getTweetsSortedByDay(){
		return tweetByDay;

	}
	
	public Map<String, String> getFirstAndLastAuthors(){
		
		List<Status> tweetsSortedByAuthor = TwitterCollection.getTweetsSortedByAuthor(tweets);
		String firstNameFromList = tweetsSortedByAuthor.get(0).getUser().getName();
		String lastNameFromList = tweetsSortedByAuthor.get(tweets.size()-1).getUser().getName();
		
		TweetWriter.save(tweetsSortedByAuthor, new File("Tweets/Tweets_Sorted_ByAuthor.txt"));
		
		Map<String, String> firstAndLast = new HashMap<>();
		firstAndLast.put("First Author", firstNameFromList);
		firstAndLast.put("Last Author", lastNameFromList);
		return firstAndLast;

	}
	
	public Map<String, String> getFirstAndLastDate(){
		
		List<Status> tweetsSortedByDate = TwitterCollection.getTweetsSortedByDate(tweets);
		String firstDateFromList = tweetsSortedByDate.get(tweets.size()-1).getCreatedAt().toString();
		String lastDateFromList = tweetsSortedByDate.get(0).getCreatedAt().toString();

		TweetWriter.save(tweetsSortedByDate, new File("Tweets/Tweets_Sorted_ByDate.txt"));
		
		Map<String, String> firstAndLast = new HashMap<>();
		firstAndLast.put("First date", firstDateFromList);
		firstAndLast.put("Last date", lastDateFromList);
		return firstAndLast;
		
	}
	
	public void generateTweet(String text){
		try {
			Status tweet = twitter.updateStatus(text);
			System.out.println(tweet.getText());
		} catch (TwitterException e) {
			System.out.println("Sorry, it wasn't possible to post this tweet! Take another shot later!");
			e.printStackTrace();
		}
	}
	
}
//"From the last week: Tweets:"+numOfTweets
//+", Retweets: "+numOfRetweets+", Favourited: "+numOfFavourites+
//". Sort by name: first: "+firstNameFromList+", Last: "+lastNameFromList+
//". Sort by date"
