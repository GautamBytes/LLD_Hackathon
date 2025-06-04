package com.rideshare.strategy;

/**
 * Factory to choose which MatchingStrategy to use.
 */
public class MatchingStrategyFactory {
    public static MatchingStrategy getStrategy(String name) {
        if (name.equalsIgnoreCase("nearest")) {
            return new NearestDriverStrategy();
        } else if (name.equalsIgnoreCase("highestRated")) {
            return new HighestRatedDriverStrategy();
        }
        return null;
    }
}

