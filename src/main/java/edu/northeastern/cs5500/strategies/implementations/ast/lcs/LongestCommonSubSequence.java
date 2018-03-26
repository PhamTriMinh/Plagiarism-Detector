package edu.northeastern.cs5500.strategies.implementations.ast.lcs;

import edu.northeastern.cs5500.strategies.SimilarityStrategy;
import edu.northeastern.cs5500.strategies.implementations.ast.pythonast.AstBuilder;
import edu.northeastern.cs5500.strategies.implementations.ast.pythonast.ParserFacade;
import edu.northeastern.cs5500.strategies.implementations.ast.pythonparser.Python3Parser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

/**
 * @author namratabilurkar
 */

@Component
public class LongestCommonSubSequence implements SimilarityStrategy{
    private static final Logger LOGGER = LogManager.getLogger(LongestCommonSubSequence.class);

    private final ParserFacade parserFacade;

    private final AstBuilder astBuilder;

    @Autowired
    public LongestCommonSubSequence(ParserFacade parserFacade, AstBuilder astBuilder) {
        this.parserFacade = parserFacade;
        this.astBuilder = astBuilder;
    }

    private int[] lcsLength(String ast1, String ast2) {

        int lengthOfAst1 = ast1.length();
        int lengthOfAst2 = ast2.length();

        int[] lcsLengthAndBase = new int[2];

        int L[][] = new int[lengthOfAst1+1][lengthOfAst2+1];


        for (int i=0; i<=lengthOfAst1; i++) {
            for (int j=0; j<=lengthOfAst2; j++) {
                if (i==0 || j==0) {
                    L[i][j] = 0;
                } else if (ast1.charAt(i-1) == ast2.charAt(j-1)) {
                    L[i][j] = L[i-1][j-1] + 1;
                } else {
                    L[i][j] = Math.max(L[i-1][j], L[i][j-1]);
                }
            }
        }

        lcsLengthAndBase[0] = L[lengthOfAst1][lengthOfAst2];
        lcsLengthAndBase[1] = Math.max(lengthOfAst1, lengthOfAst2);

        return lcsLengthAndBase;
    }

    @Override
    public double calculateSimilarity(String file1, String file2) {
        Python3Parser.File_inputContext f1;
        Python3Parser.File_inputContext f2;
        try {
            f1 = parserFacade.parse(new File(file1));
            f2 = parserFacade.parse(new File(file2));

            int[] lcsValues = lcsLength(astBuilder.build(f1), astBuilder.build(f2));
            return (((double)lcsValues[0] / lcsValues[1]) * 100);
        } catch (IOException e) {
            LOGGER.error("Failed to get Similarity for input file {} and {}", file1, file2);
        }
        return 0.0;
    }

}