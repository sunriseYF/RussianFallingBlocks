<?php include("../templates.php");
?>
<html>
<head>
<title>TP - Russian Falling Blocks</title>
<meta name="viewport" content="user-scalable=no, width=device-width" />
<link type="text/css" rel="stylesheet" href="/tproc/styles/tprocdesktop.css" media="screen and (min-width: 481px)" />
<link type="text/css" rel="stylesheet" href="/tproc/styles/tprocmobile.css" media="only screen and (max-width: 480px)" />
<link type="text/css" rel="stylesheet" href="/tproc/styles/tproc.css" />
<link rel="shortcut icon" type="image/png" href="/tproc/images/icon.png" />
<script type="text/javascript" src="timer.js"></script>
<script type="text/javascript" src="pieces.js"></script>
<script type="text/javascript" src="grid.js"></script>
<script type="text/javascript" src="draw.js"></script>
<script type="text/javascript" src="game.js"></script>
</head>
<body onload="javascript:init();">
<div id="container">
	<div id="banner"><?php print Templates::getBanner(); ?></div>
	<div id="content">
		<h2>Russian Falling Blocks (Work In Progress)</h2>
		<canvas id="rfb" width="270" height="420">
			This page uses HTML5 canvas and Javascript. Your browser does 
			not support one of these or does not have them enabled.
		</canvas>
	</div>	
	<div id="footer"><?php print Templates::getFooter(); ?></div>
</div> <!-- end container-->
</body>
</html>
