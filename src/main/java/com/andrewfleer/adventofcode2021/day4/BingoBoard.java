package com.andrewfleer.adventofcode2021.day4;

import lombok.Data;

@Data
public class BingoBoard {
    private BingoSquare[][] board;
    private int score;
}
