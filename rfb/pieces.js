var pieceTypes = ["S","Z","L","R","Q","I","T"];
var pieceCoords = {
	"S" : [{x: 0, y:-1},
	       {x: 0, y: 0},{x: 1, y: 0},
	       			    {x: 1, y: 1}],
    "Z" :              [{x: 1, y:-1},
	       {x: 0, y: 0},{x: 1, y: 0},
	       {x: 0, y: 1}],
    "L" : [{x: 0, y:-2},
           {x: 0, y:-1},
	       {x: 0, y: 0},{x: 1, y: 0}],
	"R" : [{x: 0, y: 0},{x: 1, y: 0},
	       {x: 0, y: 1},
	       {x: 0, y: 2}],
	"Q" : [{x: 0, y: 0},{x: 1, y: 0},
	       {x: 0, y: 1},{x: 1, y: 1}],
	"I" : [{x: 0, y:-2},
           {x: 0, y:-1},
           {x: 0, y: 0},
           {x: 0, y: 1}],
    "T"  :   [{x: 0, y:-1},
 {x:-1, y: 0},{x: 0, y: 0},{x: 1, y: 0}]		   
};
var pieceColors = {
	"S" : "rgb(0,0,175)",
	"Z" : "rgb(0,255,0)",
	"L" : "rgb(255,255,0)",
	"R" : "rgb(255,0,255)",
	"Q" : "rgb(0,255,255)",
	"I" : "rgb(255,0,0)",
	"T" : "rgb(175,175,175)"
};
var startY = { // Minimum Y value so it can be placed on the grid completely
	"S" : 1,
	"Z" : 1,
	"L" : 2,
	"R" : 0,
	"Q" : 0,
	"I" : 2,
	"T" : 1
};
function getMinY(crds) {
	miny = 0;
	for(var i = 0; i < 4; i++) {
		if(crds[i].y < miny) miny = crds[i].y;
	}
	return miny;
}

function getRandomPiece() {	
	pc = new Piece();
	pc.type = pieceTypes[Math.floor(Math.random() * 7)];
	pc.color = pieceColors[pc.type];
	pc.coords = pieceCoords[pc.type];
	rotations = Math.floor(Math.random() * 4);
	while(rotations-- > 0) {
		pc.rotateLeft(true);
	}
	return pc;
}
var Piece = function() {
	this.type;
	this.color;
	this.x;
	this.y;
	this.coords;
	this.rotateLeft = function(force) {
		force = (typeof force =='undefined')?false:force;
		test = [{x:0,y:0},{x:0,y:0},{x:0,y:0},{x:0,y:0}];
		for(var i = 0; i < 4; i++) {
			test[i].x = -this.coords[i].y;
			test[i].y = this.coords[i].x;
		}
		if(force || canPlaceHere(test,this.x,this.y)) {
			for(var i = 0; i < 4; i++) {
				this.coords[i].x = test[i].x;
				this.coords[i].y = test[i].y;
			}
		}
	};
	this.rotateRight = function(force) {
		force = (typeof force =='undefined')?false:force;
		test = [{x:0,y:0},{x:0,y:0},{x:0,y:0},{x:0,y:0}];
		for(var i = 0; i < 4; i++) {
			test[i].x = this.coords[i].y;
			test[i].y = -this.coords[i].x;
		}
		if(force || canPlaceHere(test,this.x,this.y)) {
			for(var i = 0; i < 4; i++) {
				this.coords[i].x = test[i].x;
				this.coords[i].y = test[i].y;
			}
		}
	};
};