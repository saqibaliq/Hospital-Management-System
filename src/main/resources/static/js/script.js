console.log("JS Working..");
function toggle(oInput) {
	var aInputs = document.getElementsByName('cb');
	for (var i = 0; i < aInputs.length; i++) {
		if (aInputs[i] != oInput) {
			aInputs[i].checked = oInput.checked;
		}
		console.log(aInputs[i]);

	}
}

function getCheckboxValue() {

	var l1 = document.getElementsByName("cb");
	console.log(l1);
	/*var res = " ";
	if (l1.checked == true) {
		return document.getElementById("result").innerHTML = "You have not selected anything";
	} else {

		var pl1 = document.getElementsByClassName("l1").value;
		res = pl1 + ",";
		console.log(pl1);
	}*/
}