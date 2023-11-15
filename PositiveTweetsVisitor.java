class PositiveTweetsVisitor implements UserVisitor {
    private int positiveTweets = 0;

    @Override
    public void visit(User user) {
        // Check for positive words in each tweet
        for (String tweet : user.getNewsFeed()) {
            if (containsPositiveWords(tweet)) {
                positiveTweets++;
            }
        }
    }

    //@Override
    public void visit(UserGroup group) {
        // Visit each user in the group
        for (User user : group.getUsers()) {
            //user.accept(this);
        }
    }

    public int getPositiveTweets() {
        return positiveTweets;
    }

    private boolean containsPositiveWords(String tweet) {
    	if (tweet.trim().equals("good") || tweet.trim().equals("great") 
    			|| tweet.trim().equals("excellent"));
        	return true;
    }
}
