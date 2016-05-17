IAN = new Object();

	IAN.ajax = new Object();

		function ajaxRequestDefault() {
			var def = {
					url: null,
					dataType: 'JSON',
					contentType: "application/json; charset=UTF-8",
					type: 'POST',
					success: function(){
						
					},
					error: function(rest){
						alert("error = " + rest.statusText);
					}
			};
					
					return def;
		}

		function verifyObjectData(cfg) {
			if(cfg.data) {
				if(isObject(cfg.data)) {
					cfg.data = JSON.stringify(cfg.data);
				}
			}
			
			return cfg;
		}


		function isObject(o) {
			return $.isArray(o) | $.isPlainObject(o) | $.isFunction(o);
			
		};

		IAN.ajax.post = function(cfg) {
			
			var def = new ajaxRequestDefault();
			cfg = verifyObjectData(cfg);
			
			var config = $.extend(def, cfg);
			$.ajax(config);
		};

		IAN.ajax.get = function(cfg) {
			
			var def = new ajaxRequestDefault();
			cfg.type = "GET",
			cfg = verifyObjectData(cfg);
			var config = $.extend(def, cfg);
			$.ajax(config);
		};
