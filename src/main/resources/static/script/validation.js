function isValidUsername(str) {
	var usernamePattern = new RegExp('^[A-Za-z0-9_-]{3,30}$');
    return usernamePattern.test(str);
}

function isValidPassword(str) {
	var passwordPattern = new RegExp('^[A-Za-z0-9!@#$%^&*()_]{3,30}$');
    return passwordPattern.test(str);
}

function getCurrentDate(){
	var curDate = new Date();
	var mnth = curDate.getMonth() + 1;
	var currentDate = curDate.getDate() + "/" +  mnth + "/" + curDate.getFullYear();
	return currentDate;
} 


