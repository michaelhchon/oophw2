import java.util.ArrayList;
import java.util.List;

// Composite pattern
class User implements UserViewObserver {
    private String userID;
    private List<User> followings;
    private List<String> newsFeed;
    private long creationTime;
    private long lastUpdateTime;

    public User(String userID, long time) {
        this.userID = userID;
        this.followings = new ArrayList<>();
        this.newsFeed = new ArrayList<>();
        this.creationTime = time;
        this.lastUpdateTime = System.currentTimeMillis();
    }
    
    public long getUpdateTime() {
    	return lastUpdateTime;
    }
    
    public long getTime() {
    	return creationTime;
    }

    public String getUserID() {
        return userID;
    }

    public List<User> getFollowings() {
        return followings;
    }

    public List<String> getNewsFeed() {
        return newsFeed;
    }

    public void followUser(User user) {
        followings.add(user);
        //user.addObserver(this);
    }

    public void postTweet(String message) {
        newsFeed.add(message);
        notifyFollowers(message);
    }

    private void notifyFollowers(String message) {
        for (User follower : followings) {
            follower.updateNewsFeed(message);
        }
    }

    @Override
    public void updateNewsFeed(String message) {
        newsFeed.add(message);
    }

    @Override
    public String toString() {
        return userID;
    }
}
