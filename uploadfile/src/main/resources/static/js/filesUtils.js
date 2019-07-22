/**
 * 
 */
var host = "/download?name=";
function allFilesRequest(){
	var xhr = new XMLHttpRequest();
	var url = "/files";
	xhr.open("GET", url, true);
	xhr.setRequestHeader("Content-Type", "application/json");
	xhr.onreadystatechange = function () {
	    if (xhr.readyState === 4 && xhr.status === 200) {
	        var json = JSON.parse(xhr.responseText);
	        showFiles (json);
	    }
	};
	xhr.send();
}


function showFiles (json) {
 
    var mainDiv = document.getElementById("blockfiles");
    
    while (mainDiv.firstChild) {
    	mainDiv.removeChild(mainDiv.firstChild);
		}
    
    var table = document.createElement("table");
    table.className = "table";
    var row = 0;
    for (var i = 0; i < json.length; i++) {
      var innerRow = table.insertRow(row);
      row++;
      var cell = innerRow.insertCell(0);
      cell.innerHTML = json[i].name;
      var cell1 = innerRow.insertCell(1);
      cell1.innerHTML = json[i].size + " bytes";

      var cell2 = innerRow.insertCell(2); 
      var button = document.createElement("a");
      button.innerHTML = "Download";
      button.setAttribute("href", host + json[i].name);
      button.setAttribute("download", json[i].name);
      
      cell2.appendChild(button);

    }
    mainDiv.appendChild(table);
  }



var form = document.forms.namedItem("fileinfo");
form.addEventListener('submit', function(ev) {

  oData = new FormData(form);

  //oData.append("CustomField", "This is some extra data");

  var oReq = new XMLHttpRequest();
  oReq.open("POST", "/upload", true);
  oReq.onload = function(oEvent) {
    if (oReq.readyState === 4 && oReq.status == 200) {
    	var json = JSON.parse(oReq.responseText);
    		console.log(json);
    		if(json.error == false){
    			var span = document.getElementById("resultinfo");
    			span.innerHTML = "Your file : " + "<b>"+ json.fileName + "</b>"+ " uploaded successfully";
    			var input = document.getElementById("inputfile");
    			input.value = "";
    			allFilesRequest();
    		}
    		
    	
    } else {
      console.log("fail");
    }
  };

  oReq.send(oData);
  ev.preventDefault();
}, false);

//function downloadRequest(event){
//	var fileName = event.target.getAttribute("file");
//	var xhr = new XMLHttpRequest();
//	var url = "/download?name=" + fileName;
//	xhr.open("GET", url, true);
//	xhr.responseType = "blob";
//	xhr.onreadystatechange = function () {
//	    if (xhr.readyState === 4 && xhr.status === 200) {
//	        console.log(xhr);
//	        
//	    }
//	};
//	xhr.send();
//}