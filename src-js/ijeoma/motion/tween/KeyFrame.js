var ijeoma = ijeoma || {};
ijeoma.motion = ijeoma.motion || {};
ijeoma.motion.tween = ijeoma.motion.tween || {};

// (function() { 
var KeyFrame = function KeyFrame(name, time, children) {
		MotionController.call(this, name, children)
		this.delay(time);
	}

ijeoma.motion.KeyFrame = KeyFrame;

KeyFrame.prototype = new MotionController();
KeyFrame.prototype.constructor = KeyFrame;

KeyFrame.prototype.dispatchMotionStartedEvent = function() {
	console.log('dispatchMotionStartedEvent');
}

KeyFrame.prototype.dispatchMotionEndedEvent = function() {
	console.log('dispatchMotionEndedEvent');
}

KeyFrame.prototype.dispatchMotionChangedEvent = function() {
	console.log('dispatchMotionChangedEvent');
}

KeyFrame.prototype.dispatchMotionRepeatedEvent = function() {
	console.log('dispatchMotionRepeatedEvent');
}
// })