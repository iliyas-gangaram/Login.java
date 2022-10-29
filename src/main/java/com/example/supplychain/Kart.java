package com.example.supplychain;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class Kart {
        private Rectangle gamePiece;
        int xPosition;
        int yPosition;
        int currentPiecePosition;

        static long lastMovementTime;

        Kart(int tileSize, Color pieceColor){
            this.currentPiecePosition = 1;
            this.xPosition = 650;
            this.yPosition =10;

            gamePiece = new Rectangle(30,30);
            gamePiece.setFill(pieceColor);
            gamePiece.setTranslateX(this.xPosition);
            gamePiece.setTranslateY(this.yPosition);
            lastMovementTime = System.currentTimeMillis();
            Image img = new Image("C:\\Users\\dell\\Downloads\\supplychain-master\\src\\kart.png");

            gamePiece.setFill(new ImagePattern(img));

        }
public Rectangle getkart()
{
    return this.gamePiece;
}
}
