function draw(ctx, state){
	ctx.clearRect(0,0,cWidth,cHeight);
	drawGrid(ctx);
	drawPiece(ctx);
	if(state.paused) {
		drawPaused(ctx);
	}
}
function drawGrid(ctx){
	ctx.fillStyle = "rgb(0,190,0)";
	ctx.fillRect(0, 0, cWidth, cHeight);
	for(var i = 0; i < gridWidth; i++) {
		for(var j = 0; j < gridHeight; j++){
			var pieceType = getPieceAt(i,j);
			if(pieceType == 0) ctx.fillStyle = "rgb(0,0,0)";
			else ctx.fillStyle = pieceColors[pieceType];
			ctx.fillRect(i * tileSize + offSet, j * tileSize + offSet, tileSize, tileSize); 
			ctx.strokeStyle = "rgb(0,0,0)";
			ctx.strokeRect(i * tileSize + offSet, j * tileSize + offSet, tileSize, tileSize);
		}
	}
}
function drawPiece(ctx){
	if(p != -1) {
		for(var i=0; i < 4; i++) {
			var gridPosX = p.coords[i].x + p.x;
			var gridPosY = p.coords[i].y + p.y;
			ctx.fillStyle = p.color;
			ctx.fillRect(gridPosX * tileSize + offSet, gridPosY * tileSize + offSet, tileSize, tileSize);
			ctx.strokeStyle = "rgb(0,0,0)";
			ctx.strokeRect(gridPosX * tileSize + offSet, gridPosY * tileSize + offSet, tileSize, tileSize);
		}
	}
}
function drawGameOver(ctx){
	ctx.fillStyle = "rgb(100,100,100)";
	ctx.fillRect(offSet*3, cHeight / 3, cWidth - offSet*6, 100);
	ctx.fillStyle = "rgb(0,0,255)";
	ctx.font = "28pt Arial";
	ctx.fillText("Game Over", offSet*4, cHeight / 3 + offSet + 28);
}

function drawPaused(ctx) {
	ctx.fillStyle = "rgb(100,100,100)";
	ctx.fillRect(offSet*3, cHeight / 3, cWidth - offSet*6, 100);
	ctx.fillStyle = "rgb(0,0,255)";
	ctx.font = "16pt Arial";
	ctx.fillText("Paused. Press p to continue.", offSet*4, cHeight / 3 + offSet + 28);
}

function drawTitleScreen(ctx) {
	ctx.fillStyle = "rgb(0,190,0)";
	ctx.fillRect(0, 0, cWidth, cHeight);
	ctx.fillStyle = "rgb(0,0,255)";
	ctx.font = "16pt Arial";
	ctx.fillText("Russian Falling Blocks", offSet*4, cHeight / 3 + offSet + 28);
}