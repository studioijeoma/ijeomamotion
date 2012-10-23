var ijeoma = ijeoma || {};
ijeoma.motion = ijeoma.motion || {};
ijeoma.motion.tween = ijeoma.motion.tween || {};

// (function() {
Parallel = function Parallel(name, children) {
	MotionController.call(this, name, children) 
}

ijeoma.motion.tween.Parallel = Parallel;

Parallel.prototype = new MotionController();
Parallel.prototype.constructor = Parallel;

Parallel.prototype.setup = function() {}

Parallel.prototype.setupEvents = function() {}

Parallel.prototype.dispatchMotionStartedEvent = function() {
	console.log('dispatchMotionStartedEvent');
}

Parallel.prototype.dispatchMotionEndedEvent = function() {
	console.log('dispatchMotionEndedEvent');
}

Parallel.prototype.dispatchMotionChangedEvent = function() {
	console.log('dispatchMotionChangedEvent');
}

Parallel.prototype.dispatchMotionRepeatedEvent = function() {
	console.log('dispatchMotionRepeatedEvent');
}

Parallel.prototype.toString = function() {
	return("TweenParallel[tweens: {" + tweens + "}]");
}
// })