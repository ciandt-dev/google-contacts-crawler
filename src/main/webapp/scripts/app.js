var app = {
		
	AUTHORIZATION_URL: "/authorize",
		
	bindEvents: function() {
		$("#authorize-button").bind("click", this.authorize);
	},
	
	authorize: function() {
		var w = 500;
		var h = 600;
		var left = (screen.width/2)-(w/2);
		var top = (screen.height/2)-(h/2);
		var popup = window.open(app.AUTHORIZATION_URL, "auth_window", 'toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=no, copyhistory=no, width='+w+', height='+h+', top='+top+', left='+left);
	}
};