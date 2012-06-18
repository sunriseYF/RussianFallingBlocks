/**
 * Based heavily off this implementation: http://www.dailycoding.com/Posts/object_oriented_programming_with_javascript__timer_class.aspx
 */
var Timer = function(intrv) {
	this.Interval = intrv; // Number of milliseconds between ticks. Consider passing in to object?
	this.Enabled = new Boolean(false);; // Will be true when Timer.Start() is called
	this.Tick; // Set to a function 
	this.timerId = 0;
	var thisObject;
	this.Start = function() {
		this.Enabled = true;
        thisObject = this;
        if (thisObject.Enabled) {
            thisObject.timerId = setInterval(
            	function() {
            		thisObject.Tick(); 
            	},
            	thisObject.Interval
            );
        }
	};
	this.Stop = function() {
		thisObject.Enabled = false;
        clearInterval(thisObject.timerId);
	};
};