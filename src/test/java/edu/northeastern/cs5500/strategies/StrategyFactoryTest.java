package edu.northeastern.cs5500.strategies;

import edu.northeastern.cs5500.strategies.implementations.LevenshteinDistance;
import edu.northeastern.cs5500.strategies.implementations.LCS;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Praveen Singh
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class StrategyFactoryTest{

    @Autowired
    LevenshteinDistance levenshteinDistance;

    @Autowired
    LCS lcs;

    @Autowired
    StrategyFactory strategyFactory;

    /**
     * test for getting levenshtein distance strategy when it is provided
     */
    @Test
    public void getLevenshteinDistanceStrategyShouldReturnTheExpectedStrategy(){
        Assert.assertEquals(levenshteinDistance,
                strategyFactory.getStrategyByStrategyType("LEVENSHTEIN_DISTANCE"));
    }
    
    /**
     * test for getting lcs strategy when it is provided
     */
    @Test
    public void getLCSStrategyShouldReturnTheExpectedStrategy(){
        Assert.assertEquals(lcs,
                strategyFactory.getStrategyByStrategyType("LCS"));
    }

    /**
     * test for getting levenshtein distance strategy when invalid strategy is provided
     */
    @Test
    public void getLevenshteinDistanceStrategyShouldReturnTheDefaultStrategyIfInvalidStrategyProvided(){
        Assert.assertEquals(levenshteinDistance,
                strategyFactory.getStrategyByStrategyType("INVALID"));
    }

    /**
     * test for getting levenshtein distance strategy when null is provided
     */
    @Test
    public void getLevenshteinDistanceStrategyShouldReturnDefaultStrategyWhenNullProvided(){
        Assert.assertEquals(levenshteinDistance, strategyFactory.getStrategyByStrategyType(null));
    }


}