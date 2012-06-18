/********************************
 *  Constants 
 ********************************/
// Positioning
var offSet = 10;
var tileSize = 25;
var intrvl = 300;
var gridWidth = grid[0].length;
var gridHeight = grid.length; // 16,
var cWidth = gridWidth * tileSize + 2 * offSet; // 10 * 25 + 2 * 10 = 270
var cHeight = gridHeight * tileSize + 2 * offSet; // 16 * 25 + 2 * 10 = 420

//Game state
var linesCleared = 0;
var timer = new Timer(intrvl);
var p = -1; // The piece, set to -1 when no piece is set.
var ctx = -1; // The canvas 2d context
var state = {
	paused: false,
	started: false
};

function init() {
	var canvas = document.getElementById("rfb");
	if(canvas.getContext) {
		ctx = canvas.getContext("2d");
		
		document.onkeydown = function(evt) { go(); };
		document.onclick = function(evt) { go(); };
		
		drawTitleScreen(ctx);
		
		clearGrid();
	}
}

function go() {
	state.started = true;
	var canvas = document.getElementById("rfb");
	if(canvas.getContext) {
		ctx = canvas.getContext("2d");

		document.onkeydown = function(evt) { handleKey(evt); };
		document.onclick = function(evt) { handleClick(evt); };
		
		//var timer = new Timer(intrvl);
		timer.Tick = function() { doLoop(ctx);};
		timer.Start();
	}
}

function gameOver(ctx) {
	timer.Stop();
	drawGameOver(ctx);
	document.onkeydown = function(evt) { init(); };
	document.onclick = function(evt) { init(); };
	state.started = false;
}

function playPause() {
	if(state.paused){
		state.paused = false;
		timer.Start();		
	} else {
		state.paused = true;
		timer.Stop();
	}
}

function getPiece() {
	p = getRandomPiece(); // sets coords
	p.y = 0 - getMinY(p.coords);
	p.x = 2 + Math.floor(Math.random() * 6); //(2-7)
}

function doLoop(ctx) {
	//Calculate new positions, obtain new pieces, then draw the result
	if(p == -1) { // get a new piece
		getPiece();
	} else { // moveDown
		if(!moveDown(p)) {
			addToGrid(p);
			getPiece();
		}
	}
	draw(ctx, state);
	if(p != -1 && !canPlaceHere(p.coords,p.x,p.y)) { // GAME OVER!
		gameOver(ctx);
	}
}

function handleKey(evt) {
	console.log("Key code: "+evt.keyCode);
	var kc = evt.keyCode;
	if(!state.paused) {
		switch(kc) {
		// try left
		case 37: // left arrow
		case 65: // A
			if(canPlaceHere(p.coords,p.x - 1,p.y)){
				p.x = p.x - 1;
				draw(ctx,state);
			}
			break;
			
		// try right
		case 39: // right arrow
		case 68: // D
			if(canPlaceHere(p.coords,p.x + 1,p.y)){
				p.x = p.x + 1;
				draw(ctx,state);
			}
			break;
			
		// rotate left
		case 38: // up arrow
		case 87: // W
			p.rotateLeft();
			draw(ctx,state);
			break;
			
		// fall down
		case 32: // space
			fallDown(p);
			draw(ctx,state);
			break;
	
		// drop down
		case 40: // down arrow
		case 83: // S
			moveDown(p);
			draw(ctx,state);
			break;
			
		// pause
		case 80: // P
			playPause();
			break;
		default: // do nothing
			break;
		}
	} else if(kc == 80) {
		playPause();
	}
}
function handleClick(evt) {
	// based on position of "click", move piece left or right, drop down, or if up/on, rotate (left?).
}