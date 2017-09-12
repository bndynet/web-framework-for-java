var app = angular.module('app', [ 'ngAnimate', 'ngMaterial', 'toaster' ]);

app.factory('appDialog', [
		'$mdDialog',
		'toaster',
		function($mdDialog, toaster) {
			var service = {};

			service.info = function(title, msg) {
				toaster.pop({
					type : 'info',
					title : title,
					body : msg,
					timeout : 5000
				});
			};
			service.wait = function(title, msg) {
				var id = 'wait-toastid-' + Math.random();
				toaster.pop({
					type : 'wait',
					title : title,
					body : msg,
					showCloseButton : false,
					toastId : id,
					timeout : 0
				});
				return id;
			};
			service.clearWait = function(wait) {
				toaster.clear(null, wait);
			};
			service.warning = function(title, msg) {
				toaster.pop({
					type : 'warning',
					title : title,
					body : msg,
					timeout : 0
				});
			};
			service.success = function(title, msg) {
				if (!title)
					title = 'Success';
				toaster.pop({
					type : 'success',
					title : title,
					body : msg,
					timeout : 3000
				});
			};
			service.error = function(title, msg) {
				if (!title)
					title = 'Operation Failed';
				toaster.pop({
					type : 'error',
					title : title,
					body : msg,
					timeout : 0
				});
			};

			service.alert = function(title, msg) {
				$mdDialog.alert().clickOutsideToClose(true).title(title)
						.textContent(msg).ok('OK')
			};

			service.confirm = function(title, msg, fnOK, fnCancel) {
				var confirm = $mdDialog.confirm().title(title).textContent(msg)
						.ok('OK').cancel('Cancel');

				$mdDialog.show(confirm).then(function() {
					if (fnOK)
						fnOK();
				}, function() {
					if (fnCancel)
						fnCancel();
				});
			};

			service.confirmDeletion = function(fnOK) {
				service.confirm('Delete?',
						'Are you sure you want to remove this item?', fnOK);
			};

			// TODO: need to test
			service.showWin = function(data, controller, templateUrl, fnOK,
					fnCancel) {
				$mdDialog.show({
					locals : {
						data : data
					},
					controller : controller,
					templateUrl : templateUrl,
					parent : angular.element(document.body),
					clickOutsideToClose : false
				}).then(function(result) {
					if (fnOK)
						fnOK(result);
				}, function() {
					if (fnCancel)
						fnCancel();
				});
			};

			return service;
		} ]);

angular.element(document).ready(function() {
	angular.bootstrap(document, [ 'app' ]);
});