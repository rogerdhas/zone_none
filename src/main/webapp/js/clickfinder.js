var start;
var load = Date.now();
var lastFocus;
var offset = new Date().getTimezoneOffset();
var uID;
var zoneNoneUrlsFind = "http://zone-none.cfapps.io/s.do"
var zoneNoneUrlsClose = "http://zone-none.cfapps.io/l.do";
jQuery.cookie = function(name, value, options) {

	if (typeof value != 'undefined') { // name and value given, set cookie
		options = options || {};
		if (value === null) {
			value = '';
			options = $.extend({}, options);
			options.expires = -1;
		}
		var expires = '';
		if (options.expires
				&& (typeof options.expires == 'number' || options.expires.toUTCString)) {
			var date;
			if (typeof options.expires == 'number') {
				date = new Date();
				date.setTime(date.getTime()
						+ (options.expires * 24 * 60 * 60 * 1000));
			} else {
				date = options.expires;
			}
			expires = '; expires=' + date.toUTCString(); // use expires
			// attribute,
			// max-age is not
			// supported by IE
		}

		var path = options.path ? '; path=' + (options.path) : '';
		var domain = options.domain ? '; domain=' + (options.domain) : '';
		var secure = options.secure ? '; secure' : '';
		document.cookie = [ name, '=', encodeURIComponent(value), expires,
				path, domain, secure ].join('');
	} else { // only name given, get cookie
		var cookieValue = null;
		if (document.cookie && document.cookie != '') {
			var cookies = document.cookie.split(';');
			for (var i = 0; i < cookies.length; i++) {
				var cookie = jQuery.trim(cookies[i]);
				// Does this cookie string begin with the name we want?
				if (cookie.substring(0, name.length + 1) == (name + '=')) {
					cookieValue = decodeURIComponent(cookie
							.substring(name.length + 1));
					break;
				}
			}
		}
		return cookieValue;
	}
};

function findUser() {
	uID = $.cookie("zone");
	if (uID == null || uID == '') {
		$.ajax({
			url : zoneNoneUrlsFind,
	        type: 'GET',
			cache : false,
			data : {
				'o' : offset,
				'h' : screen.width,
				'w' : screen.height
			},
			async : true,
			success : function(r) {
				setCookie(r);
				uID = r;
			},
			failure : function(r){
				alert(r);
			}
		})
	}
}
function setCookie(u) {
	$.cookie("zone", u, {
		expires : 30,
		path : '/',
		domain : document.domain,
		secure : true
	});
}

$(document).ready(function() {

	start = Date.now();
	lastFocus = start;
	
	window.onunload=function(){unloadF();};
	
	findUser();
})

function unloadF()
{
		end = Date.now();
		$.ajax({
			url : zoneNoneUrlsClose,
	        type: 'GET',
			cache : false,
			data : {
				'd' : (end - lastFocus) + timeFocus,
				'r' : escape(document.referrer),
				't' : escape(document.title),
				'u' : uID,
				'l' : load-start
			},
			async : false
		})
}
var timeFocus = 0;

$(window).on("blur focus", function(e) {

	// reduce double fire issues
	switch (e.type) {
	case "blur":
		timeFocus += Date.now() - lastFocus;
		break;
	case "focus":
		break;

	lastFocus = Date.now();
}

})

$(document).ready(function() {
	$("a").on({
		click : function() {
			//alert('clicked link' + this.href);
		}
	});
	$("img").on({
		click : function() {
			//alert('clicked src' + this.src);
		}
	});
});