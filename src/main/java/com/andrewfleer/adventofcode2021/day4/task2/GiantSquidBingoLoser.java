package com.andrewfleer.adventofcode2021.day4.task2;

import com.andrewfleer.adventofcode2021.day4.BingoBoard;
import com.andrewfleer.adventofcode2021.day4.BingoSquare;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
public class GiantSquidBingoLoser {

    @Autowired
    private ResourceLoader resourceLoader;

    public int doTask() {
        try {
            String[] bingoNumbers = new String[0];
            Resource resource = resourceLoader.getResource("classpath:day4Input/task.txt");
            File inputFile = resource.getFile();
            Scanner scanner = new Scanner(inputFile);
            if (scanner.hasNextLine()) {
                String numbers = scanner.nextLine();
                bingoNumbers = numbers.split(",");
            }
            String blankLine = scanner.nextLine();
            List<String> boardList = new ArrayList<>();
            List<BingoBoard> bingoBoards = new ArrayList<>();
            while (scanner.hasNextLine()) {
                String nextLine = scanner.nextLine();

                if (StringUtils.isEmpty(nextLine)) {
                    BingoBoard bingoBoard = createBingoBoard(boardList);
                    bingoBoards.add(bingoBoard);
                    boardList.clear();
                } else {
                    boardList.add(nextLine);
                }
            }

            if (!CollectionUtils.isEmpty(boardList)) {
                BingoBoard bingoBoard = createBingoBoard(boardList);
                bingoBoards.add(bingoBoard);
            }

            return playBingo(bingoNumbers, bingoBoards);

        } catch (FileNotFoundException fnfe) {
            System.out.println("Can't find file");
            fnfe.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return 0;
    }

    private int playBingo(String[] bingoNumbers, List<BingoBoard> bingoBoards) {
        for (int i = 0; i < bingoNumbers.length; i++) {
            int number = Integer.parseInt(bingoNumbers[i]);

            markNumbers(bingoBoards, number);

            if (i > 3) {
                List<BingoBoard> winningBoards = findWinningBoards(bingoBoards);
                if (!CollectionUtils.isEmpty(winningBoards)) {
                    //return calculateScore(winningBoard, number);
                    if (bingoBoards.size() > 1)
                        bingoBoards.removeAll(winningBoards);
                    else
                        return calculateScore(bingoBoards.get(0), number);
                }
            }
        }

        return 0;
    }

    private int calculateScore(BingoBoard winningBoard, int lastNumberCalled) {
        return winningBoard.getScore() * lastNumberCalled;
    }

    private List<BingoBoard> findWinningBoards(List<BingoBoard> bingoBoards) {
        List<BingoBoard> winningBoards = new ArrayList<>();
        for (BingoBoard board : bingoBoards) {
            List<Integer> winningColumns = new ArrayList<>();
            List<Integer> winningRows = new ArrayList<>();
            for (int i = 0; i < board.getBoard()[0].length; i++) {
                winningColumns.add(i);
                winningRows.add(i);
            }

            for (int row = 0; row < board.getBoard().length; row++) {
                for (int col = 0; col < board.getBoard()[row].length; col++) {
                    if (!board.getBoard()[row][col].isMarked()) {
                        if (winningColumns.contains(col))
                            winningColumns.remove((Integer) col);

                        if (winningRows.contains(row))
                            winningRows.remove((Integer) row);
                    }
                }
            }

            if (!CollectionUtils.isEmpty(winningColumns) || !CollectionUtils.isEmpty(winningRows)) {
                //winningBoard = board;
                winningBoards.add(board);
            }
        }

        return winningBoards;
    }

    private void markNumbers(List<BingoBoard> bingoBoards, int number) {
        for (BingoBoard bingoBoard : bingoBoards) {
            for (int i = 0; i < bingoBoard.getBoard().length; i++) {
                for (int j = 0; j < bingoBoard.getBoard()[i].length; j++) {
                    if (bingoBoard.getBoard()[i][j].getValue() == number) {
                        bingoBoard.getBoard()[i][j].setMarked(true);
                        bingoBoard.setScore(bingoBoard.getScore() - bingoBoard.getBoard()[i][j].getValue());
                    }
                }
            }
        }
    }

    private BingoBoard createBingoBoard(List<String> boardList) {
        BingoBoard bingoBoard = new BingoBoard();
        BingoSquare[][] board = new BingoSquare[boardList.size()][];
        int score = 0;
        int rowIndex = 0;
        for (String boardRow : boardList) {
            String[] stringArray = boardRow.stripLeading()
                                           .replaceAll("  ", " ")
                                           .split(" ");
            BingoSquare[] squareRow = new BingoSquare[stringArray.length];
            for (int i = 0; i < stringArray.length; i++) {
                BingoSquare square = new BingoSquare(Integer.parseInt(stringArray[i]), false);
                squareRow[i] = square;
                score += square.getValue();
            }

            board[rowIndex] = squareRow;
            rowIndex++;
        }

        bingoBoard.setBoard(board);
        bingoBoard.setScore(score);

        return bingoBoard;
    }
}
