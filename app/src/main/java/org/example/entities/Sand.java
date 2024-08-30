package org.example.entities;

import java.util.Random;

public class Sand extends Point {

	private Random _rand = new Random();
	
	public Sand(float positionX, float positionY) {
		super(positionX, positionY, 1F, 1F, 1F);
		//this._posX = (positionX * _rand.nextFloat(0.3f));
		//this._posY = (positionY * _rand.nextFloat(0.3f));
	}
	
	public void update() {

		super.update();
	}
}
