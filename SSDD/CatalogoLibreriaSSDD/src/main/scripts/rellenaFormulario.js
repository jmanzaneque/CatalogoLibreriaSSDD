var formulario = document.getElementById("formulario"),comodin = true;
formulario.addEventListener("submit", function(event){
	event.preventDefault();
	var elementos = this.elements;
	for (var i in elementos){
	    if (!elementos[i].value.length){
	        alert("Debe de completar el campo " + elementos[i].name);
	        comodin = false;
	        break;
	    }
	}
	if (comodin){
	    this.submit();
	}
	}, false);