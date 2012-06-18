var grid;
function clearGrid() { 
	grid = [
	[0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0],
	
	[0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0],
	
	[0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0],
	
	[0,0,0,0,0,0,0,0,0,0]
	];
}
clearGrid(); // call it here to initialize the grid

//grid[0] is a row (y)
//grid[v][0] is a column (x)
//remember to use grid[y][x]!! (or grid[row][col])

function getPieceAt(x,y) {
	return grid[y][x];
}

/**
 * Moves the piece down and returns true, or returns false if below space is occupied 
 * @param p
 */
function moveDown(p) {
	if(canPlaceHere(p.coords,p.x,p.y + 1)) {
		p.y = p.y + 1;
		return true;
	} else return false;
}

function fallDown(p) {
	while(moveDown(p));
}

function canPlaceHere(coords,px,py) {
	for(var i=0; i<4; i++) {
		grix = px + coords[i].x;
		griy = py + coords[i].y;
		if(griy < 0 || griy >= grid.length) { // vertical bounds exceeded (top or bottom)
			return false;
		} else if(grix < 0 || grix >= grid[griy].length) { // horizontal bounds exceeded (left or right)
			return false;
		} else if(grid[griy][grix] != 0) { // space occupied
			return false;
		}
	}
	return true;
}

function addToGrid(p) {
	for(var i=0; i<4; i++) {
		grix = p.x + p.coords[i].x;
		griy = p.y + p.coords[i].y;
		grid[griy][grix] = p.type;
	}
	// Check if lines need to be cleared
	clearLines();
}

function clearLines() {
	var shift = 0;
	for(var row=grid.length - 1; row >= 0; row--) {
		var isFull = true;
		for(var col=0; col < grid[row].length; col++) {
			if(grid[row][col] == 0 && grid[row][col] == "0") {
				isFull = false;
			}
		}
		if(shift > 0 && !isFull) {
			for(var col=0; col < grid[row].length; col++) {
				grid[row+shift][col] = grid[row][col];
			}
		}
		if(isFull) {
			shift++;
			for(var col=0; col < grid[row].length; col++) {
				grid[row][col] = 0;
			}			
		}		
	}
}